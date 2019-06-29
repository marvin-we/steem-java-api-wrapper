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
 *     along with SteemJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.protocol.Price;

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
