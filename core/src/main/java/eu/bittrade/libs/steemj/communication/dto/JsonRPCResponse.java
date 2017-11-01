package eu.bittrade.libs.steemj.communication.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class contains a Json RPC response and offers additional utility
 * methods.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class JsonRPCResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonRPCResponse.class);
    /** The field name of the Json RPC "error" field. */
    public static final String ERROR_FIELD_NAME = "error";
    /** The field name of the Json RPC "id" field. */
    public static final String ID_FIELD_NAME = "id";
    /** The field name of the Json RPC "result" field. */
    public static final String RESULT_FIELD_NAME = "result";

    /** */
    private JsonNode rawJsonResponse;

    /**
     * 
     * @param rawJsonResponse
     */
    public JsonRPCResponse(JsonNode rawJsonResponse) {
        this.rawJsonResponse = rawJsonResponse;
    }

    /**
     * @return The raw Json response.
     */
    public JsonNode getRawJsonResponse() {
        return rawJsonResponse;
    }

    public void handleError() {
        /*
         * ObjectNode dataObject =
         * ObjectNode.class.cast(errorObject.get(JsonRpcBasicServer.DATA)); if
         * (!hasNonNullTextualData(dataObject,
         * JsonRpcBasicServer.EXCEPTION_TYPE_NAME)) return
         * createJsonRpcClientException(errorObject);
         * 
         * try { String exceptionTypeName =
         * dataObject.get(JsonRpcBasicServer.EXCEPTION_TYPE_NAME).asText();
         * String message = hasNonNullTextualData(dataObject,
         * JsonRpcBasicServer.ERROR_MESSAGE) ?
         * dataObject.get(JsonRpcBasicServer.ERROR_MESSAGE).asText() : null;
         * return createThrowable(exceptionTypeName, message); } catch
         * (Exception e) { logger.warn("Unable to create throwable", e); return
         * createJsonRpcClientException(errorObject); } }
         * 
         * }catch(JsonParseException|
         * 
         * JsonMappingException e) { LOGGER.
         * debug("Could not parse the response. Trying to transform it to an error object."
         * , e);
         * 
         * try { // TODO: Find a better solution for errors in general. throw
         * new SteemResponseError(mapper.readValue(rawJsonResponse,
         * SteemError.class)); } catch (IOException ex) { throw new
         * SteemTransformationException("Could not transform the response into an object."
         * , ex); }
         * 
         * }catch(IOException|EncodeException| InterruptedException e) { throw
         * new
         * SteemCommunicationException("Could not send the message to the Steem Node."
         * , e); }
         */
        System.out.println("handleError");
    }

    // #########################################################################
    // ## HANDLE RESULTS #######################################################
    // #########################################################################

    /**
     * 
     * @param id
     * @param responseAsObject
     * @return
     */
    private boolean hasExpectedId(long id, ObjectNode responseAsObject) {
        return responseAsObject.has(ID_FIELD_NAME) && responseAsObject.get(ID_FIELD_NAME) != null
                && responseAsObject.get(ID_FIELD_NAME).asLong() == id;
    }

    /**
     * 
     * @return
     */
    private boolean isResult() {
        return rawJsonResponse.has(RESULT_FIELD_NAME);
    }

    /**
     * 
     * @return
     */
    private boolean isResultEmpty() {
        if (rawJsonResponse.get(RESULT_FIELD_NAME) == null || rawJsonResponse.get(RESULT_FIELD_NAME).isNull())
            return true;

        LOGGER.debug("The response is empty.");
        return false;
    }

    /**
     * 
     * @return
     */
    private boolean isResponseValid() {
        if (rawJsonResponse.isObject())
            return true;

        LOGGER.error("The response is not an object.");
        return false;
    }

    /**
     * 
     * @param type
     * @param id
     * @return asd
     * @throws SteemResponseException
     */
    public <T> List<T> handleResult(JavaType type, long id) throws SteemResponseException {
        if (isResponseValid()) {
            if (!isResult()) {
                throw new SteemResponseException(
                        "The result does not contain the required " + RESULT_FIELD_NAME + " field.");
            } else {
                ObjectNode responseAsObject = ObjectNode.class.cast(rawJsonResponse);

                if (!hasExpectedId(id, responseAsObject)) {
                    throw new SteemResponseException(
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
        // TODO: Implement
        return false;
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
     * Create a new {@link Throwable} based on the Json response.
     * 
     * @return A {@link Throwable} based on the Json response.
     */
    public Throwable createThrowable() {
        // TODO: Implement
        return new Throwable();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
