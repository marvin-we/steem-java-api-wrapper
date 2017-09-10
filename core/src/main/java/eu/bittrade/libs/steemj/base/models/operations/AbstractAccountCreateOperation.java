package eu.bittrade.libs.steemj.base.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;

/**
 * This abstract class contains fields that exist in all Steem
 * "account_create_operation" types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
abstract class AbstractAccountCreateOperation extends Operation {
    @JsonProperty("owner")
    protected Authority owner;
    @JsonProperty("active")
    protected Authority active;
    @JsonProperty("posting")
    protected Authority posting;
    @JsonProperty("memo_key")
    protected PublicKey memoKey;
    @JsonProperty("json_metadata")
    protected String jsonMetadata;

    /**
     * Create a new Operation object by providing the operation type.
     */
    protected AbstractAccountCreateOperation(boolean virtual) {
        super(virtual);
    }

    /**
     * Get the owner {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #newAccountName newAccountName}.
     * 
     * @return The owner authority.
     */
    public Authority getOwner() {
        return owner;
    }

    /**
     * Set the owner {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #newAccountName newAccountName}.
     * 
     * @param owner
     *            The owner authority.
     */
    public void setOwner(Authority owner) {
        this.owner = owner;
    }

    /**
     * Get the active {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #newAccountName newAccountName}.
     * 
     * @return The active authority.
     */
    public Authority getActive() {
        return active;
    }

    /**
     * Set the active {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #newAccountName newAccountName}.
     * 
     * @param active
     *            The active authority.
     */
    public void setActive(Authority active) {
        this.active = active;
    }

    /**
     * Get the posting {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #newAccountName newAccountName}.
     * 
     * @return The posting authority.
     */
    public Authority getPosting() {
        return posting;
    }

    /**
     * Set the posting {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #newAccountName newAccountName}.
     * 
     * @param posting
     *            The posting authority.
     */
    public void setPosting(Authority posting) {
        this.posting = posting;
    }

    /**
     * Get the memo {@link eu.bittrade.libs.steemj.base.models.PublicKey
     * PublicKey} of the {@link #newAccountName newAccountName}.
     * 
     * @return The memo key.
     */
    public PublicKey getMemoKey() {
        return memoKey;
    }

    /**
     * Set the memo {@link eu.bittrade.libs.steemj.base.models.PublicKey
     * PublicKey} of the {@link #newAccountName newAccountName}.
     * 
     * @param memoKey
     *            The memo key.
     */
    public void setMemoKey(PublicKey memoKey) {
        this.memoKey = memoKey;
    }

    /**
     * Get the json metadata which have been added to this operation.
     * 
     * @return The json metadata which have been added to this operation.
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * Add json metadata to this operation.
     * 
     * @param jsonMetadata
     *            The json metadata.
     */
    public void setJsonMetadata(String jsonMetadata) {
        this.jsonMetadata = jsonMetadata;
    }
}
