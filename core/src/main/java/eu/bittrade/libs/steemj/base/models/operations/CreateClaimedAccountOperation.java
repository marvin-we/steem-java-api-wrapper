package eu.bittrade.libs.steemj.base.models.operations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This class represents the Steem "create_claimed_account_operation" object.
 *
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CreateClaimedAccountOperation extends AccountCreateOperation {

    /**
     * Create a claim created account operation. Use this operation to claim an account.
     * This operation is the same as {@link AccountCreateOperation}
     *
     * @param creator        Set the account that will pay the <code>fee</code> to create
     *                       the <code>newAccountName</code> (see
     *                       {@link #setCreator(AccountName)}).
     * @param fee            Set the fee the <code>creator</code> will pay (see
     *                       {@link #setFee(Asset)}).
     * @param newAccountName Set the new account name (see
     *                       {@link #setNewAccountName(AccountName)}).
     * @param owner          The new owner authority or null if the owner authority should
     *                       not be updated (see {@link #setOwner(Authority)}).
     * @param active         The new active authority or null if the active authority
     *                       should not be updated (see {@link #setActive(Authority)}).
     * @param posting        The new posting authority or null if the posting authority
     *                       should not be updated (see {@link #setPosting(Authority)}).
     * @param memoKey        The new memo key or null if the memo key should not be updated
     *                       (see {@link #setMemoKey(PublicKey)}).
     * @param jsonMetadata   Set the additional information for the <code>account</code>
     *                       (see {@link #setJsonMetadata(String)}).
     * @throws InvalidParameterException If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public CreateClaimedAccountOperation(@JsonProperty("creator") AccountName creator,
                                         @JsonProperty("fee") Asset fee,
                                         @JsonProperty("new_account_name") AccountName newAccountName,
                                         @JsonProperty("owner") Authority owner,
                                         @JsonProperty("active") Authority active,
                                         @JsonProperty("posting") Authority posting,
                                         @JsonProperty("memo_key") PublicKey memoKey, @JsonProperty("json_metadata") String jsonMetadata) {
        super(creator, fee, newAccountName, owner, active, posting, memoKey, jsonMetadata);
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCreateClaimedAccountOperation = new ByteArrayOutputStream()) {
            serializedCreateClaimedAccountOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.CREATE_CLAIMED_ACCOUNT_OPERATION.getOrderId()));
            serializedCreateClaimedAccountOperation.write(this.getFee().toByteArray());
            serializedCreateClaimedAccountOperation.write(this.getCreator().toByteArray());
            serializedCreateClaimedAccountOperation.write(this.getNewAccountName().toByteArray());
            serializedCreateClaimedAccountOperation.write(this.getOwner().toByteArray());
            serializedCreateClaimedAccountOperation.write(this.getActive().toByteArray());
            serializedCreateClaimedAccountOperation.write(this.getPosting().toByteArray());
            serializedCreateClaimedAccountOperation.write(this.getMemoKey().toByteArray());
            serializedCreateClaimedAccountOperation
                    .write(SteemJUtils.transformStringToVarIntByteArray(this.getJsonMetadata()));

            return serializedCreateClaimedAccountOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }
}
