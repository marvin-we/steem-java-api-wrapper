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
package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Graphene Chain "limit_order" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrder {
    private int id;
    private TimePointSec created;
    private TimePointSec expiration;
    private AccountName seller;
    // Original type is share_type while a share_type is a int64_t so we use
    // long here.
    @JsonProperty("for_sale")
    private long forSale;
    @JsonProperty("orderid")
    private long orderId;
    @JsonProperty("sell_price")
    private Price sellPrice;
    // Original type is share_type while a share_type is a int64_t so we use
    // long here.
    @JsonProperty("deferred_fee")
    private long deferredFee;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected LimitOrder() {
    }

    /**
     * @return the id
     */
    public int getId() {
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
    public long getOrderId() {
        return orderId;
    }

    /**
     * @return the sellPrice
     */
    public Price getSellPrice() {
        return sellPrice;
    }

    /**
     * @return the deferred_fee
     */
    public long getDeferredFee() {
        return deferredFee;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
