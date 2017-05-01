package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WithdrawVestingOperation extends Operation {
    @JsonProperty("account")
    private String account;
    @JsonProperty("vesting_shares")
    private String vestingShares;

    public WithdrawVestingOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String getAccount() {
        return account;
    }

    public String getVestingShares() {
        return vestingShares;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }
}
