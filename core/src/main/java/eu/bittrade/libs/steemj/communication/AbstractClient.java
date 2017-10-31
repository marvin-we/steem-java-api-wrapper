package eu.bittrade.libs.steemj.communication;

import java.io.IOException;
import java.net.URI;

import eu.bittrade.libs.steemj.communication.dto.JsonRPCRequest;
import eu.bittrade.libs.steemj.communication.dto.JsonRPCResponse;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseError;

/**
 * This class defines the set of methods expected from a client.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class AbstractClient {
    /**
     * 
     * @param requestObject
     * @param endpointUri
     * @param sslVerificationDisabled
     * @return The response
     * @throws SteemCommunicationException
     * @throws SteemResponseError
     *             If the answer received from the node is no valid JSON.
     */
    public abstract JsonRPCResponse invokeAndReadResponse(JsonRPCRequest requestObject, URI endpointUri,
            boolean sslVerificationDisabled) throws SteemCommunicationException, SteemResponseError;

    /**
     * 
     * @param rawJsonResponse
     */
    protected abstract void handleCallback(JsonRPCResponse rawJsonResponse);

    /**
     * 
     * @throws IOException
     */
    public abstract void closeConnection() throws IOException;
}
