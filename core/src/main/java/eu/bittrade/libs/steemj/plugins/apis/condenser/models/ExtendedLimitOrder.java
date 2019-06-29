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
package eu.bittrade.libs.steemj.plugins.apis.condenser.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.chain.LimitOrder;

/**
 * This class represents a Steem "extended_limit_order" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ExtendedLimitOrder extends LimitOrder {
    @JsonProperty("real_price")
    private double realPrice;
    @JsonProperty("rewarded")
    private boolean rewarded;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ExtendedLimitOrder() {
        this.realPrice = 0.0;
        this.rewarded = false;
    }

    /**
     * @return the realPrice
     */
    public double getRealPrice() {
        return realPrice;
    }

    /**
     * @return the rewarded
     */
    public boolean isRewarded() {
        return rewarded;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
