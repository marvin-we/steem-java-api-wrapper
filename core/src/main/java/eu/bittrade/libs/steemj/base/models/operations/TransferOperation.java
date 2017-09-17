package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "transfer_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferOperation extends AbstractTransferOperation {
    @JsonProperty("memo")
    private String memo;

    /**
     * Create a new transfer operation. Use this operation to transfer an asset
     * from one account to another.
     */
    public TransferOperation() {
        super(false);
    }

    /**
     * Get the user who sends the asset.
     * 
     * @return The user who sends the asset.
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * Set the user who sends the asset. <b>Notice:</b> The private active key
     * of this account needs to be stored in the key storage.
     * 
     * @param from
     *            The user who sends the asset.
     */
    public void setFrom(AccountName from) {
        this.from = from;
    }

    /**
     * Get the user who received the transfer.
     * 
     * @return The user who received the transfer.
     */
    public AccountName getTo() {
        return to;
    }

    /**
     * Set the user who will receive the transfer.
     * 
     * @param to
     *            The user who will receive the transfer.
     */
    public void setTo(AccountName to) {
        this.to = to;
    }

    /**
     * Get the amount and the currency that has been transfered.
     * 
     * @return The amount and the currency that has been transfered.
     */
    public Asset getAmount() {
        return amount;
    }

    /**
     * Set the amount and the currency that should be transfered.
     * 
     * @param amount
     *            Set amount and the currency that should be transfered.
     */
    public void setAmount(Asset amount) {
        this.amount = amount;
    }

    /**
     * Get the message of this transfer.
     * 
     * @return The message of this transfer.
     */
    public String getMemo() {
        return memo;
    }

    /**
     * Set a message for this transfer.
     * 
     * @param memo
     *            The message of this transfer.
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransferOperation = new ByteArrayOutputStream()) {
            serializedTransferOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.TRANSFER_OPERATION.ordinal()));
            serializedTransferOperation.write(this.getFrom().toByteArray());
            serializedTransferOperation.write(this.getTo().toByteArray());
            serializedTransferOperation.write(this.getAmount().toByteArray());
            serializedTransferOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getMemo()));

            return serializedTransferOperation.toByteArray();
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
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getOwner(), PrivateKeyType.ACTIVE);
    }
}
