package eu.bittrade.libs.steemj.plugins.apis.market.history.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class implements the Steem "get_trade_history_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetTradeHistoryReturn {
    @JsonProperty("trades")
    private List<MarketTrade> trades;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetTradeHistoryReturn() {
    }

    /**
     * @return the trades
     */
    public List<MarketTrade> getTrades() {
        return trades;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
