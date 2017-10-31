package eu.bittrade.libs.steemj.communication;

import javax.websocket.CloseReason;

import org.glassfish.tyrus.client.ClientManager.ReconnectHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.bittrade.libs.steemj.configuration.SteemJConfig;

/**
 * This class handles connection issues.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WebsocketReconnectHandler extends ReconnectHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketReconnectHandler.class);

    @Override
    public boolean onDisconnect(CloseReason closeReason) {
        LOGGER.debug("The connection has been closed (Code: {}, Reason: {}).", closeReason.getCloseCode(),
                closeReason.getReasonPhrase());

        if (SteemJConfig.getInstance().getIdleTimeout() <= 0) {
            LOGGER.info(
                    "The connection has been closed, but SteemJ is configured to never close the conenction. Initiating reconnect.");
            return true;
        }

        return false;
    }

    @Override
    public boolean onConnectFailure(Exception exception) {
        LOGGER.info("The connection has been closed due to a failure.");
        LOGGER.debug("Reason: ", exception);

        // Do not reconnect in case of failure. The CommunicationHandler will
        // take care of changing the node and establishing a new connection.
        return false;
    }

    @Override
    public long getDelay() {
        return 0;
    }
}
