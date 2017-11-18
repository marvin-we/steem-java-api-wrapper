package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "cancel_transfer_from_savings_operation"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CancelTransferFromSavingsOperation extends Operation {
    @JsonProperty("from")
    private AccountName from;
    // Original type is uint32_t so we use long here.
    @JsonProperty("request_id")
    private long requestId;

    /**
     * Create a new cancel transfer from savings operation. Use this operation
     * to cancel a
     * {@link eu.bittrade.libs.steemj.base.models.operations.TransferFromSavingsOperation
     * TransferFromSavingsOperation}.
     * 
     * @param from
     *            Set the owner of the transfer from savings operation (see
     *            {@link #setFrom(AccountName)}).
     * @param requestId
     *            Set the id of the transfer operation (see
     *            {@link #setRequestId(long)}).
     * @throws InvalidParameterException
     *             If one of the parameters does not fulfill the requirements.
     */
    @JsonCreator
    public CancelTransferFromSavingsOperation(@JsonProperty("from") AccountName from,
            @JsonProperty("request_id") long requestId) {
        super(false);

        this.setFrom(from);
        this.setRequestId(requestId);
    }

    /**
     * Like {@link #CancelTransferFromSavingsOperation(AccountName, long)}, but
     * automatically sets the request id to 0.
     * 
     * @param from
     *            Set the owner of the transfer from savings operation (see
     *            {@link #setFrom(AccountName)}).
     * @throws InvalidParameterException
     *             If the <code>from</code> parameter does not fulfill the
     *             requirements.
     */
    public CancelTransferFromSavingsOperation(AccountName from) {
        // Set default values.
        this(from, 0);
    }

    /**
     * Get the account whose
     * {@link eu.bittrade.libs.steemj.base.models.operations.TransferFromSavingsOperation
     * TransferFromSavingsOperation} has been canceled.
     * 
     * @return The account whose transfer to savings operation has been
     *         canceled.
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * Set the account whose
     * {@link eu.bittrade.libs.steemj.base.models.operations.TransferFromSavingsOperation
     * TransferFromSavingsOperation} should be canceled. <b>Notice:</b> The
     * private active key of this account needs to be stored in the key storage.
     * 
     * @param from
     *            The account whose transfer to savings operation should be
     *            canceled.
     * @throws InvalidParameterException
     *             If the from account is null.
     */
    public void setFrom(AccountName from) {
        this.from = setIfNotNull(from, "The from acccount can't be null.");
    }

    /**
     * Get the id of the canceled
     * {@link eu.bittrade.libs.steemj.base.models.operations.TransferFromSavingsOperation
     * TransferFromSavingsOperation}.
     * 
     * @return The id of the canceled TransferFromSavingsOperation.
     */
    public int getRequestId() {
        return (int) requestId;
    }

    /**
     * Set the id of the
     * {@link eu.bittrade.libs.steemj.base.models.operations.TransferFromSavingsOperation
     * TransferFromSavingsOperation} to cancel.
     * 
     * @param requestId
     *            The id of the canceled TransferFromSavingsOperation.
     */
    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCancelTransferFromSavingsOperation = new ByteArrayOutputStream()) {
            serializedCancelTransferFromSavingsOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.CANCEL_TRANSFER_FROM_SAVINGS_OPERATION.getOrderId()));
            serializedCancelTransferFromSavingsOperation.write(this.getFrom().toByteArray());
            serializedCancelTransferFromSavingsOperation
                    .write(SteemJUtils.transformIntToByteArray(this.getRequestId()));

            return serializedCancelTransferFromSavingsOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getFrom(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        return;
    }
}
