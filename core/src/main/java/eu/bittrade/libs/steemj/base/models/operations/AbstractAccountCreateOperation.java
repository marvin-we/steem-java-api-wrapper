package eu.bittrade.libs.steemj.base.models.operations;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
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
     * @throws InvalidParameterException
     *             If the <code>fee</code> is null, of symbol type STEEM or less
     *             than 0.
     */
    public void setFee(Asset fee) {
        if (fee == null) {
            throw new InvalidParameterException("The fee can't be null.");
        } else if (fee.getSymbol().equals(AssetSymbolType.STEEM)) {
            throw new InvalidParameterException("The fee must be paid in STEEM.");
        } else if (fee.getAmount() < 0) {
            throw new InvalidParameterException("The fee must be a postive amount.");
        }

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
     * @throws InvalidParameterException
     *             If the <code>creator</code> is null.
     */
    public void setCreator(AccountName creator) {
        if (creator == null) {
            throw new InvalidParameterException("The creator can't be null.");
        }

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
     * @throws InvalidParameterException
     *             If the <code>newAccountName</code> is null.
     */
    public void setNewAccountName(AccountName newAccountName) {
        if (newAccountName == null) {
            throw new InvalidParameterException("The new account name can't be null.");
        }

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
     * @throws InvalidParameterException
     *             If the <code>owner</code> is null.
     */
    @Override
    public void setOwner(Authority owner) {
        if (owner == null) {
            throw new InvalidParameterException("The owner can't be null.");
        }

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
     * @throws InvalidParameterException
     *             If the <code>active</code> is null.
     */
    @Override
    public void setActive(Authority active) {
        if (active == null) {
            throw new InvalidParameterException("The active can't be null.");
        }

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
     * @throws InvalidParameterException
     *             If the <code>posting</code> is null.
     */
    @Override
    public void setPosting(Authority posting) {
        if (posting == null) {
            throw new InvalidParameterException("The posting can't be null.");
        }

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
