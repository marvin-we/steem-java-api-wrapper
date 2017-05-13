package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;

public class DelegateVestingSharesOperation extends Operation {
    private AccountName delegator;
    private AccountName delegatee;
    @JsonProperty("vesting_shares")
    private Asset vestingShares;

    public DelegateVestingSharesOperation() {
        super(PrivateKeyType.POSTING);
    }

    /**
     * The account delegating vesting shares
     * 
     * @return the delegator
     */
    public AccountName getDelegator() {
        return delegator;
    }

    /**
     *
     * @param delegator
     *            the delegator to set
     */
    public void setDelegator(AccountName delegator) {
        this.delegator = delegator;
    }

    /**
     * The account receiving vesting shares
     * 
     * @return the delegatee
     */
    public AccountName getDelegatee() {
        return delegatee;
    }

    /**
     * @param delegatee
     *            the delegatee to set
     */
    public void setDelegatee(AccountName delegatee) {
        this.delegatee = delegatee;
    }

    /**
     * The amount of vesting shares delegated
     * 
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
