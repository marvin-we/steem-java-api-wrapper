package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author http://steemit.com/@dez1337
 */
public class FeedHistory {
    private int id;
    @JsonProperty("current_median_history")
    private Price currentPrice;
    @JsonProperty("price_history")
    private List<Price> priceHistory;

    public FeedHistory(@JsonProperty("price_history") List<Price> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public int getId() {
        return id;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }

    public List<Price> getPriceHistory() {
        return priceHistory;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
