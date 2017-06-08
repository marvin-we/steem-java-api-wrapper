package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;
import eu.bittrade.libs.steem.api.wrapper.models.PublicKey;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "account_update_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountUpdateOperation extends Operation {
    @JsonProperty("account")
    private AccountName account;
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
     * Create a new create account update operation. Use this operation to
     * update the keys of an existing account.
     */
    public AccountUpdateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.OWNER);
    }

    /**
     * Get the account name of the account that has been changed.
     * 
     * @return The account name of the changed account.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Set the account name of the account that has been changed.
     * 
     * @param account
     *            The account name of the account to change.
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    /**
     * Get the owner {@link eu.bittrade.libs.steem.api.wrapper.models.Authority
     * Authority} of the {@link #account account}.
     * 
     * @return The owner authority.
     */
    public Authority getOwner() {
        return owner;
    }

    /**
     * Set the owner {@link eu.bittrade.libs.steem.api.wrapper.models.Authority
     * Authority} of the {@link #account account}.
     * 
     * @param owner
     *            The owner authority.
     */
    public void setOwner(Authority owner) {
        this.owner = owner;
    }

    /**
     * Get the active {@link eu.bittrade.libs.steem.api.wrapper.models.Authority
     * Authority} of the {@link #account account}.
     * 
     * @return The active authority.
     */
    public Authority getActive() {
        return active;
    }

    /**
     * Set the active {@link eu.bittrade.libs.steem.api.wrapper.models.Authority
     * Authority} of the {@link #account account}.
     * 
     * @param active
     *            The active authority.
     */
    public void setActive(Authority active) {
        this.active = active;
    }

    /**
     * Get the posting
     * {@link eu.bittrade.libs.steem.api.wrapper.models.Authority Authority} of
     * the {@link #account account}.
     * 
     * @return The posting authority.
     */
    public Authority getPosting() {
        return posting;
    }

    /**
     * Set the posting
     * {@link eu.bittrade.libs.steem.api.wrapper.models.Authority Authority} of
     * the {@link #account account}.
     * 
     * @param posting
     *            The posting authority.
     */
    public void setPosting(Authority posting) {
        this.posting = posting;
    }

    /**
     * Get the memo {@link eu.bittrade.libs.steem.api.wrapper.models.PublicKey
     * PublicKey} of the {@link #account account}.
     * 
     * @return The memo key.
     */
    public PublicKey getMemoKey() {
        return memoKey;
    }

    /**
     * Set the memo {@link eu.bittrade.libs.steem.api.wrapper.models.PublicKey
     * PublicKey} of the {@link #account account}.
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
        try (ByteArrayOutputStream serializedAccountUpdateOperation = new ByteArrayOutputStream()) {
            serializedAccountUpdateOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.ACCOUNT_UPDATE_OPERATION.ordinal()));
            serializedAccountUpdateOperation.write(this.getAccount().toByteArray());
            serializedAccountUpdateOperation.write(this.getOwner().toByteArray());
            serializedAccountUpdateOperation.write(this.getActive().toByteArray());
            serializedAccountUpdateOperation.write(this.getPosting().toByteArray());
            serializedAccountUpdateOperation.write(this.getMemoKey().toByteArray());
            serializedAccountUpdateOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.jsonMetadata));

            return serializedAccountUpdateOperation.toByteArray();
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
