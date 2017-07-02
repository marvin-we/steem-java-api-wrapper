package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/steem_operations.hpp">Steem
 * limit_order_create2_operation</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreate2Operation extends Operation {
    private AccountName owner;
    // Original type is uint32 so we have to use long here.
    @JsonProperty("orderid")
    private long orderId;
    @JsonProperty("amount_to_sell")
    private Asset amountToSell;
    @JsonProperty("fill_or_kill")
    private Boolean fillOrKill;
    @JsonProperty("exchange_rate")
    private Price exchangeRate;
    @JsonProperty("expiration")
    private TimePointSec expirationDate;

    /**
     * Create a new limit order create operation. This operation is identical to
     * the
     * {@link eu.bittrade.libs.steemj.base.models.operations.LimitOrderCreateOperation
     * limit order create operation} except that it serializes the price rather
     * than calculating it from other fields.
     */
    public LimitOrderCreate2Operation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
        // Set default values.
        this.setOrderId(0);
        this.setFillOrKill(false);
        this.setExpirationDate(new TimePointSec(System.currentTimeMillis()));
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
        return (int) orderId;
    }

    /**
     * Set the id of this order. The only limitation for this id is that it has
     * to be free, meaning that there is no other open order with this id.
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
     * Was this order a fill or kill order?
     * 
     * @return true if this order was a fill or kill order.
     */
    public Boolean getFillOrKill() {
        return fillOrKill;
    }

    /**
     * Define if this order is a fill or kill order.
     * 
     * @param fillOrKill
     *            True if this order is a fill or kill order.
     */
    public void setFillOrKill(Boolean fillOrKill) {
        this.fillOrKill = fillOrKill;
    }

    /**
     * Get the exchange rate (price.base / price.quote) used for this order.
     * 
     * @return The exchange rate used for this order.
     */
    public Price getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Set the exchange rate (price.base / price.quote) used for this order.
     * 
     * @param exchangeRate
     *            The exchange rate used for this order.
     */
    public void setExchangeRate(Price exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * TODO:
     * 
     * @return the expirationDate
     */
    public TimePointSec getExpirationDate() {
        return expirationDate;
    }

    /**
     * TODO:
     * 
     * @param expirationDate
     *            the expirationDate to set
     */
    public void setExpirationDate(TimePointSec expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedLimitOrderCreate2Operation = new ByteArrayOutputStream()) {
            serializedLimitOrderCreate2Operation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.LIMIT_ORDER_CREATE2_OPERATION.ordinal()));
            serializedLimitOrderCreate2Operation.write(this.getOwner().toByteArray());
            serializedLimitOrderCreate2Operation.write(SteemJUtils.transformIntToByteArray(this.getOrderId()));
            serializedLimitOrderCreate2Operation.write(this.getAmountToSell().toByteArray());
            serializedLimitOrderCreate2Operation.write(this.getExchangeRate().toByteArray());
            serializedLimitOrderCreate2Operation.write(SteemJUtils.transformBooleanToByteArray(this.getFillOrKill()));
            serializedLimitOrderCreate2Operation.write(this.getExpirationDate().toByteArray());

            return serializedLimitOrderCreate2Operation.toByteArray();
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
