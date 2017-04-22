package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;

/**
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

    public FillConvertRequestOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String getOwner() {
        return owner;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getAmountIn() {
        return amountIn;
    }

    public String getAmountOut() {
        return amountOut;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
