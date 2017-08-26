package eu.bittrade.libs.steemj.exceptions;

/**
 * A custom Exception to handle connection problems.
 * 
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemConnectionException extends SteemCommunicationException {
    private static final long serialVersionUID = 8749084658239933634L;

    public SteemConnectionException(String message) {
        super(message);
    }

    public SteemConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
