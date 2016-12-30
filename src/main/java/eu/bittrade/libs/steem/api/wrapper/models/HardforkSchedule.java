package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author http://steemit.com/@dez1337
 */
public class HardforkSchedule {
    @JsonProperty("hf_version")
    private String hardforkVersion;
    @JsonProperty("live_time")
    private String liveTime;

    public String getHardforkVersion() {
        return hardforkVersion;
    }

    public String getLiveTime() {
        return liveTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
