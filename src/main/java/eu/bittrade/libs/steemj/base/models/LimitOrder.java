package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Graphene Chain "limit_order" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrder {
    private int id;
    private TimePointSec created;
    private TimePointSec expiration;
    private AccountName seller;
    // TODO: Original type is share_type.
    @JsonProperty("for_sale")
    private int forSale;
    @JsonProperty("orderid")
    private long orderId;
    @JsonProperty("sell_price")
    private Price sellPrice;
    // TODO: Original type is share_type.
    @JsonProperty("deferred_fee")
    private int deferredFee;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the created
     */
    public TimePointSec getCreated() {
        return created;
    }

    /**
     * @return the expiration
     */
    public TimePointSec getExpiration() {
        return expiration;
    }

    /**
     * @return the seller
     */
    public AccountName getSeller() {
        return seller;
    }

    /**
     * @return the forSale
     */
    public int getForSale() {
        return forSale;
    }

    /**
     * @return the orderId
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * @return the sellPrice
     */
    public Price getSellPrice() {
        return sellPrice;
    }

    /**
     * @return the deferred_fee
     */
    public int getDeferredFee() {
        return deferredFee;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
