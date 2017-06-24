package eu.bittrade.libs.steemj.communication;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simple Endpoint implementation.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemEndpoint extends Endpoint {
    private static final Logger LOGGER = LogManager.getLogger(SteemEndpoint.class);

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        LOGGER.info("Connection has been established.");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("Connection has been closed.");
    }

    @Override
    public void onError(Session session, Throwable thr) {
        LOGGER.error("Connection error.", thr);
    }

}
