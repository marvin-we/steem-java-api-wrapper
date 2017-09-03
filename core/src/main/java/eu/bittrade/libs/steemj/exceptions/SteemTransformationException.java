package eu.bittrade.libs.steemj.exceptions;

/**
 * A custom Exception to handle transformation-Exceptions.
 * 
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemTransformationException extends SteemCommunicationException {
    private static final long serialVersionUID = -1405676306096463952L;

    public SteemTransformationException(String message) {
        super(message);
    }

    public SteemTransformationException(String message, Throwable cause) {
        super(message, cause);
    }
}
