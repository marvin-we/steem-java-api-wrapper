package dez.steemit.com.communication;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dez.steemit.com.configuration.SteemApiWrapperConfig;
import dez.steemit.com.exceptions.SteemConnectionException;
import dez.steemit.com.exceptions.SteemTimeoutException;
import dez.steemit.com.exceptions.SteemTransformationException;
import dez.steemit.com.models.SteemModel;

public class CommunicationHandler {
	private static final Logger LOGGER = LogManager.getLogger(CommunicationHandler.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();

	private ClientManager client;
	private Session session;
	private SteemApiWrapperConfig steemApiWrapperConfig;
	private SteemMessageHandler steemMessageHandler;
	private CountDownLatch messageLatch;

	public CommunicationHandler(SteemApiWrapperConfig steemApiWrapperConfig) throws SteemConnectionException {
		this.steemApiWrapperConfig = steemApiWrapperConfig;
		this.client = ClientManager.createClient();
		this.steemMessageHandler = new SteemMessageHandler(this);

		MAPPER.setDateFormat(steemApiWrapperConfig.getDateTimeFormat());

		reconnect();
	}

	public <T extends SteemModel> T performRequest(RequestObject requestObject, Class<T> targetClass)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		if (!session.isOpen()) {
			reconnect();
		}

		messageLatch = new CountDownLatch(1);

		try {
			session.getBasicRemote().sendObject(requestObject);
			if (!messageLatch.await(steemApiWrapperConfig.getTimeout(), TimeUnit.MILLISECONDS)) {
				String errorMessage = "Timeout occured. The websocket server was not able to answer in "
						+ steemApiWrapperConfig.getTimeout() + " millisecond(s).";
				LOGGER.error(errorMessage);
				throw new SteemTimeoutException(errorMessage);
			}

			return MAPPER.readValue(steemMessageHandler.getMessage(), targetClass);

		} catch (JsonParseException | JsonMappingException e) {
			throw new SteemTransformationException("Could not transform the response into an object.", e);
		} catch (IOException | EncodeException | InterruptedException e) {
			throw new SteemConnectionException("There was a problem sending a message to the server.", e);
		}
	}

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
