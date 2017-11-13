package eu.bittrade.libs.steemj.apis.database.models.state;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.ExtendedLimitOrder;

/**
 * This class represents a Steem "market" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Market {
    @JsonProperty("bids")
    private List<ExtendedLimitOrder> bids;
    @JsonProperty("asks")
    private List<ExtendedLimitOrder> asks;
    @JsonProperty("history")
    private List<OrderHistoryItem> history;
    @JsonProperty("available_candlesticks")
    private List<Integer> availableCandlesticks;
    @JsonProperty("available_zoom")
    private List<Integer> availableZoom;
    @JsonProperty("current_candlestick")
    private int currentCandlestick;
    @JsonProperty("current_zoom")
    private int currentZoom;
    @JsonProperty("price_history")
    List<CandleStick> priceHistory;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected Market() {
    }

    /**
     * @return the bids
     */
    public List<ExtendedLimitOrder> getBids() {
        return bids;
    }

    /**
     * @return the asks
     */
    public List<ExtendedLimitOrder> getAsks() {
        return asks;
    }

    /**
     * @return the history
     */
    public List<OrderHistoryItem> getHistory() {
        return history;
    }

    /**
     * @return the availableCandlesticks
     */
    public List<Integer> getAvailableCandlesticks() {
        return availableCandlesticks;
    }

    /**
     * @return the availableZoom
     */
    public List<Integer> getAvailableZoom() {
        return availableZoom;
    }

    /**
     * @return the currentCandlestick
     */
    public int getCurrentCandlestick() {
        return currentCandlestick;
    }

    /**
     * @return the currentZoom
     */
    public int getCurrentZoom() {
        return currentZoom;
    }

    /**
     * @return the priceHistory
     */
    public List<CandleStick> getPriceHistory() {
        return priceHistory;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
