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
package eu.bittrade.libs.steemj.plugins.apis.chain;

import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethod;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.chain.models.PushBlockArgs;
import eu.bittrade.libs.steemj.plugins.apis.chain.models.PushBlockReturn;
import eu.bittrade.libs.steemj.plugins.apis.chain.models.PushTransactionReturn;

/**
 * This class implements the "chain_api".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ChainApi {
    /** Add a private constructor to hide the implicit public one. */
    private ChainApi() {
    }

    public static PushBlockReturn pushBlock(CommunicationHandler communicationHandler, PushBlockArgs pushBlockArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.CHAIN_API, RequestMethod.PUSH_BLOCK,
                pushBlockArgs);

        return communicationHandler.performRequest(requestObject, PushBlockReturn.class).get(0);
    }

    /**
     * Attempts to push the transaction into the pending queue
     *
     * When called to push a locally generated transaction, set the
     * skip_block_size_check bit on the skip argument. This will allow the
     * transaction to be pushed even if it causes the pending block size to
     * exceed the maximum block size. Although the transaction will probably not
     * propagate further now, as the peers are likely to have their pending
     * queues full as well, it will be kept in the queue to be propagated later
     * when a new block flushes out the pending queues.
     */
    public static PushTransactionReturn pushTransaction(CommunicationHandler communicationHandler,
            SignedTransaction signedTransaction) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.CHAIN_API, RequestMethod.PUSH_TRANSACTION,
                signedTransaction);

        return communicationHandler.performRequest(requestObject, PushTransactionReturn.class).get(0);
    }
}
