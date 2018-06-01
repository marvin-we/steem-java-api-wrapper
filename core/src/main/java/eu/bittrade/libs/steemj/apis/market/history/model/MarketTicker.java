package eu.bittrade.libs.steemj.apis.market.history.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * This class represents a Steem "market_ticker" object of the
 * "market_history_plugin".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class MarketTicker implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    private double latest;
    @JsonProperty("lowest_ask")
    private double lowestAsk;
    @JsonProperty("highest_bid")
    private double highestBid;
    @JsonProperty("percent_change")
    private double percentChange;
    @JsonProperty("steem_volume")
    private Asset steemVolume;
    @JsonProperty("sbd_volume")
    private Asset sbdVolume;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected MarketTicker() {
    }

    /**
     * @return the latest
     */
    public double getLatest() {
        return latest;
    }

    /**
     * @return the lowestAsk
     */
    public double getLowestAsk() {
        return lowestAsk;
    }

    /**
     * @return the highestBid
     */
    public double getHighestBid() {
        return highestBid;
    }

    /**
     * @return the percentChange
     */
    public double getPercentChange() {
        return percentChange;
    }

    /**
     * @return the steemVolume
     */
    public Asset getSteemVolume() {
        return steemVolume;
    }

    /**
     * @return the sbdVolume
     */
    public Asset getSbdVolume() {
        return sbdVolume;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
