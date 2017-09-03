package eu.bittrade.libs.steemj.exceptions;

/**
 * A custom Exception to handle problems while working with keys.
 * 
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemKeyHandlingException extends Exception {
    private static final long serialVersionUID = 6567388066484382881L;

    public SteemKeyHandlingException(String message) {
        super(message);
    }

    public SteemKeyHandlingException(String message, Throwable cause) {
        super(message, cause);
    }
}
