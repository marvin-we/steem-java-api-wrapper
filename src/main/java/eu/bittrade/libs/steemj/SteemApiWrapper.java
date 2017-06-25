package eu.bittrade.libs.steemj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;

import eu.bittrade.libs.steemj.base.models.AccountActivity;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.ActiveVote;
import eu.bittrade.libs.steemj.base.models.BlockHeader;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.Config;
import eu.bittrade.libs.steemj.base.models.Discussion;
import eu.bittrade.libs.steemj.base.models.ExtendedAccount;
import eu.bittrade.libs.steemj.base.models.FeedHistory;
import eu.bittrade.libs.steemj.base.models.GlobalProperties;
import eu.bittrade.libs.steemj.base.models.HardforkSchedule;
import eu.bittrade.libs.steemj.base.models.LiquidityQueueEntry;
import eu.bittrade.libs.steemj.base.models.OrderBook;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.RewardFund;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.base.models.Transaction;
import eu.bittrade.libs.steemj.base.models.TrendingTag;
import eu.bittrade.libs.steemj.base.models.UserOrder;
import eu.bittrade.libs.steemj.base.models.Version;
import eu.bittrade.libs.steemj.base.models.Vote;
import eu.bittrade.libs.steemj.base.models.Witness;
import eu.bittrade.libs.steemj.base.models.WitnessSchedule;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.dto.GetDiscussionParametersDTO;
import eu.bittrade.libs.steemj.communication.dto.RequestWrapperDTO;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.DiscussionSortType;
import eu.bittrade.libs.steemj.enums.RequestMethods;
import eu.bittrade.libs.steemj.enums.RewardFundType;
import eu.bittrade.libs.steemj.enums.SteemApis;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemTransformationException;
import eu.bittrade.libs.steemj.plugins.follow.enums.FollowType;
import eu.bittrade.libs.steemj.plugins.follow.model.AccountReputation;
import eu.bittrade.libs.steemj.plugins.follow.model.BlogEntry;
import eu.bittrade.libs.steemj.plugins.follow.model.CommentBlogEntry;
import eu.bittrade.libs.steemj.plugins.follow.model.CommentFeedEntry;
import eu.bittrade.libs.steemj.plugins.follow.model.FeedEntry;
import eu.bittrade.libs.steemj.plugins.follow.model.FollowApiObject;
import eu.bittrade.libs.steemj.plugins.follow.model.FollowCountApiObject;
import eu.bittrade.libs.steemj.plugins.follow.model.PostsPerAuthorPair;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class is a wrapper for the Steem web socket API.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemApiWrapper {
    private static final Logger LOGGER = LogManager.getLogger(SteemApiWrapper.class);

    private CommunicationHandler communicationHandler;

    /**
     * Initialize the Steem API Wrapper.
     * 
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public SteemApiWrapper() throws SteemCommunicationException {
        this.communicationHandler = new CommunicationHandler();

        if (!("").equals(String.valueOf(SteemJConfig.getInstance().getPassword()))
                && !("").equals(SteemJConfig.getInstance().getUsername())) {

            LOGGER.info("Calling the login method with the prodvided credentials before checking the available apis.");
            if (login(SteemJConfig.getInstance().getUsername(),
                    String.valueOf(SteemJConfig.getInstance().getPassword()))) {
                LOGGER.info("You have been logged in.");
            } else {
                LOGGER.error("Login failed. The following check of available apis will be done as a anonymous user.");
            }
        } else {
            LOGGER.info(
                    "No credentials have been provided. The following check of available apis will be done as a anonymous user.");
            login("", "");
        }

        // Check all known apis
        for (SteemApis steemApi : SteemApis.values()) {
            if (getApiByName(steemApi.toString().toLowerCase()) == null) {
                LOGGER.warn("The {} is not published by the configured node.", steemApi.toString());
            }
        }
    }

    /**
     * Broadcast a transaction on the Steem blockchain.
     * 
     * @param transaction
     *            A transaction object that has been signed.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public void broadcastTransaction(Transaction transaction) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.BROADCAST_TRANSACTION);
        requestObject.setSteemApi(SteemApis.NETWORK_BROADCAST_API);

        // TODO: transaction.sign();
        Object[] parameters = { transaction };
        requestObject.setAdditionalParameters(parameters);

        communicationHandler.performRequest(requestObject, Object.class);
    }

    // TODO implement this!
    public Boolean broadcastTransactionSynchronous(String trx) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.BROADCAST_TRANSACTION_SYNCHRONOUS);
        requestObject.setSteemApi(SteemApis.NETWORK_BROADCAST_API);
        String[] parameters = { trx };
        requestObject.setAdditionalParameters(parameters);

        return null;
    }

    /**
     * Get the current number of registered Steem accounts.
     * 
     * @return The number of accounts.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     * Get the latest activities of a specific account.
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Map<Integer, AccountActivity> getAccountHistory(String accountName, int from, int limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_HISTORY);
        String[] parameters = { accountName, String.valueOf(from), String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        Map<Integer, AccountActivity> accountActivities = new HashMap<>();

        for (Object[] accountActivity : communicationHandler.performRequest(requestObject, Object[].class)) {
            accountActivities.put((Integer) accountActivity[0], communicationHandler.getObjectMapper()
                    .convertValue(accountActivity[1], new TypeReference<AccountActivity>() {
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
            innerParameters[i] = accountNames.get(i).getAccountName();
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Vote> getAccountVotes(String accountName) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_VOTES);
        String[] parameters = { accountName };
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<ActiveVote> getActiveVotes(String author, String permlink) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_ACTIVE_VOTES);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { author, permlink };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, ActiveVote.class);
    }

    /**
     * Get the account names of the active witnesses.
     * 
     * @return A list of account names of the active witnesses.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public String getApiByName(String apiName) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_API_BY_NAME);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
        String[] parameters = { apiName };
        requestObject.setAdditionalParameters(parameters);

        List<String> response = communicationHandler.performRequest(requestObject, String.class);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public BlockHeader getBlockHeader(long blockNumber) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_BLOCK_HEADER);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Discussion getContent(String author, String permlink) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_CONTENT);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { author, permlink };
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getContentReplies(String author, String permlink) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_CONTENT_REPLIES);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { author, permlink };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Discussion.class);
    }

    /**
     * TODO: Look up what this is used for and what it can return.
     * 
     * @return Unknown
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Object[] getConversationRequests() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_CONVERSATION_REQUEST);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
        String[] parameters = {};
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     * @param tag
     *            Get discussions that are tagged with this tag.
     * @param limit
     *            The number of results.
     * @param sortBy
     *            The way how the results should be sorted by.
     * @return A list of discussions.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getDiscussionsBy(String tag, int limit, DiscussionSortType sortBy)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();

        requestObject.setApiMethod(SteemJUtils.getEquivalentRequestMethod(sortBy));
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        // This steem api is the most non standardized shit I've ever seen in my
        // life. Here goes the workaround:
        GetDiscussionParametersDTO getDiscussionParameterDTO = new GetDiscussionParametersDTO();
        getDiscussionParameterDTO.setTag(tag);
        getDiscussionParameterDTO.setLimit(String.valueOf(limit));
        Object[] parameters = { getDiscussionParameterDTO };
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getDiscussionsByAuthorBeforeDate(String author, String permlink, String date, int limit)
            throws SteemCommunicationException {
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

        String[] parameters = { author, permlink, simpleDateFormat.format(beforeDate), String.valueOf(limit) };
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public FeedHistory getFeedHistory() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_FEED_HISTORY);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<LiquidityQueueEntry> getLiquidityQueue(String accoutName, int limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_LIQUIDITY_QUEUE);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        Object[] parameters = { accoutName, String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, LiquidityQueueEntry.class);
    }

    /**
     * Get the current miner queue.
     * 
     * @return A list of account names that are in the mining queue.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public HardforkSchedule getNextScheduledHarfork() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_NEXT_SCHEDULED_HARDFORK);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<UserOrder> getOpenOrders(String accountName) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_OPEN_ORDERS);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
        String[] parameters = { accountName };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, UserOrder.class);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public OrderBook getOrderBook(int limit) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_ORDER_BOOK);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
        String[] parameters = { String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, OrderBook.class).get(0);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getRepliesByLastUpdate(String username, String permlink, int limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_REPLIES_BY_LAST_UPDATE);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        Object[] parameters = { username, permlink, String.valueOf(limit) };
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public String getTransactionHex(Transaction signedTransaction) throws SteemCommunicationException {
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Version getVersion() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_VERSION);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Version.class).get(0);
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Witness getWitnessByAccount(String witnessName) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_WITNESS_BY_ACCOUNT);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { witnessName };
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Witness> getWitnessByVote(String witnessName, int limit) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_WITNESSES_BY_VOTE);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        String[] parameters = { witnessName, String.valueOf(limit) };
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Boolean login() throws SteemCommunicationException {
        return login(SteemJConfig.getInstance().getUsername(),
                String.valueOf(SteemJConfig.getInstance().getPassword()));
    }

    /**
     * Login under the use of the specified credentials.
     * 
     * <p>
     * <b>Notice:</b> The login method is only needed to access protected apis.
     * For some apis like the broadcast_api a call of this method with empty
     * strings can be enough to access them.
     * 
     * @param username
     *            The user name.
     * @param password
     *            The password.
     * @return true if the login was successful. False otherwise.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Boolean login(String username, String password) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.LOGIN);
        requestObject.setSteemApi(SteemApis.LOGIN_API);
        String[] parameters = { username, password };
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
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
     *            A whole and signed transaction object.
     * @return True if the given transaction has been signed correctly or false
     *         if not.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public Boolean verifyAuthority(Transaction signedTransaction) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.VERIFY_AUTHORITY);
        requestObject.setSteemApi(SteemApis.DATABASE_API);

        Object[] parameters = { signedTransaction };
        requestObject.setAdditionalParameters(parameters);
        // TODO: The method does not simply return false, it throws an error
        // describing the problem. The reason should be logged as info and the
        // this method should only return false.
        return communicationHandler.performRequest(requestObject, Boolean.class).get(0);
    }

    /**
     * Get a list of account names which follow the <b>startFollower</b>.
     * 
     * @param following
     *            The account name that started to follow the
     *            <b>startFollower</b>. If you want to receive all followers you
     *            need to use the same account name for both fields.
     * @param startFollower
     *            The account name for which the followers are returned.
     * @param type
     *            The follow type.
     * @param limit
     *            The maximum number of results returned.
     * @return A list of account names which follow the <b>startFollower</b>.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<FollowApiObject> getFollowers(AccountName following, AccountName startFollower, FollowType type,
            short limit) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_FOLLOWERS);
        requestObject.setSteemApi(SteemApis.FOLLOW_API);

        Object[] parameters = { following.getAccountName(), startFollower.getAccountName(),
                type.toString().toLowerCase(), limit };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, FollowApiObject.class);
    }

    /**
     * Get a list of account names which follow the <b>startFollower</b>.
     * 
     * @param following
     *            The account name that started to follow the
     *            <b>startFollower</b>. If you want to receive all followers you
     *            need to use the same account name for both fields.
     * @param startFollower
     *            The account name for which the followers are returned.
     * @param type
     *            The follow type.
     * @param limit
     *            The maximum number of results returned.
     * @return A list of account names which follow the <b>startFollower</b>.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<FollowApiObject> getFollowing(AccountName following, AccountName startFollower, FollowType type,
            short limit) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_FOLLOWING);
        requestObject.setSteemApi(SteemApis.FOLLOW_API);

        Object[] parameters = { following.getAccountName(), startFollower.getAccountName(),
                type.toString().toLowerCase(), limit };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, FollowApiObject.class);
    }

    /**
     * Get the amount of accounts following the given account and the number of
     * accounts this account follows. Both values are wrapped in a
     * FollowCountApiObject.
     * 
     * @param account
     *            The account to get the number of followers / following
     *            accounts for.
     * @return The number of followers / following accounts
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public FollowCountApiObject getFollowCount(AccountName account) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_FOLLOW_COUNT);
        requestObject.setSteemApi(SteemApis.FOLLOW_API);

        Object[] parameters = { account.getAccountName() };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, FollowCountApiObject.class).get(0);
    }

    /**
     * TODO
     * 
     * @param account
     * @param entryId
     * @param limit
     * @return
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<FeedEntry> getFeedEntries(AccountName account, int entryId, short limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_FEED_ENTRIES);
        requestObject.setSteemApi(SteemApis.FOLLOW_API);

        Object[] parameters = { account.getAccountName(), entryId, limit };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, FeedEntry.class);
    }

    /**
     * TODO
     * 
     * @param account
     * @param entryId
     * @param limit
     * @return
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<CommentFeedEntry> getFeed(AccountName account, int entryId, short limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_FEED);
        requestObject.setSteemApi(SteemApis.FOLLOW_API);

        Object[] parameters = { account.getAccountName(), entryId, limit };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, CommentFeedEntry.class);
    }

    /**
     * 
     * @param account
     * @param entryId
     * @param limit
     * @return
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<BlogEntry> getBlogEntries(AccountName account, int entryId, short limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_BLOG_ENTRIES);
        requestObject.setSteemApi(SteemApis.FOLLOW_API);

        Object[] parameters = { account.getAccountName(), entryId, limit };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, BlogEntry.class);

    }

    /**
     * 
     * @param account
     * @param entryId
     * @param limit
     * @return
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<CommentBlogEntry> getBlog(AccountName account, int entryId, short limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_BLOG);
        requestObject.setSteemApi(SteemApis.FOLLOW_API);

        Object[] parameters = { account.getAccountName(), entryId, limit };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, CommentBlogEntry.class);
    }

    /**
     * 
     * @param lowerBoundName
     * @param limit
     * @return
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<AccountReputation> getAccountReputations(AccountName lowerBoundName, int limit)
            throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_REPUTATIONS);
        requestObject.setSteemApi(SteemApis.FOLLOW_API);

        Object[] parameters = { lowerBoundName.getAccountName(), limit };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, AccountReputation.class);
    }

    /**
     * Gets list of accounts that have reblogged a particular post.
     * 
     * @param author
     * @param permlink
     * @return
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<AccountName> getRebloggedBy(AccountName author, String permlink) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_REBLOGGED_BY);
        requestObject.setSteemApi(SteemApis.FOLLOW_API);

        Object[] parameters = { author.getAccountName(), permlink };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, AccountName.class);
    }

    /**
     * 
     * Gets a list of authors that have had their content reblogged on a given
     * blog account.
     * 
     * @param blogAccount
     * @return
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setTimeout(long)
     *             setTimeout})</li>
     *             <li>If there is a connection problem.</li>
     *             <li>If the API Wrapper is unable to transform the JSON
     *             response into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<PostsPerAuthorPair> getBlogAuthors(AccountName blogAccount) throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.GET_BLOG_AUTHORS);
        requestObject.setSteemApi(SteemApis.FOLLOW_API);

        Object[] parameters = { blogAccount.getAccountName() };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, PostsPerAuthorPair.class);
    }
    
    public void setSubscriptionCallback() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.SET_SUBSCRIBE_CALLBACK);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        
        Object[] parameters = { 4, true };
        requestObject.setAdditionalParameters(parameters);

       System.out.println(communicationHandler.performRequest(requestObject, String.class).get(0));
    }
    
    public void setBlockAppliedCallback() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.SET_BLOCK_APPLIED_CALLBACK);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        
        Object[] parameters = { 4 };
        requestObject.setAdditionalParameters(parameters);

       System.out.println(communicationHandler.performRequest(requestObject, String.class).get(0));
    }
    
    public void setPendingTransactionCallback() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.SET_PENDING_TRANSACTION_CALLBACK);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        
        Object[] parameters = { 4 };
        requestObject.setAdditionalParameters(parameters);

       System.out.println(communicationHandler.performRequest(requestObject, String.class).get(0));
    }
    
    public void cancelAllSubscriptions() throws SteemCommunicationException {
        RequestWrapperDTO requestObject = new RequestWrapperDTO();
        requestObject.setApiMethod(RequestMethods.CANCEL_ALL_SUBSCRIPTIONS);
        requestObject.setSteemApi(SteemApis.DATABASE_API);
        
        Object[] parameters = { 4, true };
        requestObject.setAdditionalParameters(parameters);

       System.out.println(communicationHandler.performRequest(requestObject, String.class).get(0));
    }
}
