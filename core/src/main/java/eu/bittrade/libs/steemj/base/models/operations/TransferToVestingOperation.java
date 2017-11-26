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
 * This class represents the Steem "transfer_to_vesting_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferToVestingOperation extends AbstractTransferOperation {
    /**
     * Create a new transfer to vesting operation to transfer Steem Power to
     * other users.
     * 
     * This operation converts STEEM into VFS (Vesting Fund Shares) at the
     * current exchange rate. With this operation it is possible to give another
     * account vesting shares so that faucets can pre-fund new accounts with
     * vesting shares.
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
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public TransferToVestingOperation(@JsonProperty("from") AccountName from, @JsonProperty("to") AccountName to,
            @JsonProperty("amount") Asset amount) {
        super(false);

        this.setFrom(from);
        this.setTo(to);
        this.setAmount(amount);
    }

    /**
     * Like
     * {@link #TransferToVestingOperation(AccountName, AccountName, Asset)}, but
     * will transform the <code>asset</code> from the <code>from</code> account
     * to the <code>from</code> account.
     * 
     * @param from
     *            The account to transfer the vestings from (see
     *            {@link #setFrom(AccountName)}).
     * @param amount
     *            The amount of vests to transfer (see
     *            {@link #setAmount(Asset)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public TransferToVestingOperation(AccountName from, Asset amount) {
        this(from, from, amount);
    }

    /**
     * Set the account name of the user who will received the
     * <code>amount</code>.
     * 
     * @param to
     *            The account name of the user who will received the
     *            <code>amount</code>.
     */
    @Override
    public void setTo(AccountName to) {
        if (to == null) {
            this.to = new AccountName();
        } else {
            this.to = to;
        }
    }

    /**
     * Set the <code>amount</code> of that will be send.
     * 
     * @param amount
     *            The <code>amount</code> of that will be send.
     * @throws InvalidParameterException
     *             If the <code>amount</code> is null, not of symbol type STEEM
     *             or less than 1.
     */
    @Override
    public void setAmount(Asset amount) {
        this.amount = setIfNotNull(amount, "The amount can't be null.");
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransferToVestingOperation = new ByteArrayOutputStream()) {
            serializedTransferToVestingOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.TRANSFER_TO_VESTING_OPERATION.getOrderId()));
            serializedTransferToVestingOperation.write(this.getFrom().toByteArray());
            serializedTransferToVestingOperation.write(this.getTo().toByteArray());
            serializedTransferToVestingOperation.write(this.getAmount().toByteArray());

            return serializedTransferToVestingOperation.toByteArray();
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
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)
                && !ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)
                && (!amount.getSymbol().equals(SteemJConfig.getInstance().getTokenSymbol()))) {
            throw new InvalidParameterException("The amount must be of type STEEM.");
        }
    }
}
