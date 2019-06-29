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
package eu.bittrade.libs.steemj.plugins.apis.witness.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.plugins.apis.witness.enums.BandwidthType;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents the Steem "api_account_bandwidth_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountBandwidth {
    // Original type is "id_type".
    @JsonProperty("id")
    private long id;
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("type")
    private BandwidthType type;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("average_bandwidth")
    private long averageBandwidth;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("lifetime_bandwidth")
    private long lifetimeBandwidth;
    @JsonProperty("last_bandwidth_update")
    private TimePointSec lastBandwidthUpdate;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private AccountBandwidth() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * @return the type
     */
    public BandwidthType getType() {
        return type;
    }

    /**
     * @return the averageBandwidth
     */
    public long getAverageBandwidth() {
        return averageBandwidth;
    }

    /**
     * @return the lifetimeBandwidth
     */
    public long getLifetimeBandwidth() {
        return lifetimeBandwidth;
    }

    /**
     * @return the lastBandwidthUpdate
     */
    public TimePointSec getLastBandwidthUpdate() {
        return lastBandwidthUpdate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
