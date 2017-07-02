package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "order" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class MarketOrder {
    private TimePointSec created;
    @JsonProperty("order_price")
    private Price orderPrice;
    @JsonProperty("real_price")
    private double realPrice;
    private int steem;
    private int sbd;

    /**
     * @return the created
     */
    public TimePointSec getCreated() {
        return created;
    }

    /**
     * @return the orderPrice
     */
    public Price getOrderPrice() {
        return orderPrice;
    }

    /**
     * Get the SBD per Steem.
     * 
     * @return the realPrice
     */
    public double getRealPrice() {
        return realPrice;
    }

    /**
     * @return the steem
     */
    public int getSteem() {
        return steem;
    }

    /**
     * @return the sbd
     */
    public int getSbd() {
        return sbd;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
