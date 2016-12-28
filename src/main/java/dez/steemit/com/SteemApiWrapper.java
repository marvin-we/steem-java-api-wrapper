package dez.steemit.com;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;

import dez.steemit.com.communication.CommunicationHandler;
import dez.steemit.com.communication.RequestMethods;
import dez.steemit.com.communication.SteemApis;
import dez.steemit.com.communication.dto.GetDiscussionParametersDTO;
import dez.steemit.com.communication.dto.RequestWrapper;
import dez.steemit.com.configuration.SteemApiWrapperConfig;
import dez.steemit.com.exceptions.SteemConnectionException;
import dez.steemit.com.exceptions.SteemResponseError;
import dez.steemit.com.exceptions.SteemTimeoutException;
import dez.steemit.com.exceptions.SteemTransformationException;
import dez.steemit.com.models.AccountActivity;
import dez.steemit.com.models.ActiveVote;
import dez.steemit.com.models.ChainProperties;
import dez.steemit.com.models.Config;
import dez.steemit.com.models.Discussion;
import dez.steemit.com.models.GlobalProperties;
import dez.steemit.com.models.MedianHistoryPrice;
import dez.steemit.com.models.NodeInfo;
import dez.steemit.com.models.TrendingTag;
import dez.steemit.com.models.Version;
import dez.steemit.com.models.Vote;

/**
 * This class is a wrapper for the Steem web socket API.
 * 
 * @author http://steemit.com/@dez1337
 */
public class SteemApiWrapper {
	private static final Logger LOGGER = LogManager.getLogger(SteemApiWrapper.class);

	private CommunicationHandler communicationHandler;

