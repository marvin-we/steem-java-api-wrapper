package eu.bittrade.libs.steemj.exceptions;

/**
 * Generic Exception Type to handle connection problems with the Steem Node.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemCommunicationException extends Exception {
    private static final long serialVersionUID = -3389735550453652555L;

    public SteemCommunicationException() {
        super();
    }

    public SteemCommunicationException(String message) {
        super(message);
    }

    public SteemCommunicationException(Throwable cause) {
        super("Sorry, an error occurred while processing your request.", cause);
    }

    public SteemCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
