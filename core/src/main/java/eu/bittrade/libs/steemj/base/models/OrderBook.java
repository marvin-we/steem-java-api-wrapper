package eu.bittrade.libs.steemj.base.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "order_book" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class OrderBook {
    private List<Order> asks;
    private List<Order> bids;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private OrderBook() {
    }

    /**
     * @return A list of open buy
     *         {@link eu.bittrade.libs.steemj.base.models.Order Order}s of the
     *         internal market.
     */
    public List<Order> getAsks() {
        return asks;
    }

    /**
     * @return A list of open sell
     *         {@link eu.bittrade.libs.steemj.base.models.Order Order}s of the
     *         internal market.
     */
    public List<Order> getBids() {
        return bids;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
