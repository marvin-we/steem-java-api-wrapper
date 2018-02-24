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
package eu.bittrade.libs.steemj;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joou.UInteger;
import org.joou.ULong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import eu.bittrade.crypto.core.ECKey;
import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.base.models.Account;
import eu.bittrade.libs.steemj.base.models.BeneficiaryRouteType;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.CommentOptionsExtension;
import eu.bittrade.libs.steemj.base.models.CommentPayoutBeneficiaries;
import eu.bittrade.libs.steemj.base.models.FeedHistory;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.ScheduledHardfork;
import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.RewardFundType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.plugins.apis.account.by.key.AccountByKeyApi;
import eu.bittrade.libs.steemj.plugins.apis.account.by.key.models.GetKeyReferencesArgs;
import eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.AppliedOperation;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.GetAccountHistoryArgs;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.GetOpsInBlockArgs;
import eu.bittrade.libs.steemj.plugins.apis.block.BlockApi;
import eu.bittrade.libs.steemj.plugins.apis.block.models.ExtendedSignedBlock;
import eu.bittrade.libs.steemj.plugins.apis.block.models.GetBlockArgs;
import eu.bittrade.libs.steemj.plugins.apis.block.models.GetBlockHeaderArgs;
import eu.bittrade.libs.steemj.plugins.apis.condenser.CondenserApi;
import eu.bittrade.libs.steemj.plugins.apis.condenser.models.AccountVote;
import eu.bittrade.libs.steemj.plugins.apis.condenser.models.ExtendedAccount;
import eu.bittrade.libs.steemj.plugins.apis.condenser.models.ExtendedDynamicGlobalProperties;
import eu.bittrade.libs.steemj.plugins.apis.condenser.models.ExtendedLimitOrder;
import eu.bittrade.libs.steemj.plugins.apis.condenser.models.State;
import eu.bittrade.libs.steemj.plugins.apis.database.DatabaseApi;
import eu.bittrade.libs.steemj.plugins.apis.database.models.DynamicGlobalProperty;
import eu.bittrade.libs.steemj.plugins.apis.database.models.OrderBook;
import eu.bittrade.libs.steemj.plugins.apis.database.models.RewardFund;
import eu.bittrade.libs.steemj.plugins.apis.database.models.Witness;
import eu.bittrade.libs.steemj.plugins.apis.database.models.WitnessSchedule;
import eu.bittrade.libs.steemj.plugins.apis.follow.FollowApi;
import eu.bittrade.libs.steemj.plugins.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.AccountReputation;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.BlogEntry;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.CommentBlogEntry;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.CommentFeedEntry;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.FeedEntry;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.FollowApiObject;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.FollowCountApiObject;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.GetFollowersArgs;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.PostsPerAuthorPair;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.operations.FollowOperation;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.operations.ReblogOperation;
import eu.bittrade.libs.steemj.plugins.apis.market.history.MarketHistoryApi;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.Bucket;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetMarketHistoryArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetOrderBookArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetRecentTradesArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetTickerReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetTradeHistoryArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetVolumeReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.MarketTrade;
import eu.bittrade.libs.steemj.plugins.apis.network.broadcast.api.NetworkBroadcastApi;
import eu.bittrade.libs.steemj.plugins.apis.network.broadcast.models.BroadcastTransactionSynchronousReturn;
import eu.bittrade.libs.steemj.plugins.apis.tags.TagsApi;
import eu.bittrade.libs.steemj.plugins.apis.tags.enums.DiscussionSortType;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.Discussion;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.DiscussionQuery;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.GetActiveVotesArgs;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.Tag;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.VoteState;
import eu.bittrade.libs.steemj.plugins.apis.witness.WitnessApi;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.AccountBandwidth;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.GetAccountBandwidthArgs;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.ReserveRatioObject;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;
import eu.bittrade.libs.steemj.protocol.BlockHeader;
import eu.bittrade.libs.steemj.protocol.Price;
import eu.bittrade.libs.steemj.protocol.PublicKey;
import eu.bittrade.libs.steemj.protocol.SignedBlock;
import eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.protocol.operations.ClaimRewardBalanceOperation;
import eu.bittrade.libs.steemj.protocol.operations.CommentOperation;
import eu.bittrade.libs.steemj.protocol.operations.CommentOptionsOperation;
import eu.bittrade.libs.steemj.protocol.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.protocol.operations.DelegateVestingSharesOperation;
import eu.bittrade.libs.steemj.protocol.operations.DeleteCommentOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;
import eu.bittrade.libs.steemj.protocol.operations.TransferOperation;
import eu.bittrade.libs.steemj.protocol.operations.VoteOperation;
import eu.bittrade.libs.steemj.util.CondenserUtils;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class is a wrapper for the Steem web socket API and provides all
 * features known from the Steem CLI Wallet.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemJ {
    private static final Logger LOGGER = LoggerFactory.getLogger(SteemJ.class);

    // Error messages as constants to make SonarQube happy.
    private static final String TAG_ERROR_MESSAGE = "You need to provide at least one tag, but not more than five.";
    private static final String NO_DEFAULT_ACCOUNT_ERROR_MESSAGE = "You try to use a simplified operation without having a default account configured in SteemJConfig. Please configure a default account or use another method.";
    private static final String MARKDOWN = "markdown";

    private CommunicationHandler communicationHandler;

    /**
     * Initialize the SteemJ.
     * 
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
    public SteemJ() throws SteemCommunicationException, SteemResponseException {
        this.communicationHandler = new CommunicationHandler();
    }

    // #########################################################################
    // ## ACCOUNT BY KEY API ###################################################
    // #########################################################################

    /**
     * Search for users under the use of their public key(s).
     * 
     * @param publicKeys
     *            An array containing one or more public keys.
     * @return A list of arrays containing the matching account names.
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
    public List<AccountName> getKeyReferences(List<PublicKey> publicKeys)
            throws SteemCommunicationException, SteemResponseException {
        return AccountByKeyApi.getKeyReferences(communicationHandler, new GetKeyReferencesArgs(publicKeys))
                .getAccounts();
    }

    // #########################################################################
    // ## ACCOUNT HISTORY API ##################################################
    // #########################################################################

    /**
     * Get a sequence of operations included/generated within a particular
     * block.
     * 
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
    public List<AppliedOperation> getOpsInBlock(long blockNumber, boolean onlyVirtual)
            throws SteemCommunicationException, SteemResponseException {
        return AccountHistoryApi
                .getOpsInBlock(communicationHandler, new GetOpsInBlockArgs(UInteger.valueOf(blockNumber), onlyVirtual))
                .getOperations();
    }

    public void getTransaction() {

    }

    /**
     * Get all operations performed by the specified account.
     * 
     * @param accountName
     *            The user name of the account.
     * @param start
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
    public Map<UInteger, AppliedOperation> getAccountHistory(AccountName accountName, ULong start, UInteger limit)
            throws SteemCommunicationException, SteemResponseException {
        return AccountHistoryApi
                .getAccountHistory(communicationHandler, new GetAccountHistoryArgs(accountName, start, limit))
                .getHistory();
    }

    // #########################################################################
    // ## BLOCK API ############################################################
    // #########################################################################

    /**
     * Get a full, signed block by providing its <code>blockNumber</code>. The
     * returned object contains all information related to the block (e.g.
     * processed transactions, the witness and the creation time).
     * 
     * @param blockNumber
     *            Height of the block to be returned.
     * @return The referenced full, signed block, or <code>null</code> if no
     *         matching block was found.
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
    public Optional<ExtendedSignedBlock> getBlock(long blockNumber)
            throws SteemCommunicationException, SteemResponseException {
        return BlockApi.getBlock(communicationHandler, new GetBlockArgs(UInteger.valueOf(blockNumber))).getBlock();
    }

    /**
     * Like {@link #getBlock(long)}, but will only return the header of the
     * requested block instead of the full, signed one.
     * 
     * @param blockNumber
     *            Height of the block to be returned.
     * @return The referenced full, signed block, or <code>null</code> if no
     *         matching block was found.
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
    public Optional<BlockHeader> getBlockHeader(long blockNumber)
            throws SteemCommunicationException, SteemResponseException {
        return BlockApi.getBlockHeader(communicationHandler, new GetBlockHeaderArgs(UInteger.valueOf(blockNumber)))
                .getHeader();
    }

    // #########################################################################
    // ## NETWORK BROADCAST API ################################################
    // #########################################################################

    /**
     * Broadcast a transaction on the Steem blockchain. This method will
     * validate the transaction and return immediately. Please notice that this
     * does not mean that the operation has been accepted and has been
     * processed. If you want to make sure that this is the case use the
     * {@link #broadcastTransactionSynchronous(SignedTransaction)} method.
     * 
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
    public void broadcastTransaction(SignedTransaction transaction)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        NetworkBroadcastApi.broadcastTransaction(communicationHandler, transaction);
    }

    /**
     * Broadcast a transaction on the Steem blockchain. This method will
     * validate the transaction and return after it has been accepted and
     * applied.
     * 
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
    public BroadcastTransactionSynchronousReturn broadcastTransactionSynchronous(SignedTransaction transaction)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        return NetworkBroadcastApi.broadcastTransactionSynchronous(communicationHandler, transaction);
    }

    /**
     * Broadcast a whole block.
     * 
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
    public void broadcastBlock(SignedBlock signedBlock) throws SteemCommunicationException, SteemResponseException {
        NetworkBroadcastApi.broadcastBlock(communicationHandler, signedBlock);
    }

    // #########################################################################
    // ## DATABASE API #########################################################
    // #########################################################################

    /**
     * This API is a short-cut for returning all of the state required for a
     * particular URL with a single query.
     * 
     * TODO: Provide examples.
     * 
     * @param path
     *            TODO: Fix JavaDoc
     * @return TODO: Fix JavaDoc
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
    public State getState(Permlink path) throws SteemCommunicationException, SteemResponseException {
        return CondenserApi.getState(communicationHandler, path);
    }

    /**
     * Get the list of the current active witnesses.
     * 
     * @return The list of the current active witnesses.
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
    public List<AccountName> getActiveWitnesses() throws SteemCommunicationException, SteemResponseException {
        return DatabaseApi.getActiveWitnesses(communicationHandler);
    }

    /**
     * Get the global properties.
     * 
     * @return The dynamic global properties.
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
    public DynamicGlobalProperty getDynamicGlobalProperties()
            throws SteemCommunicationException, SteemResponseException {
        return DatabaseApi.getDynamicGlobalProperties(communicationHandler);
    }

    /**
     * Get the current number of registered Steem accounts.
     * 
     * @return The number of accounts.
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
    public int getAccountCount() throws SteemCommunicationException, SteemResponseException {
        return 0;
    }

    /**
     * 
     * @param accountNames
     *            A list of accounts you want to request the details for.
     * @return A List of accounts found for the given account names.
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
    public List<ExtendedAccount> getAccounts(List<AccountName> accountNames)
            throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setSteemApi(SteemApiType.DATABASE_API);
         * requestObject.setApiMethod(RequestMethod.GET_ACCOUNTS);
         * 
         * // The API expects an array of arrays here. String[] innerParameters
         * = new String[accountNames.size()]; for (int i = 0; i <
         * accountNames.size(); i++) { innerParameters[i] =
         * accountNames.get(i).getName(); }
         * 
         * String[][] parameters = { innerParameters };
         * 
         * requestObject.setAdditionalParameters(parameters); return
         * communicationHandler.performRequest(requestObject,
         * ExtendedAccount.class);
         */
        return null;
    }

    /**
     * Get a list of all votes done by a specific account.
     * 
     * @param accountName
     *            The user name of the account.
     * @return A List of votes done by the specified account.
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
    public List<AccountVote> getAccountVotes(AccountName accountName)
            throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setSteemApi(SteemApiType.DATABASE_API);
         * requestObject.setApiMethod(RequestMethod.GET_ACCOUNT_VOTES); String[]
         * parameters = { accountName.getName() };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * AccountVote.class);
         */
        return null;
    }

    /**
     * Get the active votes for a given post of a given author.
     * 
     * @param author
     *            The authors name.
     * @param permlink
     *            The permlink of the article.
     * @return A list of votes for a specific article.
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
    public List<VoteState> getActiveVotes(AccountName author, Permlink permlink)
            throws SteemCommunicationException, SteemResponseException {
        return TagsApi.getActiveVotes(communicationHandler, new GetActiveVotesArgs(author, permlink)).getVotes();
    }

    /**
     * Get the chain properties.
     * 
     * @return The chain properties.
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
    public ChainProperties getChainProperties() throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_CHAIN_PROPERTIES);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = {}; requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * ChainProperties.class).get(0);
         */
        return null;
    }

    /**
     * Get the details of a specific post.
     * 
     * @param author
     *            The authors name.
     * @param permlink
     *            The permlink of the article.
     * @return The details of a specific post.
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
    public Discussion getContent(AccountName author, Permlink permlink)
            throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_CONTENT);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = { author.getName(), permlink.getLink() };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Discussion.class).get(0);
         */
        return null;
    }

    /**
     * Get the replies of a specific post.
     * 
     * @param author
     *            The authors name.
     * @param permlink
     *            The permlink of the article.
     * @return A list of discussions or null if the post has no replies.
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
    public List<Discussion> getContentReplies(AccountName author, Permlink permlink)
            throws SteemCommunicationException, SteemResponseException {
        // TODO TagsApi.getContentReplies
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_CONTENT_REPLIES);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = { author.getName(), permlink.getLink() };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Discussion.class);
         */
        return null;
    }

    /**
     * TODO: Look up what this is used for and what it can return.
     * 
     * @param account
     *            The account name.
     * @return Unknown
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
    public Object[] getConversionRequests(AccountName account)
            throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_CONVERSION_REQUESTS);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = { account.getName() };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Object[].class).get(0);
         */
        return null;
    }

    /**
     * Grab the current median conversion price of SBD / STEEM.
     * 
     * @return The current median price.
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
    public Price getCurrentMedianHistoryPrice() throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.
         * GET_CURRENT_MEDIAN_HISTORY_PRICE);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = {}; requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Price.class).get(0);
         */
        return null;
    }

    /**
     * Get active discussions for a specified tag.
     * 
     * @param discussionQuery
     *            A query defining specific search parameters.
     * @param sortBy
     *            Choose the method used for sorting the results.
     * @return A list of discussions matching the given conditions.
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
    public List<Discussion> getDiscussionsBy(DiscussionQuery discussionQuery, DiscussionSortType sortBy)
            throws SteemCommunicationException, SteemResponseException {

        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * 
         * requestObject.setApiMethod(RequestMethod.valueOf(sortBy.name()));
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); Object[]
         * parameters = { discussionQuery };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Discussion.class);
         */
        return null;
    }

    /**
     * Get a list of discussion for a given author.
     * 
     * @param author
     *            The authors name.
     * @param permlink
     *            The permlink of the article.
     * @param date
     *            Only return articles before this date. (This field seems to be
     *            ignored by the Steem api)
     * @param limit
     *            The number of results you want to receive.
     * @return A list of discussions.
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
    public List<Discussion> getDiscussionsByAuthorBeforeDate(AccountName author, Permlink permlink, String date,
            int limit) throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * 
         * requestObject.setApiMethod(RequestMethod.
         * GET_DISCUSSIONS_BY_AUTHOR_BEFORE_DATE);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API);
         * 
         * // Verify that the date has the correct format. SimpleDateFormat
         * simpleDateFormat = new
         * SimpleDateFormat(SteemJConfig.getInstance().getDateTimePattern());
         * simpleDateFormat.setTimeZone(TimeZone.getTimeZone(SteemJConfig.
         * getInstance().getTimeZoneId())); Date beforeDate; try { beforeDate =
         * simpleDateFormat.parse(date); } catch (ParseException e) { throw new
         * SteemTransformationException("Could not parse the received date to a Date object."
         * , e); }
         * 
         * String[] parameters = { author.getName(), permlink.getLink(),
         * simpleDateFormat.format(beforeDate), String.valueOf(limit) };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Discussion.class);
         */
        return null;
    }

    /**
     * Get the current price and a list of history prices combined in one
     * object.
     * 
     * @return The conversion history of SBD / STEEM.
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
    public FeedHistory getFeedHistory() throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_FEED_HISTORY);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = {}; requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * FeedHistory.class).get(0);
         */
        return null;
    }

    /**
     * TODO: Check what this method is supposed to do. In a fist test it seems
     * to return the time since the current version is active.
     * 
     * @return ???
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
    public ScheduledHardfork getNextScheduledHarfork() throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_NEXT_SCHEDULED_HARDFORK)
         * ; requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = {}; requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * ScheduledHardfork.class).get(0);
         */
        return null;
    }

    /**
     * If specified user name has orders open on the internal STEEM market it
     * will return them.
     * 
     * @param accountName
     *            The name of the account.
     * @return A list of open orders for this account.
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
    public List<ExtendedLimitOrder> getOpenOrders(AccountName accountName)
            throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_OPEN_ORDERS);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = { accountName.getName() };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * ExtendedLimitOrder.class);
         */
        return null;
    }

    /**
     * Returns a list of orders on the internal steem market.
     * 
     * @param limit
     *            The maximum number of results for each category (asks / bids).
     * @return A list of orders on the internal steem market.
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
    public OrderBook getOrderBookUsingDatabaseApi(int limit)
            throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_ORDER_BOOK);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = { String.valueOf(limit) };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * OrderBook.class).get(0);
         */
        return null;
    }

    // TODO implement this!
    public List<String[]> getPotentialSignatures() throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_POTENTIAL_SIGNATURES);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); Object[]
         * parameters = {}; requestObject.setAdditionalParameters(parameters);
         * LOGGER.info("output: {}",
         * communicationHandler.performRequest(requestObject, Object[].class));
         */
        return null;
    }

    /**
     * /** Get a list of Content starting from the given post of the given user.
     * The list will be sorted by the Date of the last update.
     * 
     * @param username
     *            The name of the user.
     * @param permlink
     *            The permlink of an article.
     * @param limit
     *            Number of results.
     * @return A list of Content objects.
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
    public List<Discussion> getRepliesByLastUpdate(AccountName username, Permlink permlink, int limit)
            throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_REPLIES_BY_LAST_UPDATE);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); Object[]
         * parameters = { username, permlink.getLink(), String.valueOf(limit) };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Discussion.class);
         */
        return null;
    }

    /**
     * Get detailed information of a specific reward fund.
     * 
     * @param rewordFundType
     *            One of the {@link eu.bittrade.libs.steemj.enums.RewardFundType
     *            RewardFundType}s.
     * @return A refund object containing detailed information about the
     *         requested reward fund.
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
    public RewardFund getRewardFund(RewardFundType rewordFundType)
            throws SteemCommunicationException, SteemResponseException {
        return DatabaseApi.getRewardFunds(communicationHandler, rewordFundType);
    }

    /**
     * Use the Steem API to receive the HEX representation of a signed
     * transaction.
     * 
     * @param signedTransaction
     *            The signed Transaction object you want to receive the HEX
     *            representation for.
     * @return The HEX representation.
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
    public String getTransactionHex(SignedTransaction signedTransaction)
            throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_TRANSACTION_HEX);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API);
         * 
         * Object[] parameters = { signedTransaction };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * String.class).get(0);
         */
        return null;
    }

    /**
     * Get the witness information for a given witness account name.
     * 
     * @param witnessName
     *            The witness name.
     * @return A list of witnesses.
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
    public Witness getWitnessByAccount(AccountName witnessName)
            throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_WITNESS_BY_ACCOUNT);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = { witnessName.getName() };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Witness.class).get(0);
         */
        return null;
    }

    /**
     * Get a list of witnesses sorted by the amount of votes. The list begins
     * with the given account name and contains the next witnesses with less
     * votes than given one.
     * 
     * @param witnessName
     *            The witness name to start from.
     * @param limit
     *            The number of results.
     * @return A list of witnesses.
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
    public List<Witness> getWitnessByVote(AccountName witnessName, int limit)
            throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_WITNESSES_BY_VOTE);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = { witnessName.getName(), String.valueOf(limit) };
         * requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Witness.class);
         */
        return null;
    }

    /**
     * Get the current number of active witnesses.
     * 
     * @return The number of witnesses.
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
    public int getWitnessCount() throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_WITNESS_COUNT);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = {}; requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Integer.class).get(0);
         */
        return 0;
    }

    /**
     * Get all witnesses.
     * 
     * @return A list of witnesses.
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
    public List<Witness> getWitnesses() throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_WITNESSES);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = {}; requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * Witness.class);
         */
        return null;
    }

    /**
     * Get the witness schedule.
     * 
     * @return The witness schedule.
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
    public WitnessSchedule getWitnessSchedule() throws SteemCommunicationException, SteemResponseException {
        /*
         * JsonRPCRequest requestObject = new JsonRPCRequest();
         * requestObject.setApiMethod(RequestMethod.GET_WITNESS_SCHEDULE);
         * requestObject.setSteemApi(SteemApiType.DATABASE_API); String[]
         * parameters = {}; requestObject.setAdditionalParameters(parameters);
         * 
         * return communicationHandler.performRequest(requestObject,
         * WitnessSchedule.class).get(0);
         */
        return null;
    }

    /**
     * Search for accounts.
     * 
     * @param pattern
     *            The lower case pattern you want to search for.
     * @param limit
     *            The maximum number of account names.
     * @return A list of matching account names.
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
    public List<String> lookupAccounts(String pattern, int limit)
            throws SteemCommunicationException, SteemResponseException {
        // return DatabaseApi.findAccounts(communicationHandler,
        // findAccountsArgs);
        return null;
    }

    /**
     * Search for witness accounts.
     * 
     * @param pattern
     *            The lower case pattern you want to search for.
     * @param limit
     *            The maximum number of account names.
     * @return A list of matching account names.
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
    public List<String> lookupWitnessAccounts(String pattern, int limit)
            throws SteemCommunicationException, SteemResponseException {
        // return
        // DatabaseApi.findWitnesses(communicationHandler).getWitnesses();
        return null;
    }

    /**
     * Use the Steem API to verify the required authorities for this
     * transaction.
     * 
     * @param signedTransaction
     *            A {@link SignedTransaction} transaction which has been signed.
     * @return <code>true</code> if the given transaction has been signed
     *         correctly, otherwise an Exception will be thrown.
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
    public boolean verifyAuthority(SignedTransaction signedTransaction)
            throws SteemCommunicationException, SteemResponseException {
        return DatabaseApi.verifyAccountAuthority(communicationHandler, null).isValid();
    }

    // #########################################################################
    // ## FOLLOW API ###########################################################
    // #########################################################################

    /**
     * Get a list of account names which the <code>following</code> account is
     * followed by.
     * 
     * @param following
     *            The account name for whose followers should be returned.
     * @param startFollower
     *            A filter to limit the number of results. If not empty, the
     *            method will only return account names after the
     *            <code>following</code> account has been followed by the
     *            <code>startFollower</code> account.
     * @param type
     *            The follow type.
     * @param limit
     *            The maximum number of results returned.
     * @return A list of account names that follow the <code>follower</code>
     *         account..
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
    public List<FollowApiObject> getFollowers(AccountName following, AccountName startFollower, FollowType type,
            UInteger limit) throws SteemCommunicationException, SteemResponseException {
        return FollowApi.getFollowers(communicationHandler, new GetFollowersArgs(following, startFollower, type, limit))
                .getFollowers();
    }

    /**
     * Get a list of account names which the <code>follower</code> account
     * follows.
     * 
     * @param follower
     *            The account name for which the account names should be
     *            returned, that the <code>follower</code> is following.
     * @param startFollowing
     *            A filter to limit the number of results. If not empty, the
     *            method will only return account names after the
     *            <code>follower</code> account has followed the
     *            <code>startFollowing</code> account.
     * @param type
     *            The follow type.
     * @param limit
     *            The maximum number of results returned.
     * @return A list of account names the <code>follower</code> account is
     *         following.
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
    public List<FollowApiObject> getFollowing(AccountName follower, AccountName startFollowing, FollowType type,
            short limit) throws SteemCommunicationException, SteemResponseException {
        return FollowApi.getFollowing(communicationHandler, follower, startFollowing, type, limit);
    }

    /**
     * Get the amount of accounts following the given <code>account</code> and
     * the number of accounts this <code>account</code> follows. Both values are
     * wrapped in a FollowCountApiObject.
     * 
     * @param account
     *            The account to get the number of followers / following
     *            accounts for.
     * @return The number of followers / following accounts
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
    public FollowCountApiObject getFollowCount(AccountName account)
            throws SteemCommunicationException, SteemResponseException {
        return FollowApi.getFollowCount(communicationHandler, account);
    }

    /**
     * This method is like the {@link #getBlogEntries(AccountName, int, short)
     * getBlogEntries(AccountName, int, short)} method, but instead of returning
     * blog entries, the getFeedEntries method returns the feed of the given
     * account.
     * 
     * @param account
     *            The account to get the feed entries for.
     * @param entryId
     *            The first feed entry id to return.
     * @param limit
     *            The number of results.
     * @return A list of feed entries from the given <code>author</code> based
     *         on the given conditions (<code>entryId</code> and
     *         <code>limit</code>).
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
    public List<FeedEntry> getFeedEntries(AccountName account, int entryId, short limit)
            throws SteemCommunicationException, SteemResponseException {
        return FollowApi.getFeedEntries(communicationHandler, account, entryId, limit);
    }

    /**
     * This method is like the {@link #getBlog(AccountName, int, short)
     * getBlog(AccountName, int, short)} method, but instead of returning blog
     * entries, the getFeed method returns the feed of the given account.
     * 
     * @param account
     *            The account to get the feed entries for.
     * @param entryId
     *            The first feed entry id to return.
     * @param limit
     *            The number of results.
     * @return A list of feed entries from the given <code>author</code> based
     *         on the given conditions (<code>entryId</code> and
     *         <code>limit</code>).
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
    public List<CommentFeedEntry> getFeed(AccountName account, int entryId, short limit)
            throws SteemCommunicationException, SteemResponseException {
        return FollowApi.getFeed(communicationHandler, account, entryId, limit);
    }

    /**
     * Get the blog entries of the given <code>author</code> based on the given
     * coniditions.
     * 
     * Each blog entry of an <code>author</code> (resteemed or posted on his/her
     * own) has an <code>entryId</code>, while the <code>entryId</code> starts
     * with 0 for the first blog entry and is increment by 1 for each resteem or
     * post of the <code>author</code>.
     * 
     * Steem allows to use the <code>entryId</code> as a search criteria: The
     * first entry of the returned list is the blog entry with the given
     * <code>entryId</code>. Beside that, the <code>limit</code> can be used to
     * limit the number of results.
     * 
     * So if the method is called with <code>entryId</code> set to 5 and the
     * <code>limit</code> is set to 2, the returned list will contain 2 entries:
     * The first one is the blog entry with <code>entryId</code> of 5, the
     * second one has the <code>entryId</code> 4.
     * 
     * If the <code>entryId</code> is set to 0, the first returned item will be
     * the latest blog entry of the given <code>author</code>.
     * 
     * So if a user has 50 blog entries and this method is called with an
     * <code>entryId</code> set to 0 and a <code>limit</code> of 2, the returned
     * list will contain the blog entries with the <code>entryId</code>s 50 and
     * 49.
     * 
     * @param account
     *            The account to get the blog entries for.
     * @param entryId
     *            The first blog entry id to return.
     * @param limit
     *            The number of results.
     * @return A list of blog entries from the given <code>author</code> based
     *         on the given conditions (<code>entryId</code> and
     *         <code>limit</code>).
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
    public List<BlogEntry> getBlogEntries(AccountName account, int entryId, short limit)
            throws SteemCommunicationException, SteemResponseException {
        return FollowApi.getBlogEntries(communicationHandler, account, entryId, limit);

    }

    /**
     * Like {@link #getBlogEntries(AccountName, int, short)
     * getBlogEntries(AccountName, int, short)}, but contains the whole content
     * of the blog entry.
     * 
     * @param account
     *            The account to get the blog entries for.
     * @param entryId
     *            The first blog entry id to return.
     * @param limit
     *            The number of results.
     * @return A list of blog entries from the given <code>author</code> based
     *         on the given conditions (<code>entryId</code> and
     *         <code>limit</code>).
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
    public List<CommentBlogEntry> getBlog(AccountName account, int entryId, short limit)
            throws SteemCommunicationException, SteemResponseException {
        return FollowApi.getBlog(communicationHandler, account, entryId, limit);
    }

    /**
     * Get the reputation for one or more accounts. This method will return the
     * reputation of the {@code limit} number of accounts that mostly match the
     * given {@code accountName}.
     * 
     * <p>
     * <b>Example:</b>
     * </p>
     * <p>
     * <code>getAccountReputations(new AccountName("dez1337"), 0);</code>
     * </p>
     * <p>
     * This example will return the reputation of the account "dez1337".
     * </p>
     * <p>
     * <code>getAccountReputations(new AccountName("dez1337"), 1);</code>
     * </p>
     * <p>
     * This example will return the reputation of the account "dez1337" and
     * "dez243", because "dez243" is the most similar account name to "dez1337".
     * </p>
     * 
     * @param accountName
     *            The first account name to get the reputation for.
     * @param limit
     *            The number of results.
     * @return A list of {@link AccountReputation AccountReputation}.
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
    public List<AccountReputation> getAccountReputations(AccountName accountName, int limit)
            throws SteemCommunicationException, SteemResponseException {
        return FollowApi.getAccountReputations(communicationHandler, accountName, limit);
    }

    /**
     * Gets list of accounts that have reblogged a particular post.
     * 
     * @param author
     *            The author of the post to get the rebloggers for.
     * @param permlink
     *            The permlink of the post to get the rebloggers for.
     * @return A list of accounts that have reblogged a particular post.
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
    public List<AccountName> getRebloggedBy(AccountName author, Permlink permlink)
            throws SteemCommunicationException, SteemResponseException {
        return FollowApi.getRebloggedBy(communicationHandler, author, permlink);
    }

    /**
     * Use this method to find out how many posts of different authors have been
     * resteemed by the given <code>blogAccount</code>.
     * 
     * @param blogAccount
     *            The account whose blog should be analyzed.
     * @return A list of pairs, while each pair contains the author name and the
     *         number of blog entries from this author published by the
     *         <code>blogAuthor</code>.
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
    public List<PostsPerAuthorPair> getBlogAuthors(AccountName blogAccount)
            throws SteemCommunicationException, SteemResponseException {
        return FollowApi.getBlogAuthors(communicationHandler, blogAccount);
    }

    // #########################################################################
    // ## MARKET HISTORY API ###################################################
    // #########################################################################

    /**
     * Use this method to receive statistic values of the internal SBD:STEEM
     * market for the last 24 hours.
     * 
     * @return The market ticker for the internal SBD:STEEM market.
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
    public GetTickerReturn getTicker() throws SteemCommunicationException, SteemResponseException {
        return MarketHistoryApi.getTicker(communicationHandler);
    }

    /**
     * Use this method to get the SBD and Steem volume that has been traded in
     * the past 24 hours at the internal SBD:STEEM market.
     * 
     * @return The market volume for the past 24 hours.
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
    public GetVolumeReturn getVolume() throws SteemCommunicationException, SteemResponseException {
        return MarketHistoryApi.getVolume(communicationHandler);
    }

    /**
     * Use this method to receive the current order book of the internal
     * SBD:STEEM market.
     * 
     * @param limit
     *            The number of orders to have on each side of the order book.
     *            Maximum is 500.
     * @return Returns the current order book for the internal SBD:STEEM market.
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
     * @throws InvalidParameterException
     *             If the limit is less than 0 or greater than 500.
     */
    public eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetOrderBookReturn getOrderBookUsingMarketApi(
            short limit) throws SteemCommunicationException, SteemResponseException {
        return MarketHistoryApi.getOrderBook(communicationHandler, new GetOrderBookArgs(UInteger.valueOf(limit)));
    }

    /**
     * Use this method to get the trade history of the internal SBD:STEEM market
     * between the defined <code>start</code> and <code>end</code> time.
     * 
     * @param start
     *            The start time of the trade history.
     * @param end
     *            The end time of the trade history.
     * @param limit
     *            The number of trades to return. Maximum is 1000.
     * @return A list of completed trades.
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
     * @throws InvalidParameterException
     *             If the limit is less than 0 or greater than 500.
     */
    public List<MarketTrade> getTradeHistory(TimePointSec start, TimePointSec end, UInteger limit)
            throws SteemCommunicationException, SteemResponseException {
        return MarketHistoryApi.getTradeHistory(communicationHandler, new GetTradeHistoryArgs(start, end, limit))
                .getTrades();
    }

    /**
     * Use this method to request the most recent trades for the internal
     * SBD:STEEM market. The number of results is limited by the
     * <code>limit</code> parameter.
     *
     * @param limit
     *            The number of trades to return. Maximum is 1000.
     * @return A list of completed trades.
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
     * @throws InvalidParameterException
     *             If the limit is less than 0 or greater than 500.
     */
    public List<MarketTrade> getRecentTrades(short limit) throws SteemCommunicationException, SteemResponseException {
        return MarketHistoryApi.getRecentTrades(communicationHandler, new GetRecentTradesArgs(UInteger.valueOf(limit)))
                .getTrades();
    }

    /**
     * Use this method to receive the market history for the internal SBD:STEEM
     * market.
     * 
     * @param bucketSeconds
     *            The size of buckets the history is broken into. The bucket
     *            size must be configured in the plugin options and can be
     *            requested using the {@link #getMarketHistoryBuckets()} method.
     * @param start
     *            The start time to get market history.
     * @param end
     *            The end time to get market history.
     * @return A list of market history {@link Bucket Bucket}s.
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
    public List<Bucket> getMarketHistory(long bucketSeconds, TimePointSec start, TimePointSec end)
            throws SteemCommunicationException, SteemResponseException {
        return MarketHistoryApi.getMarketHistory(communicationHandler,
                new GetMarketHistoryArgs(UInteger.valueOf(bucketSeconds), start, end)).getBuckets();
    }

    /**
     * Use this method to receive the bucket seconds being tracked by the node.
     * 
     * @return Returns the bucket seconds being tracked by the node.
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
    public List<UInteger> getMarketHistoryBuckets() throws SteemCommunicationException, SteemResponseException {
        return MarketHistoryApi.getMarketHistoryBuckets(communicationHandler).getBucketSizes();
    }

    // #########################################################################
    // ## TAGS API #############################################################
    // #########################################################################

    /**
     * Use this method to get detailed values and metrics for tags. The methods
     * accepts a String as a search pattern and a number to limit the results.
     * 
     * <b>Example</b>
     * <p>
     * <code>getTrendingTags(communicationHandler, "steem", 2);</code> <br>
     * Will return two tags whose name has the biggest match with the String
     * "steem". An example response could contain the metrics and values for the
     * tag "steem" and "steemit", while "steem" would be the first entry in the
     * list as it has a bigger match than "steemit".
     * </p>
     * 
     * @param firstTagPattern
     *            The search pattern used to build the resulting list of tags.
     * @param limit
     *            The maximum number of results.
     * @return A list of the tags. The first entry in the list is the tag that
     *         has the biggest match with the <code>firstTagPattern</code>.
     *         while the last tag in the last has the smallest match.
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
    public List<Tag> getTrendingTags(String firstTagPattern, int limit)
            throws SteemCommunicationException, SteemResponseException {
        return TagsApi.getTrendingTags(communicationHandler, firstTagPattern, limit);
    }

    // #########################################################################
    // ## WITNESS API ##########################################################
    // #########################################################################

    /**
     * 
     * @param getAccountBandwidthArgs
     * @return
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
    public Optional<AccountBandwidth> getAccountBandwidth(GetAccountBandwidthArgs getAccountBandwidthArgs)
            throws SteemCommunicationException, SteemResponseException {
        return WitnessApi.getAccountBandwidth(communicationHandler, getAccountBandwidthArgs).getBandwidth();
    }

    /**
     * 
     * @return
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
    public ReserveRatioObject getReserveRatio() throws SteemCommunicationException, SteemResponseException {
        return WitnessApi.getReserveRatio(communicationHandler);
    }

    // #########################################################################
    // ## UTILITY METHODS ######################################################
    // #########################################################################

    public static Asset steemToSbd(Price price, Asset steemAsset) {
        if (steemAsset == null || !steemAsset.getSymbol().equals(AssetSymbolType.STEEM)) {
            throw new InvalidParameterException("The asset needs be of SymbolType STEEM.");
        }

        if (price == null) {
            return new Asset(0, AssetSymbolType.SBD);
        }

        return price.multiply(steemAsset);
    }

    public static Asset sbdToSteem(Price price, Asset sbdAsset) {
        if (sbdAsset == null || !sbdAsset.getSymbol().equals(AssetSymbolType.SBD)) {
            throw new InvalidParameterException("The asset needs be of SymbolType STEEM.");
        }

        if (price == null) {
            return new Asset(0, AssetSymbolType.STEEM);
        }

        return price.multiply(sbdAsset);
    }

    /**
     * 
     * @param accountName
     * @return
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
    public double calculateRemainingBandwidth(AccountName accountName)
            throws SteemCommunicationException, SteemResponseException {
        // TODO: Use getReserveRatio instead.
        ExtendedDynamicGlobalProperties extendedDynamicGlobalProperties = CondenserApi
                .getDynamicGlobalProperties(communicationHandler);
        // TODO: Use getAccountBandwidth instead.
        List<ExtendedAccount> extendedAccounts = CondenserApi.getAccounts(communicationHandler);

        if (!extendedAccounts.contains(accountName)) {
            throw new InvalidParameterException("No account has been found matching the provided account name.");
        }
        return calculateRemainingBandwidth(extendedDynamicGlobalProperties,
                extendedAccounts.get(extendedAccounts.indexOf(accountName)));
    }

    /**
     * 
     * @param extendedDynamicGlobalProperties
     * @param account
     * @return
     */
    public static double calculateRemainingBandwidth(ExtendedDynamicGlobalProperties extendedDynamicGlobalProperties,
            Account account) {
        long maxVirtualBandwidth = extendedDynamicGlobalProperties.getMaxVirtualBandwidth().longValue();
        long secondsPerWeek = 60 * 60 * 24 * 7;
        long secondsSinceLastUpdate = (System.currentTimeMillis() / 1000)
                - account.getLastBandwidthUpdate().getDateTimeAsInt();
        long delta = ((secondsPerWeek - secondsSinceLastUpdate) * account.getAverageBandwidth()) / secondsPerWeek;

        long bandwidthOfTheUser = (account.getVestingShares().getAmount()
                + account.getReceivedVestingShares().getAmount()) * maxVirtualBandwidth
                / extendedDynamicGlobalProperties.getTotalVestingShares().getAmount();

        return bandwidthOfTheUser;
    }

    /*
     * TODO: Provided by mdfk -> Needs to adjusted to work with the new api
     * calls. private double getEarnedMoney(Comment comment) throws
     * SteemResponseException, SteemCommunicationException { rewardFund =
     * steemJ.getRewardFund(RewardFundType.POST); currentMedianHistoryPrice =
     * steemJ.getCurrentMedianHistoryPrice();
     * 
     * BigInteger rewardBalance =
     * BigInteger.valueOf(rewardFund.getRewardBalance().getAmount()); BigInteger
     * recentClaims = rewardFund.getRecentClaims(); BigInteger steemPrice =
     * BigInteger.valueOf(currentMedianHistoryPrice.getBase().getAmount());
     * BigInteger voteShares = BigInteger.valueOf(comment.getVoteRshares());
     * 
     * Log.v("RewardBalance", String.valueOf(rewardBalance));
     * Log.v("RecentClaims", String.valueOf(recentClaims)); Log.v("SteemPrice",
     * String.valueOf(steemPrice));
     * 
     * BigInteger earnedMoney =
     * voteShares.multiply(rewardBalance).divide(recentClaims).multiply(
     * steemPrice) .divide(BigInteger.valueOf(10000));
     * 
     * return earnedMoney.doubleValue() / 100; }
     */

    /**
     * Get the private and public key of a given type for the given
     * <code>account</code>
     * 
     * @param account
     *            The account name to generate the passwords for.
     * @param role
     *            The key type that should be generated.
     * @param steemPassword
     *            The password of the <code>account</code> valid for the Steem
     *            blockchain.
     * @return The requested key pair.
     */
    public static ImmutablePair<PublicKey, String> getPrivateKeyFromPassword(AccountName account, PrivateKeyType role,
            String steemPassword) {
        String seed = account.getName() + role.name().toLowerCase() + steemPassword;
        ECKey keyPair = ECKey.fromPrivate(Sha256Hash.hash(seed.getBytes(), 0, seed.length()));

        return new ImmutablePair<>(new PublicKey(keyPair), SteemJUtils.privateKeyToWIF(keyPair));
    }

    // #########################################################################
    // ## SIMPLIFIED OPERATIONS ################################################
    // #########################################################################

    /**
     * Use this method to up or down vote a post or a comment.
     * 
     * <b>Attention</b>
     * <ul>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * voting operation the private posting key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the voter - If
     * no default account has been provided, this method will throw an error. If
     * you do not want to configure the voter as a default account, please use
     * the {@link #vote(AccountName, AccountName, Permlink, short)} method and
     * provide the voter account separately.</li>
     * </ul>
     * 
     * @param postOrCommentAuthor
     *            The author of the post or the comment to vote for.
     *            <p>
     *            Example:<br>
     *            <code>new AccountName("dez1337")</code>
     *            </p>
     * @param postOrCommentPermlink
     *            The permanent link of the post or the comment to vote for.
     *            <p>
     *            Example:<br>
     *            <code>new Permlink("steemj-v0-2-4-has-been-released-update-9")</code>
     *            </p>
     * @param percentage
     *            Define how much of your voting power should be used to up or
     *            down vote the post or the comment.
     *            <ul>
     *            <li>If you want to up vote the post or the comment provide a
     *            value between 1 (1.0%) and 100 (100.0%).</li>
     *            <li>If you want to down vote (as known as <b>flag</b>) the
     *            post or the comment provide a value between -1 (-1.0%) and
     *            -100 (-100.0%).</li>
     *            </ul>
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void vote(AccountName postOrCommentAuthor, Permlink postOrCommentPermlink, short percentage)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(
                    "Using the upVote method without providing an account requires to have a default account configured.");
        }

        this.vote(SteemJConfig.getInstance().getDefaultAccount(), postOrCommentAuthor, postOrCommentPermlink,
                percentage);
    }

    /**
     * This method is equivalent to the
     * {@link #vote(AccountName, Permlink, short)} method, but lets you define
     * the <code>voter</code> account separately instead of using the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
     * 
     * @param voter
     *            The account that should vote for the post or the comment.
     *            <p>
     *            Example<br>
     *            <code>new AccountName("steemj")</code>
     *            </p>
     * @param postOrCommentAuthor
     *            The author of the post or the comment to vote for.
     *            <p>
     *            Example:<br>
     *            <code>new AccountName("dez1337")</code>
     *            </p>
     * @param postOrCommentPermlink
     *            The permanent link of the post or the comment to vote for.
     *            <p>
     *            Example:<br>
     *            <code>new Permlink("steemj-v0-2-4-has-been-released-update-9")</code>
     *            </p>
     * @param percentage
     *            Define how much of your voting power should be used to up or
     *            down vote the post or the comment.
     *            <ul>
     *            <li>If you want to up vote the post or the comment provide a
     *            value between 1 (1.0%) and 100 (100.0%).</li>
     *            <li>If you want to down vote (as known as <b>flag</b>) the
     *            post or the comment provide a value between -1 (-1.0%) and
     *            -100 (-100.0%).</li>
     *            </ul>
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void vote(AccountName voter, AccountName postOrCommentAuthor, Permlink postOrCommentPermlink,
            short percentage)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (percentage < -100 || percentage > 100 || percentage == 0) {
            throw new InvalidParameterException(
                    "Please provide a percentage between -100 and 100 which is also not 0.");
        }

        VoteOperation voteOperation = new VoteOperation(voter, postOrCommentAuthor, postOrCommentPermlink,
                (short) (percentage * 100));

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
    }

    /**
     * Use this method to cancel a previous vote for a post or a comment.
     * 
     * <b>Attention</b>
     * <ul>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * voting operation the private posting key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the voter - If
     * no default account has been provided, this method will throw an error. If
     * you do not want to configure the voter as a default account, please use
     * the {@link #vote(AccountName, AccountName, Permlink, short)} method and
     * provide the voter account separately.</li>
     * </ul>
     * 
     * @param postOrCommentAuthor
     *            The author of the post or the comment to cancel the vote for.
     *            <p>
     *            Example:<br>
     *            <code>new AccountName("dez1337")</code>
     *            </p>
     * @param postOrCommentPermlink
     *            The permanent link of the post or the comment to cancel the
     *            vote for.
     *            <p>
     *            Example:<br>
     *            <code>new Permlink("steemj-v0-2-4-has-been-released-update-9")</code>
     *            </p>
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void cancelVote(AccountName postOrCommentAuthor, Permlink postOrCommentPermlink)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(
                    "Using the cancelVote method without providing an account requires to have a default account configured.");
        }
        cancelVote(SteemJConfig.getInstance().getDefaultAccount(), postOrCommentAuthor, postOrCommentPermlink);
    }

    /**
     * This method is equivalent to the
     * {@link #cancelVote(AccountName, Permlink)} method, but lets you define
     * the <code>voter</code> account separately instead of using the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
     * 
     * @param voter
     *            The account that should vote for the post or the comment.
     *            <p>
     *            Example<br>
     *            <code>new AccountName("steemj")</code>
     *            </p>
     * @param postOrCommentAuthor
     *            The author of the post or the comment to vote for.
     *            <p>
     *            Example:<br>
     *            <code>new AccountName("dez1337")</code>
     *            </p>
     * @param postOrCommentPermlink
     *            The permanent link of the post or the comment to vote for.
     *            <p>
     *            Example:<br>
     *            <code>new Permlink("steemj-v0-2-4-has-been-released-update-9")</code>
     *            </p>
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void cancelVote(AccountName voter, AccountName postOrCommentAuthor, Permlink postOrCommentPermlink)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        VoteOperation voteOperation = new VoteOperation(voter, postOrCommentAuthor, postOrCommentPermlink, (short) 0);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
    }

    /**
     * Use this method to follow the <code>accountToFollow</code>.
     * 
     * <b>Attention</b>
     * <ul>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * follow operation the private posting key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account
     * that will follow the <code>accountToFollow</code> - If no default account
     * has been provided, this method will throw an error. If you do not want to
     * configure the following account as a default account, please use the
     * {@link #follow(AccountName, AccountName)} method and provide the
     * following account separately.</li>
     * </ul>
     * 
     * @param accountToFollow
     *            The account name of the account the
     *            {@link SteemJConfig#getDefaultAccount() DefaultAccount} should
     *            follow.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void follow(AccountName accountToFollow)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        follow(SteemJConfig.getInstance().getDefaultAccount(), accountToFollow);
    }

    /**
     * This method is equivalent to the {@link #follow(AccountName)} method, but
     * lets you define the <code>accountThatFollows</code> separately instead of
     * using the {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
     * 
     * @param accountThatFollows
     *            The account name of the account that will follow the
     *            <code>accountToFollow</code>.
     * @param accountToFollow
     *            The account name of the account the
     *            {@link SteemJConfig#getDefaultAccount() DefaultAccount} should
     *            follow.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void follow(AccountName accountThatFollows, AccountName accountToFollow)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(accountThatFollows);

        String id = "follow";
        String json = (new FollowOperation(accountThatFollows, accountToFollow, Arrays.asList(FollowType.BLOG)))
                .toJson();

        CustomJsonOperation customJsonOperation = new CustomJsonOperation(null, requiredPostingAuths, id, json);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
    }

    /**
     * Use this method to unfollow the <code>accountToUnfollow</code>.
     * 
     * <b>Attention</b>
     * <ul>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * unfollow operation the private posting key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account
     * that will no longer follow the <code>accountToFollow</code> - If no
     * default account has been provided, this method will throw an error. If
     * you do not want to configure the following account as a default account,
     * please use the {@link #follow(AccountName, AccountName)} method and
     * provide the following account separately.</li>
     * </ul>
     * 
     * @param accountToUnfollow
     *            The account name of the account the
     *            {@link SteemJConfig#getDefaultAccount() DefaultAccount} should
     *            no longer follow.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void unfollow(AccountName accountToUnfollow)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        unfollow(SteemJConfig.getInstance().getDefaultAccount(), accountToUnfollow);
    }

    /**
     * This method is equivalent to the {@link #unfollow(AccountName)} method,
     * but lets you define the <code>accountThatUnfollows</code> account
     * separately instead of using the {@link SteemJConfig#getDefaultAccount()
     * DefaultAccount}.
     * 
     * @param accountThatUnfollows
     *            The account name of the account that will no longer follow the
     *            <code>accountToUnfollow</code>.
     * @param accountToUnfollow
     *            The account name of the account the
     *            {@link SteemJConfig#getDefaultAccount() DefaultAccount} should
     *            no longer follow.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void unfollow(AccountName accountThatUnfollows, AccountName accountToUnfollow)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(accountThatUnfollows);

        String id = "follow";
        String json = (new FollowOperation(accountThatUnfollows, accountToUnfollow,
                Arrays.asList(FollowType.UNDEFINED))).toJson();

        CustomJsonOperation customJsonOperation = new CustomJsonOperation(null, requiredPostingAuths, id, json);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
    }

    /**
     * Use this method to reblog a post.
     * 
     * <b>Attention</b>
     * <ul>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * reblog operation the private posting key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} that will resteem
     * the post defined by the <code>accountThatResteemsThePost</code> and
     * <code>authorOfThePostToResteem</code> parameters. - If no default account
     * has been provided, this method will throw an error. If you do not want to
     * configure the following account as a default account, please use the
     * {@link #reblog(AccountName, Permlink)} method and provide the account who
     * wants to reblog the post separately.</li>
     * <li>Please be aware that there is no way to undo a reblog operation - If
     * a post has been reblogged it has been reblogged.
     * </ul>
     * 
     * @param authorOfThePostToReblog
     *            The author of the post to reblog.
     * @param permlinkOfThePostToReblog
     *            The permlink of the post to reblog.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void reblog(AccountName authorOfThePostToReblog, Permlink permlinkOfThePostToReblog)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        reblog(SteemJConfig.getInstance().getDefaultAccount(), authorOfThePostToReblog, permlinkOfThePostToReblog);
    }

    /**
     * This method is equivalent to the {@link #reblog(AccountName, Permlink)}
     * method, but lets you define the <code>accountThatReblogsThePost</code>
     * account separately instead of using the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
     * 
     * @param accountThatReblogsThePost
     *            The account who wants to reblog the post defined by the
     *            <code>accountThatResteemsThePost</code> and
     *            <code>authorOfThePostToResteem</code> parameters.
     * @param authorOfThePostToReblog
     *            The author of the post to reblog.
     * @param permlinkOfThePostToReblog
     *            The permlink of the post to reblog.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void reblog(AccountName accountThatReblogsThePost, AccountName authorOfThePostToReblog,
            Permlink permlinkOfThePostToReblog)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        ArrayList<Operation> operations = new ArrayList<>();

        ReblogOperation reblogOperation = new ReblogOperation(accountThatReblogsThePost, authorOfThePostToReblog,
                permlinkOfThePostToReblog);

        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(accountThatReblogsThePost);

        CustomJsonOperation customJsonReblogOperation = new CustomJsonOperation(null, requiredPostingAuths, "reblog",
                reblogOperation.toJson());

        operations.add(customJsonReblogOperation);

        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
    }

    /**
     * Use this method to create a new post.
     * 
     * <b>Attention</b>
     * <ul>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * create post operation the private posting key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>In case the {@link SteemJConfig#getSteemJWeight() SteemJWeight} is
     * set to a positive value this method will add a comment options operation.
     * Due to this, the {@link SteemJConfig#getSteemJWeight() SteemJWeight}
     * percentage will be paid to the SteemJ account.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account
     * that will publish the post - If no default account has been provided,
     * this method will throw an error. If you do not want to configure the
     * author account as a default account, please use the
     * {@link #createPost(AccountName, String, String, String[])} method and
     * provide the author account separately.</li>
     * </ul>
     * 
     * @param title
     *            The title of the post to publish.
     * @param content
     *            The content of the post to publish.
     * @param tags
     *            A list of tags while the first tag in this list is the main
     *            tag. You can provide up to five tags but at least one needs to
     *            be provided.
     * @return The {@link CommentOperation} which has been created within this
     *         method. The returned Operation allows you to access the generated
     *         values.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public CommentOperation createPost(String title, String content, String[] tags)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        return createPost(SteemJConfig.getInstance().getDefaultAccount(), title, content, tags);
    }

    /**
     * This method is equivalent to the
     * {@link #createPost(String, String, String[])} method, but lets you define
     * the <code>authorThatPublishsThePost</code> account separately instead of
     * using the {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
     * 
     * @param authorThatPublishsThePost
     *            The account who wants to publish the post.
     * @param title
     *            The title of the post to publish.
     * @param content
     *            The content of the post to publish.
     * @param tags
     *            A list of tags while the first tag in this list is the main
     *            tag. You can provide up to five tags but at least one needs to
     *            be provided.
     * @return The {@link CommentOperation} which has been created within this
     *         method. The returned Operation allows you to access the generated
     *         values.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public CommentOperation createPost(AccountName authorThatPublishsThePost, String title, String content,
            String[] tags)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (tags == null || tags.length < 1 || tags.length > 5) {
            throw new InvalidParameterException(TAG_ERROR_MESSAGE);
        }
        ArrayList<Operation> operations = new ArrayList<>();

        // Generate the permanent link from the title by replacing all unallowed
        // characters.
        Permlink permlink = new Permlink(CondenserUtils.createPermlinkString(title));
        // On new posts the parentPermlink is the main tag.
        Permlink parentPermlink = new Permlink(tags[0]);
        // One new posts the parentAuthor is empty.
        AccountName parentAuthor = new AccountName("");

        String jsonMetadata = CondenserUtils.generateSteemitMetadata(content, tags,
                SteemJConfig.getSteemJAppName() + "/" + SteemJConfig.getSteemJVersion(), MARKDOWN);

        CommentOperation commentOperation = new CommentOperation(parentAuthor, parentPermlink,
                authorThatPublishsThePost, permlink, title, content, jsonMetadata);

        operations.add(commentOperation);

        boolean allowVotes = true;
        boolean allowCurationRewards = true;
        short percentSteemDollars = (short) 10000;
        Asset maxAcceptedPayout = new Asset(1000000000, AssetSymbolType.SBD);

        CommentOptionsOperation commentOptionsOperation;
        // Only add a BeneficiaryRouteType if it makes sense.
        if (SteemJConfig.getInstance().getSteemJWeight() > 0) {
            BeneficiaryRouteType beneficiaryRouteType = new BeneficiaryRouteType(SteemJConfig.getSteemJAccount(),
                    SteemJConfig.getInstance().getSteemJWeight());

            ArrayList<BeneficiaryRouteType> beneficiaryRouteTypes = new ArrayList<>();
            beneficiaryRouteTypes.add(beneficiaryRouteType);

            CommentPayoutBeneficiaries commentPayoutBeneficiaries = new CommentPayoutBeneficiaries();
            commentPayoutBeneficiaries.setBeneficiaries(beneficiaryRouteTypes);

            ArrayList<CommentOptionsExtension> commentOptionsExtensions = new ArrayList<>();
            commentOptionsExtensions.add(commentPayoutBeneficiaries);

            commentOptionsOperation = new CommentOptionsOperation(authorThatPublishsThePost, permlink,
                    maxAcceptedPayout, percentSteemDollars, allowVotes, allowCurationRewards, commentOptionsExtensions);
        } else {
            commentOptionsOperation = new CommentOptionsOperation(authorThatPublishsThePost, permlink,
                    maxAcceptedPayout, percentSteemDollars, allowVotes, allowCurationRewards, null);
        }

        operations.add(commentOptionsOperation);

        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);

        return commentOperation;
    }

    /**
     * Use this method to create a new comment.
     * 
     * <b>Attention</b>
     * <ul>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * create comment operation the private posting key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>In case the {@link SteemJConfig#getSteemJWeight() SteemJWeight} is
     * set to a positive value this method will add a comment options operation.
     * Due to this, the {@link SteemJConfig#getSteemJWeight() SteemJWeight}
     * percentage will be paid to the SteemJ account.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account
     * that will publish the comment - If no default account has been provided,
     * this method will throw an error. If you do not want to configure the
     * author account as a default account, please use the
     * {@link #createComment(AccountName, AccountName, Permlink, String, String[])}
     * method and provide the author account separately.</li>
     * </ul>
     * 
     * @param authorOfThePostOrCommentToReplyTo
     *            The author of the post or comment to reply to.
     * @param permlinkOfThePostOrCommentToReplyTo
     *            The permlink of the post or comment to reply to.
     * @param content
     *            The content to publish.
     * @param tags
     *            A list of tags while the first tag in this list is the main
     *            tag. You can provide up to five tags but at least one needs to
     *            be provided.
     * @return The {@link CommentOperation} which has been created within this
     *         method. The returned Operation allows you to access the generated
     *         values.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public CommentOperation createComment(AccountName authorOfThePostOrCommentToReplyTo,
            Permlink permlinkOfThePostOrCommentToReplyTo, String content, String[] tags)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        return createComment(SteemJConfig.getInstance().getDefaultAccount(), authorOfThePostOrCommentToReplyTo,
                permlinkOfThePostOrCommentToReplyTo, content, tags);
    }

    /**
     * This method is equivalent to the
     * {@link #createComment(AccountName, Permlink, String, String[])} method,
     * but lets you define the <code>authorThatPublishsTheComment</code> account
     * separately instead of using the {@link SteemJConfig#getDefaultAccount()
     * DefaultAccount}.
     * 
     * @param authorThatPublishsTheComment
     *            The account that wants to publish the comment.
     * @param authorOfThePostOrCommentToReplyTo
     *            The author of the post or comment to reply to.
     * @param permlinkOfThePostOrCommentToReplyTo
     *            The permlink of the post or comment to reply to.
     * @param content
     *            The content to publish.
     * @param tags
     *            A list of tags while the first tag in this list is the main
     *            tag. You can provide up to five tags but at least one needs to
     *            be provided.
     * @return The {@link CommentOperation} which has been created within this
     *         method. The returned Operation allows you to access the generated
     *         values.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public CommentOperation createComment(AccountName authorThatPublishsTheComment,
            AccountName authorOfThePostOrCommentToReplyTo, Permlink permlinkOfThePostOrCommentToReplyTo, String content,
            String[] tags)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (tags == null || tags.length < 1 || tags.length > 5) {
            throw new InvalidParameterException(TAG_ERROR_MESSAGE);
        }
        ArrayList<Operation> operations = new ArrayList<>();

        // Generate the permanent link by adding the current timestamp and a
        // UUID.
        Permlink permlink = new Permlink("re-" + authorOfThePostOrCommentToReplyTo.getName().replaceAll("\\.", "") + "-"
                + permlinkOfThePostOrCommentToReplyTo.getLink() + "-" + System.currentTimeMillis() + "t"
                + UUID.randomUUID().toString() + "uid");

        String jsonMetadata = CondenserUtils.generateSteemitMetadata(content, tags,
                SteemJConfig.getSteemJAppName() + "/" + SteemJConfig.getSteemJVersion(), MARKDOWN);

        CommentOperation commentOperation = new CommentOperation(authorOfThePostOrCommentToReplyTo,
                permlinkOfThePostOrCommentToReplyTo, authorThatPublishsTheComment, permlink, "", content, jsonMetadata);

        operations.add(commentOperation);

        boolean allowVotes = true;
        boolean allowCurationRewards = true;
        short percentSteemDollars = (short) 10000;
        Asset maxAcceptedPayout = new Asset(1000000000, AssetSymbolType.SBD);

        CommentOptionsOperation commentOptionsOperation;
        // Only add a BeneficiaryRouteType if it makes sense.
        if (SteemJConfig.getInstance().getSteemJWeight() > 0) {
            BeneficiaryRouteType beneficiaryRouteType = new BeneficiaryRouteType(SteemJConfig.getSteemJAccount(),
                    SteemJConfig.getInstance().getSteemJWeight());

            ArrayList<BeneficiaryRouteType> beneficiaryRouteTypes = new ArrayList<>();
            beneficiaryRouteTypes.add(beneficiaryRouteType);

            CommentPayoutBeneficiaries commentPayoutBeneficiaries = new CommentPayoutBeneficiaries();
            commentPayoutBeneficiaries.setBeneficiaries(beneficiaryRouteTypes);

            ArrayList<CommentOptionsExtension> commentOptionsExtensions = new ArrayList<>();
            commentOptionsExtensions.add(commentPayoutBeneficiaries);

            commentOptionsOperation = new CommentOptionsOperation(authorThatPublishsTheComment, permlink,
                    maxAcceptedPayout, percentSteemDollars, allowVotes, allowCurationRewards, commentOptionsExtensions);
        } else {
            commentOptionsOperation = new CommentOptionsOperation(authorThatPublishsTheComment, permlink,
                    maxAcceptedPayout, percentSteemDollars, allowVotes, allowCurationRewards, null);
        }

        operations.add(commentOptionsOperation);

        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);

        return commentOperation;
    }

    /**
     * Use this method to update an existing post.
     * 
     * <b>Attention</b>
     * <ul>
     * <li>Updating a post only works if Steem can identify the existing post -
     * If this is not the case, this operation will create a new post instead of
     * updating the existing one. The identification is based on the
     * <code>permlinkOfThePostToUpdate</code> and the first tag of the
     * <code>tags</code> array to be the same ones as of the post to update.
     * </li>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * update post operation the private posting key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the author of
     * the post to update - If no default account has been provided, this method
     * will throw an error. If you do not want to configure the author account
     * as a default account, please use the
     * {@link #updatePost(AccountName, Permlink, String, String, String[])}
     * method and provide the author account separately.</li>
     * </ul>
     * 
     * @param permlinkOfThePostToUpdate
     *            The permlink of the post to update. <b>Attention</b> If the
     *            permlink is not configured currently, SteemJ could accidently
     *            create a new post instead of updating an existing one.
     * @param title
     *            The new title of the post to set.
     * @param content
     *            The new content of the post to set.
     * @param tags
     *            The new tags of the post. <b>Attention</b> The first tag still
     *            needs to be the same as before otherwise SteemJ could
     *            accidently create a new post instead of updating an existing
     *            one.
     * @return The {@link CommentOperation} which has been created within this
     *         method. The returned Operation allows you to access the generated
     *         values.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public CommentOperation updatePost(Permlink permlinkOfThePostToUpdate, String title, String content, String[] tags)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        return updatePost(SteemJConfig.getInstance().getDefaultAccount(), permlinkOfThePostToUpdate, title, content,
                tags);
    }

    /**
     * This method is equivalent to the
     * {@link #updatePost(Permlink, String, String, String[])} method, but lets
     * you define the <code>authorOfThePostToUpdate</code> account separately
     * instead of using the {@link SteemJConfig#getDefaultAccount()
     * DefaultAccount}.
     * 
     * @param authorOfThePostToUpdate
     *            The account that wants to perform the update. In most cases,
     *            this should be the author of the already existing post.
     * @param permlinkOfThePostToUpdate
     *            The permlink of the post to update. <b>Attention</b> If the
     *            permlink is not configured currently, SteemJ could accidently
     *            create a new post instead of updating an existing one.
     * @param title
     *            The new title of the post to set.
     * @param content
     *            The new content of the post to set.
     * @param tags
     *            The new tags of the post. <b>Attention</b> The first tag still
     *            needs to be the same as before otherwise SteemJ could
     *            accidently create a new post instead of updating an existing
     *            one.
     * @return The {@link CommentOperation} which has been created within this
     *         method. The returned Operation allows you to access the generated
     *         values.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public CommentOperation updatePost(AccountName authorOfThePostToUpdate, Permlink permlinkOfThePostToUpdate,
            String title, String content, String[] tags)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (tags == null || tags.length < 1 || tags.length > 5) {
            throw new InvalidParameterException(TAG_ERROR_MESSAGE);
        }

        ArrayList<Operation> operations = new ArrayList<>();
        AccountName parentAuthor = new AccountName("");
        Permlink parentPermlink = new Permlink(tags[0]);

        String jsonMetadata = CondenserUtils.generateSteemitMetadata(content, tags,
                SteemJConfig.getSteemJAppName() + "/" + SteemJConfig.getSteemJVersion(), MARKDOWN);

        CommentOperation commentOperation = new CommentOperation(parentAuthor, parentPermlink, authorOfThePostToUpdate,
                permlinkOfThePostToUpdate, title, content, jsonMetadata);

        operations.add(commentOperation);

        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);

        return commentOperation;
    }

    /**
     * Use this method to update an existing comment.
     * 
     * <b>Attention</b>
     * <ul>
     * <li>Updating a comment only works if Steem can identify the existing
     * comment - If this is not the case, this operation will create a new
     * comment instead of updating the existing one. The identification is based
     * on the <code>originalPermlinkOfYourComment</code>, the
     * <code>parentAuthor</code>, the <code>parentPermlink</code> and the first
     * tag of the <code>tags</code> array to be the same ones as of the post to
     * update.</li>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * update comment operation the private posting key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the author of
     * the comment to update - If no default account has been provided, this
     * method will throw an error. If you do not want to configure the author
     * account as a default account, please use the
     * {@link #updateComment(AccountName, AccountName, Permlink, Permlink, String, String[])}
     * method and provide the author account separately.</li>
     * </ul>
     * 
     * @param parentAuthor
     *            The author of the post or comment that you initially replied
     *            to.
     * @param parentPermlink
     *            The permlink of the post or comment that you initially replied
     *            to.
     * @param originalPermlinkOfTheCommentToUpdate
     *            The permlink of the comment to update.
     * @param content
     *            The new content of the comment to set.
     * @param tags
     *            The new tags of the comment. <b>Attention</b> The first tag
     *            still needs to be the same as before otherwise SteemJ could
     *            accidently create a new comment instead of updating an
     *            existing one.
     * @return The {@link CommentOperation} which has been created within this
     *         method. The returned Operation allows you to access the generated
     *         values.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public CommentOperation updateComment(AccountName parentAuthor, Permlink parentPermlink,
            Permlink originalPermlinkOfTheCommentToUpdate, String content, String[] tags)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        return updateComment(SteemJConfig.getInstance().getDefaultAccount(), parentAuthor, parentPermlink,
                originalPermlinkOfTheCommentToUpdate, content, tags);
    }

    /**
     * This method is like the
     * {@link #updateComment(AccountName, Permlink, Permlink, String, String[])}
     * method, but allows you to define the
     * <code>originalAuthorOfTheCommentToUpdate</code> account separately
     * instead of using the {@link SteemJConfig#getDefaultAccount()
     * DefaultAccount}.
     * 
     * @param originalAuthorOfTheCommentToUpdate
     *            The account that wants to perform the update. In most cases,
     *            this should be the author of the already existing comment.
     * @param parentAuthor
     *            The author of the post or comment that you initially replied
     *            to.
     * @param parentPermlink
     *            The permlink of the post or comment that you initially replied
     *            to.
     * @param originalPermlinkOfTheCommentToUpdate
     *            The permlink of the comment to update.
     * @param content
     *            The new content of the comment to set.
     * @param tags
     *            The new tags of the comment. <b>Attention</b> The first tag
     *            still needs to be the same as before otherwise SteemJ could
     *            accidently create a new comment instead of updating an
     *            existing one.
     * @return The {@link CommentOperation} which has been created within this
     *         method. The returned Operation allows you to access the generated
     *         values.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public CommentOperation updateComment(AccountName originalAuthorOfTheCommentToUpdate, AccountName parentAuthor,
            Permlink parentPermlink, Permlink originalPermlinkOfTheCommentToUpdate, String content, String[] tags)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (tags == null || tags.length < 1 || tags.length > 5) {
            throw new InvalidParameterException(TAG_ERROR_MESSAGE);
        }
        ArrayList<Operation> operations = new ArrayList<>();

        String jsonMetadata = CondenserUtils.generateSteemitMetadata(content, tags,
                SteemJConfig.getSteemJAppName() + "/" + SteemJConfig.getSteemJVersion(), MARKDOWN);

        CommentOperation commentOperation = new CommentOperation(parentAuthor, parentPermlink,
                originalAuthorOfTheCommentToUpdate, originalPermlinkOfTheCommentToUpdate, "", content, jsonMetadata);

        operations.add(commentOperation);
        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);

        return commentOperation;
    }

    /**
     * Use this method to remove a comment or a post.
     * 
     * <b>Attention</b>
     * <ul>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * voting operation the private posting key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the author of
     * the comment or post to remove - If no default account has been provided,
     * this method will throw an error. If you do not want to configure the
     * author as a default account, please use the
     * {@link #deletePostOrComment(AccountName, Permlink)} method and provide
     * the author account separately.</li>
     * </ul>
     * 
     * 
     * @param postOrCommentPermlink
     *            The permanent link of the post or the comment to delete.
     *            <p>
     *            Example:<br>
     *            <code>new Permlink("steemj-v0-2-4-has-been-released-update-9")</code>
     *            </p>
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void deletePostOrComment(Permlink postOrCommentPermlink)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        deletePostOrComment(SteemJConfig.getInstance().getDefaultAccount(), postOrCommentPermlink);
    }

    /**
     * This method is like the {@link #deletePostOrComment(Permlink)} method,
     * but allows you to define the author account separately instead of using
     * the {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
     * 
     * @param postOrCommentAuthor
     *            The author of the post or the comment to vote for.
     *            <p>
     *            Example:<br>
     *            <code>new AccountName("dez1337")</code>
     *            </p>
     * @param postOrCommentPermlink
     *            The permanent link of the post or the comment to delete.
     *            <p>
     *            Example:<br>
     *            <code>new Permlink("steemj-v0-2-4-has-been-released-update-9")</code>
     *            </p>
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void deletePostOrComment(AccountName postOrCommentAuthor, Permlink postOrCommentPermlink)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        DeleteCommentOperation deleteCommentOperation = new DeleteCommentOperation(postOrCommentAuthor,
                postOrCommentPermlink);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(deleteCommentOperation);

        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
    }

    /**
     * Transfer the currency of your choice from
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} to recipient.
     * Amount is automatically converted from normalized representation to base
     * representation. For example, to transfer 1.00 SBD to another account,
     * simply use:
     * <code>SteemJ.transfer(new AccountName("accountb"), new Asset(1.0, AssetSymbolType.SBD), "My memo");</code>
     *
     * <b>Attention</b>
     * <ul>
     * <li>This method will write data on the blockchain. As all writing
     * operations, a private key is required to sign the transaction. For a
     * transfer operation the private active key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account to
     * transfer from. If no default account has been provided, this method will
     * throw an error. If you do not want to configure the following account as
     * a default account, please use the
     * {@link #transfer(AccountName, AccountName, Asset, String)} method and
     * provide the <code>from</code> account separately.</li>
     * </ul>
     *
     * @param to
     *            The account name of the account the
     *            {@link SteemJConfig#getDefaultAccount() DefaultAccount} should
     *            transfer currency to.
     * @param amount
     *            An {@link Asset} object containing the Asset type (see
     *            {@link eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType}
     *            and the amount to transfer.
     * @param memo
     *            Message include with transfer (255 char max)
     * @return The TransferOperation broadcast.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public TransferOperation transfer(AccountName to, Asset amount, String memo)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        return transfer(SteemJConfig.getInstance().getDefaultAccount(), to, amount, memo);
    }

    /**
     * Transfer currency from specified account to recipient. Amount is
     * automatically converted from normalized representation to base
     * representation. For example, to transfer 1.00 SBD to another account,
     * simply use:
     * <code>SteemJ.transfer(new AccountName("accounta"), new AccountName("accountb"), AssetSymbolType.SBD, 1.0, "My memo");</code>
     *
     * <b>Attention</b> This method will write data on the blockchain. As all
     * writing operations, a private key is required to sign the transaction.
     * For a transfer operation the private active key of the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
     * configured in the {@link SteemJConfig#getPrivateKeyStorage()
     * PrivateKeyStorage}.
     *
     * @param from
     *            The account from which to transfer currency.
     * @param to
     *            The account to which to transfer currency.
     * @param amount
     *            An {@link Asset} object containing the Asset type (see
     *            {@link eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType}
     *            and the amount to transfer.
     * @param memo
     *            Message include with transfer (255 char max)
     * @return The TransferOperation broadcast.
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
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public TransferOperation transfer(AccountName from, AccountName to, Asset amount, String memo)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        TransferOperation transferOperation = new TransferOperation(from, to, amount, memo);
        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferOperation);
        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();
        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);
        signedTransaction.sign();
        this.broadcastTransaction(signedTransaction);
        return transferOperation;
    }

    /**
     * Claim all available Steem, SDB and VEST (Steam Power) rewards for the
     * default account.
     *
     * <b>Attention</b>
     * <ul>
     * <li>This method will write data on the blockchain if a reward balance is
     * available to be claimed. As with all writing operations, a private key is
     * required to sign the transaction. See
     * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.</li>
     * <li>This method will automatically use the
     * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account
     * that will follow the <code>accountToFollow</code> - If no default account
     * has been provided, this method will throw an error. If you do not want to
     * configure the following account as a default account, please use the
     * {@link #follow(AccountName, AccountName)} method and provide the
     * following account separately.</li>
     * </ul>
     *
     * @return The ClaimOperation for reward balances found. This will only have
     *         been broadcast if one of the balances is non-zero.
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
     *             If there is a problem while signing the transaction.
     */
    public ClaimRewardBalanceOperation claimRewards()
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        return claimRewards(SteemJConfig.getInstance().getDefaultAccount());
    }

    /**
     * Claim all available Steem, SDB and VEST (Steam Power) rewards for the
     * specified account.
     *
     * <b>Attention</b> This method will write data on the blockchain if a
     * reward balance is available to be claimed. As with all writing
     * operations, a private key is required to sign the transaction. See
     * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.
     *
     * @param accountName
     *            The account to claim rewards for.
     * @return The ClaimOperation for reward balances found. This will only have
     *         been broadcast if one of the balances is non-zero.
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
     *             If there is a problem while signing the transaction.
     */
    public ClaimRewardBalanceOperation claimRewards(AccountName accountName)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        // Get extended account info to determine reward balances
        ExtendedAccount extendedAccount = this.getAccounts(Lists.newArrayList(accountName)).get(0);
        Asset steemReward = extendedAccount.getRewardSteemBalance();
        Asset sbdReward = extendedAccount.getRewardSdbBalance();
        Asset vestingReward = extendedAccount.getRewardVestingBalance();

        // Create claim operation based on available reward balances
        ClaimRewardBalanceOperation claimOperation = new ClaimRewardBalanceOperation(accountName, steemReward,
                sbdReward, vestingReward);

        // Broadcast claim operation if there are any balances available
        if (steemReward.getAmount() > 0 || sbdReward.getAmount() > 0 || vestingReward.getAmount() > 0) {
            ArrayList<Operation> operations = new ArrayList<>();
            operations.add(claimOperation);
            DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();
            SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                    null);
            signedTransaction.sign();
            this.broadcastTransaction(signedTransaction);
        }

        return claimOperation;
    }

    /**
     * Use this method to delegate Steem Power (Vesting Shares) from the default
     * account to the <code>delegatee</code> account. The vesting shares are
     * still owned by the original account, but content voting rights and
     * bandwidth allocation are transferred to the receiving account. This sets
     * the delegation to `vesting_shares`, increasing it or decreasing it as
     * needed. (i.e. a delegation of 0 removes the delegation) When a delegation
     * is removed the shares are placed in limbo for a week to prevent a satoshi
     * of VESTS from voting on the same content twice.
     * 
     * @param delegatee
     *            The account that the vesting shares are delegated to.
     * @param vestingShares
     *            The amount of vesting shares.
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
     *             If there is a problem while signing the transaction.
     */
    public void delegateVestingShares(AccountName delegatee, Asset vestingShares)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
        }

        delegateVestingShares(SteemJConfig.getInstance().getDefaultAccount(), delegatee, vestingShares);
    }

    /**
     * This method is like the
     * {@link #delegateVestingShares(AccountName, AccountName, Asset)} method,
     * but allows you to define the author account separately instead of using
     * the {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
     * 
     * @param delegator
     *            The account that will delegate the vesting shares.
     * @param delegatee
     *            The account that the vesting shares are delegated to.
     * @param vestingShares
     *            The amount of vesting shares.
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
     *             If there is a problem while signing the transaction.
     */
    public void delegateVestingShares(AccountName delegator, AccountName delegatee, Asset vestingShares)
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        DelegateVestingSharesOperation delegateVestingSharesOperation = new DelegateVestingSharesOperation(delegator,
                delegatee, vestingShares);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(delegateVestingSharesOperation);
        DynamicGlobalProperty globalProperties = this.getDynamicGlobalProperties();
        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);
        signedTransaction.sign();
        this.broadcastTransaction(signedTransaction);
    }
}
