package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "transfer_from_savings_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferFromSavingsOperation extends AbstractTransferOperation {
    // Original type is uint32_t so we use long here.
    @JsonProperty("request_id")
    private long requestId;
    @JsonProperty("memo")
    private String memo;

    /**
     * Create a new transfer from savings operation. Use this operation to
     * transfer an {@link #getAmount() amount} from the savings of the
     * {@link #getFrom() from} account to the {@link #getTo() to} account.
     * 
     * @param from
     *            The account to transfer the vestings from (see
     *            {@link #setFrom(AccountName)}).
     * @param to
     *            The account that will receive the transfered vestings (see
     *            {@link #setTo(AccountName)}).
     * @param amount
     *            The amount of vests to transfer (see
     *            {@link #setAmount(Asset)}).
     * @param requestId
     *            The id of this request to set (see
     *            {@link #setRequestId(long)}).
     * @param memo
     *            An additional message added to the operation (see
     *            {@link #setMemo(String)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public TransferFromSavingsOperation(@JsonProperty("from") AccountName from, @JsonProperty("to") AccountName to,
            @JsonProperty("amount") Asset amount, @JsonProperty("request_id") long requestId,
            @JsonProperty("memo") String memo) {
        super(false);

        this.setFrom(from);
        this.setTo(to);
        this.setAmount(amount);
        this.setRequestId(requestId);
        this.setMemo(memo);
    }

    /**
     * Like
     * {@link #TransferFromSavingsOperation(AccountName, AccountName, Asset, long, String)},
     * but sets the request id to its default value (0).
     * 
     * @param from
     *            The account to transfer the vestings from (see
     *            {@link #setFrom(AccountName)}).
     * @param to
     *            The account that will receive the transfered vestings (see
     *            {@link #setTo(AccountName)}).
     * @param amount
     *            The amount of vests to transfer (see
     *            {@link #setAmount(Asset)}).
     * @param memo
     *            An additional message added to the operation (see
     *            {@link #setMemo(String)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public TransferFromSavingsOperation(AccountName from, AccountName to, Asset amount, String memo) {
        this(from, to, amount, 0, memo);
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
     * Set the <code>amount</code> of that will be send.
     * 
     * @param amount
     *            The <code>amount</code> of that will be send.
     * @throws InvalidParameterException
     *             If the <code>amount</code> is null, not of symbol type
     *             STEEM/SBD or less than 1.
     */
    @Override
    public void setAmount(Asset amount) {
        this.amount = setIfNotNull(amount, "The amount can't be null.");
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
     * @throws InvalidParameterException
     *             If the <code>memo</code> has more than 2048 characters.
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransferFromSavingsOperation = new ByteArrayOutputStream()) {
            serializedTransferFromSavingsOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.TRANSFER_FROM_SAVINGS_OPERATION.getOrderId()));
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

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)) {
            super.validate(validationType);

            if (!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)) {
                if (!amount.getSymbol().equals(SteemJConfig.getInstance().getTokenSymbol())
                        && !amount.getSymbol().equals(SteemJConfig.getInstance().getDollarSymbol())) {
                    throw new InvalidParameterException("The amount must be of type STEEM or SBD.");
                } else if (amount.getAmount() <= 0) {
                    throw new InvalidParameterException("Must transfer a nonzero amount.");
                }
            }

            if (memo.length() > 2048) {
                throw new InvalidParameterException("The memo is too long. Only 2048 characters are allowed.");
            }
        }
    }
}
