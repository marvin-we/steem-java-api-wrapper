package eu.bittrade.libs.steemj.apis.network.broadcast;

import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethods;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

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
     * Broadcast a transaction on the Steem blockchain.
     * 
     * @param communicationHandler
     * 
     * @param transaction
     *            A transaction object that has been signed.
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
    public void broadcastTransaction(CommunicationHandler communicationHandler, SignedTransaction transaction)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.BROADCAST_TRANSACTION);
        requestObject.setSteemApi(SteemApiType.NETWORK_BROADCAST_API);

        // TODO: transaction.sign();
        Object[] parameters = { transaction };
        requestObject.setAdditionalParameters(parameters);

        communicationHandler.performRequest(requestObject, Object.class);
    }

    /**
     * 
     */
    public void broadcastTransactionWithCallback() {

    }

    /**
     * 
     * @param transaction
     * @return Nothing
     * @throws SteemCommunicationException
     */
    public Boolean broadcastTransactionSynchronous(SignedTransaction transaction) throws SteemCommunicationException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.BROADCAST_TRANSACTION_SYNCHRONOUS);
        requestObject.setSteemApi(SteemApiType.NETWORK_BROADCAST_API);
        Object[] parameters = { transaction };
        requestObject.setAdditionalParameters(parameters);

        return null;
    }

    /**
     * 
     */
    public void broadcastTransactionSynchronous() {

    }

    /**
     * 
     */
    public void broadcastBlock() {

    }

    /**
     * 
     */
    public void setMaxBlockAge() {

    }
}
