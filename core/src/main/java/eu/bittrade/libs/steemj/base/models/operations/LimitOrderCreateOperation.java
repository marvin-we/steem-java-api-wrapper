package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/steem_operations.hpp">Steem
 * limit_order_create_operation</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreateOperation extends AbstractLimitOrderOperation {
    @JsonProperty("amount_to_sell")
    private Asset amountToSell;
    @JsonProperty("min_to_receive")
    private Asset minToReceive;
    @JsonProperty("fill_or_kill")
    private boolean fillOrKill;
    @JsonProperty("expiration")
    private TimePointSec expirationDate;

    /**
     * Create a new limit order operation.
     * 
     * @param owner
     *            The account to create the operation for (see
     *            {@link #setOwner(AccountName)}).
     * @param orderId
     *            The id of this order (see {@link #setOrderId(UInteger)}).
     * @param amountToSell
     *            The amount to sell (see {@link #setAmountToSell(Asset)}).
     * @param minToReceive
     *            The minimal amount to receive for the offered asset (see
     *            {@link #setMinToReceive(Asset)}).
     * @param fillOrKill
     *            Define if this order is a fill or kill order (see
     *            {@link #setFillOrKill(boolean)}).
     * @param expirationDate
     *            Define how long this order is valid (see
     *            {@link #setExpirationDate(TimePointSec)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public LimitOrderCreateOperation(@JsonProperty("owner") AccountName owner,
            @JsonProperty("orderid") UInteger orderId, @JsonProperty("amount_to_sell") Asset amountToSell,
            @JsonProperty("min_to_receive") Asset minToReceive, @JsonProperty("fill_or_kill") boolean fillOrKill,
            @JsonProperty("expiration") TimePointSec expirationDate) {
        super(false);

        this.setOwner(owner);
        this.setOrderId(orderId);
        this.setAmountToSell(amountToSell);
        this.setMinToReceive(minToReceive);
        this.setFillOrKill(fillOrKill);
        this.setExpirationDate(expirationDate);
    }

    /**
     * Like
     * {@link #LimitOrderCreateOperation(AccountName, UInteger, Asset, Asset)},
     * but this constructor applies default values for the
     * <code>fillOrKill</code> and the <code>expirationDate</code> parameters.
     * The <code>fillOrKill</code> parameter is set to false and the
     * <code>expirationDate</code> to the latest possible date, so that it will
     * never expire.
     * 
     * @param owner
     *            The account to create the operation for (see
     *            {@link #setOwner(AccountName)}).
     * @param orderId
     *            The id of this order (see {@link #setOrderId(UInteger)}).
     * @param amountToSell
     *            The amount to sell (see {@link #setAmountToSell(Asset)}).
     * @param minToReceive
     *            The minimal amount to receive for the offered asset (see
     *            {@link #setMinToReceive(Asset)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public LimitOrderCreateOperation(AccountName owner, UInteger orderId, Asset amountToSell, Asset minToReceive) {
        this(owner, orderId, amountToSell, minToReceive, false, new TimePointSec(Long.MAX_VALUE));
    }

    /**
     * Like
     * {@link #LimitOrderCreateOperation(AccountName, UInteger, Asset, Asset)},
     * but also sets the <code>orderId</code> to its default value (0).
     * 
     * @param owner
     *            The account to create the operation for (see
     *            {@link #setOwner(AccountName)}).
     * @param amountToSell
     *            The amount to sell (see {@link #setAmountToSell(Asset)}).
     * @param minToReceive
     *            The minimal amount to receive for the offered asset (see
     *            {@link #setMinToReceive(Asset)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public LimitOrderCreateOperation(AccountName owner, Asset amountToSell, Asset minToReceive) {
        this(owner, UInteger.valueOf(0), amountToSell, minToReceive, false, new TimePointSec(Long.MAX_VALUE));
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
     * Set the account to create the order for. <b>Notice:</b> The private
     * active key of this account needs to be stored in the key storage.
     * 
     * @param owner
     *            The account to create the order for.
     * @throws InvalidParameterException
     *             If the <code>owner</code> is null.
     */
    public void setOwner(AccountName owner) {
        this.owner = setIfNotNull(owner, "The provided owner can't be null.");
    }

    /**
     * Get the id of this order.
     * 
     * @return The id of this order.
     */
    public UInteger getOrderId() {
        return orderId;
    }

    /**
     * Set the id of this order. The only limitation for this id is that it has
     * to be free, meaning that there is no other open order with this id.
     * 
     * @param orderId
     *            The id of this order.
     * @throws InvalidParameterException
     *             If the <code>orderId</code> is null.
     */
    public void setOrderId(UInteger orderId) {
        this.orderId = setIfNotNull(orderId, "The provided order id can't be null.");
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
     * @throws InvalidParameterException
     *             If the <code>amountToSell</code> is null.
     */
    public void setAmountToSell(Asset amountToSell) {
        this.amountToSell = setIfNotNull(amountToSell, "The amount to sell owner can't be null.");
    }

    /**
     * Get the amount that the owner has received.
     * 
     * @return The amount that the owner has received.
     */
    public Asset getMinToReceive() {
        return minToReceive;
    }

    /**
     * Set the amount that should be received for the asset that will be sold.
     * 
     * @param minToReceive
     *            The amount that should be received for the asset that will be
     *            sold.
     * @throws InvalidParameterException
     *             If the <code>minToReceive</code> is null.
     */
    public void setMinToReceive(Asset minToReceive) {
        this.minToReceive = setIfNotNull(minToReceive, "The min to receive owner can't be null.");
    }

    /**
     * Get the information if this order was a "fill or kill" order. A "fill or
     * kill" is an option that can be added to limit order. If set to
     * <code>true</code>, the order will be automatically removed, if the order
     * can't be fulfilled immediately.
     * 
     * @return <code>true</code> if this order was a fill or kill order,
     *         otherwise <code>false</code>.
     */
    public boolean getFillOrKill() {
        return fillOrKill;
    }

    /**
     * Define if this order was a "fill or kill" order. A "fill or kill" is an
     * option that can be added to limit order. If set to <code>true</code>, the
     * order will be automatically removed, if the order can't be fulfilled
     * Immediately.
     * 
     * @param fillOrKill
     *            <code>true</code> if this order is a fill or kill order,
     *            otherwise <code>false</code>.
     */
    public void setFillOrKill(boolean fillOrKill) {
        this.fillOrKill = fillOrKill;
    }

    /**
     * Get the expiration date for this order.
     * 
     * @return The expiration date of this order.
     */
    public TimePointSec getExpirationDate() {
        return expirationDate;
    }

    /**
     * Set the expiration date for this order.
     * 
     * @param expirationDate
     *            The expiration date to set.
     */
    public void setExpirationDate(TimePointSec expirationDate) {
        if (expirationDate == null) {
            this.expirationDate = new TimePointSec(Long.MAX_VALUE);
        } else {
            this.expirationDate = expirationDate;
        }
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedLimitOrderCreateOperation = new ByteArrayOutputStream()) {
            serializedLimitOrderCreateOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.LIMIT_ORDER_CREATE_OPERATION.getOrderId()));
            serializedLimitOrderCreateOperation.write(this.getOwner().toByteArray());
            serializedLimitOrderCreateOperation
                    .write(SteemJUtils.transformIntToByteArray(this.getOrderId().intValue()));
            serializedLimitOrderCreateOperation.write(this.getAmountToSell().toByteArray());
            serializedLimitOrderCreateOperation.write(this.getMinToReceive().toByteArray());
            serializedLimitOrderCreateOperation.write(SteemJUtils.transformBooleanToByteArray(this.getFillOrKill()));
            serializedLimitOrderCreateOperation.write(this.getExpirationDate().toByteArray());

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

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)
                && !ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)
                && (!SteemJConfig.getInstance().getTokenSymbol().equals(amountToSell)
                        && SteemJConfig.getInstance().getDollarSymbol().equals(minToReceive))
                || (SteemJConfig.getInstance().getDollarSymbol().equals(amountToSell)
                        && SteemJConfig.getInstance().getTokenSymbol().equals(minToReceive))) {
            // TODO: (amount_to_sell / min_to_receive).validate();
            throw new InvalidParameterException("Limit order must be for the STEEM:SBD market.");
        }
    }
}
