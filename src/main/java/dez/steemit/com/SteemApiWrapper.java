package dez.steemit.com;

import java.util.List;

import dez.steemit.com.communication.CommunicationHandler;
import dez.steemit.com.communication.RequestMethods;
import dez.steemit.com.communication.RequestWrapper;
import dez.steemit.com.communication.SteemApis;
import dez.steemit.com.configuration.SteemApiWrapperConfig;
import dez.steemit.com.exceptions.SteemConnectionException;
import dez.steemit.com.exceptions.SteemResponseError;
import dez.steemit.com.exceptions.SteemTimeoutException;
import dez.steemit.com.exceptions.SteemTransformationException;
import dez.steemit.com.models.AccountHistory;
import dez.steemit.com.models.NodeInfo;
import dez.steemit.com.models.Version;
import dez.steemit.com.models.Vote;

/**
 * This class is a wrapper for the Steem web socket API.
 * 
 * @author http://steemit.com/@dez1337
 */
public class SteemApiWrapper {
	private CommunicationHandler communicationHandler;

	/**
	 * Initialize the Steem API Wrapper.
	 * 
	 * @param steemApiWrapperConfig
	 *            A SteemApiWrapperConfig object that contains the required
	 *            configuration.
	 * @throws SteemConnectionException
	 *             If there are problems due to connecting to the server.
	 */
	public SteemApiWrapper(SteemApiWrapperConfig steemApiWrapperConfig) throws SteemConnectionException {
		this.communicationHandler = new CommunicationHandler(steemApiWrapperConfig);
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
	 */
	public AccountHistory getAccountHistory(String accountName, int from, int limit)
			throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setSteemApi(SteemApis.NETWORK_NODE_API);
		requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_HISTORY);
		String[] parameters = { accountName, String.valueOf(from), String.valueOf(limit) };
		requestObject.setAdditionalParameters(parameters);

		// TODO: Fix it!
		// System.out.println(communicationHandler.performRequest(requestObject,
		// Object.class).get(0).toString());
		return null;
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
	 * Same functionality as @see getVersion . Get the version information of
	 * the connected node.
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
		RequestWrapper requestObject = new RequestWrapper();
		requestObject.setApiMethod(RequestMethods.GET_VERSION);
		requestObject.setSteemApi(SteemApis.LOGIN_API);
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
}
