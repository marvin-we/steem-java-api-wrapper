package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedHistory {
    private int id;
    @JsonProperty("current_median_history")
    private MedianHistoryPrice currentPrice;
    @JsonProperty("price_history")
    private List<MedianHistoryPrice> priceHistory;

    public FeedHistory(@JsonProperty("price_history") List<MedianHistoryPrice> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public int getId() {
        return id;
    }

    public MedianHistoryPrice getCurrentPrice() {
        return currentPrice;
    }

    public List<MedianHistoryPrice> getPriceHistory() {
        return priceHistory;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
