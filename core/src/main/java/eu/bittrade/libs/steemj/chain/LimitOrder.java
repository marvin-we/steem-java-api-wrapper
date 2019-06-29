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
package eu.bittrade.libs.steemj.chain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Price;

/**
 * This class represents a Graphene Chain "limit_order_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrder {
    private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();

    @Override
    public Map<String, Object> _getter() {
        return _anyGetterSetterMap;
    }

    @Override
    public void _setter(String key, Object value) {
        _getter().put(key, value);
    }

    // Original type is "id_type".
    private long id;
    private TimePointSec created;
    private TimePointSec expiration;
    private AccountName seller;
    // Original type is share_type while a share_type is a int64_t so we use
    // long here.
    @JsonProperty("for_sale")
    private long forSale;
    @JsonProperty("orderid")
    private UInteger orderId;
    @JsonProperty("sell_price")
    private Price sellPrice;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected LimitOrder() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the created
     */
    public TimePointSec getCreated() {
        return created;
    }

    /**
     * @return the expiration
     */
    public TimePointSec getExpiration() {
        return expiration;
    }

    /**
     * @return the seller
     */
    public AccountName getSeller() {
        return seller;
    }

    /**
     * @return the forSale
     */
    public long getForSale() {
        return forSale;
    }

    /**
     * @return the orderId
     */
    public UInteger getOrderId() {
        return orderId;
    }

    /**
     * @return the sellPrice
     */
    public Price getSellPrice() {
        return sellPrice;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
