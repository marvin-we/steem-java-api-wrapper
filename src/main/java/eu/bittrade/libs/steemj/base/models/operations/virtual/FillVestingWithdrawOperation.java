package eu.bittrade.libs.steemj.base.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class represents the Steem "fill_vesting_withdraw_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FillVestingWithdrawOperation extends Operation {
    @JsonProperty("from_account")
    private AccountName fromAccount;
    @JsonProperty("to_account")
    private AccountName toAccount;
    private Asset withdrawn;
    private Asset deposited;

    /**
     * @return the fromAccount
     */
    public AccountName getFromAccount() {
        return fromAccount;
    }

    /**
     * @return the toAccount
     */
    public AccountName getToAccount() {
        return toAccount;
    }

    /**
     * @return the withdrawn
     */
    public Asset getWithdrawn() {
        return withdrawn;
    }

    /**
     * @return the deposited
     */
    public Asset getDeposited() {
        return deposited;
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
