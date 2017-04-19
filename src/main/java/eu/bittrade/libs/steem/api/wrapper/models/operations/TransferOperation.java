package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferOperation extends Operation {
    @JsonProperty("from")
    private String from;
    @JsonProperty("to")
    private String to;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("memo")
    private String memo;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getAmount() {
        return amount;
    }

    public String getMemo() {
        return memo;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
