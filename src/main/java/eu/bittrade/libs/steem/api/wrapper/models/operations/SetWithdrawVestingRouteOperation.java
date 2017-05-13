package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetWithdrawVestingRouteOperation extends Operation {
    @JsonProperty("from_account")
    private AccountName fromAccount;
    @JsonProperty("to_account")
    private AccountName toAccount;
    // Original type is uint16_t so we use int here.
    @JsonProperty("percent")
    private int percent;
    @JsonProperty("auto_vest")
    private Boolean autoVest;

    public SetWithdrawVestingRouteOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    /**
     * @return the fromAccount
     */
    public AccountName getFromAccount() {
        return fromAccount;
    }

    /**
     * @param fromAccount
     *            the fromAccount to set
     */
    public void setFromAccount(AccountName fromAccount) {
        this.fromAccount = fromAccount;
    }

    /**
     * @return the toAccount
     */
    public AccountName getToAccount() {
        return toAccount;
    }

    /**
     * @param toAccount
     *            the toAccount to set
     */
    public void setToAccount(AccountName toAccount) {
        this.toAccount = toAccount;
    }

    /**
     * @return the percent
     */
    public int getPercent() {
        return percent;
    }

    /**
     * @param percent
     *            the percent to set
     */
    public void setPercent(int percent) {
        this.percent = percent;
    }

    /**
     * @return the autoVest
     */
    public Boolean getAutoVest() {
        return autoVest;
    }

    /**
     * @param autoVest
     *            the autoVest to set
     */
    public void setAutoVest(Boolean autoVest) {
        this.autoVest = autoVest;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
