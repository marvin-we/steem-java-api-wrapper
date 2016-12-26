package dez.steemit.com.communication;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simple Endpoint implementation.
 * 
 * @author http://steemit.com/@dez1337
 */
public class SteemEndpoint extends Endpoint{
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
