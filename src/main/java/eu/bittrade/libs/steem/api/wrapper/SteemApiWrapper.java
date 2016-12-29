package eu.bittrade.libs.steem.api.wrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;

import eu.bittrade.libs.steem.api.wrapper.communication.CommunicationHandler;
import eu.bittrade.libs.steem.api.wrapper.communication.DiscussionSortType;
import eu.bittrade.libs.steem.api.wrapper.communication.RequestMethods;
import eu.bittrade.libs.steem.api.wrapper.communication.SteemApis;
import eu.bittrade.libs.steem.api.wrapper.communication.dto.GetDiscussionParametersDTO;
import eu.bittrade.libs.steem.api.wrapper.communication.dto.RequestWrapper;
import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemConnectionException;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemResponseError;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemTimeoutException;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemTransformationException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountActivity;
import eu.bittrade.libs.steem.api.wrapper.models.ActiveVote;
import eu.bittrade.libs.steem.api.wrapper.models.ChainProperties;
import eu.bittrade.libs.steem.api.wrapper.models.Config;
import eu.bittrade.libs.steem.api.wrapper.models.Discussion;
import eu.bittrade.libs.steem.api.wrapper.models.GlobalProperties;
import eu.bittrade.libs.steem.api.wrapper.models.MedianHistoryPrice;
import eu.bittrade.libs.steem.api.wrapper.models.TrendingTag;
import eu.bittrade.libs.steem.api.wrapper.models.Version;
import eu.bittrade.libs.steem.api.wrapper.models.Vote;
import eu.bittrade.libs.steem.api.wrapper.models.WitnessSchedule;

/**
 * This class is a wrapper for the Steem web socket API.
 * 
 * @author http://steemit.com/@dez1337
 */
public class SteemApiWrapper {
	private static final Logger LOGGER = LogManager.getLogger(SteemApiWrapper.class);

	private CommunicationHandler communicationHandler;
	private SteemApiWrapperConfig steemApiWrapperConfig;

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
		this.steemApiWrapperConfig = steemApiWrapperConfig;

		if (!("").equals(String.valueOf(steemApiWrapperConfig.getPassword()))
				&& !("").equals(steemApiWrapperConfig.getUsername())) {
			LOGGER.info("Calling the login method with the prodvided credentials before checking the available apis.");
			if (login(steemApiWrapperConfig.getUsername(), String.valueOf(steemApiWrapperConfig.getPassword()))) {
				LOGGER.info("You have been logged in.");
			} else {
				LOGGER.error("Login failed. The following check of available apis will be done as a anonymous user.");
			}
		} else {
			LOGGER.info(
					"No credentials have been provided. The following check of available apis will be done as a anonymous user.");
		}

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
	 * @return The number of accounts.
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
	 * @return A map containing the activities. The key is the id of the
	 *         activity.
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
	 * Get a list of all votes done by a specific account.
	 * 
	 * @param accountName
	 *            The user name of the account.
	 * @return A List of votes done by the specified account.
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
	 * @return The number of witnesses.
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
	 * @return A list of account names that are in the mining queue.
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
	 * @return The steem configuration.
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
	 * Get the version information of the connected node.
	 * 
	 * @return The steem version that the connected node is running.
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
	 * Login under the use of the credentials which are stored in the config
	 * object.
	 * 
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
	public Boolean login()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		return login(steemApiWrapperConfig.getUsername(), String.valueOf(steemApiWrapperConfig.getPassword()));
	}

	/**
	 * Login under the use of the specified credentials.
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
		/*
		 * TODO this method always returns true? Created the following GitHub
		 * issue for this: https://github.com/steemit/steem/issues/739
		 */

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
	 * @return The id for the given api name or null, if the api is not active
	 *         or does not exist.
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
	 * @return A list of tags.
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
	 * @return The hardfork version that the connected node is running on.
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
	 * Get the witness schedule.
	 * 
	 * @return The witness schedule.
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
	public WitnessSchedule getWitnessSchedule()
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_WITNESS_SCHEDULE);
		requestObject.setSteemApi(SteemApis.DATABASE_API);
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
	 * @return The dynamic global properties.
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
	 * @return The chain properties.
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
	 * @return The current median history price.
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
	 * @return The details of a specific post.
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
	 * @return A list of votes for a specific article.
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
	 * @param sortBy
	 *            The way how the results should be sorted by.
	 * @return A list of discussions.
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
	public List<Discussion> getDiscussionsBy(String tag, int limit, DiscussionSortType sortBy)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		
		switch (sortBy) {
		case SORT_BY_ACTIVE:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_ACTIVE);
			break;
		case SORT_BY_BLOG:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_BLOG);
			break;
		case SORT_BY_CASHOUT:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_CASHOUT);
			break;
		case SORT_BY_CHILDREN:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_CHILDREN);
			break;
		case SORT_BY_COMMENTS:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_COMMENTS);
			break;
		case SORT_BY_CREATED:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_CREATED);
			break;
		case SORT_BY_FEED:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_FEED);
			break;
		case SORT_BY_HOT:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_HOT);
			break;
		case SORT_BY_PAYOUT:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_PAYOUT);
			break;
		case SORT_BY_PROMOTED:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_PROMOTED);
			break;
		case SORT_BY_TRENDING:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_TRENDING);
			break;
		case SORT_BY_TRENDING_30_DAYS:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_TRENDING30);
			break;
		case SORT_BY_VOTES:
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_VOTES);
			break;
		default:
			LOGGER.warn("Unkown sort type. The resulting discussions are now sorted by the values of the 'active' field (SORT_BY_ACTIVE).");
			requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_ACTIVE);
			break;
		}
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

	// TODO implement this!
	public Boolean broadcastTransaction(String trx)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException, SteemResponseError {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.BROADCAST_TRANSACTION);
		requestObject.setSteemApi(SteemApis.NETWORK_BROADCAST_API);
		String[] parameters = { trx };
		requestObject.setAdditionalParameters(parameters);

		communicationHandler.performRequest(requestObject, Object[].class);
		return null;
	}
}
