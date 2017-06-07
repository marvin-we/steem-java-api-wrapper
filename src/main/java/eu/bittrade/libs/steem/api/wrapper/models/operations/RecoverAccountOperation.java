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

public class RecoverAccountOperation extends Operation {
    @JsonProperty("account_to_recover")
    private AccountName accountToRecover;
    @JsonProperty("new_owner_authority")
    private Authority newOwnerAuthority;
    @JsonProperty("recent_owner_authority")
    private Authority recentOwnerAuthority;
    // Original type is "extension_type" which is an array of "future_extions".
    private List<FutureExtensions> extensions;

    public RecoverAccountOperation() {
        super(PrivateKeyType.POSTING);
    }

    /**
     * The account to be recovered
     * 
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
     * The new owner authority as specified in the request account recovery
     * operation.
     * 
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
     * A previous owner authority that the account holder will use to prove past
     * ownership of the account to be recovered.
     * 
     * @return the recentOwnerAuthority
     */
    public Authority getRecentOwnerAuthority() {
        return recentOwnerAuthority;
    }

    /**
     * @param recentOwnerAuthority
     *            the recentOwnerAuthority to set
     */
    public void setRecentOwnerAuthority(Authority recentOwnerAuthority) {
        this.recentOwnerAuthority = recentOwnerAuthority;
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
        
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
