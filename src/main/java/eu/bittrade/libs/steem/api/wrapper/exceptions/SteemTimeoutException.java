package eu.bittrade.libs.steem.api.wrapper.exceptions;

/**
 * A custom Exception to handle timeouts.
 * 
 * @author http://steemit.com/@dez1337
 */
public class SteemTimeoutException extends Exception {
    private static final long serialVersionUID = 147694337695115012L;

    public SteemTimeoutException(String message) {
        super(message);
    }

    public SteemTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
