package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;
import eu.bittrade.libs.steem.api.wrapper.models.FutureExtensions;

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
    // Original type is "extension_type" which is an array of "future_extions".
    private List<FutureExtensions> extensions;

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
     * Get the list of configured extensions.
     * 
     * @return All extensions.
     */
    public List<FutureExtensions> getExtensions() {
        if (extensions == null || extensions.isEmpty()) {
            // Create a new ArrayList that contains an empty FutureExtension so
            // one byte gets added to the signature for sure.
            extensions = new ArrayList<>();
            extensions.add(new FutureExtensions());
        }
        return extensions;
    }

    /**
     * Extensions are currently not supported and will be ignored.
     * 
     * @param extensions
     *            Define a list of extensions.
     */
    public void setExtensions(List<FutureExtensions> extensions) {
        this.extensions = extensions;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        for (FutureExtensions futureExtensions : this.getExtensions()) {
            //serializedAccountCreateWithDelegationOperation.write(futureExtensions.toByteArray());
        }
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
