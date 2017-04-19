package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferToVestingOperation extends Operation {
    @JsonProperty("from")
    private String from;
    @JsonProperty("to")
    private String to;
    @JsonProperty("amount")
    private String amount;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
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
