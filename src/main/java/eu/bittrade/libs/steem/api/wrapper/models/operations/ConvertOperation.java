package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ConvertOperation extends Operation {
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("requestid")
    private String requestId;
    @JsonProperty("amount")
    private String amount;

    public String getOwner() {
        return owner;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
