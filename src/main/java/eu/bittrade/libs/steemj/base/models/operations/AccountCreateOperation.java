package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "account_create_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateOperation extends Operation {
    @JsonProperty("fee")
    private Asset fee;
    // Accurate type is fixed_string
    @JsonProperty("creator")
    private AccountName creator;
    // Accurate type is fixed_string
    @JsonProperty("new_account_name")
    private AccountName newAccountName;
    @JsonProperty("owner")
    private Authority owner;
    @JsonProperty("active")
    private Authority active;
    @JsonProperty("posting")
    private Authority posting;
    @JsonProperty("memo_key")
    private PublicKey memoKey;
    @JsonProperty("json_metadata")
    private String jsonMetadata;

    /**
     * Create a new create account operation. Use this operation to create a new
     * account.
     */
    public AccountCreateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
    }

    /**
     * Get the fee the {@link #creator creator} has paid to create this new
     * account.
     * 
     * @return The fee.
     */
    public Asset getFee() {
        return fee;
    }

    /**
     * Set the fee you are willing to pay to create a new acocunt.
     * 
     * @param fee
     *            The fee.
     */
    public void setFee(Asset fee) {
        this.fee = fee;
    }

    /**
     * Get the account name of the user who created a new account.
     * 
     * @return The account name of the user who created a new account.
     */
    public AccountName getCreator() {
        return creator;
    }

    /**
     * Set the account name of the user who created a new account.
     * 
     * @param creator
     *            The account name of the user who created a new account.
     */
    public void setCreator(AccountName creator) {
        this.creator = creator;
    }

    /**
     * Get the account name of the user which has been created.
     * 
     * @return The account name of the user which has been created.
     */
    public AccountName getNewAccountName() {
        return newAccountName;
    }

    /**
     * Set the account name of the account that should be created.
     * 
     * @param newAccountName
     *            The account name of the user which should be created.
     */
    public void setNewAccountName(AccountName newAccountName) {
        this.newAccountName = newAccountName;
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
     * Get the posting
     * {@link eu.bittrade.libs.steemj.base.models.Authority Authority} of
     * the {@link #newAccountName newAccountName}.
     * 
     * @return The posting authority.
     */
    public Authority getPosting() {
        return posting;
    }

    /**
     * Set the posting
     * {@link eu.bittrade.libs.steemj.base.models.Authority Authority} of
     * the {@link #newAccountName newAccountName}.
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

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountCreateOperation = new ByteArrayOutputStream()) {
            serializedAccountCreateOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.ACCOUNT_CREATE_OPERATION.ordinal()));
            serializedAccountCreateOperation.write(this.getFee().toByteArray());
            serializedAccountCreateOperation.write(this.getCreator().toByteArray());
            serializedAccountCreateOperation.write(this.getNewAccountName().toByteArray());
            serializedAccountCreateOperation.write(this.getOwner().toByteArray());
            serializedAccountCreateOperation.write(this.getActive().toByteArray());
            serializedAccountCreateOperation.write(this.getPosting().toByteArray());
            serializedAccountCreateOperation.write(this.getMemoKey().toByteArray());
            serializedAccountCreateOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.jsonMetadata));

            return serializedAccountCreateOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
