package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class OrderBook {
    private List<MarketOrder> asks;
    private List<MarketOrder> bids;

    public OrderBook(@JsonProperty("asks") List<MarketOrder> asks, @JsonProperty("bids") List<MarketOrder> bids) {
        this.asks = asks;
        this.bids = bids;
    }

    public List<MarketOrder> getAsks() {
        return asks;
    }

    public List<MarketOrder> getBids() {
        return bids;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
