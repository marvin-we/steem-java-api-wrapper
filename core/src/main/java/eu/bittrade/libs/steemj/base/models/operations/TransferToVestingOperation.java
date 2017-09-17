package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
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
     */
    public TransferToVestingOperation() {
        super(false);
    }

    /**
     * Get the account name of the user who send the VFS.
     * 
     * @return The user that send the VFS.
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * Set the account name of the user who will send the VFS. <b>Notice:</b>
     * The private active key of this account needs to be stored in the key
     * storage.
     * 
     * @param from
     *            The account name of the user who will send the VFS.
     */
    public void setFrom(AccountName from) {
        this.from = from;
    }

    /**
     * Get the account name of the user who received the VFS.
     * 
     * @return The account name of the user who received the VFS.
     */
    public AccountName getTo() {
        return to;
    }

    /**
     * Set the account name of the user who will received the VFS.
     * 
     * @param to
     *            The account name of the user who will received the VFS.
     */
    public void setTo(AccountName to) {
        this.to = to;
    }

    /**
     * Get the amount of Steem that has been send.
     * 
     * @return The amount of Steem that has been send.
     */
    public Asset getAmount() {
        return amount;
    }

    /**
     * Set the amount of Steem that will be send.
     * 
     * @param amount
     *            The amount of Steem that will be send.
     */
    public void setAmount(Asset amount) {
        if (!amount.getSymbol().equals(AssetSymbolType.STEEM)) {
            throw new InvalidParameterException("The Symbol needs to be STEEM.");
        }

        this.amount = amount;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransferToVestingOperation = new ByteArrayOutputStream()) {
            serializedTransferToVestingOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.TRANSFER_TO_VESTING_OPERATION.ordinal()));
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
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getOwner(), PrivateKeyType.ACTIVE);
    }
}
