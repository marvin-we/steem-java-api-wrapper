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
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.plugins.apis.witness.WitnessApi;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.GetAccountBandwidthArgs;

/**
 * This class implements the Steem "get_market_history_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetMarketHistoryArgs {
    @JsonProperty("bucket_seconds")
    private UInteger bucketSeconds;
    @JsonProperty("start")
    private TimePointSec start;
    @JsonProperty("end")
    private TimePointSec end;

    /**
     * Create a new {@link GetAccountBandwidthArgs} instance to be passed to the
     * {@link WitnessApi#getAccountBandwidth(CommunicationHandler, GetAccountBandwidthArgs)}
     * method.
     * 
     * @param bucketSeconds
     * @param start
     * @param end
     */
    @JsonCreator()
    public GetMarketHistoryArgs(@JsonProperty("bucket_seconds") UInteger bucketSeconds,
            @JsonProperty("start") TimePointSec start, @JsonProperty("end") TimePointSec end) {
        this.setBucketSeconds(bucketSeconds);
        this.setStart(start);
        this.setEnd(end);
    }

    /**
     * @return the bucketSeconds
     */
    public UInteger getBucketSeconds() {
        return bucketSeconds;
    }

    /**
     * @param bucketSeconds
     *            the bucketSeconds to set
     */
    public void setBucketSeconds(UInteger bucketSeconds) {
        this.bucketSeconds = bucketSeconds;
    }

    /**
     * @return the start
     */
    public TimePointSec getStart() {
        return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(TimePointSec start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public TimePointSec getEnd() {
        return end;
    }

    /**
     * @param end
     *            the end to set
     */
    public void setEnd(TimePointSec end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
