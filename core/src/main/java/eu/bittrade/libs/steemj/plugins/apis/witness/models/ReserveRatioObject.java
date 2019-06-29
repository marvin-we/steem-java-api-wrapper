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

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the Steem "reserve_ratio_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ReserveRatioObject {
    // Original type is "id_type".
    @JsonProperty("id")
    private long id;
    /**
     * Average block size is updated every block to be:
     *
     * average_block_size = (99 * average_block_size + new_block_size) / 100
     *
     * This property is used to update the current_reserve_ratio to maintain
     * approximately 50% or less utilization of network capacity.
     */
    // Original type is "int32_t".
    @JsonProperty("average_block_size")
    private int averageBlockSize;

    /**
     * Any time average_block_size <= 50% maximum_block_size this value grows by
     * 1 until it reaches STEEM_MAX_RESERVE_RATIO. Any time average_block_size
     * is greater than 50% it falls by 1%. Upward adjustments happen once per
     * round, downward adjustments happen every block.
     */
    // Original type is "int64_t".
    @JsonProperty("current_reserve_ratio")
    private long currentReserveRatio;

    /**
     * The maximum bandwidth the blockchain can support is:
     *
     * max_bandwidth = maximum_block_size *
     * STEEM_BANDWIDTH_AVERAGE_WINDOW_SECONDS / STEEM_BLOCK_INTERVAL
     *
     * The maximum virtual bandwidth is:
     *
     * max_bandwidth * current_reserve_ratio
     */
    // Original type is "uint128_t".
    @JsonProperty("max_virtual_bandwidth")
    private BigInteger maxVirtualBandwidth;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ReserveRatioObject() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the averageBlockSize
     */
    public int getAverageBlockSize() {
        return averageBlockSize;
    }

    /**
     * @return the currentReserveRatio
     */
    public long getCurrentReserveRatio() {
        return currentReserveRatio;
    }

    /**
     * @return the maxVirtualBandwidth
     */
    public BigInteger getMaxVirtualBandwidth() {
        return maxVirtualBandwidth;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
