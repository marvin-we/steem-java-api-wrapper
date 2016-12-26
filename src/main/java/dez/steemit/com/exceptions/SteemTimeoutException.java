package dez.steemit.com.exceptions;

public class SteemTimeoutException extends Exception{
	private static final long serialVersionUID = 147694337695115012L;

	public SteemTimeoutException(String message) {
		super(message);
	}
	
	public SteemTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}
}
