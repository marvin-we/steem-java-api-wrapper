package dez.steemit.com.exceptions;

import dez.steemit.com.models.error.SteemError;

/**
 * A custom Exception to handle timeouts.
 * 
 * @author http://steemit.com/@dez1337
 */
public class SteemResponseError extends Exception {
	private static final long serialVersionUID = 147694337695115012L;
	
	private SteemError steemError;

	public SteemResponseError(SteemError steemError) {
		super();
		this.steemError = steemError;
	}
	
	public SteemResponseError(String message, SteemError steemError) {
		super(message);
		this.steemError = steemError;
	}
	
	public SteemError getError() {
		return steemError;
	}
}
