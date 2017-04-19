package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetWithdrawVestingRouteOperation extends Operation {
    @JsonProperty("from_account")
    private String fromAccount;
    @JsonProperty("to_account")
    private String toAccount;
    @JsonProperty("percent")
    private short percent;
    @JsonProperty("auto_vest")
    private Boolean autoVest;

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public short getPercent() {
        return percent;
    }

    public Boolean getAutoVest() {
        return autoVest;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
