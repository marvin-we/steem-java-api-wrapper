package eu.bittrade.libs.steemj.apis.market.history.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * This class represents a Steem "order_book" object of the "market_volume".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class MarketVolume implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    @JsonProperty("steem_volume")
    private Asset steemVolume;
    @JsonProperty("sbd_volume")
    private Asset sbdVolume;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected MarketVolume() {
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
