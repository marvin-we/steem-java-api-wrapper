/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.plugins.apis.condenser.models.ExtendedLimitOrder;

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
