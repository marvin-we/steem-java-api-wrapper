package eu.bittrade.libs.steemj.exceptions;

/**
 * A custom Exception to handle invalid transactions.
 * 
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemInvalidTransactionException extends Exception {
    private static final long serialVersionUID = -3747123400720358339L;

    public SteemInvalidTransactionException(String message) {
        super(message);
    }

    public SteemInvalidTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
