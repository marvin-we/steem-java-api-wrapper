package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/steem_operations.hpp">Steem
 * limit_order_create object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreateOperation extends Operation {
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
    // TODO: Type should be long for byte transformation.
    @JsonProperty("expiration")
    private Date expiration;

    /**
     * Create a new limit order operation.
     */
    public LimitOrderCreateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
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
    public long getOrderId() {
        return orderId;
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
    public Boolean getFillOrKill() {
        return fillOrKill;
    }

    /**
     * 
     * @param fillOrKill
     */
    public void setFillOrKill(Boolean fillOrKill) {
        this.fillOrKill = fillOrKill;
    }

    /**
     * 
     * @return
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * Define how long this order is valid. The date has to be specified
     * as String and needs a special format: yyyy-MM-dd'T'HH:mm:ss
     * 
     * <p>
     * Example: "2016-08-08T12:24:17"
     * </p>
     * 
     * If not set the current time plus the maximal allowed offset is used by
     * default.
     * 
     * @param expirationDate
     *            The expiration date as its String representation.
     * @throws ParseException
     *             If the given String does not patch the pattern.
     */
    public void setExpirationDate(String expirationDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                SteemApiWrapperConfig.getInstance().getDateTimePattern());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(SteemApiWrapperConfig.getInstance().getTimeZoneId()));
        calendar.setTime(simpleDateFormat.parse(expirationDate + SteemApiWrapperConfig.getInstance().getTimeZoneId()));
        // TODO: this.expiration = calendar.getTimeInMillis();
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        byte[] serializedLimitOrderCreateOperation = {};

        return serializedLimitOrderCreateOperation;
    }
}
