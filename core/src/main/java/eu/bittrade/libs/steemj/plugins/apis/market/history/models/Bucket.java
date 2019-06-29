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
package eu.bittrade.libs.steemj.plugins.apis.market.history.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;

/**
 * This class represents a Steem "bucket_object" object of the
 * "market_history_plugin".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Bucket {
    // Original type is "id_type".
    private long id;
    private TimePointSec open;
    // Original type is "uint32_t" so we use long here.
    private UInteger seconds;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("high_steem")
    private long highSteem;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("high_sbd")
    private long highSbd;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("low_steem")
    private long lowSteem;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("low_sbd")
    private long lowSbd;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("open_steem")
    private long openSteem;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("open_sbd")
    private long openSbd;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("close_steem")
    private long closeSteem;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("close_sbd")
    private long closeSbd;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("steem_volume")
    private long steemVolume;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("sbd_volume")
    private long sbdVolume;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected Bucket() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the open
     */
    public TimePointSec getOpen() {
        return open;
    }

    /**
     * @return the seconds
     */
    public UInteger getSeconds() {
        return seconds;
    }

    /**
     * @return the highSteem
     */
    public long getHighSteem() {
        return highSteem;
    }

    /**
     * @return the highSbd
     */
    public long getHighSbd() {
        return highSbd;
    }

    /**
     * @return the lowSteem
     */
    public long getLowSteem() {
        return lowSteem;
    }

    /**
     * @return the lowSbd
     */
    public long getLowSbd() {
        return lowSbd;
    }

    /**
     * @return the openSteem
     */
    public long getOpenSteem() {
        return openSteem;
    }

    /**
     * @return the openSbd
     */
    public long getOpenSbd() {
        return openSbd;
    }

    /**
     * @return the closeSteem
     */
    public long getCloseSteem() {
        return closeSteem;
    }

    /**
     * @return the closeSbd
     */
    public long getCloseSbd() {
        return closeSbd;
    }

    /**
     * @return the steemVolume
     */
    public long getSteemVolume() {
        return steemVolume;
    }

    /**
     * @return the sbdVolume
     */
    public long getSbdVolume() {
        return sbdVolume;
    }

    /**
     * Calculate the highest Price per Steem.
     * 
     * @return The highest Price per Steem.
     */
    public double calculateHigh() {
        return this.getHighSbd() / (double) this.getHighSteem();
    }

    /**
     * Calculate the lowest Price per Steem.
     * 
     * @return The lowest Price per Steem.
     */
    public double calculateLow() {
        return this.getLowSbd() / (double) this.getLowSteem();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
