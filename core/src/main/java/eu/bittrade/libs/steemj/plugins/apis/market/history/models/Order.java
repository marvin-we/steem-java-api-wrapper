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
package eu.bittrade.libs.steemj.plugins.apis.market.history.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.Price;

/**
 * This class represents a Steem "order" object of the "market_history_plugin".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Order {
    @JsonProperty("order_price")
    private Price orderPrice;
    @JsonProperty("real_price")
    private double realPrice;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("steem")
    private long steem;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("sbd")
    private long sbd;
    @JsonProperty("created")
    private TimePointSec created;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Order() {
    }

    /**
     * @return the orderPrice
     */
    public Price getOrderPrice() {
        return orderPrice;
    }

    /**
     * @param orderPrice
     *            the orderPrice to set
     */
    public void setOrderPrice(Price orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * @return the realPrice
     */
    public double getRealPrice() {
        return realPrice;
    }

    /**
     * @param realPrice
     *            the realPrice to set
     */
    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    /**
     * @return the steem
     */
    public long getSteem() {
        return steem;
    }

    /**
     * @param steem
     *            the steem to set
     */
    public void setSteem(long steem) {
        this.steem = steem;
    }

    /**
     * @return the sbd
     */
    public long getSbd() {
        return sbd;
    }

    /**
     * @param sbd
     *            the sbd to set
     */
    public void setSbd(long sbd) {
        this.sbd = sbd;
    }

    /**
     * @return the created
     */
    public TimePointSec getCreated() {
        return created;
    }

    /**
     * @param created
     *            the created to set
     */
    public void setCreated(TimePointSec created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
