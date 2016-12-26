package dez.steemit.com.exceptions;

public class SteemConnectionException extends Exception{
	private static final long serialVersionUID = 8749084658239933634L;
	
	public SteemConnectionException(String message) {
		super(message);
	}
	
	public SteemConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
