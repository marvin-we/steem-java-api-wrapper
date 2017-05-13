package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;

public class ChangeRecoveryAccountOperation extends Operation {
    @JsonProperty("account_to_recover")
    private AccountName accountToRecover;
    @JsonProperty("new_recovery_account")
    private AccountName newRecoveryAccount;
    // TODO: Original type is "extension_type" which is an array of
    // "future_extion".
    private Object[] extensions;

    public ChangeRecoveryAccountOperation() {
        super(PrivateKeyType.POSTING);
    }

    /**
     * The account that would be recovered in case of compromise
     * 
     * @return the accountToRecover
     */
    public AccountName getAccountToRecover() {
        return accountToRecover;
    }

    /**
     * The account that creates the recover request
     * 
     * @param accountToRecover
     *            the accountToRecover to set
     */
    public void setAccountToRecover(AccountName accountToRecover) {
        this.accountToRecover = accountToRecover;
    }

    /**
     *
     * @return the newRecoveryAccount
     */
    public AccountName getNewRecoveryAccount() {
        return newRecoveryAccount;
    }

    /**
     * @param newRecoveryAccount
     *            the newRecoveryAccount to set
     */
    public void setNewRecoveryAccount(AccountName newRecoveryAccount) {
        this.newRecoveryAccount = newRecoveryAccount;
    }

    /**
     * Extensions. Not currently used.
     * 
     * @return the extensions
     */
    public Object[] getExtensions() {
        return extensions;
    }

    /**
     * @param extensions
     *            the extensions to set
     */
    public void setExtensions(Object[] extensions) {
        this.extensions = extensions;
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
