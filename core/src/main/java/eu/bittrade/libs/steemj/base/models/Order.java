package eu.bittrade.libs.steemj.base.models;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * This class represents a Steem "order" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Order implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    private TimePointSec created;
    @JsonProperty("order_price")
    private Price orderPrice;
    @JsonProperty("real_price")
    private double realPrice;
    // Original type is share_type while a share_type is a int64_t so we use
    // long here.
    private long steem;
    // Original type is share_type while a share_type is a int64_t so we use
    // long here.
    private long sbd;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Order() {
    }

    /**
     * @return the created
     */
    public TimePointSec getCreated() {
        return created;
    }

    /**
     * @return the orderPrice
     */
    public Price getOrderPrice() {
        return orderPrice;
    }

    /**
     * Get the SBD per Steem.
     * 
     * @return the realPrice
     */
    public double getRealPrice() {
        return realPrice;
    }

    /**
     * @return the steem
     */
    public long getSteem() {
        return steem;
    }

    /**
     * @return the sbd
     */
    public long getSbd() {
        return sbd;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
