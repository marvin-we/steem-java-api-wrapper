package eu.bittrade.libs.steemj;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;

import eu.bittrade.crypto.core.ECKey;
import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.apis.database.DatabaseApi;
import eu.bittrade.libs.steemj.apis.database.models.state.Discussion;
import eu.bittrade.libs.steemj.apis.database.models.state.State;
import eu.bittrade.libs.steemj.apis.follow.FollowApi;
import eu.bittrade.libs.steemj.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.apis.follow.model.AccountReputation;
import eu.bittrade.libs.steemj.apis.follow.model.BlogEntry;
import eu.bittrade.libs.steemj.apis.follow.model.CommentBlogEntry;
import eu.bittrade.libs.steemj.apis.follow.model.CommentFeedEntry;
import eu.bittrade.libs.steemj.apis.follow.model.FeedEntry;
import eu.bittrade.libs.steemj.apis.follow.model.FollowApiObject;
import eu.bittrade.libs.steemj.apis.follow.model.FollowCountApiObject;
import eu.bittrade.libs.steemj.apis.follow.model.PostsPerAuthorPair;
import eu.bittrade.libs.steemj.apis.follow.models.operations.FollowOperation;
import eu.bittrade.libs.steemj.apis.follow.models.operations.ReblogOperation;
import eu.bittrade.libs.steemj.apis.login.LoginApi;
import eu.bittrade.libs.steemj.apis.login.models.SteemVersionInfo;
import eu.bittrade.libs.steemj.apis.market.history.MarketHistoryApi;
import eu.bittrade.libs.steemj.apis.market.history.model.Bucket;
import eu.bittrade.libs.steemj.apis.market.history.model.MarketTicker;
import eu.bittrade.libs.steemj.apis.market.history.model.MarketTrade;
import eu.bittrade.libs.steemj.apis.market.history.model.MarketVolume;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.AccountVote;
import eu.bittrade.libs.steemj.base.models.AppliedOperation;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.BeneficiaryRouteType;
import eu.bittrade.libs.steemj.base.models.BlockHeader;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.CommentOptionsExtension;
import eu.bittrade.libs.steemj.base.models.CommentPayoutBeneficiaries;
import eu.bittrade.libs.steemj.base.models.Config;
import eu.bittrade.libs.steemj.base.models.DiscussionQuery;
import eu.bittrade.libs.steemj.base.models.DynamicGlobalProperty;
import eu.bittrade.libs.steemj.base.models.ExtendedAccount;
import eu.bittrade.libs.steemj.base.models.ExtendedLimitOrder;
import eu.bittrade.libs.steemj.base.models.FeedHistory;
import eu.bittrade.libs.steemj.base.models.LiquidityBalance;
import eu.bittrade.libs.steemj.base.models.OrderBook;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.RewardFund;
import eu.bittrade.libs.steemj.base.models.ScheduledHardfork;
import eu.bittrade.libs.steemj.base.models.SignedBlock;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.Tag;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.base.models.VoteState;
import eu.bittrade.libs.steemj.base.models.Witness;
import eu.bittrade.libs.steemj.base.models.WitnessSchedule;
import eu.bittrade.libs.steemj.base.models.operations.ClaimRewardBalanceOperation;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.base.models.operations.CommentOptionsOperation;
import eu.bittrade.libs.steemj.base.models.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.base.models.operations.DelegateVestingSharesOperation;
import eu.bittrade.libs.steemj.base.models.operations.DeleteCommentOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.base.models.operations.TransferOperation;
import eu.bittrade.libs.steemj.base.models.operations.VoteOperation;
import eu.bittrade.libs.steemj.communication.BlockAppliedCallback;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.DiscussionSortType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.RequestMethods;
import eu.bittrade.libs.steemj.enums.RewardFundType;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.exceptions.SteemTransformationException;
import eu.bittrade.libs.steemj.plugins.network.broadcast.api.NetworkBroadcastApi;
import eu.bittrade.libs.steemj.plugins.network.broadcast.model.BroadcastTransactionSynchronousReturn;
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public SteemJ() throws SteemCommunicationException, SteemResponseException {
		this.communicationHandler = new CommunicationHandler();

		if (!("").equals(String.valueOf(SteemJConfig.getInstance().getApiPassword()))
				&& !SteemJConfig.getInstance().getApiUsername().isEmpty()) {

			LOGGER.info("Credentials have been provided - Trying to login.");
			if (login(SteemJConfig.getInstance().getApiUsername(),
					String.valueOf(SteemJConfig.getInstance().getApiPassword()))) {
				LOGGER.info("Login successful.");
			} else {
				LOGGER.error("Login failed.");
			}
		}

		/*
		 * This API call is no longer supported.
		 */
		// if
		// (SteemJConfig.getInstance().getSynchronizationLevel().equals(SynchronizationType.FULL)
		// ||
		// SteemJConfig.getInstance().getSynchronizationLevel().equals(SynchronizationType.APIS_ONLY))
		// {
		// for (SteemApiType steemApi : SteemApiType.values()) {
		// if (getApiByName(steemApi.toString().toLowerCase()) == null) {
		// LOGGER.debug("The {} is not published by the configured node.", steemApi);
		// }
		// }
		// }
	}

	// #########################################################################
	// ## NETWORK BROADCAST API ################################################
	// #########################################################################

	/**
	 * Broadcast a transaction on the Steem blockchain. This method will validate
	 * the transaction and return immediately. Please notice that this does not mean
	 * that the operation has been accepted and has been processed. If you want to
	 * make sure that this is the case use the
	 * {@link #broadcastTransactionSynchronous(SignedTransaction)} method.
	 * 
	 * @param transaction
	 *            The {@link SignedTransaction} object to broadcast.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * Broadcast a transaction on the Steem blockchain. This method will validate
	 * the transaction and return after it has been accepted and applied.
	 * 
	 * @param transaction
	 *            The {@link SignedTransaction} object to broadcast.
	 * @return A {@link BroadcastTransactionSynchronousReturn} object providing
	 *         information about the block in which the transaction has been
	 *         applied.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public void broadcastBlock(SignedBlock signedBlock) throws SteemCommunicationException, SteemResponseException {
		NetworkBroadcastApi.broadcastBlock(communicationHandler, signedBlock);
	}

	// #########################################################################
	// ## LOGIN API ############################################################
	// #########################################################################

	/**
	 * Use this method to authenticate to the RPC server.
	 * 
	 * <p>
	 * When setting up a Steem Node the operator has the possibility to protect
	 * specific APIs with a user name and a password. Requests to secured
	 * API-Endpoints require a login before being accessed. This can be achieved by
	 * using this method.
	 * </p>
	 * 
	 * @param accountName
	 *            The user name used to login.
	 * @param password
	 *            The password that belongs to the <code>accountName</code>.
	 * @return <code>true</code> if the login was successful, otherwise
	 *         <code>false</code>.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public boolean login(AccountName accountName, String password)
			throws SteemCommunicationException, SteemResponseException {
		return LoginApi.login(communicationHandler, accountName, password);
	}

	/**
	 * Use this method to receive the ID of an API or <code>null</code> if an API
	 * with the <code>apiName</code> does not exist or is disabled.
	 * 
	 * @deprecated This API call is no longer supported.
	 * 
	 * @param apiName
	 *            The name of the API.
	 * @return The ID for the given API name or <code>null</code>, if the API is not
	 *         active or does not exist.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * 
	 */
	// @Deprecated
	// public Integer getApiByName(String apiName) throws
	// SteemCommunicationException, SteemResponseException {
	// return LoginApi.getApiByName(communicationHandler, apiName);
	// }

	/**
	 * Use this method to get detailed information about the Steem version of the
	 * node SteemJ is connected to.
	 *
	 * @return A {@link SteemVersionInfo} object which contains detailed information
	 *         about the Steem version the node is running.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public SteemVersionInfo getVersion() throws SteemCommunicationException, SteemResponseException {
		return LoginApi.getVersion(communicationHandler);
	}

	// #########################################################################
	// ## DATABASE API #########################################################
	// #########################################################################

	/**
	 * Use this method to register a callback method that is called whenever a new
	 * block has been applied.
	 * 
	 * <p>
	 * Please <b>Notice</b>, that there can only be one active Callback. If you call
	 * this method multiple times with different callback methods, only the last one
	 * will be called.
	 * 
	 * Beside that there is currently no way to cancel a subscription. Once you've
	 * registered a callback it will be called until the connection has been closed.
	 * </p>
	 * 
	 * @param blockAppliedCallback
	 *            A class implementing the
	 *            {@link eu.bittrade.libs.steemj.communication.BlockAppliedCallback
	 *            BlockAppliedCallback}.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public void setBlockAppliedCallback(BlockAppliedCallback blockAppliedCallback)
			throws SteemCommunicationException, SteemResponseException {
		DatabaseApi.setBlockAppliedCallback(communicationHandler, blockAppliedCallback);
	}

	/**
	 * Use this method to get detailed values and metrics for tags. The methods
	 * accepts a String as a search pattern and a number to limit the results.
	 * 
	 * <b>Example</b>
	 * <p>
	 * <code>getTrendingTags(communicationHandler, "steem", 2);</code> <br>
	 * Will return two tags whose name has the biggest match with the String
	 * "steem". An example response could contain the metrics and values for the tag
	 * "steem" and "steemit", while "steem" would be the first entry in the list as
	 * it has a bigger match than "steemit".
	 * </p>
	 * 
	 * @param firstTagPattern
	 *            The search pattern used to build the resulting list of tags.
	 * @param limit
	 *            The maximum number of results.
	 * @return A list of the tags. The first entry in the list is the tag that has
	 *         the biggest match with the <code>firstTagPattern</code>. while the
	 *         last tag in the last has the smallest match.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<Tag> getTrendingTags(String firstTagPattern, int limit)
			throws SteemCommunicationException, SteemResponseException {
		return DatabaseApi.getTrendingTags(communicationHandler, firstTagPattern, limit);
	}

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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public State getState(Permlink path) throws SteemCommunicationException, SteemResponseException {
		return DatabaseApi.getState(communicationHandler, path);
	}

	/**
	 * Get the list of the current active witnesses.
	 * 
	 * @return The list of the current active witnesses.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<AccountName> getActiveWitnesses() throws SteemCommunicationException, SteemResponseException {
		return DatabaseApi.getActiveWitnesses(communicationHandler);
	}

	/**
	 * Use this method to get the current miner queue. <b>Attention:</b> Please be
	 * aware that mining has been disabled for the Steem Blockchain. Therefore this
	 * method will return an empty list for the original Steem Blockchain.
	 * 
	 * @return A list of account names that are in the mining queue.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<AccountName> getMinerQueue() throws SteemCommunicationException, SteemResponseException {
		return DatabaseApi.getMinerQueue(communicationHandler);
	}

	/**
	 * Get a full, signed block by providing its <code>blockNumber</code>. The
	 * returned object contains all information related to the block (e.g. processed
	 * transactions, the witness and the creation time).
	 * 
	 * @param blockNumber
	 *            Height of the block to be returned.
	 * @return The referenced full, signed block, or <code>null</code> if no
	 *         matching block was found.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public SignedBlockWithInfo getBlock(long blockNumber) throws SteemCommunicationException, SteemResponseException {
		return DatabaseApi.getBlock(communicationHandler, blockNumber);
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public BlockHeader getBlockHeader(long blockNumber) throws SteemCommunicationException, SteemResponseException {
		return DatabaseApi.getBlockHeader(communicationHandler, blockNumber);
	}

	/**
	 * Get a sequence of operations included/generated within a particular block.
	 * 
	 * @param blockNumber
	 *            Height of the block whose generated virtual operations should be
	 *            returned.
	 * @param onlyVirtual
	 *            Define if only virtual operations should be returned
	 *            (<code>true</code>) or not (<code>false</code>).
	 * @return A sequence of operations included/generated within a particular
	 *         block.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<AppliedOperation> getOpsInBlock(int blockNumber, boolean onlyVirtual)
			throws SteemCommunicationException, SteemResponseException {
		return DatabaseApi.getOpsInBlock(communicationHandler, blockNumber, onlyVirtual);
	}

	/**
	 * Get the current number of registered Steem accounts.
	 * 
	 * @return The number of accounts.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public int getAccountCount() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_COUNT);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Integer.class).get(0);
	}

	/**
	 * Get all operations performed by the specified account.
	 * 
	 * @param accountName
	 *            The user name of the account.
	 * @param from
	 *            The starting point.
	 * @param limit
	 *            The maximum number of entries.
	 * @return A map containing the activities. The key is the id of the activity.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public Map<Integer, AppliedOperation> getAccountHistory(AccountName accountName, int from, int limit)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_HISTORY);
		String[] parameters = { accountName.getName(), String.valueOf(from), String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		Map<Integer, AppliedOperation> accountActivities = new HashMap<>();

		for (Object[] accountActivity : communicationHandler.performRequest(requestObject, Object[].class)) {
			accountActivities.put((Integer) accountActivity[0], (AppliedOperation) CommunicationHandler
					.getObjectMapper().convertValue(accountActivity[1], new TypeReference<AppliedOperation>() {
					}));
		}

		return accountActivities;
	}

	/**
	 * 
	 * @param accountNames
	 *            A list of accounts you want to request the details for.
	 * @return A List of accounts found for the given account names.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<ExtendedAccount> getAccounts(List<AccountName> accountNames)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		requestObject.setApiMethod(RequestMethods.GET_ACCOUNTS);

		// The API expects an array of arrays here.
		String[] innerParameters = new String[accountNames.size()];
		for (int i = 0; i < accountNames.size(); i++) {
			innerParameters[i] = accountNames.get(i).getName();
		}

		String[][] parameters = { innerParameters };

		requestObject.setAdditionalParameters(parameters);
		return communicationHandler.performRequest(requestObject, ExtendedAccount.class);
	}

	/**
	 * Get a list of all votes done by a specific account.
	 * 
	 * @param accountName
	 *            The user name of the account.
	 * @return A List of votes done by the specified account.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<AccountVote> getAccountVotes(AccountName accountName)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_VOTES);
		String[] parameters = { accountName.getName() };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, AccountVote.class);
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<VoteState> getActiveVotes(AccountName author, Permlink permlink)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_ACTIVE_VOTES);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = { author.getName(), permlink.getLink() };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, VoteState.class);
	}

	/**
	 * Get the chain properties.
	 * 
	 * @return The chain properties.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public ChainProperties getChainProperties() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_CHAIN_PROPERTIES);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, ChainProperties.class).get(0);
	}

	/**
	 * Get the configuration.
	 * 
	 * @return The steem configuration.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public Config getConfig() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_CONFIG);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Config.class).get(0);
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public Discussion getContent(AccountName author, Permlink permlink)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_CONTENT);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = { author.getName(), permlink.getLink() };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Discussion.class).get(0);
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<Discussion> getContentReplies(AccountName author, Permlink permlink)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_CONTENT_REPLIES);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = { author.getName(), permlink.getLink() };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Discussion.class);
	}

	/**
	 * TODO: Look up what this is used for and what it can return.
	 * 
	 * @param account
	 *            The account name.
	 * @return Unknown
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public Object[] getConversionRequests(AccountName account)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_CONVERSION_REQUESTS);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = { account.getName() };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Object[].class).get(0);
	}

	/**
	 * Grab the current median conversion price of SBD / STEEM.
	 * 
	 * @return The current median price.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public Price getCurrentMedianHistoryPrice() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_CURRENT_MEDIAN_HISTORY_PRICE);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Price.class).get(0);
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<Discussion> getDiscussionsBy(DiscussionQuery discussionQuery, DiscussionSortType sortBy)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();

		requestObject.setApiMethod(RequestMethods.valueOf(sortBy.name()));
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		Object[] parameters = { discussionQuery };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Discussion.class);
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<Discussion> getDiscussionsByAuthorBeforeDate(AccountName author, Permlink permlink, String date,
			int limit) throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();

		requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_AUTHOR_BEFORE_DATE);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);

		// Verify that the date has the correct format.
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SteemJConfig.getInstance().getDateTimePattern());
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(SteemJConfig.getInstance().getTimeZoneId()));
		Date beforeDate;
		try {
			beforeDate = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			throw new SteemTransformationException("Could not parse the received date to a Date object.", e);
		}

		String[] parameters = { author.getName(), permlink.getLink(), simpleDateFormat.format(beforeDate),
				String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Discussion.class);
	}

	/**
	 * Get the global properties.
	 * 
	 * @return The dynamic global properties.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public DynamicGlobalProperty getDynamicGlobalProperties()
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_DYNAMIC_GLOBAL_PROPERTIES);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, DynamicGlobalProperty.class).get(0);
	}

	/**
	 * Get the current price and a list of history prices combined in one object.
	 * 
	 * @return The conversion history of SBD / STEEM.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public FeedHistory getFeedHistory() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_FEED_HISTORY);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, FeedHistory.class).get(0);
	}

	/**
	 * Get the hardfork version the node you are connected to is using.
	 * 
	 * @return The hardfork version that the connected node is running on.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public String getHardforkVersion() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_HARDFORK_VERSION);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, String.class).get(0);
	}

	/**
	 * Search for users under the use of their public key(s).
	 * 
	 * @param publicKeys
	 *            An array containing one or more public keys.
	 * @return A list of arrays containing the matching account names.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<String[]> getKeyReferences(String[] publicKeys)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_KEY_REFERENCES);
		requestObject.setSteemApi(SteemApiType.ACCOUNT_BY_KEY_API);
		Object[] parameters = { publicKeys };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, String[].class);
	}

	/**
	 * Get the liquidity queue for a specified account.
	 * 
	 * @param accoutName
	 *            The name of the account you want to request the queue entries for.
	 * @param limit
	 *            Number of results.
	 * @return A list of liquidity queue entries.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<LiquidityBalance> getLiquidityQueue(AccountName accoutName, int limit)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_LIQUIDITY_QUEUE);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		Object[] parameters = { accoutName.getName(), String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, LiquidityBalance.class);
	}

	/**
	 * TODO: Check what this method is supposed to do. In a fist test it seems to
	 * return the time since the current version is active.
	 * 
	 * @return ???
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public ScheduledHardfork getNextScheduledHarfork() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_NEXT_SCHEDULED_HARDFORK);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, ScheduledHardfork.class).get(0);
	}

	/**
	 * If specified user name has orders open on the internal STEEM market it will
	 * return them.
	 * 
	 * @param accountName
	 *            The name of the account.
	 * @return A list of open orders for this account.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<ExtendedLimitOrder> getOpenOrders(AccountName accountName)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_OPEN_ORDERS);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = { accountName.getName() };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, ExtendedLimitOrder.class);
	}

	/**
	 * Returns a list of orders on the internal steem market.
	 * 
	 * @param limit
	 *            The maximum number of results for each category (asks / bids).
	 * @return A list of orders on the internal steem market.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public OrderBook getOrderBookUsingDatabaseApi(int limit)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_ORDER_BOOK);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = { String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, OrderBook.class).get(0);
	}

	// TODO implement this!
	public List<String[]> getPotentialSignatures() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_POTENTIAL_SIGNATURES);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		Object[] parameters = {};
		requestObject.setAdditionalParameters(parameters);
		LOGGER.info("output: {}", communicationHandler.performRequest(requestObject, Object[].class));
		return null;
	}

	/**
	 * /** Get a list of Content starting from the given post of the given user. The
	 * list will be sorted by the Date of the last update.
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<Discussion> getRepliesByLastUpdate(AccountName username, Permlink permlink, int limit)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_REPLIES_BY_LAST_UPDATE);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		Object[] parameters = { username, permlink.getLink(), String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Discussion.class);
	}

	/**
	 * Get detailed information of a specific reward fund.
	 * 
	 * @param rewordFundType
	 *            One of the {@link eu.bittrade.libs.steemj.enums.RewardFundType
	 *            RewardFundType}s.
	 * @return A refund object containing detailed information about the requested
	 *         reward fund.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public RewardFund getRewardFund(RewardFundType rewordFundType)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_REWARD_FUND);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		Object[] parameters = { rewordFundType.name().toLowerCase() };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, RewardFund.class).get(0);
	}

	/**
	 * Use the Steem API to receive the HEX representation of a signed transaction.
	 * 
	 * @param signedTransaction
	 *            The signed Transaction object you want to receive the HEX
	 *            representation for.
	 * @return The HEX representation.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public String getTransactionHex(SignedTransaction signedTransaction)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_TRANSACTION_HEX);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);

		Object[] parameters = { signedTransaction };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, String.class).get(0);
	}

	/**
	 * Get the witness information for a given witness account name.
	 * 
	 * @param witnessName
	 *            The witness name.
	 * @return A list of witnesses.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public Witness getWitnessByAccount(AccountName witnessName)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_WITNESS_BY_ACCOUNT);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = { witnessName.getName() };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Witness.class).get(0);
	}

	/**
	 * Get a list of witnesses sorted by the amount of votes. The list begins with
	 * the given account name and contains the next witnesses with less votes than
	 * given one.
	 * 
	 * @param witnessName
	 *            The witness name to start from.
	 * @param limit
	 *            The number of results.
	 * @return A list of witnesses.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<Witness> getWitnessByVote(AccountName witnessName, int limit)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_WITNESSES_BY_VOTE);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = { witnessName.getName(), String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Witness.class);
	}

	/**
	 * Get the current number of active witnesses.
	 * 
	 * @return The number of witnesses.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public int getWitnessCount() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_WITNESS_COUNT);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Integer.class).get(0);
	}

	/**
	 * Get all witnesses.
	 * 
	 * @return A list of witnesses.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<Witness> getWitnesses() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_WITNESSES);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Witness.class);
	}

	/**
	 * Get the witness schedule.
	 * 
	 * @return The witness schedule.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public WitnessSchedule getWitnessSchedule() throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.GET_WITNESS_SCHEDULE);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, WitnessSchedule.class).get(0);
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<String> lookupAccounts(String pattern, int limit)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.LOOKUP_ACCOUNTS);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = { pattern, String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, String.class);
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<String> lookupWitnessAccounts(String pattern, int limit)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.LOOKUP_WITNESS_ACCOUNTS);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);
		String[] parameters = { pattern, String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, String.class);
	}

	/**
	 * Use the Steem API to verify the required authorities for this transaction.
	 * 
	 * @param signedTransaction
	 *            A {@link SignedTransaction} transaction which has been signed.
	 * @return <code>true</code> if the given transaction has been signed correctly,
	 *         otherwise an Exception will be thrown.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public boolean verifyAuthority(SignedTransaction signedTransaction)
			throws SteemCommunicationException, SteemResponseException {
		JsonRPCRequest requestObject = new JsonRPCRequest();
		requestObject.setApiMethod(RequestMethods.VERIFY_AUTHORITY);
		requestObject.setSteemApi(SteemApiType.DATABASE_API);

		Object[] parameters = { signedTransaction };
		requestObject.setAdditionalParameters(parameters);
		// The method does not simply return false, it throws an error
		// describing the problem.
		return communicationHandler.performRequest(requestObject, Boolean.class).get(0);
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
	 *            A filter to limit the number of results. If not empty, the method
	 *            will only return account names after the <code>following</code>
	 *            account has been followed by the <code>startFollower</code>
	 *            account.
	 * @param type
	 *            The follow type.
	 * @param limit
	 *            The maximum number of results returned.
	 * @return A list of account names that follow the <code>follower</code>
	 *         account..
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<FollowApiObject> getFollowers(AccountName following, AccountName startFollower, FollowType type,
			short limit) throws SteemCommunicationException, SteemResponseException {
		return FollowApi.getFollowers(communicationHandler, following, startFollower, type, limit);
	}

	/**
	 * Get a list of account names which the <code>follower</code> account follows.
	 * 
	 * @param follower
	 *            The account name for which the account names should be returned,
	 *            that the <code>follower</code> is following.
	 * @param startFollowing
	 *            A filter to limit the number of results. If not empty, the method
	 *            will only return account names after the <code>follower</code>
	 *            account has followed the <code>startFollowing</code> account.
	 * @param type
	 *            The follow type.
	 * @param limit
	 *            The maximum number of results returned.
	 * @return A list of account names the <code>follower</code> account is
	 *         following.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<FollowApiObject> getFollowing(AccountName follower, AccountName startFollowing, FollowType type,
			short limit) throws SteemCommunicationException, SteemResponseException {
		return FollowApi.getFollowing(communicationHandler, follower, startFollowing, type, limit);
	}

	/**
	 * Get the amount of accounts following the given <code>account</code> and the
	 * number of accounts this <code>account</code> follows. Both values are wrapped
	 * in a FollowCountApiObject.
	 * 
	 * @param account
	 *            The account to get the number of followers / following accounts
	 *            for.
	 * @return The number of followers / following accounts
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * @return A list of feed entries from the given <code>author</code> based on
	 *         the given conditions (<code>entryId</code> and <code>limit</code>).
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * @return A list of feed entries from the given <code>author</code> based on
	 *         the given conditions (<code>entryId</code> and <code>limit</code>).
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * own) has an <code>entryId</code>, while the <code>entryId</code> starts with
	 * 0 for the first blog entry and is increment by 1 for each resteem or post of
	 * the <code>author</code>.
	 * 
	 * Steem allows to use the <code>entryId</code> as a search criteria: The first
	 * entry of the returned list is the blog entry with the given
	 * <code>entryId</code>. Beside that, the <code>limit</code> can be used to
	 * limit the number of results.
	 * 
	 * So if the method is called with <code>entryId</code> set to 5 and the
	 * <code>limit</code> is set to 2, the returned list will contain 2 entries: The
	 * first one is the blog entry with <code>entryId</code> of 5, the second one
	 * has the <code>entryId</code> 4.
	 * 
	 * If the <code>entryId</code> is set to 0, the first returned item will be the
	 * latest blog entry of the given <code>author</code>.
	 * 
	 * So if a user has 50 blog entries and this method is called with an
	 * <code>entryId</code> set to 0 and a <code>limit</code> of 2, the returned
	 * list will contain the blog entries with the <code>entryId</code>s 50 and 49.
	 * 
	 * @param account
	 *            The account to get the blog entries for.
	 * @param entryId
	 *            The first blog entry id to return.
	 * @param limit
	 *            The number of results.
	 * @return A list of blog entries from the given <code>author</code> based on
	 *         the given conditions (<code>entryId</code> and <code>limit</code>).
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<BlogEntry> getBlogEntries(AccountName account, int entryId, short limit)
			throws SteemCommunicationException, SteemResponseException {
		return FollowApi.getBlogEntries(communicationHandler, account, entryId, limit);

	}

	/**
	 * Like {@link #getBlogEntries(AccountName, int, short)
	 * getBlogEntries(AccountName, int, short)}, but contains the whole content of
	 * the blog entry.
	 * 
	 * @param account
	 *            The account to get the blog entries for.
	 * @param entryId
	 *            The first blog entry id to return.
	 * @param limit
	 *            The number of results.
	 * @return A list of blog entries from the given <code>author</code> based on
	 *         the given conditions (<code>entryId</code> and <code>limit</code>).
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * @return A list of
	 *         {@link eu.bittrade.libs.steemj.apis.follow.model.AccountReputation
	 *         AccountReputation}.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * Use this method to receive statistic values of the internal SBD:STEEM market
	 * for the last 24 hours.
	 * 
	 * @return The market ticker for the internal SBD:STEEM market.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public MarketTicker getTicker() throws SteemCommunicationException, SteemResponseException {
		return MarketHistoryApi.getTicker(communicationHandler);
	}

	/**
	 * Use this method to get the SBD and Steem volume that has been traded in the
	 * past 24 hours at the internal SBD:STEEM market.
	 * 
	 * @return The market volume for the past 24 hours.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public MarketVolume getVolume() throws SteemCommunicationException, SteemResponseException {
		return MarketHistoryApi.getVolume(communicationHandler);
	}

	/**
	 * Use this method to receive the current order book of the internal SBD:STEEM
	 * market.
	 * 
	 * @deprecated Use {@link #getOrderBookUsingDatabaseApi(int)}
	 * 
	 * @param limit
	 *            The number of orders to have on each side of the order book.
	 *            Maximum is 500.
	 * @return Returns the current order book for the internal SBD:STEEM market.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * @throws InvalidParameterException
	 *             If the limit is less than 0 or greater than 500.
	 */
	public eu.bittrade.libs.steemj.apis.market.history.model.OrderBook getOrderBookUsingMarketApi(short limit)
			throws SteemCommunicationException, SteemResponseException {
		return MarketHistoryApi.getOrderBook(communicationHandler, limit);
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * @throws InvalidParameterException
	 *             If the limit is less than 0 or greater than 500.
	 */
	public List<MarketTrade> getTradeHistory(TimePointSec start, TimePointSec end, short limit)
			throws SteemCommunicationException, SteemResponseException {
		return MarketHistoryApi.getTradeHistory(communicationHandler, start, end, limit);
	}

	/**
	 * Use this method to request the most recent trades for the internal SBD:STEEM
	 * market. The number of results is limited by the <code>limit</code> parameter.
	 *
	 * @param limit
	 *            The number of trades to return. Maximum is 1000.
	 * @return A list of completed trades.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * @throws InvalidParameterException
	 *             If the limit is less than 0 or greater than 500.
	 */
	public List<MarketTrade> getRecentTrades(short limit) throws SteemCommunicationException, SteemResponseException {
		return MarketHistoryApi.getRecentTrades(communicationHandler, limit);
	}

	/**
	 * Use this method to receive the market history for the internal SBD:STEEM
	 * market.
	 * 
	 * @param bucketSeconds
	 *            The size of buckets the history is broken into. The bucket size
	 *            must be configured in the plugin options and can be requested
	 *            using the {@link #getMarketHistoryBuckets()} method.
	 * @param start
	 *            The start time to get market history.
	 * @param end
	 *            The end time to get market history.
	 * @return A list of market history
	 *         {@link eu.bittrade.libs.steemj.apis.market.history.model.Bucket
	 *         Bucket}s.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<Bucket> getMarketHistory(long bucketSeconds, TimePointSec start, TimePointSec end)
			throws SteemCommunicationException, SteemResponseException {
		return MarketHistoryApi.getMarketHistory(communicationHandler, bucketSeconds, start, end);
	}

	/**
	 * Use this method to receive the bucket seconds being tracked by the node.
	 * 
	 * @return Returns the bucket seconds being tracked by the node.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 */
	public List<Integer> getMarketHistoryBuckets() throws SteemCommunicationException, SteemResponseException {
		return MarketHistoryApi.getMarketHistoryBuckets(communicationHandler);
	}

	// #########################################################################
	// ## UTILITY METHODS ######################################################
	// #########################################################################

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
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a voting operation the
	 * private posting key of the {@link SteemJConfig#getDefaultAccount()
	 * DefaultAccount} needs to be configured in the
	 * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the voter - If no
	 * default account has been provided, this method will throw an error. If you do
	 * not want to configure the voter as a default account, please use the
	 * {@link #vote(AccountName, AccountName, Permlink, short)} method and provide
	 * the voter account separately.</li>
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
	 *            Define how much of your voting power should be used to up or down
	 *            vote the post or the comment.
	 *            <ul>
	 *            <li>If you want to up vote the post or the comment provide a value
	 *            between 1 (1.0%) and 100 (100.0%).</li>
	 *            <li>If you want to down vote (as known as <b>flag</b>) the post or
	 *            the comment provide a value between -1 (-1.0%) and -100
	 *            (-100.0%).</li>
	 *            </ul>
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * This method is equivalent to the {@link #vote(AccountName, Permlink, short)}
	 * method, but lets you define the <code>voter</code> account separately instead
	 * of using the {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
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
	 *            Define how much of your voting power should be used to up or down
	 *            vote the post or the comment.
	 *            <ul>
	 *            <li>If you want to up vote the post or the comment provide a value
	 *            between 1 (1.0%) and 100 (100.0%).</li>
	 *            <li>If you want to down vote (as known as <b>flag</b>) the post or
	 *            the comment provide a value between -1 (-1.0%) and -100
	 *            (-100.0%).</li>
	 *            </ul>
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a voting operation the
	 * private posting key of the {@link SteemJConfig#getDefaultAccount()
	 * DefaultAccount} needs to be configured in the
	 * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the voter - If no
	 * default account has been provided, this method will throw an error. If you do
	 * not want to configure the voter as a default account, please use the
	 * {@link #vote(AccountName, AccountName, Permlink, short)} method and provide
	 * the voter account separately.</li>
	 * </ul>
	 * 
	 * @param postOrCommentAuthor
	 *            The author of the post or the comment to cancel the vote for.
	 *            <p>
	 *            Example:<br>
	 *            <code>new AccountName("dez1337")</code>
	 *            </p>
	 * @param postOrCommentPermlink
	 *            The permanent link of the post or the comment to cancel the vote
	 *            for.
	 *            <p>
	 *            Example:<br>
	 *            <code>new Permlink("steemj-v0-2-4-has-been-released-update-9")</code>
	 *            </p>
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * This method is equivalent to the {@link #cancelVote(AccountName, Permlink)}
	 * method, but lets you define the <code>voter</code> account separately instead
	 * of using the {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a follow operation the
	 * private posting key of the {@link SteemJConfig#getDefaultAccount()
	 * DefaultAccount} needs to be configured in the
	 * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account that
	 * will follow the <code>accountToFollow</code> - If no default account has been
	 * provided, this method will throw an error. If you do not want to configure
	 * the following account as a default account, please use the
	 * {@link #follow(AccountName, AccountName)} method and provide the following
	 * account separately.</li>
	 * </ul>
	 * 
	 * @param accountToFollow
	 *            The account name of the account the
	 *            {@link SteemJConfig#getDefaultAccount() DefaultAccount} should
	 *            follow.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a unfollow operation
	 * the private posting key of the {@link SteemJConfig#getDefaultAccount()
	 * DefaultAccount} needs to be configured in the
	 * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account that
	 * will no longer follow the <code>accountToFollow</code> - If no default
	 * account has been provided, this method will throw an error. If you do not
	 * want to configure the following account as a default account, please use the
	 * {@link #follow(AccountName, AccountName)} method and provide the following
	 * account separately.</li>
	 * </ul>
	 * 
	 * @param accountToUnfollow
	 *            The account name of the account the
	 *            {@link SteemJConfig#getDefaultAccount() DefaultAccount} should no
	 *            longer follow.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * This method is equivalent to the {@link #unfollow(AccountName)} method, but
	 * lets you define the <code>accountThatUnfollows</code> account separately
	 * instead of using the {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
	 * 
	 * @param accountThatUnfollows
	 *            The account name of the account that will no longer follow the
	 *            <code>accountToUnfollow</code>.
	 * @param accountToUnfollow
	 *            The account name of the account the
	 *            {@link SteemJConfig#getDefaultAccount() DefaultAccount} should no
	 *            longer follow.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a reblog operation the
	 * private posting key of the {@link SteemJConfig#getDefaultAccount()
	 * DefaultAccount} needs to be configured in the
	 * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} that will resteem the
	 * post defined by the <code>accountThatResteemsThePost</code> and
	 * <code>authorOfThePostToResteem</code> parameters. - If no default account has
	 * been provided, this method will throw an error. If you do not want to
	 * configure the following account as a default account, please use the
	 * {@link #reblog(AccountName, Permlink)} method and provide the account who
	 * wants to reblog the post separately.</li>
	 * <li>Please be aware that there is no way to undo a reblog operation - If a
	 * post has been reblogged it has been reblogged.
	 * </ul>
	 * 
	 * @param authorOfThePostToReblog
	 *            The author of the post to reblog.
	 * @param permlinkOfThePostToReblog
	 *            The permlink of the post to reblog.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * Use this method to create a new post. Supports setting body format and extra
	 * metadata.<br>
	 * <br>
	 * 
	 * <b>Attention</b>
	 * <ul>
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a create post
	 * operation the private posting key of the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
	 * configured in the {@link SteemJConfig#getPrivateKeyStorage()
	 * PrivateKeyStorage}.</li>
	 * <li>In case the {@link SteemJConfig#getSteemJWeight() SteemJWeight} is set to
	 * a positive value this method will add a comment options operation. Due to
	 * this, the {@link SteemJConfig#getSteemJWeight() SteemJWeight} percentage will
	 * be paid to the SteemJ account.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account that
	 * will publish the post - If no default account has been provided, this method
	 * will throw an error. If you do not want to configure the author account as a
	 * default account, please use the
	 * {@link #createPost(AccountName, String, String, String[])} method and provide
	 * the author account separately.</li>
	 * </ul>
	 * 
	 * @param title
	 *            The title of the post to publish.
	 * @param content
	 *            The content of the post to publish.
	 * @param tags
	 *            A list of tags while the first tag in this list is the main tag.
	 *            You can provide up to five tags but at least one needs to be
	 *            provided.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * @throws SteemInvalidTransactionException
	 *             If there is a problem while signing the transaction.
	 * @throws InvalidParameterException
	 *             If one of the provided parameters does not fulfill the
	 *             requirements described above.
	 */
	public CommentOperation createPost(String title, String content, String[] tags, String format,
			Map<String, Object> extraMetadata)
			throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
		if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
			throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
		}
		return createPost(SteemJConfig.getInstance().getDefaultAccount(), title, content, tags, format, extraMetadata);
	}

	/**
	 * Use this method to create a new post. Supports setting body format and extra
	 * metadata.<br>
	 * <br>
	 * 
	 * <b>Attention</b>
	 * <ul>
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a create post
	 * operation the private posting key of the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
	 * configured in the {@link SteemJConfig#getPrivateKeyStorage()
	 * PrivateKeyStorage}.</li>
	 * <li>In case the {@link SteemJConfig#getSteemJWeight() SteemJWeight} is set to
	 * a positive value this method will add a comment options operation. Due to
	 * this, the {@link SteemJConfig#getSteemJWeight() SteemJWeight} percentage will
	 * be paid to the SteemJ account.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account that
	 * will publish the post - If no default account has been provided, this method
	 * will throw an error. If you do not want to configure the author account as a
	 * default account, please use the
	 * {@link #createPost(AccountName, String, String, String[])} method and provide
	 * the author account separately.</li>
	 * </ul>
	 * 
	 * @param title
	 *            The title of the post to publish.
	 * @param content
	 *            The content of the post to publish.
	 * @param tags
	 *            A list of tags while the first tag in this list is the main tag.
	 *            You can provide up to five tags but at least one needs to be
	 *            provided. <strong>format is set to "markdown"</strong><br>
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
		return createPost(title, content, tags, MARKDOWN, null);
	}

	/**
	 * This method is equivalent to the
	 * {@link #createPost(String, String, String[])} method, but lets you define the
	 * <code>authorThatPublishsThePost</code> account separately instead of using
	 * the {@link SteemJConfig#getDefaultAccount() DefaultAccount}. Supports setting
	 * body format and extra metadata.<br>
	 * <br>
	 * 
	 * @param authorThatPublishsThePost
	 *            The account who wants to publish the post.
	 * @param title
	 *            The title of the post to publish.
	 * @param content
	 *            The content of the post to publish.
	 * @param tags
	 *            A list of tags while the first tag in this list is the main tag.
	 *            You can provide up to five tags but at least one needs to be
	 *            provided.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * @throws SteemInvalidTransactionException
	 *             If there is a problem while signing the transaction.
	 * @throws InvalidParameterException
	 *             If one of the provided parameters does not fulfill the
	 *             requirements described above.
	 */
	public CommentOperation createPost(AccountName authorThatPublishsThePost, String title, String content,
			String[] tags, String format, Map<String, Object> extraMetadata)
			throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
		if (tags == null || tags.length < 1) {
			throw new InvalidParameterException(TAG_ERROR_MESSAGE);
		}
		ArrayList<Operation> operations = new ArrayList<>();

		// Generate the permanent link from the title by replacing all unallowed
		// characters.
		Permlink permlink = new Permlink(SteemJUtils.createPermlinkString(title));
		// On new posts the parentPermlink is the main tag.
		Permlink parentPermlink = new Permlink(tags[0]);
		// One new posts the parentAuthor is empty.
		AccountName parentAuthor = new AccountName("");

		String jsonMetadata = CondenserUtils.generateSteemitMetadata(content, tags,
				SteemJConfig.getSteemJAppName() + "/" + SteemJConfig.getSteemJVersion(), format, extraMetadata);

		CommentOperation commentOperation = new CommentOperation(parentAuthor, parentPermlink,
				authorThatPublishsThePost, permlink, title, content, jsonMetadata);

		operations.add(commentOperation);

		boolean allowVotes = true;
		boolean allowCurationRewards = true;
		short percentSteemDollars = (short) 10000;
		Asset maxAcceptedPayout = new Asset(1000000000, AssetSymbolType.SBD);

		CommentOptionsOperation commentOptionsOperation;
		// Only add a BeneficiaryRouteType if it makes sense.
		SteemJConfig config = SteemJConfig.getInstance();
		if (config.getSteemJWeight() > 0) {
			BeneficiaryRouteType beneficiaryRouteType = new BeneficiaryRouteType(config.getBeneficiaryAccount(),
					config.getSteemJWeight());

			ArrayList<BeneficiaryRouteType> beneficiaryRouteTypes = new ArrayList<>();
			beneficiaryRouteTypes.add(beneficiaryRouteType);

			CommentPayoutBeneficiaries commentPayoutBeneficiaries = new CommentPayoutBeneficiaries();
			commentPayoutBeneficiaries.setBeneficiaries(beneficiaryRouteTypes);

			ArrayList<CommentOptionsExtension> commentOptionsExtensions = new ArrayList<>();
			commentOptionsExtensions.add(commentPayoutBeneficiaries);

			commentOptionsOperation = new CommentOptionsOperation(authorThatPublishsThePost, permlink,
					maxAcceptedPayout, (int) percentSteemDollars, allowVotes, allowCurationRewards,
					commentOptionsExtensions);
		} else {
			commentOptionsOperation = new CommentOptionsOperation(authorThatPublishsThePost, permlink,
					maxAcceptedPayout, (int) percentSteemDollars, allowVotes, allowCurationRewards, null);
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
	 * This method is equivalent to the
	 * {@link #createPost(String, String, String[])} method, but lets you define the
	 * <code>authorThatPublishsThePost</code> account separately instead of using
	 * the {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
	 * 
	 * @param authorThatPublishsThePost
	 *            The account who wants to publish the post.
	 * @param title
	 *            The title of the post to publish.
	 * @param content
	 *            The content of the post to publish.
	 * @param tags
	 *            A list of tags while the first tag in this list is the main tag.
	 *            You can provide up to five tags but at least one needs to be
	 *            provided.
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
		return createPost(authorThatPublishsThePost, title, content, tags, MARKDOWN, null);
	}

	/**
	 * Use this method to create a new comment. Supports setting body format and
	 * extra metadata.<br>
	 * <br>
	 * 
	 * <b>Attention</b>
	 * <ul>
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a create comment
	 * operation the private posting key of the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
	 * configured in the {@link SteemJConfig#getPrivateKeyStorage()
	 * PrivateKeyStorage}.</li>
	 * <li>In case the {@link SteemJConfig#getSteemJWeight() SteemJWeight} is set to
	 * a positive value this method will add a comment options operation. Due to
	 * this, the {@link SteemJConfig#getSteemJWeight() SteemJWeight} percentage will
	 * be paid to the SteemJ account.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account that
	 * will publish the comment - If no default account has been provided, this
	 * method will throw an error. If you do not want to configure the author
	 * account as a default account, please use the
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
	 *            A list of tags while the first tag in this list is the main tag.
	 *            You can provide up to five tags but at least one needs to be
	 *            provided.
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * @throws SteemInvalidTransactionException
	 *             If there is a problem while signing the transaction.
	 * @throws InvalidParameterException
	 *             If one of the provided parameters does not fulfill the
	 *             requirements described above.
	 */
	public CommentOperation createComment(AccountName authorOfThePostOrCommentToReplyTo,
			Permlink permlinkOfThePostOrCommentToReplyTo, String content, String[] tags, String format,
			Map<String, Object> extraMetadata)
			throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
		if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
			throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
		}

		return createComment(SteemJConfig.getInstance().getDefaultAccount(), authorOfThePostOrCommentToReplyTo,
				permlinkOfThePostOrCommentToReplyTo, content, tags, format, extraMetadata);
	}

	/**
	 * Use this method to create a new comment.
	 * 
	 * <b>Attention</b>
	 * <ul>
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a create comment
	 * operation the private posting key of the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
	 * configured in the {@link SteemJConfig#getPrivateKeyStorage()
	 * PrivateKeyStorage}.</li>
	 * <li>In case the {@link SteemJConfig#getSteemJWeight() SteemJWeight} is set to
	 * a positive value this method will add a comment options operation. Due to
	 * this, the {@link SteemJConfig#getSteemJWeight() SteemJWeight} percentage will
	 * be paid to the SteemJ account.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account that
	 * will publish the comment - If no default account has been provided, this
	 * method will throw an error. If you do not want to configure the author
	 * account as a default account, please use the
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
	 *            A list of tags while the first tag in this list is the main tag.
	 *            You can provide up to five tags but at least one needs to be
	 *            provided.
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
		return createComment(authorOfThePostOrCommentToReplyTo, permlinkOfThePostOrCommentToReplyTo, content, tags,
				MARKDOWN, null);
	}

	/**
	 * This method is equivalent to the
	 * {@link #createComment(AccountName, Permlink, String, String[])} method, but
	 * lets you define the <code>authorThatPublishsTheComment</code> account
	 * separately instead of using the {@link SteemJConfig#getDefaultAccount()
	 * DefaultAccount}. Supports setting body format and extra metadata.<br>
	 * <br>
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
	 *            A list of tags while the first tag in this list is the main tag.
	 *            You can provide up to five tags but at least one needs to be
	 *            provided.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
			String[] tags, String format, Map<String, Object> extraMetadata)
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
				SteemJConfig.getSteemJAppName() + "/" + SteemJConfig.getSteemJVersion(), format, extraMetadata);

		CommentOperation commentOperation = new CommentOperation(authorOfThePostOrCommentToReplyTo,
				permlinkOfThePostOrCommentToReplyTo, authorThatPublishsTheComment, permlink, "", content, jsonMetadata);

		operations.add(commentOperation);

		boolean allowVotes = true;
		boolean allowCurationRewards = true;
		short percentSteemDollars = (short) 10000;
		Asset maxAcceptedPayout = new Asset(1000000000, AssetSymbolType.SBD);

		CommentOptionsOperation commentOptionsOperation;
		// Only add a BeneficiaryRouteType if it makes sense.
		SteemJConfig config = SteemJConfig.getInstance();
		if (config.getSteemJWeight() > 0) {
			BeneficiaryRouteType beneficiaryRouteType = new BeneficiaryRouteType(config.getBeneficiaryAccount(),
					config.getSteemJWeight());

			ArrayList<BeneficiaryRouteType> beneficiaryRouteTypes = new ArrayList<>();
			beneficiaryRouteTypes.add(beneficiaryRouteType);

			CommentPayoutBeneficiaries commentPayoutBeneficiaries = new CommentPayoutBeneficiaries();
			commentPayoutBeneficiaries.setBeneficiaries(beneficiaryRouteTypes);

			ArrayList<CommentOptionsExtension> commentOptionsExtensions = new ArrayList<>();
			commentOptionsExtensions.add(commentPayoutBeneficiaries);

			commentOptionsOperation = new CommentOptionsOperation(authorThatPublishsTheComment, permlink,
					maxAcceptedPayout, (int) percentSteemDollars, allowVotes, allowCurationRewards,
					commentOptionsExtensions);
		} else {
			commentOptionsOperation = new CommentOptionsOperation(authorThatPublishsTheComment, permlink,
					maxAcceptedPayout, (int) percentSteemDollars, allowVotes, allowCurationRewards, null);
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
	 * This method is equivalent to the
	 * {@link #createComment(AccountName, Permlink, String, String[])} method, but
	 * lets you define the <code>authorThatPublishsTheComment</code> account
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
	 *            A list of tags while the first tag in this list is the main tag.
	 *            You can provide up to five tags but at least one needs to be
	 *            provided.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
		return createComment(authorThatPublishsTheComment, authorOfThePostOrCommentToReplyTo,
				permlinkOfThePostOrCommentToReplyTo, content, tags, MARKDOWN, null);
	}

	/**
	 * Use this method to update an existing post. Supports setting body format and
	 * extra metadata.<br>
	 * <br>
	 * 
	 * <b>Attention</b>
	 * <ul>
	 * <li>Updating a post only works if Steem can identify the existing post - If
	 * this is not the case, this operation will create a new post instead of
	 * updating the existing one. The identification is based on the
	 * <code>permlinkOfThePostToUpdate</code> and the first tag of the
	 * <code>tags</code> array to be the same ones as of the post to update.</li>
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a update post
	 * operation the private posting key of the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
	 * configured in the {@link SteemJConfig#getPrivateKeyStorage()
	 * PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the author of the
	 * post to update - If no default account has been provided, this method will
	 * throw an error. If you do not want to configure the author account as a
	 * default account, please use the
	 * {@link #updatePost(AccountName, Permlink, String, String, String[])} method
	 * and provide the author account separately.</li>
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
	 *            needs to be the same as before otherwise SteemJ could accidently
	 *            create a new post instead of updating an existing one.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * @throws SteemInvalidTransactionException
	 *             If there is a problem while signing the transaction.
	 * @throws InvalidParameterException
	 *             If one of the provided parameters does not fulfill the
	 *             requirements described above.
	 */
	public CommentOperation updatePost(Permlink permlinkOfThePostToUpdate, String title, String content, String[] tags,
			String format, Map<String, Object> extraMetadata)
			throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
		if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
			throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
		}

		return updatePost(SteemJConfig.getInstance().getDefaultAccount(), permlinkOfThePostToUpdate, title, content,
				tags, format, extraMetadata);
	}

	/**
	 * Use this method to update an existing post.
	 * 
	 * <b>Attention</b>
	 * <ul>
	 * <li>Updating a post only works if Steem can identify the existing post - If
	 * this is not the case, this operation will create a new post instead of
	 * updating the existing one. The identification is based on the
	 * <code>permlinkOfThePostToUpdate</code> and the first tag of the
	 * <code>tags</code> array to be the same ones as of the post to update.</li>
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a update post
	 * operation the private posting key of the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
	 * configured in the {@link SteemJConfig#getPrivateKeyStorage()
	 * PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the author of the
	 * post to update - If no default account has been provided, this method will
	 * throw an error. If you do not want to configure the author account as a
	 * default account, please use the
	 * {@link #updatePost(AccountName, Permlink, String, String, String[])} method
	 * and provide the author account separately.</li>
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
	 *            needs to be the same as before otherwise SteemJ could accidently
	 *            create a new post instead of updating an existing one.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
		return updatePost(permlinkOfThePostToUpdate, title, content, tags, MARKDOWN, null);
	}

	/**
	 * This method is equivalent to the
	 * {@link #updatePost(Permlink, String, String, String[])} method, but lets you
	 * define the <code>authorOfThePostToUpdate</code> account separately instead of
	 * using the {@link SteemJConfig#getDefaultAccount() DefaultAccount}. Supports
	 * setting body format and extra metadata.<br>
	 * <br>
	 * 
	 * @param authorOfThePostToUpdate
	 *            The account that wants to perform the update. In most cases, this
	 *            should be the author of the already existing post.
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
	 *            needs to be the same as before otherwise SteemJ could accidently
	 *            create a new post instead of updating an existing one.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * @throws SteemInvalidTransactionException
	 *             If there is a problem while signing the transaction.
	 * @throws InvalidParameterException
	 *             If one of the provided parameters does not fulfill the
	 *             requirements described above.
	 */
	public CommentOperation updatePost(AccountName authorOfThePostToUpdate, Permlink permlinkOfThePostToUpdate,
			String title, String content, String[] tags, String format, Map<String, Object> extraMetadata)
			throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
		if (tags == null || tags.length < 1 || tags.length > 5) {
			throw new InvalidParameterException(TAG_ERROR_MESSAGE);
		}

		ArrayList<Operation> operations = new ArrayList<>();
		AccountName parentAuthor = new AccountName("");
		Permlink parentPermlink = new Permlink(tags[0]);

		String jsonMetadata = CondenserUtils.generateSteemitMetadata(content, tags,
				SteemJConfig.getSteemJAppName() + "/" + SteemJConfig.getSteemJVersion(), format, extraMetadata);

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
	 * This method is equivalent to the
	 * {@link #updatePost(Permlink, String, String, String[])} method, but lets you
	 * define the <code>authorOfThePostToUpdate</code> account separately instead of
	 * using the {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
	 * 
	 * @param authorOfThePostToUpdate
	 *            The account that wants to perform the update. In most cases, this
	 *            should be the author of the already existing post.
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
	 *            needs to be the same as before otherwise SteemJ could accidently
	 *            create a new post instead of updating an existing one.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
		return updatePost(authorOfThePostToUpdate, permlinkOfThePostToUpdate, title, content, tags, MARKDOWN, null);
	}

	/**
	 * Use this method to update an existing comment. Supports setting body format
	 * and extra metadata.<br>
	 * <br>
	 * 
	 * <b>Attention</b>
	 * <ul>
	 * <li>Updating a comment only works if Steem can identify the existing comment
	 * - If this is not the case, this operation will create a new comment instead
	 * of updating the existing one. The identification is based on the
	 * <code>originalPermlinkOfYourComment</code>, the <code>parentAuthor</code>,
	 * the <code>parentPermlink</code> and the first tag of the <code>tags</code>
	 * array to be the same ones as of the post to update.</li>
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a update comment
	 * operation the private posting key of the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
	 * configured in the {@link SteemJConfig#getPrivateKeyStorage()
	 * PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the author of the
	 * comment to update - If no default account has been provided, this method will
	 * throw an error. If you do not want to configure the author account as a
	 * default account, please use the
	 * {@link #updateComment(AccountName, AccountName, Permlink, Permlink, String, String[])}
	 * method and provide the author account separately.</li>
	 * </ul>
	 * 
	 * @param parentAuthor
	 *            The author of the post or comment that you initially replied to.
	 * @param parentPermlink
	 *            The permlink of the post or comment that you initially replied to.
	 * @param originalPermlinkOfTheCommentToUpdate
	 *            The permlink of the comment to update.
	 * @param content
	 *            The new content of the comment to set.
	 * @param tags
	 *            The new tags of the comment. <b>Attention</b> The first tag still
	 *            needs to be the same as before otherwise SteemJ could accidently
	 *            create a new comment instead of updating an existing one.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * @throws SteemInvalidTransactionException
	 *             If there is a problem while signing the transaction.
	 * @throws InvalidParameterException
	 *             If one of the provided parameters does not fulfill the
	 *             requirements described above.
	 */
	public CommentOperation updateComment(AccountName parentAuthor, Permlink parentPermlink,
			Permlink originalPermlinkOfTheCommentToUpdate, String content, String[] tags, String format,
			Map<String, Object> extraMetadata)
			throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
		if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
			throw new InvalidParameterException(NO_DEFAULT_ACCOUNT_ERROR_MESSAGE);
		}

		return updateComment(SteemJConfig.getInstance().getDefaultAccount(), parentAuthor, parentPermlink,
				originalPermlinkOfTheCommentToUpdate, content, tags, format, extraMetadata);
	}

	/**
	 * Use this method to update an existing comment.
	 * 
	 * <b>Attention</b>
	 * <ul>
	 * <li>Updating a comment only works if Steem can identify the existing comment
	 * - If this is not the case, this operation will create a new comment instead
	 * of updating the existing one. The identification is based on the
	 * <code>originalPermlinkOfYourComment</code>, the <code>parentAuthor</code>,
	 * the <code>parentPermlink</code> and the first tag of the <code>tags</code>
	 * array to be the same ones as of the post to update.</li>
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a update comment
	 * operation the private posting key of the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} needs to be
	 * configured in the {@link SteemJConfig#getPrivateKeyStorage()
	 * PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the author of the
	 * comment to update - If no default account has been provided, this method will
	 * throw an error. If you do not want to configure the author account as a
	 * default account, please use the
	 * {@link #updateComment(AccountName, AccountName, Permlink, Permlink, String, String[])}
	 * method and provide the author account separately.</li>
	 * </ul>
	 * 
	 * @param parentAuthor
	 *            The author of the post or comment that you initially replied to.
	 * @param parentPermlink
	 *            The permlink of the post or comment that you initially replied to.
	 * @param originalPermlinkOfTheCommentToUpdate
	 *            The permlink of the comment to update.
	 * @param content
	 *            The new content of the comment to set.
	 * @param tags
	 *            The new tags of the comment. <b>Attention</b> The first tag still
	 *            needs to be the same as before otherwise SteemJ could accidently
	 *            create a new comment instead of updating an existing one.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
		return updateComment(parentAuthor, parentPermlink, originalPermlinkOfTheCommentToUpdate, content, tags,
				MARKDOWN, null);
	}

	/**
	 * This method is like the
	 * {@link #updateComment(AccountName, Permlink, Permlink, String, String[])}
	 * method, but allows you to define the
	 * <code>originalAuthorOfTheCommentToUpdate</code> account separately instead of
	 * using the {@link SteemJConfig#getDefaultAccount() DefaultAccount}. Supports
	 * setting body format and extra metadata.<br>
	 * <br>
	 * 
	 * @param originalAuthorOfTheCommentToUpdate
	 *            The account that wants to perform the update. In most cases, this
	 *            should be the author of the already existing comment.
	 * @param parentAuthor
	 *            The author of the post or comment that you initially replied to.
	 * @param parentPermlink
	 *            The permlink of the post or comment that you initially replied to.
	 * @param originalPermlinkOfTheCommentToUpdate
	 *            The permlink of the comment to update.
	 * @param content
	 *            The new content of the comment to set.
	 * @param tags
	 *            The new tags of the comment. <b>Attention</b> The first tag still
	 *            needs to be the same as before otherwise SteemJ could accidently
	 *            create a new comment instead of updating an existing one.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
	 *             <li>If the Server returned an error object.</li>
	 *             </ul>
	 * @throws SteemInvalidTransactionException
	 *             If there is a problem while signing the transaction.
	 * @throws InvalidParameterException
	 *             If one of the provided parameters does not fulfill the
	 *             requirements described above.
	 */
	public CommentOperation updateComment(AccountName originalAuthorOfTheCommentToUpdate, AccountName parentAuthor,
			Permlink parentPermlink, Permlink originalPermlinkOfTheCommentToUpdate, String content, String[] tags,
			String format, Map<String, Object> extraMetadata)
			throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
		if (tags == null || tags.length < 1 || tags.length > 5) {
			throw new InvalidParameterException(TAG_ERROR_MESSAGE);
		}
		ArrayList<Operation> operations = new ArrayList<>();

		String jsonMetadata = CondenserUtils.generateSteemitMetadata(content, tags,
				SteemJConfig.getSteemJAppName() + "/" + SteemJConfig.getSteemJVersion(), format, extraMetadata);

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
	 * This method is like the
	 * {@link #updateComment(AccountName, Permlink, Permlink, String, String[])}
	 * method, but allows you to define the
	 * <code>originalAuthorOfTheCommentToUpdate</code> account separately instead of
	 * using the {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
	 * 
	 * @param originalAuthorOfTheCommentToUpdate
	 *            The account that wants to perform the update. In most cases, this
	 *            should be the author of the already existing comment.
	 * @param parentAuthor
	 *            The author of the post or comment that you initially replied to.
	 * @param parentPermlink
	 *            The permlink of the post or comment that you initially replied to.
	 * @param originalPermlinkOfTheCommentToUpdate
	 *            The permlink of the comment to update.
	 * @param content
	 *            The new content of the comment to set.
	 * @param tags
	 *            The new tags of the comment. <b>Attention</b> The first tag still
	 *            needs to be the same as before otherwise SteemJ could accidently
	 *            create a new comment instead of updating an existing one.
	 * @param format
	 *            Declared format of message body. Usually one of
	 *            <strong>markdown</strong>, <strong>markdown+html</strong>, or
	 *            <strong>text/html</strong>.
	 * @param extraMetadata
	 *            Additional jsonMetadata to be added to post. Entries here
	 *            <strong>supersede</strong> any SteemJ autocalculated jsonMetadata
	 *            values.
	 * 
	 * @return The {@link CommentOperation} which has been created within this
	 *         method. The returned Operation allows you to access the generated
	 *         values.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
		return updateComment(originalAuthorOfTheCommentToUpdate, parentAuthor, parentPermlink,
				originalPermlinkOfTheCommentToUpdate, content, tags, MARKDOWN, null);
	}

	/**
	 * Use this method to remove a comment or a post.
	 * 
	 * <b>Attention</b>
	 * <ul>
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a voting operation the
	 * private posting key of the {@link SteemJConfig#getDefaultAccount()
	 * DefaultAccount} needs to be configured in the
	 * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the author of the
	 * comment or post to remove - If no default account has been provided, this
	 * method will throw an error. If you do not want to configure the author as a
	 * default account, please use the
	 * {@link #deletePostOrComment(AccountName, Permlink)} method and provide the
	 * author account separately.</li>
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * This method is like the {@link #deletePostOrComment(Permlink)} method, but
	 * allows you to define the author account separately instead of using the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
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
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} to recipient. Amount
	 * is automatically converted from normalized representation to base
	 * representation. For example, to transfer 1.00 SBD to another account, simply
	 * use:
	 * <code>SteemJ.transfer(new AccountName("accountb"), new Asset(1.0, AssetSymbolType.SBD), "My memo");</code>
	 *
	 * <b>Attention</b>
	 * <ul>
	 * <li>This method will write data on the blockchain. As all writing operations,
	 * a private key is required to sign the transaction. For a transfer operation
	 * the private active key of the {@link SteemJConfig#getDefaultAccount()
	 * DefaultAccount} needs to be configured in the
	 * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account to
	 * transfer from. If no default account has been provided, this method will
	 * throw an error. If you do not want to configure the following account as a
	 * default account, please use the
	 * {@link #transfer(AccountName, AccountName, Asset, String)} method and provide
	 * the <code>from</code> account separately.</li>
	 * </ul>
	 *
	 * @param to
	 *            The account name of the account the
	 *            {@link SteemJConfig#getDefaultAccount() DefaultAccount} should
	 *            transfer currency to.
	 * @param amount
	 *            An {@link Asset} object containing the Asset type (see
	 *            {@link eu.bittrade.libs.steemj.enums.AssetSymbolType} and the
	 *            amount to transfer.
	 * @param memo
	 *            Message include with transfer (255 char max)
	 * @return The TransferOperation broadcast.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * representation. For example, to transfer 1.00 SBD to another account, simply
	 * use:
	 * <code>SteemJ.transfer(new AccountName("accounta"), new AccountName("accountb"), AssetSymbolType.SBD, 1.0, "My memo");</code>
	 *
	 * <b>Attention</b> This method will write data on the blockchain. As all
	 * writing operations, a private key is required to sign the transaction. For a
	 * transfer operation the private active key of the
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
	 *            {@link eu.bittrade.libs.steemj.enums.AssetSymbolType} and the
	 *            amount to transfer.
	 * @param memo
	 *            Message include with transfer (255 char max)
	 * @return The TransferOperation broadcast.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * Claim all available Steem, SDB and VEST (Steam Power) rewards for the default
	 * account.
	 *
	 * <b>Attention</b>
	 * <ul>
	 * <li>This method will write data on the blockchain if a reward balance is
	 * available to be claimed. As with all writing operations, a private key is
	 * required to sign the transaction. See
	 * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.</li>
	 * <li>This method will automatically use the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount} as the account that
	 * will follow the <code>accountToFollow</code> - If no default account has been
	 * provided, this method will throw an error. If you do not want to configure
	 * the following account as a default account, please use the
	 * {@link #follow(AccountName, AccountName)} method and provide the following
	 * account separately.</li>
	 * </ul>
	 *
	 * @return The ClaimOperation for reward balances found. This will only have
	 *         been broadcast if one of the balances is non-zero.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * <b>Attention</b> This method will write data on the blockchain if a reward
	 * balance is available to be claimed. As with all writing operations, a private
	 * key is required to sign the transaction. See
	 * {@link SteemJConfig#getPrivateKeyStorage() PrivateKeyStorage}.
	 *
	 * @param accountName
	 *            The account to claim rewards for.
	 * @return The ClaimOperation for reward balances found. This will only have
	 *         been broadcast if one of the balances is non-zero.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * account to the <code>delegatee</code> account. The vesting shares are still
	 * owned by the original account, but content voting rights and bandwidth
	 * allocation are transferred to the receiving account. This sets the delegation
	 * to `vesting_shares`, increasing it or decreasing it as needed. (i.e. a
	 * delegation of 0 removes the delegation) When a delegation is removed the
	 * shares are placed in limbo for a week to prevent a satoshi of VESTS from
	 * voting on the same content twice.
	 * 
	 * @param delegatee
	 *            The account that the vesting shares are delegated to.
	 * @param vestingShares
	 *            The amount of vesting shares.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
	 * {@link #delegateVestingShares(AccountName, AccountName, Asset)} method, but
	 * allows you to define the author account separately instead of using the
	 * {@link SteemJConfig#getDefaultAccount() DefaultAccount}.
	 * 
	 * @param delegator
	 *            The account that will delegate the vesting shares.
	 * @param delegatee
	 *            The account that the vesting shares are delegated to.
	 * @param vestingShares
	 *            The amount of vesting shares.
	 * @throws SteemCommunicationException
	 *             <ul>
	 *             <li>If the server was not able to answer the request in the given
	 *             time (see
	 *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
	 *             setResponseTimeout}).</li>
	 *             <li>If there is a connection problem.</li>
	 *             </ul>
	 * @throws SteemResponseException
	 *             <ul>
	 *             <li>If the SteemJ is unable to transform the JSON response into a
	 *             Java object.</li>
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
