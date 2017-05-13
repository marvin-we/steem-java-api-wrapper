package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RequestAccountRecoveryOperation extends Operation {
    @JsonProperty("recovery_account")
    private AccountName recoveryAccount;
    @JsonProperty("account_to_recover")
    private AccountName accountToRecover;
    @JsonProperty("new_owner_authority")
    private Authority newOwnerAuthority;
    // TODO: Original type is "extension_type" which is an array of "future_extion".
    private Object[] extensions;

    // account_name_type recovery_account; /// < The recovery account is listed
    // as
    /// the recovery account on the account
    /// to recover.

    // account_name_type account_to_recover; /// < The account to recover. This
    // is
    /// likely due to a compromised owner
    /// authority.

    // authority new_owner_authority; /// < The new owner authority the account
    // to
    /// recover wishes to have. This is secret
    /// < known by the account to recover and
    /// will be confirmed in a
    /// recover_account_operation
    // 
    // extensions_type extensions; /// < Extensions. Not currently used.

    public RequestAccountRecoveryOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    /**
     * @return the recoveryAccount
     */
    public AccountName getRecoveryAccount() {
        return recoveryAccount;
    }

    /**
     * @param recoveryAccount
     *            the recoveryAccount to set
     */
    public void setRecoveryAccount(AccountName recoveryAccount) {
        this.recoveryAccount = recoveryAccount;
    }

    /**
     * @return the accountToRecover
     */
    public AccountName getAccountToRecover() {
        return accountToRecover;
    }

    /**
     * @param accountToRecover
     *            the accountToRecover to set
     */
    public void setAccountToRecover(AccountName accountToRecover) {
        this.accountToRecover = accountToRecover;
    }

    /**
     * @return the newOwnerAuthority
     */
    public Authority getNewOwnerAuthority() {
        return newOwnerAuthority;
    }

    /**
     * @param newOwnerAuthority
     *            the newOwnerAuthority to set
     */
    public void setNewOwnerAuthority(Authority newOwnerAuthority) {
        this.newOwnerAuthority = newOwnerAuthority;
    }

    /**
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
