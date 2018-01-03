package eu.bittrade.libs.steemj.plugins.apis.market.history.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "get_order_book_return" object of the
 * "market_history_plugin".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetOrderBookReturn {
    private List<Order> bids;
    private List<Order> asks;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected GetOrderBookReturn() {
    }

    /**
     * @return the bids
     */
    public List<Order> getBids() {
        return bids;
    }

    /**
     * @return the asks
     */
    public List<Order> getAsks() {
        return asks;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
