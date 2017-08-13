package eu.bittrade.libs.steemj.communication;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSession;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.client.SslContextConfigurator;
import org.glassfish.tyrus.client.SslEngineConfigurator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import eu.bittrade.libs.steemj.base.models.SignedBlockHeader;
import eu.bittrade.libs.steemj.base.models.error.SteemError;
import eu.bittrade.libs.steemj.base.models.serializer.BooleanSerializer;
import eu.bittrade.libs.steemj.communication.dto.NotificationDTO;
import eu.bittrade.libs.steemj.communication.dto.RequestWrapperDTO;
import eu.bittrade.libs.steemj.communication.dto.ResponseWrapperDTO;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseError;
import eu.bittrade.libs.steemj.exceptions.SteemTimeoutException;
import eu.bittrade.libs.steemj.exceptions.SteemTransformationException;

/**
 * This class handles the communication to the Steem web socket API.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommunicationHandler extends Endpoint implements MessageHandler.Whole<String> {
    private static final Logger LOGGER = LogManager.getLogger(CommunicationHandler.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private CountDownLatch responseCountDownLatch = new CountDownLatch(1);
    private ClientManager client;
    private Session session;
    private String rawJsonResponse;

    /**
     * Initialize the Connection Handler.
     * 
     * @throws SteemCommunicationException
     *             If no connection to the Steem Node could be established.
     */
    public CommunicationHandler() throws SteemCommunicationException {
        this.client = ClientManager.createClient();

        if (SteemJConfig.getInstance().isSslVerificationDisabled()) {
            SslEngineConfigurator sslEngineConfigurator = new SslEngineConfigurator(new SslContextConfigurator());
            sslEngineConfigurator.setHostnameVerifier((String host, SSLSession sslSession) -> true);
            client.getProperties().put(ClientProperties.SSL_ENGINE_CONFIGURATOR, sslEngineConfigurator);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SteemJConfig.getInstance().getDateTimePattern());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(SteemJConfig.getInstance().getTimeZoneId()));

        MAPPER.setDateFormat(simpleDateFormat);
        MAPPER.setTimeZone(TimeZone.getTimeZone(SteemJConfig.getInstance().getTimeZoneId()));
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        SimpleModule simpleModule = new SimpleModule("BooleanAsString", new Version(1, 0, 0, null, null, null));
        simpleModule.addSerializer(Boolean.class, new BooleanSerializer());
        simpleModule.addSerializer(boolean.class, new BooleanSerializer());

        MAPPER.registerModule(simpleModule);

        reconnect();
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        LOGGER.info("Connection has been established.");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("Connection has been closed.", closeReason);
    }

    @Override
    public void onError(Session session, Throwable thr) {
        LOGGER.error("Connection error.", thr);
    }

    /**
     * Perform a request to the web socket API whose response will automatically
     * get transformed into the given object.
     * 
     * @param requestObject
     *            A request object that contains all needed parameters.
     * @param targetClass
     *            The target class for the transformation.
     * @param <T>
     *            The object that you want to map the result to.
     * @return The server response transformed into a list of given objects.
     * @throws SteemTimeoutException
     *             If the server was not able to answer the request in the given
     *             time (@see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout()})
     * @throws SteemCommunicationException
     *             If there is a connection problem.
     * @throws SteemTransformationException
     *             If the API Wrapper is unable to transform the JSON response
     *             into a Java object.
     * @throws SteemResponseError
     *             If the Server returned an error object.
     */
    public <T> List<T> performRequest(RequestWrapperDTO requestObject, Class<T> targetClass)
            throws SteemCommunicationException {
        if (!session.isOpen()) {
            reconnect();
        }

        try {
            sendMessageSynchronously(requestObject);

            @SuppressWarnings("unchecked")
            ResponseWrapperDTO<T> response = MAPPER.readValue(rawJsonResponse, ResponseWrapperDTO.class);

            if (response == null || "".equals(response.toString()) || response.getResult() == null
                    || "".equals(response.getResult().toString())) {
                LOGGER.debug("The response was empty. The requested node may not provid the method {}.",
                        requestObject.getApiMethod().toString());
                List<T> emptyResult = new ArrayList<>();
                emptyResult.add(null);
                return emptyResult;
            }

            if (response.getResponseId() != requestObject.getId()) {
                LOGGER.error("The request and the response id are not equal! This may cause some strange behaivior.");
            }

            // Make sure that the inner result object has the correct type.
            JavaType type = MAPPER.getTypeFactory().constructCollectionType(List.class, targetClass);

            return MAPPER.convertValue(response.getResult(), type);
        } catch (JsonParseException | JsonMappingException e) {
            LOGGER.debug("Could not parse the response. Trying to transform it to an error object.", e);

            try {
                // TODO: Find a better solution for errors in general.
                throw new SteemResponseError(MAPPER.readValue(rawJsonResponse, SteemError.class));
            } catch (IOException ex) {
                throw new SteemTransformationException("Could not transform the response into an object.", ex);
            }

        } catch (IOException | EncodeException | InterruptedException e) {
            throw new SteemCommunicationException("Could not send the message to the Steem Node.", e);
        }
    }

    /**
     * This method establishes a new connection to the web socket Server.
     * 
     * @throws SteemCommunicationException
     *             If there is a connection problem.
     */
    private void reconnect() throws SteemCommunicationException {
        try {
            session = client.connectToServer(this, SteemJConfig.getInstance().getClientEndpointConfig(),
                    SteemJConfig.getInstance().getWebsocketEndpointURI());
            session.addMessageHandler(this);
        } catch (DeploymentException | IOException e) {
            throw new SteemCommunicationException("Could not connect to the server.", e);
        }
    }

    /**
     * 
     * @param requestObject
     * @throws IOException
     * @throws EncodeException
     * @throws SteemTimeoutException
     * @throws InterruptedException
     */
    private void sendMessageSynchronously(RequestWrapperDTO requestObject)
            throws IOException, EncodeException, SteemTimeoutException, InterruptedException {
        responseCountDownLatch = new CountDownLatch(1);

        session.getBasicRemote().sendObject(requestObject);

        // Wait until we received a response from the Server.
        if (SteemJConfig.getInstance().getTimeout() == 0) {
            responseCountDownLatch.await();
        } else {
            if (!responseCountDownLatch.await(SteemJConfig.getInstance().getTimeout(), TimeUnit.MILLISECONDS)) {
                String errorMessage = "Timeout occured. The websocket server was not able to answer in "
                        + SteemJConfig.getInstance().getTimeout() + " millisecond(s).";

                LOGGER.error(errorMessage);
                throw new SteemTimeoutException(errorMessage);
            }
        }
    }

    @Override
    public void onMessage(String message) {
        // Check if we are waiting for an answer.
        if (responseCountDownLatch.getCount() > 0) {
            LOGGER.debug("Raw JSON response: {}", message);

            this.rawJsonResponse = message;

            responseCountDownLatch.countDown();
        } else {
            // A message has been send while we are not waiting for it - It can
            // be a callback.
            LOGGER.debug("Received callback: {}", message);

            try {
                NotificationDTO response = MAPPER.readValue(message, NotificationDTO.class);

                // Make sure that the inner result object is a BlockHeader.
                CallbackHub.getInstance().getCallbackByUuid(Integer.valueOf(response.getParams()[0].toString()))
                        .onNewBlock(MAPPER.convertValue(((ArrayList<Object>)(response.getParams()[1])).get(0), SignedBlockHeader.class));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                LOGGER.error("Could not parse callback {}.", e);
            }
        }
    }

    // TODO: Find a way to remove this.
    public ObjectMapper getObjectMapper() {
        return MAPPER;
    }
}
