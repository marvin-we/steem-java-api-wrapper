package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;

public class SetResetAccountOperation extends Operation {
    private AccountName account;
    @JsonProperty("current_reset_account")
    private AccountName currentResetAccount;
    @JsonProperty("reset_account")
    private AccountName resetAccount;

    public SetResetAccountOperation() {
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
     * @return the currentResetAccount
     */
    public AccountName getCurrentResetAccount() {
        return currentResetAccount;
    }

    /**
     * @param currentResetAccount
     *            the currentResetAccount to set
     */
    public void setCurrentResetAccount(AccountName currentResetAccount) {
        this.currentResetAccount = currentResetAccount;
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
