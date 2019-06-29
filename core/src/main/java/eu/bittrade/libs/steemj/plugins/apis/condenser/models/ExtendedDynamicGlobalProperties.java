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

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;
import org.joou.ULong;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.plugins.apis.database.models.DynamicGlobalProperty;

/**
 * This class represents a Steem "extended_dynamic_global_properties" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ExtendedDynamicGlobalProperties extends DynamicGlobalProperty {
    @JsonProperty("average_block_size")
    private UInteger avarageBlockSize;
    @JsonProperty("current_reserve_ratio")
    private ULong currentReserveRatio;
    // Original type is "uint128_t".
    @JsonProperty("max_virtual_bandwidth")
    private BigInteger maxVirtualBandwidth;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ExtendedDynamicGlobalProperties() {
        this.avarageBlockSize = UInteger.valueOf(0);
        this.currentReserveRatio = ULong.valueOf(1);
        this.maxVirtualBandwidth = BigInteger.valueOf(0);
    }

    /**
     * @return the avarageBlockSize
     */
    public UInteger getAvarageBlockSize() {
        return avarageBlockSize;
    }

    /**
     * @return the currentReserveRatio
     */
    public ULong getCurrentReserveRatio() {
        return currentReserveRatio;
    }

    /**
     * @return The maximum bandwidth available for all Steem users.
     */
    public BigInteger getMaxVirtualBandwidth() {
        return maxVirtualBandwidth;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
