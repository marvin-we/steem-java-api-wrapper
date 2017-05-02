package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * This class represents a Steem "fill_convert_request_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FillConvertRequestOperation extends Operation {
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("requestid")
    private int requestId;
    @JsonProperty("amount_in")
    private String amountIn;
    @JsonProperty("amount_out")
    private String amountOut;

    /**
     * 
     * @return
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 
     * @return
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * 
     * @return
     */
    public String getAmountIn() {
        return amountIn;
    }

    /**
     * 
     * @return
     */
    public String getAmountOut() {
        return amountOut;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // The byte representation is not needed for virtual operations as we
        // can't broadcast them.
        return new byte[0];
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
