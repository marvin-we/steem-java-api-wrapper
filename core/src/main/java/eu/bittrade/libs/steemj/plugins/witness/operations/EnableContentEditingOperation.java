package eu.bittrade.libs.steemj.plugins.witness.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.base.models.operations.CustomJsonOperationPayload;
import eu.bittrade.libs.steemj.enums.ValidationType;

/**
 * This class represents the Steem "enable_content_editing_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EnableContentEditingOperation extends CustomJsonOperationPayload {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("relock_time")
    private TimePointSec relockTime;

    public EnableContentEditingOperation() {

    }

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    /**
     * @return the relockTime
     */
    public TimePointSec getRelockTime() {
        return relockTime;
    }

    /**
     * @param relockTime
     *            the relockTime to set
     */
    public void setRelockTime(TimePointSec relockTime) {
        this.relockTime = relockTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public void validate(ValidationType validationType) {
        // TODO Auto-generated method stub

    }

}
