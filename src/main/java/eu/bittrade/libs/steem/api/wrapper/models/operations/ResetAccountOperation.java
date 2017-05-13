package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;

public class ResetAccountOperation extends Operation {
    @JsonProperty("reset_account")
    private AccountName resetAccount;
    @JsonProperty("account_to_reset")
    private AccountName accountToReset;
    @JsonProperty("new_owner_authority")
    private Authority newOwwnerAuthority;

    public ResetAccountOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    /**
     * @return the resetAccount
     */
    public AccountName getResetAccount() {
        return resetAccount;
    }

    /**
     * @param resetAccount
     *            the resetAccount to set
     */
    public void setResetAccount(AccountName resetAccount) {
        this.resetAccount = resetAccount;
    }

    /**
     * @return the accountToReset
     */
    public AccountName getAccountToReset() {
        return accountToReset;
    }

    /**
     * @param accountToReset
     *            the accountToReset to set
     */
    public void setAccountToReset(AccountName accountToReset) {
        this.accountToReset = accountToReset;
    }

    /**
     * @return the newOwwnerAuthority
     */
    public Authority getNewOwwnerAuthority() {
        return newOwwnerAuthority;
    }

    /**
     * @param newOwwnerAuthority
     *            the newOwwnerAuthority to set
     */
    public void setNewOwwnerAuthority(Authority newOwwnerAuthority) {
        this.newOwwnerAuthority = newOwwnerAuthority;
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
