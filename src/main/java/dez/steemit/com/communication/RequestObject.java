package dez.steemit.com.communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestObject {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Logger LOGGER = LogManager.getLogger(RequestObject.class);
	
	/** The id of the request (used to identify which answer belongs to which request */
	@JsonIgnore
	private static int overallRequestId = 0;
	
	private final String jsonrpc = "2.0";
	private String[] params;
	private int id = 0;
	private RequestMethods method;

	public RequestObject() {
		this.id = overallRequestId++;
	}
	
	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public String getMethod() {
		return method.toString().toLowerCase();
	}

	public void setMethod(RequestMethods method) {
		this.method = method;
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public int getId() {
		return id;
	}
	
	public static int incrementOverallRequestId() {
		return ++overallRequestId;
	}

	@Override
	public String toString() {
		try {
			return MAPPER.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			LOGGER.error("Could not transform object to JSON.", e);
			return "";
		}
	}
}
