package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.util.SteemUtils;

/**
 * This class represents the Steem "limit_order_cancel_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCancelOperation extends Operation {
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
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
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
     * Set the owner of the order that should be canceled.
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
                    SteemUtils.transformIntToVarIntByteArray(OperationType.LIMIT_ORDER_CANCEL_OPERATION.ordinal()));
            serializedLimitOrderCancelOperation.write(this.getOwner().toByteArray());
            serializedLimitOrderCancelOperation.write(SteemUtils.transformIntToByteArray(this.getOrderId()));

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
