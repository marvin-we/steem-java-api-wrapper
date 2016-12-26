package dez.steemit.com;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

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

public class SteemApiWrapper {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private CommunicationHandler communicationHandler;
	
	public SteemApiWrapper(SteemApiWrapperConfig apiWrapperConfig) throws SteemConnectionException {
		this.communicationHandler = new CommunicationHandler(apiWrapperConfig);

		MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
	}
	
	public AccountCount getAccountCount() throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		RequestObject requestObject = new RequestObject();
		requestObject.setMethod(RequestMethods.GET_ACCOUNT_COUNT);
		String[] parameters = {};
		requestObject.setParams(parameters);
		try {
			return MAPPER.readValue(communicationHandler.performRequest(requestObject), AccountCount.class);
		} catch (IOException e) {
			throw new SteemTransformationException("Could not transform the response into an object.", e);
		}
	}
	
	public AccountHistory getAccountHistory(String accountName, int from, int limit) throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		throw new RuntimeException("Not Implemented.");
	}
	
	public AccountVotes getAccountVotes(String username) throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		RequestObject requestObject = new RequestObject();
		requestObject.setMethod(RequestMethods.GET_ACCOUNT_VOTES);
		String[] parameters = {username};
		requestObject.setParams(parameters);
		try {
			return MAPPER.readValue(communicationHandler.performRequest(requestObject), AccountVotes.class);
		} catch (IOException e) {
			throw new SteemTransformationException("Could not transform the response into an object.", e);
		}
	}
	
	public void getNodeInfo() throws SteemTimeoutException, SteemConnectionException, SteemTransformationException {
		throw new RuntimeException("Not Implemented.");
	}
	
	public static void main(String args[]) {
		
		try {
			SteemApiWrapper asdf = new SteemApiWrapper(new SteemApiWrapperConfig());
			System.out.println(asdf.getAccountCount().toString());
			//System.out.println(asdf.getAccountHistory("lantto",20,10).toString());
			System.out.println(asdf.getAccountVotes("dez1337").toString());
		} catch (SteemTimeoutException | SteemConnectionException | SteemTransformationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
