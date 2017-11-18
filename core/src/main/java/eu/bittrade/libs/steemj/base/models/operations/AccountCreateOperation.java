package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "account_create_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateOperation extends AbstractAccountCreateOperation {
    /**
     * Create a new create account operation. Use this operation to create a new
     * account.
     * 
     * @param creator
     *            Set the account that will pay the <code>fee</code> to create
     *            the <code>newAccountName</code> (see
     *            {@link #setCreator(AccountName)}).
     * @param fee
     *            Set the fee the <code>creator</code> will pay (see
     *            {@link #setFee(Asset)}).
     * @param newAccountName
     *            Set the new account name (see
     *            {@link #setNewAccountName(AccountName)}).
     * @param owner
     *            The new owner authority or null if the owner authority should
     *            not be updated (see {@link #setOwner(Authority)}).
     * @param active
     *            The new active authority or null if the active authority
     *            should not be updated (see {@link #setActive(Authority)}).
     * @param posting
     *            The new posting authority or null if the posting authority
     *            should not be updated (see {@link #setPosting(Authority)}).
     * @param memoKey
     *            The new memo key or null if the memo key should not be updated
     *            (see {@link #setMemoKey(PublicKey)}).
     * @param jsonMetadata
     *            Set the additional information for the <code>account</code>
     *            (see {@link #setJsonMetadata(String)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public AccountCreateOperation(@JsonProperty("creator") AccountName creator, @JsonProperty("fee") Asset fee,
            @JsonProperty("new_account_name") AccountName newAccountName, @JsonProperty("owner") Authority owner,
            @JsonProperty("active") Authority active, @JsonProperty("posting") Authority posting,
            @JsonProperty("memo_key") PublicKey memoKey, @JsonProperty("json_metadata") String jsonMetadata) {
        super(false);

        this.setCreator(creator);
        this.setFee(fee);
        this.setNewAccountName(newAccountName);
        this.setOwner(owner);
        this.setActive(active);
        this.setPosting(posting);
        this.setMemoKey(memoKey);
        this.setJsonMetadata(jsonMetadata);
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountCreateOperation = new ByteArrayOutputStream()) {
            serializedAccountCreateOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.ACCOUNT_CREATE_OPERATION.getOrderId()));
            serializedAccountCreateOperation.write(this.getFee().toByteArray());
            serializedAccountCreateOperation.write(this.getCreator().toByteArray());
            serializedAccountCreateOperation.write(this.getNewAccountName().toByteArray());
            serializedAccountCreateOperation.write(this.getOwner().toByteArray());
            serializedAccountCreateOperation.write(this.getActive().toByteArray());
            serializedAccountCreateOperation.write(this.getPosting().toByteArray());
            serializedAccountCreateOperation.write(this.getMemoKey().toByteArray());
            serializedAccountCreateOperation
                    .write(SteemJUtils.transformStringToVarIntByteArray(this.getJsonMetadata()));

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
