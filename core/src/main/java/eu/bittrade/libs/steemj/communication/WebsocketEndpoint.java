package eu.bittrade.libs.steemj.communication;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.bittrade.libs.steemj.communication.dto.JsonRPCResponse;
import eu.bittrade.libs.steemj.exceptions.SteemResponseError;

/**
 * This class handles a WebSocket connection.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WebsocketEndpoint extends Endpoint implements MessageHandler.Whole<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketEndpoint.class);

    /** */
    private String latestResponse;
    /** */
    private WebsocketClient websocketClient;

    /**
     * 
     * @param websocketClient
     */
    public WebsocketEndpoint(WebsocketClient websocketClient) {
        this.websocketClient = websocketClient;
    }

    /**
     * @param latestResponse
     *            the latestResponse to set
     * @throws IOException
     */
    protected JsonRPCResponse getLatestResponse() throws SteemResponseError {
        try {
            if (latestResponse != null)
                return new JsonRPCResponse(CommunicationHandler.getObjectMapper().readTree(latestResponse));

            return null;
        } catch (IOException e) {
            throw new SteemResponseError("Unable to parse the response.", e);
        }
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("Connection has been closed (Code: {}, Reason: {}).", closeReason.getCloseCode(),
                closeReason.getReasonPhrase());
    }

    @Override
    public void onError(Session session, Throwable thr) {
        LOGGER.error("Connection error.", thr);
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.websocketClient.setSession(session);
        this.websocketClient.getSession().addMessageHandler(this);

        LOGGER.info("Connection has been established.");
    }

    @Override
    public void onMessage(String message) {
        latestResponse = message;

        this.websocketClient.getResponseCountDownLatch().countDown();
    }
}
