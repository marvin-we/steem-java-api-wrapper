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
package eu.bittrade.libs.steemj.plugins.apis.network.broadcast.api;

import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethod;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.network.broadcast.models.BroadcastTransactionSynchronousReturn;
import eu.bittrade.libs.steemj.protocol.SignedBlock;

/**
 * This class implements the network broadcast api which is required to send
 * transactions.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class NetworkBroadcastApi {
    /** Add a private constructor to hide the implicit public one. */
    private NetworkBroadcastApi() {
    }

    /**
     * Broadcast a transaction on the Steem blockchain. This method will
     * validate the transaction and return immediately. Please notice that this
     * does not mean that the operation has been accepted and has been
     * processed. If you want to make sure that this is the case use the
     * {@link #broadcastTransactionSynchronous(CommunicationHandler, SignedTransaction)}
     * method.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param transaction
     *            The {@link SignedTransaction} object to broadcast.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             </ul>
     * @throws SteemResponseException
     *             <ul>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     * @throws SteemInvalidTransactionException
     *             In case the provided transaction is not valid.
     */
    public static void broadcastTransaction(CommunicationHandler communicationHandler, SignedTransaction transaction)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (transaction.getSignatures() == null || transaction.getSignatures().isEmpty()) {
            transaction.sign();
        }

        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.NETWORK_BROADCAST_API,
                RequestMethod.BROADCAST_TRANSACTION, transaction);

        communicationHandler.performRequest(requestObject, Object.class);
    }

    /**
     * Broadcast a transaction on the Steem blockchain. This method will
     * validate the transaction and return after it has been accepted and
     * applied.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param transaction
     *            The {@link SignedTransaction} object to broadcast.
     * @return A {@link BroadcastTransactionSynchronousReturn} object providing
     *         information about the block in which the transaction has been
     *         applied.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             </ul>
     * @throws SteemResponseException
     *             <ul>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     * @throws SteemInvalidTransactionException
     *             In case the provided transaction is not valid.
     */
    public static BroadcastTransactionSynchronousReturn broadcastTransactionSynchronous(
            CommunicationHandler communicationHandler, SignedTransaction transaction)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (transaction.getSignatures() == null || transaction.getSignatures().isEmpty()) {
            transaction.sign();
        }

        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.NETWORK_BROADCAST_API,
                RequestMethod.BROADCAST_TRANSACTION_SYNCHRONOUS, transaction);

        return communicationHandler.performRequest(requestObject, BroadcastTransactionSynchronousReturn.class).get(0);
    }

    /**
     * Broadcast a whole block.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param signedBlock
     *            The {@link SignedBlock} object to broadcast.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             </ul>
     * @throws SteemResponseException
     *             <ul>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public static void broadcastBlock(CommunicationHandler communicationHandler, SignedBlock signedBlock)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.NETWORK_BROADCAST_API,
                RequestMethod.BROADCAST_BLOCK, signedBlock);

        communicationHandler.performRequest(requestObject, Object.class);
    }
}
