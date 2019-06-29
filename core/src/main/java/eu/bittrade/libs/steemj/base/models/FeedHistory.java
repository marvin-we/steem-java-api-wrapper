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
package eu.bittrade.libs.steemj.base.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.Price;

/**
 * This class represents a Steem "feed_history_api_obj" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FeedHistory {
    private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();

    @Override
    public Map<String, Object> _getter() {
        return _anyGetterSetterMap;
    }

    @Override
    public void _setter(String key, Object value) {
        _getter().put(key, value);
    }

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
