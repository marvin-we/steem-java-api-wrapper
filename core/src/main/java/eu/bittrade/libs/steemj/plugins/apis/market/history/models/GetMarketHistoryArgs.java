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
