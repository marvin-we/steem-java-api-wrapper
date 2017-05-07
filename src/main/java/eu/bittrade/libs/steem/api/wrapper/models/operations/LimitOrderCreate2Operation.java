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
import eu.bittrade.libs.steem.api.wrapper.models.Price;
import eu.bittrade.libs.steem.api.wrapper.util.SteemUtils;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/steem_operations.hpp">Steem
 * limit_order_create2_operation</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreate2Operation extends Operation implements Expirable {
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
    private long expirationDate;

    /**
     * Create a new limit order create operation. This operation is identical to
     * the
     * {@link eu.bittrade.libs.steem.api.wrapper.models.operations.LimitOrderCreateOperation
     * limit order create operation} except that it serializes the price rather
     * than calculating it from other fields.
     */
    public LimitOrderCreate2Operation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
    }

    /**
     * @return the owner
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(AccountName owner) {
        this.owner = owner;
    }

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return (int)orderId;
    }

    /**
     * @param orderId
     *            the orderId to set
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the amountToSell
     */
    public Asset getAmountToSell() {
        return amountToSell;
    }

    /**
     * @param amountToSell
     *            the amountToSell to set
     */
    public void setAmountToSell(Asset amountToSell) {
        this.amountToSell = amountToSell;
    }

    /**
     * @return the fillOrKill
     */
    public Boolean getFillOrKill() {
        return fillOrKill;
    }

    /**
     * @param fillOrKill
     *            the fillOrKill to set
     */
    public void setFillOrKill(Boolean fillOrKill) {
        this.fillOrKill = fillOrKill;
    }

    /**
     * @return the exchangeRate
     */
    public Price getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate
     *            the exchangeRate to set
     */
    public void setExchangeRate(Price exchangeRate) {
        this.exchangeRate = exchangeRate;
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
        try (ByteArrayOutputStream serializedLimitOrderCreate2Operation = new ByteArrayOutputStream()) {
            serializedLimitOrderCreate2Operation.write(
                    SteemUtils.transformIntToVarIntByteArray(OperationType.LIMIT_ORDER_CREATE2_OPERATION.ordinal()));
            serializedLimitOrderCreate2Operation.write(this.getOwner().toByteArray());
            serializedLimitOrderCreate2Operation.write(SteemUtils.transformIntToByteArray(this.getOrderId()));
            serializedLimitOrderCreate2Operation.write(this.getAmountToSell().toByteArray());
            serializedLimitOrderCreate2Operation.write(SteemUtils.transformBooleanToByteArray(this.getFillOrKill()));
            serializedLimitOrderCreate2Operation
                    .write(SteemUtils.transformIntToByteArray(this.getExpirationDateAsInt()));

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
