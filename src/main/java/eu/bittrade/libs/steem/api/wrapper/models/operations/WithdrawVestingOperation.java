package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WithdrawVestingOperation extends Operation {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("vesting_shares")
    private Asset vestingShares;

    /**
     * At any given point in time an account can be withdrawing from their
     * vesting shares. A user may change the number of shares they wish to cash
     * out at any time between 0 and their total vesting stake.
     *
     * After applying this operation, vesting_shares will be withdrawn at a rate
     * of vesting_shares/104 per week for two years starting one week after this
     * operation is included in the blockchain.
     *
     * This operation is not valid if the user has no vesting shares.
     */
    public WithdrawVestingOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
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
     * @return the vestingShares
     */
    public Asset getVestingShares() {
        return vestingShares;
    }

    /**
     * @param vestingShares
     *            the vestingShares to set
     */
    public void setVestingShares(Asset vestingShares) {
        this.vestingShares = vestingShares;
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
