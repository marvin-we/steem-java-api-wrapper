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
     * Use this method to close the connection of this client.
     * 
     * @throws IOException
     *             If the connection can't be closed.
     */
    public abstract void closeConnection() throws IOException;
}
