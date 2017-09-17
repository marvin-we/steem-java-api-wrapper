package eu.bittrade.libs.steemj.base.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "feed_history_api_obj" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FeedHistory {
    private int id;
    @JsonProperty("current_median_history")
    private Price currentPrice;
    @JsonProperty("price_history")
    private List<Price> priceHistory;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private FeedHistory() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the currentPrice
     */
    public Price getCurrentPrice() {
        return currentPrice;
    }

    /**
     * @return the priceHistory
     */
    public List<Price> getPriceHistory() {
        return priceHistory;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
