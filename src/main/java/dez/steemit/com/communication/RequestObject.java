package dez.steemit.com.communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A wrapper object that carries all required fields for a request.
 * 
 * @author http://steemit.com/@dez1337
 */
public class RequestObject {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Logger LOGGER = LogManager.getLogger(RequestObject.class);

	/**
	 * The id of the request (used to identify which answer belongs to which
	 * request
	 */
	@JsonIgnore
	private static int overallRequestId = 0;

	private final String jsonrpc = "2.0";
	private String[] params;
	private int id = 0;
	private RequestMethods method;

	/**
	 * Instantiate a new RequestObject.
	 */
	public RequestObject() {
		this.id = overallRequestId++;
	}

	/**
	 * Get the list of used parameters.
	 * 
	 * @return
	 */
	public String[] getParams() {
		return params;
	}

	/**
	 * Set the parameters for this request.
	 * 
	 * @param method
	 */
	public void setParams(String[] params) {
		this.params = params;
	}

	/**
	 * Get the API-Method (@see RequestMethods).
	 * 
	 * @return
	 */
	public String getMethod() {
		return method.toString().toLowerCase();
	}

	/**
	 * Set the API-Method (@see RequestMethods).
	 * 
	 * @param method
	 */
	public void setMethod(RequestMethods method) {
		this.method = method;
	}

	/**
	 * Get the json-rpc version.
	 * 
	 * @return
	 */
	public String getJsonrpc() {
		return jsonrpc;
	}

	/**
	 * Get the id of this request.
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Increments the global request id.
	 * 
	 * @return
	 */
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
