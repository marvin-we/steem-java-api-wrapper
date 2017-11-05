package eu.bittrade.libs.steemj.communication;

import java.io.IOException;
import java.net.URI;

import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCResponse;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class defines the set of methods expected from a client.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class AbstractClient {
    /**
     * Use this method to send a <code>requestObject</code> to the
     * <code>endpointUri</code> and to receive an answer.
     * 
     * @param requestObject
     *            The object to send.
     * @param endpointUri
     *            The endpoint to connect and send to.
     * @param sslVerificationDisabled
     *            Define if the SSL verification should be disabled.
     * @return The response returned by the Steem Node wrapped in a
     *         {@link JsonRPCResponse} object.
     * @throws SteemCommunicationException
     *             In case of communication problems.
     * @throws SteemResponseException
     *             If the answer received from the node is no valid JSON.
     */
    public abstract JsonRPCResponse invokeAndReadResponse(JsonRPCRequest requestObject, URI endpointUri,
            boolean sslVerificationDisabled) throws SteemCommunicationException, SteemResponseException;

    /**
     * Use this method to handle callbacks.
     * 
     * @param rawJsonResponse
     *            A {@link JsonRPCResponse} instance wrapping a potential
     *            callback.
     * @throws SteemCommunicationException
     *             If the <code>rawJsonResponse</code> is not a callback.
     */
    protected abstract void handleCallback(JsonRPCResponse rawJsonResponse) throws SteemCommunicationException;

    /**
     * Use this method to close the connection of this client.
     * 
     * @throws IOException
     *             If the connection can't be closed.
     */
    public abstract void closeConnection() throws IOException;
}
