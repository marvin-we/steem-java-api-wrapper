package eu.bittrade.libs.steemj.exceptions;

/**
 * A custom Exception to handle timeouts.
 * 
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemResponseError extends Exception {
    private static final long serialVersionUID = 147694337695115012L;

    public SteemResponseError(String message) {
        super(message);
    }

    public SteemResponseError(String message, Throwable cause) {
        super(message, cause);
    }
}
