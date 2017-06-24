package eu.bittrade.libs.steemj.exceptions;

/**
 * A custom Exception to handle unexpected and urgent problems.
 * 
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemFatalErrorException extends RuntimeException {
    private static final long serialVersionUID = -1387551120252237903L;

    public SteemFatalErrorException(String message) {
        super(message);
    }

    public SteemFatalErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
