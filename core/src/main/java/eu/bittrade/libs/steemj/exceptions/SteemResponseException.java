package eu.bittrade.libs.steemj.exceptions;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * A custom Exception to handle timeouts.
 * 
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemResponseException extends Exception {
    /** Generated serial version uid. */
    private static final long serialVersionUID = 147694337695115012L;
    /** The error code. */
    private final Integer code;
    /** The error data. */
    private final JsonNode data;

    /**
     * Create a new {@link SteemResponseException} instance.
     * 
     * @param message
     *            The error message to set.
     */
    public SteemResponseException(String message) {
        super(message);

        this.code = null;
        this.data = null;
    }

    /**
     * Create a new {@link SteemResponseException} instance.
     * 
     * @param message
     *            The error message to set.
     * @param cause
     *            The cause of this response exception.
     */
    public SteemResponseException(String message, Throwable cause) {
        super(message, cause);

        this.code = null;
        this.data = null;
    }

    /**
     * Create a new {@link SteemResponseException} instance.
     * 
     * @param code
     *            The error code to set.
     * @param message
     *            The error message to set.
     * @param data
     *            The additional data to set.
     */
    public SteemResponseException(Integer code, String message, JsonNode data) {
        super(message);

        this.code = code;
        this.data = data;
    }

    /**
     * Create a new {@link SteemResponseException} instance.
     * 
     * @param code
     *            The error code to set.
     * @param message
     *            The error message to set.
     * @param data
     *            The additional data to set.
     * @param cause
     *            The cause of this response exception.
     */
    public SteemResponseException(Integer code, String message, JsonNode data, Throwable cause) {
        super(message, cause);

        this.code = code;
        this.data = data;
    }

    /**
     * Get the error code.
     * 
     * @return The code if its available, <code>null</code> otherwise.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * Get the data attached to the response error.
     * 
     * @return The data.
     */
    public JsonNode getData() {
        return data;
    }
}
