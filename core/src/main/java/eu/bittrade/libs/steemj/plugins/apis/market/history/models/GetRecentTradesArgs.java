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

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.plugins.apis.witness.WitnessApi;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.GetAccountBandwidthArgs;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class implements the Steem "get_recent_trades_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetRecentTradesArgs {
    @JsonProperty("limit")
    private UInteger limit;

    /**
     * Create a new {@link GetAccountBandwidthArgs} instance to be passed to the
     * {@link WitnessApi#getAccountBandwidth(CommunicationHandler, GetAccountBandwidthArgs)}
     * method.
     * 
     * @param limit
     * 
     */
    @JsonCreator()
    public GetRecentTradesArgs(@Nullable @JsonProperty("limit") UInteger limit) {
        this.setLimit(limit);
    }

    /**
     * @return the limit
     */
    public UInteger getLimit() {
        return limit;
    }

    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(@Nullable UInteger limit) {
        this.limit = SteemJUtils.setIfNotNull(limit, UInteger.valueOf(500));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
