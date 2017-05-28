package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;
import eu.bittrade.libs.steem.api.wrapper.models.PublicKey;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
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
     * Create a new create account operation.
     */
    public AccountCreateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
    }

    /**
     * 
     * @return
     */
    public Asset getFee() {
        return fee;
    }

    /**
     * 
     * @param fee
     */
    public void setFee(Asset fee) {
        this.fee = fee;
    }

    /**
     * 
     * @return
     */
    public AccountName getCreator() {
        return creator;
    }

    /**
     * 
     * @param creator
     */
    public void setCreator(AccountName creator) {
        this.creator = creator;
    }

    /**
     * 
     * @return
     */
    public AccountName getNewAccountName() {
        return newAccountName;
    }

    /**
     * 
     * @param newAccountName
     */
    public void setNewAccountName(AccountName newAccountName) {
        this.newAccountName = newAccountName;
    }

    /**
     * 
     * @return
     */
    public Authority getOwner() {
        return owner;
    }

    /**
     * 
     * @param owner
     */
    public void setOwner(Authority owner) {
        this.owner = owner;
    }

    /**
     * 
     * @return
     */
    public Authority getActive() {
        return active;
    }

    /**
     * 
     * @param active
     */
    public void setActive(Authority active) {
        this.active = active;
    }

    /**
     * 
     * @return
     */
    public Authority getPosting() {
        return posting;
    }

    /**
     * 
     * @param posting
     */
    public void setPosting(Authority posting) {
        this.posting = posting;
    }

    /**
     * 
     * @return
     */
    public PublicKey getMemoKey() {
        return memoKey;
    }

    /**
     * 
     * @param memoKey
     */
    public void setMemoKey(PublicKey memoKey) {
        this.memoKey = memoKey;
    }

    /**
     * 
     * @return
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * 
     * @param jsonMetadata
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
