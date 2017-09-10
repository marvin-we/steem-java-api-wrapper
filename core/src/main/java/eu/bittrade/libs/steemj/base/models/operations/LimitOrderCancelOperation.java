package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.annotations.SignatureRequired;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "limit_order_cancel_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCancelOperation extends Operation {
    @SignatureRequired(type = PrivateKeyType.ACTIVE)
    @JsonProperty("owner")
    private AccountName owner;
    // Original type is uint32_t so we use long here.
    @JsonProperty("orderid")
    private long orderId;

    /**
     * Create a new limit order cancel operation. This order is used to cancel
     * an order. The balance will be returned to the owner.
     */
    public LimitOrderCancelOperation() {
        super(false);
        // Set default values:
        this.setOrderId(0);
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
     */
    public void setOwner(AccountName owner) {
        this.owner = owner;
    }

    /**
     * Get the order id of the order that has been canceled.
     * 
     * @return The order id of the order that has been canceled.
     */
    public int getOrderId() {
        return (int) orderId;
    }

    /**
     * Set the order id of the order that should be canceled.
     * 
     * @param orderId
     *            The order id of the order that should be canceled.
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedLimitOrderCancelOperation = new ByteArrayOutputStream()) {
            serializedLimitOrderCancelOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.LIMIT_ORDER_CANCEL_OPERATION.ordinal()));
            serializedLimitOrderCancelOperation.write(this.getOwner().toByteArray());
            serializedLimitOrderCancelOperation.write(SteemJUtils.transformIntToByteArray(this.getOrderId()));

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
}
