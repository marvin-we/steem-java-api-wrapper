package eu.bittrade.libs.steemj.communication.jrpc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import eu.bittrade.libs.steemj.base.models.SignedBlockHeader;
import eu.bittrade.libs.steemj.communication.BlockAppliedCallback;
import eu.bittrade.libs.steemj.communication.CallbackHub;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class contains a Json RPC response and offers additional utility
 * methods.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class JsonRPCResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonRPCResponse.class);
    /** The field name of the JSON RPC "error" field. */
    public static final String ERROR_FIELD_NAME = "error";
    /** The field name of the JSON RPC "code" field. */
    public static final String ERROR_CODE_FIELD_NAME = "code";
    /** The field name of the JSON RPC "message" field. */
    public static final String ERROR_MESSAGE_FIELD_NAME = "message";
    /** The field name of the JSON RPC "data" field. */
    public static final String ERROR_DATA_FIELD_NAME = "data";
    /** The field name of the JSON RPC "method" field. */
    public static final String METHOD_FIELD_NAME = "method";
    /** The field name of the JSON RPC "method" field. */
    public static final String PARAMETERS_FIELD_NAME = "params";
    /** The field name of the JSON RPC "id" field. */
    public static final String ID_FIELD_NAME = "id";
    /** The field name of the JSON RPC "result" field. */
    public static final String RESULT_FIELD_NAME = "result";

    /** The method name indicating a callback. */
    public static final String CALLBACK_METHOD_NAME = "notice";

    /** The raw JSON String returned by a node. */
    private JsonNode rawJsonResponse;

    /**
     * Create a new {@link JsonRPCResponse} instance.
     * 
     * @param rawJsonResponse
     *            The raw JSON response that should be wrapped by this
     *            {@link JsonRPCResponse} instance.
     */
    public JsonRPCResponse(JsonNode rawJsonResponse) {
        this.rawJsonResponse = rawJsonResponse;
    }

    /**
     * Get the raw JSON response that is wrapped by this {@link JsonRPCResponse}
     * instance.
     * 
     * @return The raw JSON response that is wrapped by this
     *         {@link JsonRPCResponse} instance.
     */
    public JsonNode getRawJsonResponse() {
        return rawJsonResponse;
    }

    // #########################################################################
    // ## HANDLE RESULTS #######################################################
    // #########################################################################

    /**
     * Check if the given <code>response</code> has the expected <code> id.
     * 
     * &#64;param id The request id to compare with.
     * &#64;param response The JSON returned from the node.
     * @return <code>true</code> if the <code>response</code> contains the
     *         <code>id</code> or <code>false</code> if not.
     */
    private boolean hasExpectedId(long id, ObjectNode response) {
        return response.has(ID_FIELD_NAME) && response.get(ID_FIELD_NAME) != null
                && response.get(ID_FIELD_NAME).asLong() == id;
    }

    /**
     * Check if the JSON response wrapped by this {@link JsonRPCResponse}
     * instance contains a result field.
     * 
     * @return <code>true</code> if this is the case or <code>false</code> if
     *         not.
     */
    private boolean isResult() {
        return rawJsonResponse.has(RESULT_FIELD_NAME);
    }

    /**
     * Check if the JSON response wrapped by this {@link JsonRPCResponse}
     * instance is empty.
     * 
     * @return <code>true</code> if this is the case or <code>false</code> if
     *         not.
     */
    private boolean isResultEmpty() {
        ObjectNode responseAsObject = ObjectNode.class.cast(rawJsonResponse);

        if (isFieldNullOrEmpty(RESULT_FIELD_NAME, responseAsObject))
            return true;

        LOGGER.debug("The response is empty.");
        return false;
    }

    /**
     * Check if the JSON response wrapped by this {@link JsonRPCResponse}
     * instance has the expected JSON structure.
     * 
     * @return <code>true</code> if this is the case or <code>false</code> if
     *         not.
     */
    private boolean isResponseValid() {
        if (rawJsonResponse.isObject())
            return true;

        LOGGER.error("The response is not an object.");
        return false;
    }

    /**
     * This method checks if the JSON response wrapped by this
     * {@link JsonRPCResponse} instance has the expected <code>id</code> and
     * will try to transform the JSON into the given <code>type</code>.
     * 
     * @param type
     *            The type to transform the JSON to.
     * @param id
     *            The expected id of the response.
     * @return A list of of <code>type</code> instances.
     * @throws SteemCommunicationException
     *             If the response does not contain the expected <code>id</code>
     *             or if the response could not be transformed into the expected
     *             <code>type</code>.
     */
    public <T> List<T> handleResult(JavaType type, long id) throws SteemCommunicationException {
        if (isResponseValid()) {
            if (!isResult()) {
                throw new SteemCommunicationException(
                        "The result does not contain the required " + RESULT_FIELD_NAME + " field.");
            } else {
                ObjectNode responseAsObject = ObjectNode.class.cast(rawJsonResponse);

                if (!hasExpectedId(id, responseAsObject)) {
                    throw new SteemCommunicationException(
                            "The id of this response does not match the expected id. This can cause an unexpected behavior.");
                }

                if (!isResultEmpty())
                    return CommunicationHandler.getObjectMapper().convertValue(responseAsObject.get(RESULT_FIELD_NAME),
                            type);
            }
        }

        return new ArrayList<>();
    }

    // #########################################################################
    // ## HANDLE CALLBACKS #####################################################
    // #########################################################################

    /**
     * Check if the {@link #getRawJsonResponse()} contains the expected fields
     * of a callback response.
     * 
     * @return <code>true</code> if the response contains the expected fields,
     *         <code>false</code> otherwise.
     */
    public boolean isCallback() {
        return rawJsonResponse.has(METHOD_FIELD_NAME) && rawJsonResponse.get(METHOD_FIELD_NAME) != null
                && !rawJsonResponse.get(METHOD_FIELD_NAME).isNull()
                && rawJsonResponse.get(METHOD_FIELD_NAME).asText().equals(CALLBACK_METHOD_NAME);
    }

    /**
     * This method will try to find the matching callback class and call it.
     * 
     * @throws SteemCommunicationException
     *             If the response is no callback or if no matching callback
     *             instance could be found.
     */
    public void handleCallback() throws SteemCommunicationException {
        if (isResponseValid()) {
            if (!isCallback()) {
                throw new SteemCommunicationException("The result is not a callback.");
            }

            ObjectNode responseAsObject = ObjectNode.class.cast(rawJsonResponse);

            if (!isFieldNullOrEmpty(PARAMETERS_FIELD_NAME, responseAsObject)) {
                JsonNode parametersNode = responseAsObject.get(PARAMETERS_FIELD_NAME);

                if (parametersNode.isArray()) {
                    ArrayNode parameters = ArrayNode.class.cast(parametersNode);

                    BlockAppliedCallback callbackInstance = CallbackHub.getInstance()
                            .getCallbackByUuid(Integer.valueOf(parameters.get(0).toString()));

                    if (callbackInstance != null && parameters.get(1).isArray()) {
                        ArrayNode payload = ArrayNode.class.cast(parameters.get(1));

                        callbackInstance.onNewBlock(CommunicationHandler.getObjectMapper().convertValue(payload.get(0),
                                SignedBlockHeader.class));
                        return;
                    }
                }
                // The parameter is not an array as expected - Do nothing so
                // that the exception at the bottom of the method is thrown.
            } else {
                LOGGER.info("Detected a callback but its payload was empty.");
            }
        }

        throw new SteemCommunicationException("Tried to handle a callback based on an unexpected Json structure.");
    }

    // #########################################################################
    // ## HANDLE ERRORS ########################################################
    // #########################################################################

    /**
     * Check if the {@link #getRawJsonResponse()} contains an error.
     * 
     * @return <code>true</code> if the response contains an error,
     *         <code>false</code> otherwise.
     */
    public boolean isError() {
        return rawJsonResponse.has(ERROR_FIELD_NAME) && rawJsonResponse.get(ERROR_FIELD_NAME) != null
                && !rawJsonResponse.get(ERROR_FIELD_NAME).isNull();
    }

    /**
     * This method checks if the JSON response wrapped by this
     * {@link JsonRPCResponse} instance has the expected <code>id</code> and
     * will try to generate and throw a {@link SteemResponseException} based on
     * the response.
     * 
     * @param id
     *            The expected id of the response.
     * @return A {@link SteemResponseException} based on the Json response.
     * @throws SteemCommunicationException
     *             If the response does not contain the expected <code>id</code>
     *             or the response do not contain an error.
     * 
     */
    public SteemResponseException handleError(long id) throws SteemCommunicationException {
        if (isResponseValid()) {
            if (!isError()) {
                throw new SteemCommunicationException(
                        "The result does not contain the required " + ERROR_FIELD_NAME + " field.");
            } else {
                ObjectNode responseAsObject = ObjectNode.class.cast(rawJsonResponse);

                if (!hasExpectedId(id, responseAsObject)) {
                    throw new SteemCommunicationException(
                            "The id of this response does not match the expected id. This can cause an unexpected behavior.");
                }

                return createThrowable(responseAsObject);
            }
        }

        throw new SteemCommunicationException("Tried to generate a throwable out of a unexpected Json structure.");
    }

    /**
     * Create a new {@link SteemResponseException} based on the Json response.
     * 
     * @param response
     *            The response object to transform.
     * @return A {@link SteemResponseException} based on the Json response.
     * @throws SteemCommunicationException
     *             If the response does not contain the expected
     *             <code>id</code>.
     */
    private SteemResponseException createThrowable(ObjectNode response) throws SteemCommunicationException {
        JsonNode errorNode = response.get(ERROR_FIELD_NAME);

        if (!errorNode.isObject()) {
            throw new SteemCommunicationException("The response does not have the expected structure.");
        }

        ObjectNode errorObject = ObjectNode.class.cast(errorNode);

        Integer errorCode = isFieldNullOrEmpty(ERROR_CODE_FIELD_NAME, errorObject) ? null
                : errorObject.get(ERROR_CODE_FIELD_NAME).asInt();
        String message = isFieldNullOrEmpty(ERROR_MESSAGE_FIELD_NAME, errorObject) ? null
                : errorObject.get(ERROR_MESSAGE_FIELD_NAME).asText();
        JsonNode data = isFieldNullOrEmpty(ERROR_DATA_FIELD_NAME, errorObject) ? null
                : errorObject.get(ERROR_DATA_FIELD_NAME);

        return new SteemResponseException(errorCode, message, data);
    }

    // #########################################################################
    // ## UTILITY METHODS ######################################################
    // #########################################################################

    /**
     * Check if the given <code>fieldName</code> has an empty or null value.
     * 
     * @param fieldName
     *            The field name to check.
     * @param response
     *            The response to check.
     * @return <code>true</code> if the <code>fieldName</code> has an empty or
     *         null value.
     */
    public boolean isFieldNullOrEmpty(String fieldName, ObjectNode response) {
        return (response.get(fieldName) == null || response.get(fieldName).isNull());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
