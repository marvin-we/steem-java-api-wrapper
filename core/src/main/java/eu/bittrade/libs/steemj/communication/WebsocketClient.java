package eu.bittrade.libs.steemj.communication;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.client.SslContextConfigurator;
import org.glassfish.tyrus.client.SslEngineConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCResponse;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.exceptions.SteemTimeoutException;

/**
 * This class handles the communication to a Steem Node using the WebSocket
 * protocol.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WebsocketClient extends AbstractClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketClient.class);

    /** The client. */
    private ClientManager client;
    /** A {@link CountDownLatch} used to indicate that a message is expected. */
    private CountDownLatch responseCountDownLatch;
    /** The current session. */
    private Session session;
    /**
     * The {@link WebsocketEndpoint} instance that will handle the incoming
     * messages.
     */
    private WebsocketEndpoint websocketEndpoint;
    /** The endpoint this client instance is currently connected to. */
    private URI currentEndpointUri;

    /**
     * Initialize a new Websocket Client.
     */
    public WebsocketClient() {
        // Initialize fields.
        this.client = ClientManager.createClient();

        this.client.setDefaultMaxSessionIdleTimeout(SteemJConfig.getInstance().getIdleTimeout());
        this.client.getProperties().put(ClientProperties.RECONNECT_HANDLER, new WebsocketReconnectHandler());

        this.websocketEndpoint = new WebsocketEndpoint(this);
        this.responseCountDownLatch = new CountDownLatch(1);
    }

    @Override
    public JsonRPCResponse invokeAndReadResponse(JsonRPCRequest requestObject, URI endpointUri,
            boolean sslVerificationDisabled) throws SteemCommunicationException, SteemResponseException {
        if (session == null || !session.isOpen() || currentEndpointUri == null
                || !currentEndpointUri.equals(endpointUri)) {
            connect(endpointUri, sslVerificationDisabled);
            // "Save" the URI we are currently connected to.
            currentEndpointUri = endpointUri;
        }

        responseCountDownLatch = new CountDownLatch(1);

        try {
            String request = requestObject.toJson();
            LOGGER.debug("Sending {}.", request);
            session.getBasicRemote().sendObject(request);
        } catch (IOException | EncodeException e) {
            // Throw an Exception and let the CommunicationHandler handle the
            // reconnect to another node.
            throw new SteemCommunicationException("Could not transfer the data to the Steem Node. - Reconnecting.", e);
        }

        try {
            // Wait until we received a response from the Server.
            do {
                if (SteemJConfig.getInstance().getResponseTimeout() == 0) {
                    responseCountDownLatch.await();
                } else {
                    if (!responseCountDownLatch.await(SteemJConfig.getInstance().getResponseTimeout(),
                            TimeUnit.MILLISECONDS)) {
                        throw new SteemTimeoutException(
                                "Timeout occured. The WebSocket server was not able to answer in "
                                        + SteemJConfig.getInstance().getResponseTimeout() + " millisecond(s).");
                    }
                }

                if (websocketEndpoint.getLatestResponse().isCallback()) {
                    handleCallback(websocketEndpoint.getLatestResponse());
                }
            } while (websocketEndpoint.getLatestResponse() == null
                    || websocketEndpoint.getLatestResponse().isCallback());
        } catch (InterruptedException e) {
            LOGGER.warn("Thread has been interrupted.", e);
            Thread.currentThread().interrupt();
        }

        return websocketEndpoint.getLatestResponse();
    }

    @Override
    protected void handleCallback(JsonRPCResponse response) throws SteemCommunicationException {
        response.handleCallback();
    }

    @Override
    public void closeConnection() throws IOException {
        if (session != null && session.isOpen()) {
            LOGGER.debug("Closing existing session.");
            session.close();
        }
    }

    /**
     * Get the {@link CountDownLatch} used by this instance to change its
     * counter.
     * 
     * @return The {@link CountDownLatch} used by this instance.
     */
    protected CountDownLatch getResponseCountDownLatch() {
        return this.responseCountDownLatch;
    }

    /**
     * Get the current {@link Session}.
     * 
     * @return The session used by this instance.
     */
    protected Session getSession() {
        return session;
    }

    /**
     * Update the {@link Session} this instance should use.
     * 
     * @param session
     *            The session to set.
     */
    protected void setSession(Session session) {
        this.session = session;
    }

    /**
     * This method establishes a new connection to the web socket Server.
     * 
     * @throws SteemCommunicationException
     */
    private synchronized void connect(URI endpointURI, boolean sslVerificationDisabled)
            throws SteemCommunicationException {
        // Tyrus expects a SSL connection if the SSL_ENGINE_CONFIGURATOR
        // property is present. This leads to a "connection failed" error when
        // a non SSL secured protocol is used. Due to this we only add the
        // property when connecting to a SSL secured node.
        if (sslVerificationDisabled && endpointURI.getScheme().equals("wss")) {
            SslEngineConfigurator sslEngineConfigurator = new SslEngineConfigurator(new SslContextConfigurator());
            sslEngineConfigurator.setHostnameVerifier(new HostnameVerifier() {
                // Could be "sslEngineConfigurator.setHostnameVerifier((String
                // host, SSLSession sslSession) -> true);" when Java 8 is used.
                @Override
                public boolean verify(String host, SSLSession sslSession) {
                    return true;
                }
            });

            client.getProperties().put(ClientProperties.SSL_ENGINE_CONFIGURATOR, sslEngineConfigurator);
        }

        try {
            // Close the current session in case it is still open.
            closeConnection();

            LOGGER.info("Connecting to {}.", endpointURI);

            session = client.connectToServer(websocketEndpoint, SteemJConfig.getInstance().getClientEndpointConfig(),
                    endpointURI);
        } catch (DeploymentException | IOException e) {
            // Throw an Exception and let the CommunicationHandler handle the
            // reconnect to another node.
            throw new SteemCommunicationException("Could not connect to the node - Trying to reconnect.", e);
        }
    }
}
