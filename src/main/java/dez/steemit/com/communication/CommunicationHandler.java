package dez.steemit.com.communication;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dez.steemit.com.configuration.SteemApiWrapperConfig;
import dez.steemit.com.exceptions.SteemConnectionException;
import dez.steemit.com.exceptions.SteemResponseError;
import dez.steemit.com.exceptions.SteemTimeoutException;
import dez.steemit.com.exceptions.SteemTransformationException;
import dez.steemit.com.models.error.SteemError;

/**
 * This class handles the communication to the Steem web socket API.
 * 
 * @author http://steemit.com/@dez1337
 */
public class CommunicationHandler {
	private static final Logger LOGGER = LogManager.getLogger(CommunicationHandler.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();

	private ClientManager client;
	private Session session;
	private SteemApiWrapperConfig steemApiWrapperConfig;
	private SteemMessageHandler steemMessageHandler;
	private CountDownLatch messageLatch;

	/**
	 * Initialize the Connection Handler.
	 * 
	 * @param apiWrapperConfig
	 *            A SteemApiWrapperConfig object that contains the required
	 *            configuration.
	 * @throws SteemConnectionException
	 *             If there are problems due to connecting to the server.
	 */
	public CommunicationHandler(SteemApiWrapperConfig steemApiWrapperConfig) throws SteemConnectionException {
		this.steemApiWrapperConfig = steemApiWrapperConfig;
		this.client = ClientManager.createClient();
		this.steemMessageHandler = new SteemMessageHandler(this);

		MAPPER.setDateFormat(steemApiWrapperConfig.getDateTimeFormat());
		MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		reconnect();
	}

	/**
	 * Perform a request to the web socket API whose response will automatically
	 * get transformed into the given object.
	 * 
	 * @param requestObject
	 *            A request object that contains all needed parameters.
	 * @param targetClass
	 *            The target class for the transformation.
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public <T> List<T> performRequest(RequestWrapper requestObject, Class<T> targetClass)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		if (!session.isOpen()) {
			reconnect();
		}

		messageLatch = new CountDownLatch(1);

		String rawJsonResponse = "";
		try {
			session.getBasicRemote().sendObject(requestObject);
			if (!messageLatch.await(steemApiWrapperConfig.getTimeout(), TimeUnit.MILLISECONDS)) {
				String errorMessage = "Timeout occured. The websocket server was not able to answer in "
						+ steemApiWrapperConfig.getTimeout() + " millisecond(s).";
				LOGGER.error(errorMessage);
				throw new SteemTimeoutException(errorMessage);
			}

			rawJsonResponse = steemMessageHandler.getMessage();

			LOGGER.debug("Raw JSON response: {}", rawJsonResponse);

			@SuppressWarnings("unchecked")
			ResponseWrapper<T> response = MAPPER.readValue(rawJsonResponse, ResponseWrapper.class);

			if (response.getResponseId() != requestObject.getId()) {
				LOGGER.error("The request and the response id are not equal! This may cause some strange behaivior.");
			}

			// Make sure that the inner result object has the correct type.
			JavaType type = MAPPER.getTypeFactory().constructCollectionType(List.class, targetClass);

			return MAPPER.convertValue(response.getResult(), type);
		} catch (JsonParseException | JsonMappingException e) {
			// The response may be an error, so lets try to parse it as one.
			try {
				throw new SteemResponseError(MAPPER.readValue(rawJsonResponse, SteemError.class));
			} catch (IOException ex) {
				throw new SteemTransformationException("Could not transform the response into an object.", ex);
			}

		} catch (IOException | EncodeException | InterruptedException e) {
			throw new SteemConnectionException("There was a problem sending a message to the server.", e);
		}
	}

	/**
	 * This method establishes a new connection to the web socket Server.
	 * 
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 */
	private void reconnect() throws SteemConnectionException {
		try {
			session = client.connectToServer(new SteemEndpoint(), steemApiWrapperConfig.getClientEndpointConfig(),
					steemApiWrapperConfig.getWebsocketEndpointURI());
			session.addMessageHandler(steemMessageHandler);
		} catch (DeploymentException | IOException e) {
			throw new SteemConnectionException("Could not connect to the server.", e);
		}
	}

	public void countDownLetch() {
		messageLatch.countDown();
	}
}
