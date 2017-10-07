package eu.bittrade.libs.steemj;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

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
import eu.bittrade.libs.steemj.apis.market.history.MarketHistoryApi;
import eu.bittrade.libs.steemj.apis.market.history.model.Bucket;
import eu.bittrade.libs.steemj.apis.market.history.model.MarketTicker;
import eu.bittrade.libs.steemj.apis.market.history.model.MarketTrade;
import eu.bittrade.libs.steemj.apis.market.history.model.MarketVolume;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.AppliedOperation;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.BeneficiaryRouteType;
import eu.bittrade.libs.steemj.base.models.BlockHeader;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.CommentOptionsExtension;
import eu.bittrade.libs.steemj.base.models.CommentPayoutBeneficiaries;
import eu.bittrade.libs.steemj.base.models.Config;
import eu.bittrade.libs.steemj.base.models.Discussion;
import eu.bittrade.libs.steemj.base.models.DiscussionQuery;
import eu.bittrade.libs.steemj.base.models.ExtendedAccount;
import eu.bittrade.libs.steemj.base.models.ExtendedLimitOrder;
import eu.bittrade.libs.steemj.base.models.FeedHistory;
import eu.bittrade.libs.steemj.base.models.GlobalProperties;
import eu.bittrade.libs.steemj.base.models.HardforkSchedule;
import eu.bittrade.libs.steemj.base.models.LiquidityBalance;
import eu.bittrade.libs.steemj.base.models.OrderBook;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.RewardFund;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.SteemVersionInfo;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.base.models.TrendingTag;
import eu.bittrade.libs.steemj.base.models.Vote;
import eu.bittrade.libs.steemj.base.models.VoteState;
import eu.bittrade.libs.steemj.base.models.Witness;
import eu.bittrade.libs.steemj.base.models.WitnessSchedule;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.base.models.operations.CommentOptionsOperation;
import eu.bittrade.libs.steemj.base.models.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.base.models.operations.DeleteCommentOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.base.models.operations.VoteOperation;
import eu.bittrade.libs.steemj.communication.BlockAppliedCallback;
import eu.bittrade.libs.steemj.communication.CallbackHub;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.dto.RequestWrapperDTO;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.DiscussionSortType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.RequestMethods;
import eu.bittrade.libs.steemj.enums.RewardFundType;
import eu.bittrade.libs.steemj.enums.SteemApis;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemTransformationException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class is a wrapper for the Steem web socket API and provides all
 * features known from the Steem CLI Wallet.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemJ {
    private static final Logger LOGGER = LoggerFactory.getLogger(SteemJ.class);

    private CommunicationHandler communicationHandler;

    /**
     * Initialize the SteemJ.
     * 
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public SteemJ() throws SteemCommunicationException {
        this.communicationHandler = new CommunicationHandler();

        if (!("").equals(String.valueOf(SteemJConfig.getInstance().getApiPassword()))
                && !SteemJConfig.getInstance().getApiUsername().isEmpty()) {

            LOGGER.info("Calling the login method with the prodvided credentials before checking the available apis.");
            if (login(SteemJConfig.getInstance().getApiUsername(),
                    String.valueOf(SteemJConfig.getInstance().getApiPassword()))) {
                LOGGER.info("You have been logged in.");
            } else {
                LOGGER.error("Login failed. The following check of available apis will be done as a anonymous user.");
            }
        } else {
            LOGGER.info(
                    "No credentials have been provided. The following check of available apis will be done as a anonymous user.");
            login(new AccountName(""), "");
        }

        // Check all known apis
        for (SteemApis steemApi : SteemApis.values()) {
            if (getApiByName(steemApi.toString().toLowerCase()) == null) {
                LOGGER.warn("The {} is not published by the configured node.", steemApi);
            }
        }
    }

    // #########################################################################
    // ## NETWORK BROADCAST API ################################################
    // #########################################################################

    /**
     * Broadcast a transaction on the Steem blockchain.
     * 
     * @param transaction
     *            A transaction object that has been signed.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public void broadcastTransaction(SignedTransaction transaction) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.BROADCAST_TRANSACTION);
        requestObject.setSteemApi(SteemApis.NETWORK_BROADCAST_API);

        // TODO: transaction.sign();
        Object[] parameters = { transaction };
        requestObject.setAdditionalParameters(parameters);

        communicationHandler.performRequest(requestObject, Object.class);
    }

    // TODO implement this!
    public Boolean broadcastTransactionSynchronous(SignedTransaction transaction) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.BROADCAST_TRANSACTION_SYNCHRONOUS);
        requestObject.setSteemApi(SteemApis.NETWORK_BROADCAST_API);
        Object[] parameters = { transaction };
        requestObject.setAdditionalParameters(parameters);

        return null;
    }

    // #########################################################################
    // ## DATABASE API #########################################################
    // #########################################################################

    /**
     * Get the current number of registered Steem accounts.
     * 
     * @return The number of accounts.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public int getAccountCount() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_COUNT);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     * @return A map containing the activities. The key is the id of the
     *         activity.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Map<Integer, AppliedOperation> getAccountHistory(AccountName accountName, int from, int limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_HISTORY);
        String[] parameters = { accountName.getName(), String.valueOf(from), String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        Map<Integer, AppliedOperation> accountActivities = new HashMap<>();

        for (Object[] accountActivity : communicationHandler.performRequest(requestObject, Object[].class)) {
            accountActivities.put((Integer) accountActivity[0], CommunicationHandler.getObjectMapper()
                    .convertValue(accountActivity[1], new TypeReference<AppliedOperation>() {
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<ExtendedAccount> getAccounts(List<AccountName> accountNames) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Vote> getAccountVotes(AccountName accountName) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_VOTES);
        String[] parameters = { accountName.getName() };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Vote.class);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<VoteState> getActiveVotes(AccountName author, Permlink permlink) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_ACTIVE_VOTES);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { author.getName(), permlink.getLink() };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, VoteState.class);
    }

    /**
     * Get the account names of the active witnesses.
     * 
     * @return A list of account names of the active witnesses.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public String[] getActiveWitnesses() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_ACTIVE_WITNESSES);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, String[].class).get(0);
    }

    /**
     * Returns the id of an api or null if no api with the given name could be
     * found.
     * 
     * @param apiName
     *            The name of the api.
     * @return The id for the given api name or null, if the api is not active
     *         or does not exist.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Integer getApiByName(String apiName) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_API_BY_NAME);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
        String[] parameters = { apiName };
        requestObject.setAdditionalParameters(parameters);

        List<Integer> response = communicationHandler.performRequest(requestObject, Integer.class);
        if (!response.isEmpty()) {
            return response.get(0);
        }

        return null;
    }

    /**
     * Get a complete block by a given block number including all transactions
     * of this block.
     * 
     * @param blockNumber
     *            The id of the block the header should be requested from.
     * @return A complete block.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public SignedBlockWithInfo getBlock(long blockNumber) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_BLOCK);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { String.valueOf(blockNumber) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, SignedBlockWithInfo.class).get(0);
    }

    /**
     * Get only the header of a block instead of the complete one.
     * 
     * @param blockNumber
     *            The id of the block the header should be requested from.
     * @return The header of a block.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public BlockHeader getBlockHeader(long blockNumber) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_BLOCK_HEADER);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { String.valueOf(blockNumber) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, BlockHeader.class).get(0);
    }

    /**
     * Get the chain properties.
     * 
     * @return The chain properties.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public ChainProperties getChainProperties() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_CHAIN_PROPERTIES);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Config getConfig() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_CONFIG);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Discussion getContent(AccountName author, Permlink permlink) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_CONTENT);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getContentReplies(AccountName author, Permlink permlink)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_CONTENT_REPLIES);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Object[] getConversionRequests(AccountName account) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_CONVERSION_REQUESTS);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Price getCurrentMedianHistoryPrice() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_CURRENT_MEDIAN_HISTORY_PRICE);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getDiscussionsBy(DiscussionQuery discussionQuery, DiscussionSortType sortBy)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();

        requestObject.setApiMethod(RequestMethods.valueOf(sortBy.name()));
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getDiscussionsByAuthorBeforeDate(AccountName author, Permlink permlink, String date,
            int limit) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();

        requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_AUTHOR_BEFORE_DATE);
        requestObject.setSteemApi(SteemApis.DATABASE_API);

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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public GlobalProperties getDynamicGlobalProperties() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_DYNAMIC_GLOBAL_PROPERTIES);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, GlobalProperties.class).get(0);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public FeedHistory getFeedHistory() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_FEED_HISTORY);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public String getHardforkVersion() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_HARDFORK_VERSION);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<String[]> getKeyReferences(String[] publicKeys) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_KEY_REFERENCES);
        requestObject.setSteemApi(SteemApis.ACCOUNT_BY_KEY_API);
        Object[] parameters = { publicKeys };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, String[].class);
    }

    /**
     * Get the liquidity queue for a specified account.
     * 
     * @param accoutName
     *            The name of the account you want to request the queue entries
     *            for.
     * @param limit
     *            Number of results.
     * @return A list of liquidity queue entries.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<LiquidityBalance> getLiquidityQueue(AccountName accoutName, int limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_LIQUIDITY_QUEUE);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        Object[] parameters = { accoutName.getName(), String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, LiquidityBalance.class);
    }

    /**
     * Get the current miner queue.
     * 
     * @return A list of account names that are in the mining queue.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public String[] getMinerQueue() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_MINER_QUEUE);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, String[].class).get(0);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public HardforkSchedule getNextScheduledHarfork() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_NEXT_SCHEDULED_HARDFORK);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, HardforkSchedule.class).get(0);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<ExtendedLimitOrder> getOpenOrders(AccountName accountName) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_OPEN_ORDERS);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public OrderBook getOrderBookUsingDatabaseApi(int limit) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_ORDER_BOOK);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, OrderBook.class).get(0);
    }

    /**
     * Get a list of all performed operations for a given block number.
     * 
     * @param blockNumber
     *            The block number.
     * @param onlyVirtual
     *            Define if only virtual operations should be returned or not.
     * @return A list of all performed operations for a given block number.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<AppliedOperation> getOpsInBlock(int blockNumber, boolean onlyVirtual)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_OPS_IN_BLOCK);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { String.valueOf(blockNumber), String.valueOf(onlyVirtual) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, AppliedOperation.class);
    }

    // TODO implement this!
    public List<String[]> getPotentialSignatures() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_POTENTIAL_SIGNATURES);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        Object[] parameters = {};
        requestObject.setAdditionalParameters(parameters);
        LOGGER.info("output: {}", communicationHandler.performRequest(requestObject, Object[].class));
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getRepliesByLastUpdate(AccountName username, Permlink permlink, int limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_REPLIES_BY_LAST_UPDATE);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     * @return A refund object containing detailed information about the
     *         requested reward fund.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public RewardFund getRewardFund(RewardFundType rewordFundType) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_REWARD_FUND);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        Object[] parameters = { rewordFundType.name().toLowerCase() };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, RewardFund.class).get(0);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public String getTransactionHex(SignedTransaction signedTransaction) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_TRANSACTION_HEX);
        requestObject.setSteemApi(SteemApis.DATABASE_API);

        Object[] parameters = { signedTransaction };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, String.class).get(0);
    }

    /**
     * Returns detailed values for tags that match the given conditions.
     * 
     * @param firstTag
     *            Start the list after this category. An empty String will
     *            result in starting from the top.
     * @param limit
     *            The number of results.
     * @return A list of tags.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<TrendingTag> getTrendingTags(String firstTag, int limit) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_TRENDING_TAGS);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { firstTag, String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, TrendingTag.class);
    }

    /**
     * Get the version information of the connected node.
     * 
     * @return The steem version that the connected node is running.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public SteemVersionInfo getVersion() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_VERSION);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, SteemVersionInfo.class).get(0);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Witness getWitnessByAccount(AccountName witnessName) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_WITNESS_BY_ACCOUNT);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { witnessName.getName() };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Witness.class).get(0);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Witness> getWitnessByVote(AccountName witnessName, int limit) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_WITNESSES_BY_VOTE);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public int getWitnessCount() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_WITNESS_COUNT);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Witness> getWitnesses() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_WITNESSES);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public WitnessSchedule getWitnessSchedule() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_WITNESS_SCHEDULE);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, WitnessSchedule.class).get(0);
    }

    /**
     * Login under the use of the credentials which are stored in the config
     * object.
     * 
     * <p>
     * <b>Notice:</b> The login method is only needed to access protected apis.
     * For some apis like the broadcast_api a call of this method with empty
     * strings can be enough to access them.
     * 
     * @return true if the login was successful. False otherwise.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Boolean login() throws SteemCommunicationException {
        return login(SteemJConfig.getInstance().getApiUsername(),
                String.valueOf(SteemJConfig.getInstance().getApiPassword()));
    }

    /**
     * Login under the use of the specified credentials.
     * 
     * <p>
     * <b>Notice:</b> The login method is only needed to access protected apis.
     * For some apis like the broadcast_api a call of this method with empty
     * strings can be enough to access them.
     * 
     * @param accountName
     *            The username used to login.
     * @param password
     *            The password.
     * @return true if the login was successful. False otherwise.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Boolean login(AccountName accountName, String password) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.LOGIN);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
        String[] parameters = { accountName.getName(), password };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Boolean.class).get(0);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<String> lookupAccounts(String pattern, int limit) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.LOOKUP_ACCOUNTS);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<String> lookupWitnessAccounts(String pattern, int limit) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.LOOKUP_WITNESS_ACCOUNTS);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { pattern, String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, String.class);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Boolean verifyAuthority(SignedTransaction signedTransaction) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.VERIFY_AUTHORITY);
        requestObject.setSteemApi(SteemApis.DATABASE_API);

        Object[] parameters = { signedTransaction };
        requestObject.setAdditionalParameters(parameters);
        // The method does not simply return false, it throws an error
        // describing the problem.
        return communicationHandler.performRequest(requestObject, Boolean.class).get(0);
    }

    /**
     * Use this method to register a callback method that is called whenever a
     * new block has been applied.
     * 
     * <p>
     * <b>Notice:</b>
     * 
     * That there can only be one active Callback. If you call this method
     * multiple times with different callback methods, only the last one will be
     * called.
     * 
     * Beside that there is currently no way to cancel a subscription. Once
     * you've registered a callback it will be called until the connection has
     * been closed.
     * </p>
     * 
     * @param blockAppliedCallback
     *            A class implementing the
     *            {@link eu.bittrade.libs.steemj.communication.BlockAppliedCallback
     *            BlockAppliedCallback}.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public void setBlockAppliedCallback(BlockAppliedCallback blockAppliedCallback) throws SteemCommunicationException {
        // Register the given callback at the callback hub.
        CallbackHub.getInstance().addCallback(blockAppliedCallback);

        // Register the callback at the steem node.
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.SET_BLOCK_APPLIED_CALLBACK);
        requestObject.setSteemApi(SteemApis.DATABASE_API);

        Object[] parameters = { blockAppliedCallback.getUuid() };
        requestObject.setAdditionalParameters(parameters);

        communicationHandler.performRequest(requestObject, Object.class);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<FollowApiObject> getFollowers(AccountName following, AccountName startFollower, FollowType type,
            short limit) throws SteemCommunicationException {
        return FollowApi.getFollowers(communicationHandler, following, startFollower, type, limit);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<FollowApiObject> getFollowing(AccountName follower, AccountName startFollowing, FollowType type,
            short limit) throws SteemCommunicationException {
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public FollowCountApiObject getFollowCount(AccountName account) throws SteemCommunicationException {
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<FeedEntry> getFeedEntries(AccountName account, int entryId, short limit)
            throws SteemCommunicationException {
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<CommentFeedEntry> getFeed(AccountName account, int entryId, short limit)
            throws SteemCommunicationException {
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<BlogEntry> getBlogEntries(AccountName account, int entryId, short limit)
            throws SteemCommunicationException {
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<CommentBlogEntry> getBlog(AccountName account, int entryId, short limit)
            throws SteemCommunicationException {
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
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<AccountReputation> getAccountReputations(AccountName accountName, int limit)
            throws SteemCommunicationException {
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<AccountName> getRebloggedBy(AccountName author, Permlink permlink) throws SteemCommunicationException {
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<PostsPerAuthorPair> getBlogAuthors(AccountName blogAccount) throws SteemCommunicationException {
        return FollowApi.getBlogAuthors(communicationHandler, blogAccount);
    }

    // #########################################################################
    // ## MARKET HISTORY API ###################################################
    // #########################################################################

    /**
     * @return The market ticker for the internal SBD:STEEM market.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public MarketTicker getTicker() throws SteemCommunicationException {
        return MarketHistoryApi.getTicker(communicationHandler);
    }

    /**
     * @return The market volume for the past 24 hours.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public MarketVolume getVolume() throws SteemCommunicationException {
        return MarketHistoryApi.getVolume(communicationHandler);
    }

    /**
     * @param limit
     *            The number of orders to have on each side of the order book.
     *            Maximum is 500.
     * @return Returns the current order book for the internal SBD:STEEM market.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     * @throws InvalidParameterException
     *             If the limit is less than 0 or greater than 500.
     */
    public eu.bittrade.libs.steemj.apis.market.history.model.OrderBook getOrderBookUsingMarketApi(short limit)
            throws SteemCommunicationException {
        return MarketHistoryApi.getOrderBook(communicationHandler, limit);
    }

    /**
     * Returns the trade history for the internal SBD:STEEM market.
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     * @throws InvalidParameterException
     *             If the limit is less than 0 or greater than 500.
     */
    public List<MarketTrade> getTradeHistory(TimePointSec start, TimePointSec end, short limit)
            throws SteemCommunicationException {
        return MarketHistoryApi.getTradeHistory(communicationHandler, start, end, limit);
    }

    /**
     * Returns the <code>limit</code> most recent trades for the internal
     * SBD:STEEM market.
     *
     * @param limit
     *            The number of trades to return. Maximum is 1000.
     * @return A list of completed trades.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     * @throws InvalidParameterException
     *             If the limit is less than 0 or greater than 500.
     */
    public List<MarketTrade> getRecentTrades(short limit) throws SteemCommunicationException {
        return MarketHistoryApi.getRecentTrades(communicationHandler, limit);
    }

    /**
     * Returns the market history for the internal SBD:STEEM market.
     * 
     * @param bucketSeconds
     *            The size of buckets the history is broken into. The bucket
     *            size must be configured in the plugin options.
     * @param start
     *            The start time to get market history.
     * @param end
     *            The end time to get market history.
     * @return A list of market history
     *         {@link eu.bittrade.libs.steemj.apis.market.history.model.Bucket
     *         Bucket}s.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Bucket> getMarketHistory(long bucketSeconds, TimePointSec start, TimePointSec end)
            throws SteemCommunicationException {
        return MarketHistoryApi.getMarketHistory(communicationHandler, bucketSeconds, start, end);
    }

    /**
     * @return Returns the bucket seconds being tracked by the plugin.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(long)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Integer> getMarketHistoryBuckets() throws SteemCommunicationException {
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
     *             If there is a problem reaching the Steem Node.
     * @throws SteemInvalidTransactionException
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void vote(AccountName postOrCommentAuthor, Permlink postOrCommentPermlink, short percentage)
            throws SteemCommunicationException, SteemInvalidTransactionException {
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
     *             If there is a problem reaching the Steem Node.
     * @throws SteemInvalidTransactionException
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void vote(AccountName voter, AccountName postOrCommentAuthor, Permlink postOrCommentPermlink,
            short percentage) throws SteemCommunicationException, SteemInvalidTransactionException {
        if (percentage < -100 || percentage > 100 || percentage == 0) {
            throw new InvalidParameterException(
                    "Please provide a percentage between -100 and 100 which is also not 0.");
        }

        VoteOperation voteOperation = new VoteOperation(voter, postOrCommentAuthor, postOrCommentPermlink,
                (short) (percentage * 100));

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        GlobalProperties globalProperties = this.getDynamicGlobalProperties();

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
     *             If there is a problem reaching the Steem Node.
     * @throws SteemInvalidTransactionException
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void cancelVote(AccountName postOrCommentAuthor, Permlink postOrCommentPermlink)
            throws SteemCommunicationException, SteemInvalidTransactionException {
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
     *             If there is a problem reaching the Steem Node.
     * @throws SteemInvalidTransactionException
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void cancelVote(AccountName voter, AccountName postOrCommentAuthor, Permlink postOrCommentPermlink)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        VoteOperation voteOperation = new VoteOperation(voter, postOrCommentAuthor, postOrCommentPermlink, (short) 0);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        GlobalProperties globalProperties = this.getDynamicGlobalProperties();

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
     *             If there is a problem reaching the Steem Node.
     * @throws SteemInvalidTransactionException
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void follow(AccountName accountToFollow)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(
                    "Using the follow method without providing an account requires to have a default account configured.");
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
     *             If there is a problem reaching the Steem Node.
     * @throws SteemInvalidTransactionException
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void follow(AccountName accountThatFollows, AccountName accountToFollow)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(accountThatFollows);

        String id = "follow";
        String json = "[\"follow\",{\"follower\":\"" + accountThatFollows.getName() + "\",\"following\":\""
                + accountToFollow.getName() + "\",\"what\":[\"blog\"]}]";

        CustomJsonOperation customJsonOperation = new CustomJsonOperation(null, requiredPostingAuths, id, json);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        GlobalProperties globalProperties = this.getDynamicGlobalProperties();

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
     *             If there is a problem reaching the Steem Node.
     * @throws SteemInvalidTransactionException
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void unfollow(AccountName accountToUnfollow)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(
                    "Using the unfollow method without providing an account requires to have a default account configured.");
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
     *             If there is a problem reaching the Steem Node.
     * @throws SteemInvalidTransactionException
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void unfollow(AccountName accountThatUnfollows, AccountName accountToUnfollow)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(accountThatUnfollows);

        String id = "follow";
        String json = "[\"follow\",{\"follower\":\"" + accountThatUnfollows.getName() + "\",\"following\":\""
                + accountToUnfollow.getName() + "\",\"what\":[\"\"]}]";

        CustomJsonOperation customJsonOperation = new CustomJsonOperation(null, requiredPostingAuths, id, json);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        GlobalProperties globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
    }

    /**
     * 
     * @param title
     * @param content
     * @param tags
     * @throws SteemCommunicationException
     * @throws SteemInvalidTransactionException
     */
    public void createPost(String title, String content, String[] tags)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(
                    "Using the unfollow method without providing an account requires to have a default account configured.");
        }

        createPost(SteemJConfig.getInstance().getDefaultAccount(), title, content, tags);
    }

    /**
     * 
     * @param authorThatPublishsThePost
     * @param title
     * @param content
     * @param tags
     * @throws SteemCommunicationException
     * @throws SteemInvalidTransactionException
     */
    public void createPost(AccountName authorThatPublishsThePost, String title, String content, String[] tags)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        if (tags == null || tags.length < 1 || tags.length > 5) {
            throw new InvalidParameterException("You need to provide at least one tag, but not more than five.");
        }
        ArrayList<Operation> operations = new ArrayList<>();

        // Generate the permanent link from the title by replacing all unallowed
        // characters.
        Permlink permlink = new Permlink(title.toLowerCase().replaceAll("[^a-z0-9-]+", ""));
        // On new posts the parentPermlink is the main tag.
        Permlink parentPermlink = new Permlink(tags[0]);
        // One new posts the parentAuthor is empty.
        AccountName parentAuthor = new AccountName("");
        // Collect all information for the meta data.
        // 1. Gather all links from the content
        List<String> linksInContent = SteemJUtils.extractLinksFromContent(content);
        // 2. Gather all images from the content
        List<String> imagesFromLinks = new ArrayList<>();
        List<String> linksInContentCleaned = new ArrayList<>();
        for (String link : linksInContent) {
            if (link.endsWith(".png") || link.endsWith(".PNG") || link.endsWith(".jpg") || link.endsWith(".JPG")
                    || link.endsWith(".jpeg") || link.endsWith(".JPEG") || link.endsWith(".gif")
                    || link.endsWith(".GIF")) {
                imagesFromLinks.add(link);
            } else {
                linksInContentCleaned.add(link);
            }
        }
        // 3. Gather all users from the content
        List<String> usernamesInContent = SteemJUtils.extractUsersFromContent(content);

        // Now build everything together and add it as the metadata.
        // TODO: Improve this!
        StringBuilder jsonMetadataBuilder = new StringBuilder();
        jsonMetadataBuilder.append("{");
        jsonMetadataBuilder.append("\"tags\":[\"" + tags[0] + "\"");
        for (int i = 1; i < tags.length; i++) {
            jsonMetadataBuilder.append(",\"" + tags[i] + "\"");
        }
        jsonMetadataBuilder.append("],");
        if (!usernamesInContent.isEmpty()) {
            jsonMetadataBuilder.append("\"users\":[\"" + usernamesInContent.get(0) + "\"");
            for (int i = 1; i < usernamesInContent.size(); i++) {
                jsonMetadataBuilder.append(",\"" + usernamesInContent.get(i) + "\"");
            }
            jsonMetadataBuilder.append("],");
        }
        if (!imagesFromLinks.isEmpty()) {
            jsonMetadataBuilder.append("\"images\":[\"" + imagesFromLinks.get(0) + "\"");
            for (int i = 1; i < imagesFromLinks.size(); i++) {
                jsonMetadataBuilder.append(",\"" + imagesFromLinks.get(i) + "\"");
            }
            jsonMetadataBuilder.append("],");
        }
        if (!linksInContentCleaned.isEmpty()) {
            jsonMetadataBuilder.append("\"links\":[\"" + linksInContentCleaned.get(0) + "\"");
            for (int i = 1; i < linksInContentCleaned.size(); i++) {
                jsonMetadataBuilder.append(",\"" + linksInContentCleaned.get(i) + "\"");
            }
            jsonMetadataBuilder.append("],");
        }
        jsonMetadataBuilder.append("\"app\":\"steemj/0.4.0\",\"format\":\"markdown\"}");

        CommentOperation commentOperation = new CommentOperation(parentAuthor, parentPermlink,
                authorThatPublishsThePost, permlink, title, content, jsonMetadataBuilder.toString());

        operations.add(commentOperation);

        boolean allowVotes = true;
        boolean allowCurationRewards = true;
        short percentSteemDollars = (short) 10000;
        Asset maxAcceptedPayout = new Asset(1000000000, AssetSymbolType.SBD);

        BeneficiaryRouteType beneficiaryRouteType = new BeneficiaryRouteType();
        beneficiaryRouteType.setAccount(new AccountName("steemj"));
        beneficiaryRouteType.setWeight(SteemJConfig.getInstance().getSteemJWeight());

        ArrayList<BeneficiaryRouteType> beneficiaryRouteTypes = new ArrayList<>();
        beneficiaryRouteTypes.add(beneficiaryRouteType);

        CommentPayoutBeneficiaries commentPayoutBeneficiaries = new CommentPayoutBeneficiaries();
        commentPayoutBeneficiaries.setBeneficiaries(beneficiaryRouteTypes);

        ArrayList<CommentOptionsExtension> commentOptionsExtensions = new ArrayList<>();
        commentOptionsExtensions.add(commentPayoutBeneficiaries);

        CommentOptionsOperation commentOptionsOperation = new CommentOptionsOperation(authorThatPublishsThePost,
                permlink, maxAcceptedPayout, percentSteemDollars, allowVotes, allowCurationRewards,
                commentOptionsExtensions);

        operations.add(commentOptionsOperation);

        GlobalProperties globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
    }

    /**
     * 
     * @param authorOfThePostOrCommentToReplyTo
     * @param permlinkOfThePostOrCommentToReplyTo
     * @param content
     * @param tags
     * @throws SteemCommunicationException
     * @throws SteemInvalidTransactionException
     */
    public void createComment(AccountName authorOfThePostOrCommentToReplyTo,
            Permlink permlinkOfThePostOrCommentToReplyTo, String content, String[] tags)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(
                    "Using the unfollow method without providing an account requires to have a default account configured.");
        }

        createComment(authorOfThePostOrCommentToReplyTo, permlinkOfThePostOrCommentToReplyTo,
                SteemJConfig.getInstance().getDefaultAccount(), content, tags);
    }

    /**
     * 
     * @param authorOfThePostOrCommentToReplyTo
     * @param permlinkOfThePostOrCommentToReplyTo
     * @param authorThatPublishsTheComment
     * @param content
     * @param tags
     * @throws SteemCommunicationException
     * @throws SteemInvalidTransactionException
     */
    public void createComment(AccountName authorOfThePostOrCommentToReplyTo,
            Permlink permlinkOfThePostOrCommentToReplyTo, AccountName authorThatPublishsTheComment, String content,
            String[] tags) throws SteemCommunicationException, SteemInvalidTransactionException {
        if (tags == null || tags.length < 1 || tags.length > 5) {
            throw new InvalidParameterException("You need to provide at least one tag, but not more than five.");
        }
        ArrayList<Operation> operations = new ArrayList<>();

        // Generate the permanent link by adding the current timestamp and a
        // UUID.
        Permlink permlink = new Permlink("re-" + authorOfThePostOrCommentToReplyTo.getName() + "-"
                + permlinkOfThePostOrCommentToReplyTo.getLink() + "-" + System.currentTimeMillis() + "t"
                + UUID.randomUUID().toString() + "uid");
        // Collect all information for the meta data.
        // Collect all information for the meta data.
        // 1. Gather all links from the content
        List<String> linksInContent = SteemJUtils.extractLinksFromContent(content);
        // 2. Gather all images from the content
        List<String> imagesFromLinks = new ArrayList<>();
        List<String> linksInContentCleaned = new ArrayList<>();
        for (String link : linksInContent) {
            if (link.endsWith(".png") || link.endsWith(".PNG") || link.endsWith(".jpg") || link.endsWith(".JPG")
                    || link.endsWith(".jpeg") || link.endsWith(".JPEG") || link.endsWith(".gif")
                    || link.endsWith(".GIF")) {
                imagesFromLinks.add(link);
            } else {
                linksInContentCleaned.add(link);
            }
        }
        // 3. Gather all users from the content
        List<String> usernamesInContent = SteemJUtils.extractUsersFromContent(content);

        // Now build everything together and add it as the metadata.
        // TODO: Improve this!
        StringBuilder jsonMetadataBuilder = new StringBuilder();
        jsonMetadataBuilder.append("{");
        jsonMetadataBuilder.append("\"tags\":[\"" + tags[0] + "\"");
        for (int i = 1; i < tags.length; i++) {
            jsonMetadataBuilder.append(",\"" + tags[i] + "\"");
        }
        jsonMetadataBuilder.append("],");
        if (!usernamesInContent.isEmpty()) {
            jsonMetadataBuilder.append("\"users\":[\"" + usernamesInContent.get(0) + "\"");
            for (int i = 1; i < usernamesInContent.size(); i++) {
                jsonMetadataBuilder.append(",\"" + usernamesInContent.get(i) + "\"");
            }
            jsonMetadataBuilder.append("],");
        }
        if (!imagesFromLinks.isEmpty()) {
            jsonMetadataBuilder.append("\"images\":[\"" + imagesFromLinks.get(0) + "\"");
            for (int i = 1; i < imagesFromLinks.size(); i++) {
                jsonMetadataBuilder.append(",\"" + imagesFromLinks.get(i) + "\"");
            }
            jsonMetadataBuilder.append("],");
        }
        if (!linksInContentCleaned.isEmpty()) {
            jsonMetadataBuilder.append("\"links\":[\"" + linksInContentCleaned.get(0) + "\"");
            for (int i = 1; i < linksInContentCleaned.size(); i++) {
                jsonMetadataBuilder.append(",\"" + linksInContentCleaned.get(i) + "\"");
            }
            jsonMetadataBuilder.append("],");
        }
        jsonMetadataBuilder.append("\"app\":\"steemj/0.4.0\",\"format\":\"markdown\"}");

        CommentOperation commentOperation = new CommentOperation(authorOfThePostOrCommentToReplyTo,
                permlinkOfThePostOrCommentToReplyTo, authorThatPublishsTheComment, permlink, "", content,
                jsonMetadataBuilder.toString());

        operations.add(commentOperation);

        boolean allowVotes = true;
        boolean allowCurationRewards = true;
        short percentSteemDollars = (short) 10000;
        Asset maxAcceptedPayout = new Asset(1000000000, AssetSymbolType.SBD);

        BeneficiaryRouteType beneficiaryRouteType = new BeneficiaryRouteType();
        beneficiaryRouteType.setAccount(new AccountName("steemj"));
        beneficiaryRouteType.setWeight(SteemJConfig.getInstance().getSteemJWeight());

        ArrayList<BeneficiaryRouteType> beneficiaryRouteTypes = new ArrayList<>();
        beneficiaryRouteTypes.add(beneficiaryRouteType);

        CommentPayoutBeneficiaries commentPayoutBeneficiaries = new CommentPayoutBeneficiaries();
        commentPayoutBeneficiaries.setBeneficiaries(beneficiaryRouteTypes);

        ArrayList<CommentOptionsExtension> commentOptionsExtensions = new ArrayList<>();
        commentOptionsExtensions.add(commentPayoutBeneficiaries);

        CommentOptionsOperation commentOptionsOperation = new CommentOptionsOperation(authorThatPublishsTheComment,
                permlink, maxAcceptedPayout, percentSteemDollars, allowVotes, allowCurationRewards,
                commentOptionsExtensions);

        operations.add(commentOptionsOperation);

        GlobalProperties globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
    }

    /**
     * 
     * @param permlinkOfThePostOrCommentToUpdate
     * @param content
     * @param tags
     * @throws SteemCommunicationException
     * @throws SteemInvalidTransactionException
     */
    public void updateCommentOrPost(Permlink permlinkOfThePostOrCommentToUpdate, String content, String[] tags)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(
                    "Using the unfollow method without providing an account requires to have a default account configured.");
        }

        updateCommentOrPost(SteemJConfig.getInstance().getDefaultAccount(), permlinkOfThePostOrCommentToUpdate, content,
                tags);
    }

    /**
     * 
     * @param authorOfThePostOrCommentToUpdate
     * @param permlinkOfThePostOrCommentToUpdate
     * @param content
     * @param tags
     * @throws SteemCommunicationException
     * @throws SteemInvalidTransactionException
     */
    public void updateCommentOrPost(AccountName authorOfThePostOrCommentToUpdate,
            Permlink permlinkOfThePostOrCommentToUpdate, String content, String[] tags)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        if (tags == null || tags.length < 1 || tags.length > 5) {
            throw new InvalidParameterException("You need to provide at least one tag, but not more than five.");
        }

        ArrayList<Operation> operations = new ArrayList<>();

        // Generate the permanent link by adding the current timestamp and a
        // UUID.

        // Collect all information for the meta data.
        // Collect all information for the meta data.
        // 1. Gather all links from the content
        List<String> linksInContent = SteemJUtils.extractLinksFromContent(content);
        // 2. Gather all images from the content
        List<String> imagesFromLinks = new ArrayList<>();
        List<String> linksInContentCleaned = new ArrayList<>();
        for (String link : linksInContent) {
            if (link.endsWith(".png") || link.endsWith(".PNG") || link.endsWith(".jpg") || link.endsWith(".JPG")
                    || link.endsWith(".jpeg") || link.endsWith(".JPEG") || link.endsWith(".gif")
                    || link.endsWith(".GIF")) {
                imagesFromLinks.add(link);
            } else {
                linksInContentCleaned.add(link);
            }
        }
        // 3. Gather all users from the content
        List<String> usernamesInContent = SteemJUtils.extractUsersFromContent(content);

        // Now build everything together and add it as the metadata.
        // TODO: Improve this!
        StringBuilder jsonMetadataBuilder = new StringBuilder();
        jsonMetadataBuilder.append("{");
        jsonMetadataBuilder.append("\"tags\":[\"" + tags[0] + "\"");
        for (int i = 1; i < tags.length; i++) {
            jsonMetadataBuilder.append(",\"" + tags[i] + "\"");
        }
        jsonMetadataBuilder.append("],");
        if (!usernamesInContent.isEmpty()) {
            jsonMetadataBuilder.append("\"users\":[\"" + usernamesInContent.get(0) + "\"");
            for (int i = 1; i < usernamesInContent.size(); i++) {
                jsonMetadataBuilder.append(",\"" + usernamesInContent.get(i) + "\"");
            }
            jsonMetadataBuilder.append("],");
        }
        if (!imagesFromLinks.isEmpty()) {
            jsonMetadataBuilder.append("\"images\":[\"" + imagesFromLinks.get(0) + "\"");
            for (int i = 1; i < imagesFromLinks.size(); i++) {
                jsonMetadataBuilder.append(",\"" + imagesFromLinks.get(i) + "\"");
            }
            jsonMetadataBuilder.append("],");
        }
        if (!linksInContentCleaned.isEmpty()) {
            jsonMetadataBuilder.append("\"links\":[\"" + linksInContentCleaned.get(0) + "\"");
            for (int i = 1; i < linksInContentCleaned.size(); i++) {
                jsonMetadataBuilder.append(",\"" + linksInContentCleaned.get(i) + "\"");
            }
            jsonMetadataBuilder.append("],");
        }
        jsonMetadataBuilder.append("\"app\":\"steemj/0.4.0\",\"format\":\"markdown\"}");

        // CommentOperation commentOperation = new
        // CommentOperation(authorOfThePostOrCommentToReplyTo,
        // permlinkOfThePostOrCommentToReplyTo, authorThatPublishsTheComment,
        // permlink, "", content,
        // jsonMetadataBuilder.toString());

        // operations.add(commentOptionsOperation);

        GlobalProperties globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
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
     *             If there is a problem reaching the Steem Node.
     * @throws SteemInvalidTransactionException
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void deletePostOrComment(Permlink postOrCommentPermlink)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        if (SteemJConfig.getInstance().getDefaultAccount().isEmpty()) {
            throw new InvalidParameterException(
                    "Using the upVote method without providing an account requires to have a default account configured.");
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
     *             If there is a problem reaching the Steem Node.
     * @throws SteemInvalidTransactionException
     *             If there is a problem while signing the transaction.
     * @throws InvalidParameterException
     *             If one of the provided parameters does not fulfill the
     *             requirements described above.
     */
    public void deletePostOrComment(AccountName postOrCommentAuthor, Permlink postOrCommentPermlink)
            throws SteemCommunicationException, SteemInvalidTransactionException {
        DeleteCommentOperation deleteCommentOperation = new DeleteCommentOperation(postOrCommentAuthor,
                postOrCommentPermlink);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(deleteCommentOperation);

        GlobalProperties globalProperties = this.getDynamicGlobalProperties();

        SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                null);

        signedTransaction.sign();

        this.broadcastTransaction(signedTransaction);
    }
}
