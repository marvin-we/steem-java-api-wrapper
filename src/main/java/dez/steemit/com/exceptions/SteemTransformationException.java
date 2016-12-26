package dez.steemit.com.exceptions;

public class SteemTransformationException extends Exception{
	private static final long serialVersionUID = -1405676306096463952L;

	public SteemTransformationException(String message) {
		super(message);
	}
	
	public SteemTransformationException(String message, Throwable cause) {
		super(message, cause);
	}
}
