package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object represents the Steem "scheduled_hardfork" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ScheduledHardfork {
    @JsonProperty("hf_version")
    private HardforkVersion hardforkVersion;
    @JsonProperty("live_time")
    private TimePointSec liveTime;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ScheduledHardfork() {
    }

    /**
     * @return The next expected Hardfork Version.
     */
    public HardforkVersion getHardforkVersion() {
        return hardforkVersion;
    }

    /**
     * @return The time when the next Hardfork is planned.
     */
    public TimePointSec getLiveTime() {
        return liveTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
