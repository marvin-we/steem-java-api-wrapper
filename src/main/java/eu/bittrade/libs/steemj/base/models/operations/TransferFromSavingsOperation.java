package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "transfer_from_savings_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferFromSavingsOperation extends Operation {
    private AccountName from;
    // Original type is uint32_t so we use long here.
    @JsonProperty("request_id")
    private long requestId;
    private AccountName to;
    private Asset amount;
    private String memo;

    /**
     * Create a new transfer from savings operation. Use this operation to
     * transfer an {@link #amount amount} from the savings of the {@link #from
     * from} account to the {@link #to to} account.
     */
    public TransferFromSavingsOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
        // Set default values.
        this.setRequestId(0);
    }

    /**
     * Get the account which transfered the amount.
     * 
     * @return The account which transfered the amount.
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * Set the account which wants to transfer the amount.
     * 
     * @param from
     *            The account which transfered the amount.
     */
    public void setFrom(AccountName from) {
        this.from = from;
    }

    /**
     * Get the id of this request.
     * 
     * @return The id of this request.
     */
    public int getRequestId() {
        return (int) requestId;
    }

    /**
     * Set the id of this request. <b>Attention:</b> The id has to be unique.
     * 
     * @param requestId
     *            The id of this request.
     */
    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    /**
     * Get the account which received the transfered amount.
     * 
     * @return The account which received the transfered amount.
     */
    public AccountName getTo() {
        return to;
    }

    /**
     * Set the account which should receive the amount.
     * 
     * @param to
     *            The account which received the transfered amount.
     */
    public void setTo(AccountName to) {
        this.to = to;
    }

    /**
     * Get the transfered amount.
     * 
     * @return the amount
     */
    public Asset getAmount() {
        return amount;
    }

    /**
     * Set the amount to transfer.
     * 
     * @param amount
     *            The transfered amount.
     */
    public void setAmount(Asset amount) {
        this.amount = amount;
    }

    /**
     * Get the message added to this operation.
     * 
     * @return The message added to this operation.
     */
    public String getMemo() {
        return memo;
    }

    /**
     * Add an additional message to this operation.
     * 
     * @param memo
     *            The message added to this operation.
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransferFromSavingsOperation = new ByteArrayOutputStream()) {
            serializedTransferFromSavingsOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.TRANSFER_FROM_SAVINGS_OPERATION.ordinal()));
            serializedTransferFromSavingsOperation.write(this.getFrom().toByteArray());
            serializedTransferFromSavingsOperation.write(SteemJUtils.transformIntToByteArray(this.getRequestId()));
            serializedTransferFromSavingsOperation.write(this.getTo().toByteArray());
            serializedTransferFromSavingsOperation.write(this.getAmount().toByteArray());
            serializedTransferFromSavingsOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getMemo()));

            return serializedTransferFromSavingsOperation.toByteArray();
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
