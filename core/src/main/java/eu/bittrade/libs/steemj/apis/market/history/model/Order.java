package eu.bittrade.libs.steemj.apis.market.history.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "order" object of the "market_history_plugin".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Order {
    private double price;
    // Original type is "share_type" which is a "safe<int64_t>".
    private long steem;
    // Original type is "share_type" which is a "safe<int64_t>".
    private long sbd;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected Order() {
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the steem
     */
    public long getSteem() {
        return steem;
    }

    /**
     * @return the sbd
     */
    public long getSbd() {
        return sbd;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
