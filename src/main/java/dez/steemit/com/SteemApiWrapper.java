package dez.steemit.com;

import dez.steemit.com.communication.CommunicationHandler;
import dez.steemit.com.communication.RequestMethods;
import dez.steemit.com.communication.RequestObject;
import dez.steemit.com.configuration.SteemApiWrapperConfig;
import dez.steemit.com.exceptions.SteemConnectionException;
import dez.steemit.com.exceptions.SteemTimeoutException;
import dez.steemit.com.exceptions.SteemTransformationException;
import dez.steemit.com.models.AccountCount;
import dez.steemit.com.models.AccountHistory;
import dez.steemit.com.models.AccountVotes;
import dez.steemit.com.models.WitnessCount;
import dez.steemit.com.models.votes.Vote;

/**
 * This class is a wrapper for the Steem web socket API.
 * 
 * @author http://steemit.com/@dez1337
 */
public class SteemApiWrapper {	
	private CommunicationHandler communicationHandler;
	
	public SteemApiWrapper(SteemApiWrapperConfig apiWrapperConfig) throws SteemConnectionException {
		this.communicationHandler = new CommunicationHandler(apiWrapperConfig);
	}
	
	public int getAccountCount() throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		RequestObject requestObject = new RequestObject();
		requestObject.setMethod(RequestMethods.GET_ACCOUNT_COUNT);
		String[] parameters = {};
		requestObject.setParams(parameters);
		
		return communicationHandler.performRequest(requestObject, AccountCount.class).getCount();
	}
	
	public AccountHistory getAccountHistory(String accountName, int from, int limit) throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		throw new RuntimeException("Not Implemented.");
	}
	
	public Vote[] getAccountVotes(String username) throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		RequestObject requestObject = new RequestObject();
		requestObject.setMethod(RequestMethods.GET_ACCOUNT_VOTES);
		String[] parameters = {username};
		requestObject.setParams(parameters);
		
		return communicationHandler.performRequest(requestObject, AccountVotes.class).getVotes();
	}
	
	public int getWitnessCount() throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		RequestObject requestObject = new RequestObject();
		requestObject.setMethod(RequestMethods.GET_WITNESS_COUNT);
		String[] parameters = {};
		requestObject.setParams(parameters);

		return communicationHandler.performRequest(requestObject, WitnessCount.class).getCount();
	}
	
	public void getNodeInfo() throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		throw new RuntimeException("Not Implemented.");
	}
}
