package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * This class represents the Steem "return_vesting_delegation_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ReturnVestingDelegationOperation extends Operation {
    private AccountName account;
    @JsonProperty("vesting_shares")
    private Asset vestingShares;

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * @return the vestingShares
     */
    public Asset getVestingShares() {
        return vestingShares;
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