	/**
	 * Initialize the Steem API Wrapper.
	 * 
	 * @param steemApiWrapperConfig
	 *            A SteemApiWrapperConfig object that contains the required
	 *            configuration.
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public SteemApiWrapper(SteemApiWrapperConfig steemApiWrapperConfig)
			throws SteemConnectionException, SteemTimeoutException, SteemTransformationException, SteemResponseError {
		this.communicationHandler = new CommunicationHandler(steemApiWrapperConfig);

		// Check all known apis
		for (SteemApis steemApi : SteemApis.values()) {
			if (getApiByName(steemApi.toString().toLowerCase()) == null) {
				LOGGER.warn("The {} is not published by the configured node.", steemApi.toString());
			}
		}
	}

	/**
	 * Get the current number of registered Steem accounts.
	 * 
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public int getAccountCount()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
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
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public Map<Integer, AccountActivity> getAccountHistory(String accountName, int from, int limit)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_HISTORY);
		String[] parameters = { accountName, String.valueOf(from), String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		Map<Integer, AccountActivity> accountActivities = new HashMap<>();

		// TODO There are still problems with the deserialization of the op()
		// object.
		for (Object[] accountActivity : communicationHandler.performRequest(requestObject, Object[].class)) {
			accountActivities.put((Integer) accountActivity[0], communicationHandler.getObjectMapper()
					.convertValue(accountActivity[1], new TypeReference<AccountActivity>() {
					}));
		}

		return accountActivities;
	}

	/**
	 * Get an array of all votes done by a specific account.
	 * 
	 * @param accountName
	 *            The user name of the account.
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public List<Vote> getAccountVotes(String accountName)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_VOTES);
		String[] parameters = { accountName };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Vote.class);
	}

	/**
	 * Get the current number of active witnesses.
	 * 
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public int getWitnessCount()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_WITNESS_COUNT);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Integer.class).get(0);
	}

	/**
	 * Get the current miner queue.
	 * 
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public String[] getMinerQueue()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_MINER_QUEUE);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, String[].class).get(0);
	}

	/**
	 * Get the configuration.
	 * 
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public Config getConfig()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_CONFIG);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Config.class).get(0);
	}

	/**
	 * Get general network information, such as p2p port.
	 * 
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public NodeInfo getNodeInfo()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		// TODO: Implement this!
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_INFO);
		requestObject.setSteemApi(SteemApis.NETWORK_NODE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, NodeInfo.class).get(0);
	}

	/**
	 * Get the version information of the connected node.
	 * 
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public Version getVersion()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_VERSION);
		requestObject.setSteemApi(SteemApis.LOGIN_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Version.class).get(0);
	}

	/**
	 * Login.
	 * 
	 * @param username
	 *            The user name.
	 * @param password
	 *            The password.
	 * @return true if the login was successful. False otherwise.
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public Boolean login(String username, String password)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		// TODO this method always returns true? :o
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.LOGIN);
		requestObject.setSteemApi(SteemApis.LOGIN_API);
		String[] parameters = { username, password };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Boolean.class).get(0);
	}

	/**
	 * Returns the id of an api or null if no api with the given name could be
	 * found.
	 * 
	 * @param apiName
	 *            The name of the api.
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public String getApiByName(String apiName)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_API_BY_NAME);
		requestObject.setSteemApi(SteemApis.LOGIN_API);
		String[] parameters = { apiName };
		requestObject.setAdditionalParameters(parameters);

		List<String> response = communicationHandler.performRequest(requestObject, String.class);
		if (response != null && response.get(0) != null) {
			return response.get(0);
		}

		return null;
	}

	/**
	 * Returns detailed values for tags that match the given conditions.
	 * 
	 * @param firstTag
	 *            Start the list after this category. An empty String will
	 *            result in starting from the top.
	 * @param limit
	 *            The number of results.
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public List<TrendingTag> getTrendingTags(String firstTag, int limit)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_TRENDING_TAGS);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = { firstTag, String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, TrendingTag.class);
	}

	/**
	 * Get the hardfork version.
	 * 
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public String getHardforkVersion()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_HARDFORK_VERSION);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, String.class).get(0);
	}

	/**
	 * Search for accounts.
	 * 
	 * @param pattern
	 *            The lower case pattern you want to search for.
	 * @param limit
	 *            The maximum number of account names.
	 * @return A list of matching account names.
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public List<String> lookupAccounts(String pattern, int limit)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
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
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public List<String> lookupWitnessAccounts(String pattern, int limit)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.LOOKUP_WITNESS_ACCOUNTS);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = { pattern, String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, String.class);
	}

	/**
	 * Get the global properties.
	 * 
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public GlobalProperties getDynamicGlobalProperties()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_DYNAMIC_GLOBAL_PROPERTIES);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, GlobalProperties.class).get(0);
	}

	/**
	 * Get the chain properties.
	 * 
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public ChainProperties getChainProperties()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_CHAIN_PROPERTIES);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, ChainProperties.class).get(0);
	}

	/**
	 * Get the current median price.
	 * 
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public MedianHistoryPrice getCurrentMedianHistoryPrice()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_CURRENT_MEDIAN_HISTORY_PRICE);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = {};
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, MedianHistoryPrice.class).get(0);
	}

	/**
	 * Get the details of a specific post.
	 * 
	 * @param author
	 *            The authors name.
	 * @param permlink
	 *            The permlink of the article.
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public Discussion getContent(String author, String permlink)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
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
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public List<Discussion> getContentReplies(String author, String permlink)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_CONTENT_REPLIES);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = { author, permlink };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, Discussion.class);
	}

	/**
	 * Get the active votes for a given post of a given author.
	 * 
	 * @param author
	 *            The authors name.
	 * @param permlink
	 *            The permlink of the article.
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public List<ActiveVote> getActiveVotes(String author, String permlink)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_ACTIVE_VOTES);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
		String[] parameters = { author, permlink };
		requestObject.setAdditionalParameters(parameters);

		return communicationHandler.performRequest(requestObject, ActiveVote.class);
	}

	/**
	 * Get active discussions for a specified tag.
	 * 
	 * @param tag
	 *            Get discussions that are tagged with this tag.
	 * @param limit
	 *            The number of results.
	 * @return
	 * @throws SteemTimeoutException
	 *             If the server was not able to answer the request in the given
	 *             time (@see SteemApiWrapperConfig)
	 * @throws SteemConnectionException
	 *             If there is a connection problem.
	 * @throws SteemTransformationException
	 *             If the API Wrapper is unable to transform the JSON response
	 *             into a Java object.
	 * @throws SteemResponseError
	 *             If the Server returned an error object.
	 */
	public List<Discussion> getDiscussionsByActive(String tag, int limit)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_ACTIVE);
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

	// TODO implement this!
	public Boolean broadcastTransactionSynchronous(String trx)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.BROADCAST_TRANSACTION_SYNCHRONOUS);
		requestObject.setSteemApi(SteemApis.NETWORK_BROADCAST_API);
		String[] parameters = { trx };
		requestObject.setAdditionalParameters(parameters);

		return null;
	}
}
