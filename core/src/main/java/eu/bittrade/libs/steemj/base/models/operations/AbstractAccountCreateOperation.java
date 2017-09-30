package eu.bittrade.libs.steemj.base.models.operations;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;

/**
 * This abstract class contains fields that exist in all Steem Operations
 * related to the account creation.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class AbstractAccountCreateOperation extends AbstractAccountOperation {
    @JsonProperty("fee")
    protected Asset fee;
    @JsonProperty("creator")
    protected AccountName creator;
    @JsonProperty("new_account_name")
    protected AccountName newAccountName;

    /**
     * Create a new Operation object by providing the operation type.
     */
    protected AbstractAccountCreateOperation(boolean virtual) {
        super(virtual);
    }

    /**
     * Get the fee the {@link #getCreator() creator} has paid to create this new
     * account.
     * 
     * @return The fee.
     */
    public Asset getFee() {
        return fee;
    }

    /**
     * Set the fee the {@link #getCreator() creator} will pay to create a new
     * account.
     * 
     * @param fee
     *            The fee.
     */
    public void setFee(Asset fee) {
        this.fee = fee;
    }

    /**
     * Get the account who created a new account.
     * 
     * @return The the user who created a new account.
     */
    public AccountName getCreator() {
        return creator;
    }

    /**
     * Set the user who created a new account. <b>Notice:</b> The private active
     * key of this account needs to be stored in the key storage.
     * 
     * @param creator
     *            The the user who creates a new account.
     */
    public void setCreator(AccountName creator) {
        this.creator = creator;
    }

    /**
     * Get the account name of the created account.
     * 
     * @return The account name of the user which has been created.
     */
    public AccountName getNewAccountName() {
        return newAccountName;
    }

    /**
     * Set the account name for the new account.
     * 
     * @param newAccountName
     *            The account name of the user which should be created.
     */
    public void setNewAccountName(AccountName newAccountName) {
        this.newAccountName = newAccountName;
    }

    /**
     * Get the owner {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getNewAccountName() newAccountName}.
     * 
     * @return The owner authority.
     */
    @Override
    public Authority getOwner() {
        return owner;
    }

    /**
     * Set the owner {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getNewAccountName() newAccountName}.
     * 
     * @param owner
     *            The owner authority.
     */
    @Override
    public void setOwner(Authority owner) {
        this.owner = owner;
    }

    /**
     * Get the active {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getNewAccountName() newAccountName}.
     * 
     * @return The active authority.
     */
    @Override
    public Authority getActive() {
        return active;
    }

    /**
     * Set the active {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getNewAccountName() newAccountName}.
     * 
     * @param active
     *            The active authority.
     */
    @Override
    public void setActive(Authority active) {
        this.active = active;
    }

    /**
     * Get the posting {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getNewAccountName() newAccountName}.
     * 
     * @return The posting authority.
     */
    @Override
    public Authority getPosting() {
        return posting;
    }

    /**
     * Set the posting {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getNewAccountName() newAccountName}.
     * 
     * @param posting
     *            The posting authority.
     */
    @Override
    public void setPosting(Authority posting) {
        this.posting = posting;
    }

    /**
     * Get the memo {@link eu.bittrade.libs.steemj.base.models.PublicKey
     * PublicKey} of the {@link #getNewAccountName() newAccountName}.
     * 
     * @return The memo key.
     */
    @Override
    public PublicKey getMemoKey() {
        return memoKey;
    }

    /**
     * Set the memo {@link eu.bittrade.libs.steemj.base.models.PublicKey
     * PublicKey} of the {@link #getNewAccountName() newAccountName}.
     * 
     * @param memoKey
     *            The memo key.
     */
    public void setMemoKey(PublicKey memoKey) {
        this.memoKey = memoKey;
    }

    @Override
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getCreator(), PrivateKeyType.ACTIVE);
    }
}
