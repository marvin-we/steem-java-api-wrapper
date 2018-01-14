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
package eu.bittrade.libs.steemj.plugins.apis.account.history;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethod;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.GetAccountHistoryArgs;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.GetAccountHistoryReturn;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.GetOpsInBlockArgs;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.GetOpsInBlockReturn;
import eu.bittrade.libs.steemj.protocol.AnnotatedSignedTransaction;

/**
 * This class implements the "account_history_api".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountHistoryApi {
    /** Add a private constructor to hide the implicit public one. */
    private AccountHistoryApi() {
    }

    /**
     * Get a sequence of operations included/generated within a particular
     * block.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param blockNumber
     *            Height of the block whose generated virtual operations should
     *            be returned.
     * @param onlyVirtual
     *            Define if only virtual operations should be returned
     *            (<code>true</code>) or not (<code>false</code>).
     * @return A sequence of operations included/generated within a particular
     *         block.
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
    public static GetOpsInBlockReturn getOpsInBlock(CommunicationHandler communicationHandler,
            GetOpsInBlockArgs getOpsInBlockArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.ACCOUNT_HISTORY_API,
                RequestMethod.GET_OPS_IN_BLOCK, getOpsInBlockArgs);

        return communicationHandler.performRequest(requestObject, GetOpsInBlockReturn.class).get(0);
    }

    /**
     * Find a transaction by its <code>transactionId</code>.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param transactionId
     *            The <code>transactionId</code> to search for.
     * @return A sequence of operations included/generated within a particular
     *         block.
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
    public static AnnotatedSignedTransaction getTransaction(CommunicationHandler communicationHandler,
            GetAccountHistoryArgs getTransactionArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.ACCOUNT_HISTORY_API,
                RequestMethod.GET_TRANSACTION, getTransactionArgs);

        return communicationHandler.performRequest(requestObject, AnnotatedSignedTransaction.class).get(0);
    }

    /**
     * Get all operations performed by the specified <code>accountName</code>.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param accountName
     *            The user name of the account.
     * @param from
     *            The starting point.
     * @param limit
     *            The maximum number of entries.
     * @return A map containing the activities. The key is the id of the
     *         activity.
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
    public static GetAccountHistoryReturn getAccountHistory(CommunicationHandler communicationHandler,
            GetAccountHistoryArgs getAccountHistoryArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.ACCOUNT_HISTORY_API,
                RequestMethod.GET_ACCOUNT_HISTORY, getAccountHistoryArgs);

        return communicationHandler.performRequest(requestObject, GetAccountHistoryReturn.class).get(0);
    }
}
