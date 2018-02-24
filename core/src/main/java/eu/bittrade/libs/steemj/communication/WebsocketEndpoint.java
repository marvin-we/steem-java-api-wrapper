/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.communication;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCResponse;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class handles a WebSocket connection.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WebsocketEndpoint extends Endpoint implements MessageHandler.Whole<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketEndpoint.class);

    /** The latest response received from a Steem Node. */
    private String latestResponse;
    /** The {@link WebsocketClient} whose session object should be updated. */
    private WebsocketClient websocketClient;

    /**
     * Create a new {@link WebsocketEndpoint} instance.
     * 
     * @param websocketClient
     *            The @link WebsocketClient} whose session object should be
     *            updated.
     */
    public WebsocketEndpoint(WebsocketClient websocketClient) {
        this.websocketClient = websocketClient;
    }

    /**
     * Pull the latest response.
     * 
     * @return In case a response has already been received a new
     *         {@link JsonRPCResponse} instance that wraps the response,
     *         otherwise the method will return <code>null</code>.
     * @throws SteemResponseException
     *             In case the response can not be parsed as a tree.
     */
    protected JsonRPCResponse getLatestResponse() throws SteemResponseException {
        try {
            if (latestResponse != null)
                return new JsonRPCResponse(CommunicationHandler.getObjectMapper().readTree(latestResponse));

            return null;
        } catch (IOException e) {
            throw new SteemResponseException("Unable to parse the response.", e);
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

        if (this.websocketClient.getResponseCountDownLatch().getCount() > 0) {
            this.websocketClient.getResponseCountDownLatch().countDown();
        } else {
            // Sadly it is not possible to throw an exception here, so the
            // only useful thing we can do is to log it.
            LOGGER.error("Received an unexpected message.");
        }
    }
}
