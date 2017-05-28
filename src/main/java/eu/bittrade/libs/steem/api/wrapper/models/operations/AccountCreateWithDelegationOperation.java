package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;
import eu.bittrade.libs.steem.api.wrapper.models.PublicKey;

public class AccountCreateWithDelegationOperation extends Operation {
    private Asset fee;
    private Asset delegation;
    private AccountName creator;
    @JsonProperty("new_account_name")
    private AccountName newAccountName;
    private Authority owner;
    private Authority active;
    private Authority posting;
    @JsonProperty("memo_key")
    private PublicKey memoKey;
    @JsonProperty("json_metadata")
    private String jsonMetadata;
    // TODO: Original type is "extension_type" which is an array of
    // "future_extion".
    private Object[] extensions;

    public AccountCreateWithDelegationOperation() {
        super(PrivateKeyType.ACTIVE);
    }

    /**
     * @return the fee
     */
    public Asset getFee() {
        return fee;
    }

    /**
     * @param fee
     *            the fee to set
     */
    public void setFee(Asset fee) {
        this.fee = fee;
    }

    /**
     * @return the delegation
     */
    public Asset getDelegation() {
        return delegation;
    }

    /**
     * @param delegation
     *            the delegation to set
     */
    public void setDelegation(Asset delegation) {
        this.delegation = delegation;
    }

    /**
     * @return the creator
     */
    public AccountName getCreator() {
        return creator;
    }

    /**
     * @param creator
     *            the creator to set
     */
    public void setCreator(AccountName creator) {
        this.creator = creator;
    }

    /**
     * @return the newAccountName
     */
    public AccountName getNewAccountName() {
        return newAccountName;
    }

    /**
     * @param newAccountName
     *            the newAccountName to set
     */
    public void setNewAccountName(AccountName newAccountName) {
        this.newAccountName = newAccountName;
    }

    /**
     * @return the owner
     */
    public Authority getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(Authority owner) {
        this.owner = owner;
    }

    /**
     * @return the active
     */
    public Authority getActive() {
        return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(Authority active) {
        this.active = active;
    }

    /**
     * @return the posting
     */
    public Authority getPosting() {
        return posting;
    }

    /**
     * @param posting
     *            the posting to set
     */
    public void setPosting(Authority posting) {
        this.posting = posting;
    }

    /**
     * @return the memoKey
     */
    public PublicKey getMemoKey() {
        return memoKey;
    }

    /**
     * @param memoKey
     *            the memoKey to set
     */
    public void setMemoKey(PublicKey memoKey) {
        this.memoKey = memoKey;
    }

    /**
     * @return the jsonMetadata
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * @param jsonMetadata
     *            the jsonMetadata to set
     */
    public void setJsonMetadata(String jsonMetadata) {
        this.jsonMetadata = jsonMetadata;
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
