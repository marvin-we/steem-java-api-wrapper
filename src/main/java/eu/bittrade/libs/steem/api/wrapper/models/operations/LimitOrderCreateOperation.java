package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.interfaces.Expirable;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.util.SteemUtils;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/steem_operations.hpp">Steem
 * limit_order_create_operation</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreateOperation extends Operation implements Expirable {
    @JsonProperty("owner")
    private AccountName owner;
    @JsonProperty("orderid")
    // Type is uint32 in the original code, but has to be long here as Java does
    // not support unsigned numbers accurate.
    private long orderId;
    @JsonProperty("amount_to_sell")
    private Asset amountToSell;
    @JsonProperty("min_to_receive")
    private Asset minToReceive;
    @JsonProperty("fill_or_kill")
    private Boolean fillOrKill;
    @JsonProperty("expiration")
    private long expirationDate;

    /**
     * Create a new limit order operation.
     */
    public LimitOrderCreateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
    }

    /**
     * Get the account the order should be created for.
     * 
     * @return The account to create the order for.
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * Get the account to create the order for.
     * 
     * @param owner
     *            The account to create the order for.
     */
    public void setOwner(AccountName owner) {
        this.owner = owner;
    }

    /**
     * Get the id of this order.
     * 
     * @return The id of this order.
     */
    public int getOrderId() {
        return (int)orderId;
    }

    /**
     * Set the id of this order.
     * 
     * @param orderId
     *            The id of this order.
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * Get the amount to sell within this order.
     * 
     * @return The amount to sell within this order.
     */
    public Asset getAmountToSell() {
        return amountToSell;
    }

    /**
     * Set the amount to sell within this order.
     * 
     * @param amountToSell
     *            The amount to sell within this order.
     */
    public void setAmountToSell(Asset amountToSell) {
        this.amountToSell = amountToSell;
    }

    /**
     * 
     * @return
     */
    public Asset getMinToReceive() {
        return minToReceive;
    }

    /**
     * 
     * @param minToReceive
     */
    public void setMinToReceive(Asset minToReceive) {
        this.minToReceive = minToReceive;
    }

    /**
     * 
     * @return
     */
    public Boolean getFillOrKillAsBoolean() {
        return fillOrKill;
    }

    /**
     * 
     * @param fillOrKill
     */
    public void setFillOrKill(Boolean fillOrKill) {
        this.fillOrKill = fillOrKill;
    }

    @Override
    public void setExpirationDate(String expirationDate) throws ParseException {
        this.setExpirationDate(SteemUtils.transformStringToTimestamp(expirationDate));
    }

    @Override
    public String getExpirationDate() {
        return SteemUtils.transformDateToString(this.getExpirationDateAsDate());
    }

    @Override
    public Date getExpirationDateAsDate() {
        return new Date(expirationDate);
    }

    @Override
    public int getExpirationDateAsInt() {
        return (int) this.expirationDate;
    }

    @Override
    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedLimitOrderCreateOperation = new ByteArrayOutputStream()) {
            serializedLimitOrderCreateOperation.write(
                    SteemUtils.transformIntToVarIntByteArray(OperationType.LIMIT_ORDER_CREATE_OPERATION.ordinal()));
            serializedLimitOrderCreateOperation.write(this.getOwner().toByteArray());
            serializedLimitOrderCreateOperation.write(SteemUtils.transformIntToByteArray(this.getOrderId()));
            serializedLimitOrderCreateOperation.write(this.getAmountToSell().toByteArray());
            serializedLimitOrderCreateOperation.write(this.getMinToReceive().toByteArray());
            serializedLimitOrderCreateOperation.write(SteemUtils.transformBooleanToByteArray(this.getFillOrKillAsBoolean()));
            serializedLimitOrderCreateOperation
                    .write(SteemUtils.transformIntToByteArray(this.getExpirationDateAsInt()));

            return serializedLimitOrderCreateOperation.toByteArray();
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
