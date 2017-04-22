package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;

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

    public SetWithdrawVestingRouteOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

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
