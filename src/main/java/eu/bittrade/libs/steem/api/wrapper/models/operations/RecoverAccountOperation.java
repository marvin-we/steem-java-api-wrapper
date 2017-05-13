package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;

public class RecoverAccountOperation extends Operation {
    @JsonProperty("account_to_recover")
    private AccountName accountToRecover;
    @JsonProperty("new_owner_authority")
    private Authority newOwnerAuthority;
    @JsonProperty("recent_owner_authority")
    private Authority recentOwnerAuthority;
    // TODO: Original type is "extension_type" which is an array of "future_extion".
    private Object[] extensions;

    public RecoverAccountOperation() {
        super(PrivateKeyType.POSTING);
    }

    /**
     * The account to be recovered
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
     * The new owner authority as specified in the request account recovery operation.
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

    /**A previous owner authority that the account holder will use to prove past ownership of the account to be recovered.
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
     * @return the extensions
     */
    public Object[] getExtensions() {
        return extensions;
    }

    /**
     * Extensions. Not currently used.
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
