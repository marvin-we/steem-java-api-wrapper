package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "limit_order_cancel_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCancelOperation extends AbstractLimitOrderOperation {

    /**
     * Create a new limit order cancel operation. This order is used to cancel
     * an order. The balance will be returned to the owner.
     * 
     * @param owner
     *            The owner of the operation (see
     *            {@link #setOwner(AccountName)}).
     * @param orderId
     *            The id of the order to cancel (see
     *            {@link #setOrderId(UInteger)}).
     * @throws InvalidParameterException
     *             If the provided <code>owner</code> is null.
     */
    @JsonCreator
    public LimitOrderCancelOperation(@JsonProperty("owner") AccountName owner,
            @JsonProperty("orderid") UInteger orderId) {
        super(false);

        this.setOwner(owner);
        this.setOrderId(orderId);
    }

    /**
     * Like {@link #LimitOrderCancelOperation(AccountName, UInteger)}, but sets
     * the <code>orderId</code> to its default value (0).
     * 
     * @param owner
     *            The owner of the operation (see
     *            {@link #setOwner(AccountName)}).
     * @throws InvalidParameterException
     *             If the provided <code>owner</code> is null.
     */
    public LimitOrderCancelOperation(AccountName owner) {
        this(owner, UInteger.valueOf(0));
    }

    /**
     * Get the owner of the order that has been canceled.
     * 
     * @return The owner of the order that has been canceled.
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * Set the owner of the order that should be canceled. <b>Notice:</b> The
     * private active key of this account needs to be stored in the key storage.
     * 
     * @param owner
     *            The owner of the order that should be canceled.
     * @throws InvalidParameterException
     *             If the <code>owner</code> is null.
     */
    public void setOwner(AccountName owner) {
        this.owner = setIfNotNull(owner, "The provided owner can't be null.");
    }

    /**
     * Get the order id of the order that has been canceled.
     * 
     * @return The order id of the order that has been canceled.
     */
    public UInteger getOrderId() {
        return orderId;
    }

    /**
     * Set the order id of the order that should be canceled.
     * 
     * @param orderId
     *            The order id of the order that should be canceled.
     * @throws InvalidParameterException
     *             If the <code>orderId</code> is null.
     */
    public void setOrderId(UInteger orderId) {
        this.orderId = setIfNotNull(orderId, "The provided order id can't be null.");
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedLimitOrderCancelOperation = new ByteArrayOutputStream()) {
            serializedLimitOrderCancelOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.LIMIT_ORDER_CANCEL_OPERATION.getOrderId()));
            serializedLimitOrderCancelOperation.write(this.getOwner().toByteArray());
            serializedLimitOrderCancelOperation
                    .write(SteemJUtils.transformIntToByteArray(this.getOrderId().intValue()));

            return serializedLimitOrderCancelOperation.toByteArray();
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
        return;
    }
}
