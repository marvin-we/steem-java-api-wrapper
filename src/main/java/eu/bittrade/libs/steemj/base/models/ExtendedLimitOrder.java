package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "extended_limit_order" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ExtendedLimitOrder extends LimitOrder {
    @JsonProperty("real_price")
    private double realPrice;
    private boolean rewarded;

    public ExtendedLimitOrder() {
        this.realPrice = 0.0;
        this.rewarded = false;
    }

    /**
     * @return the realPrice
     */
    public double getRealPrice() {
        return realPrice;
    }

    /**
     * @return the rewarded
     */
    public boolean isRewarded() {
        return rewarded;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
