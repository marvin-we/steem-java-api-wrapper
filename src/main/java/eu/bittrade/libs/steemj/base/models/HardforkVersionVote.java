package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is the java implementation of the Steem "hardfork_version_vote"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class HardforkVersionVote {
    @JsonProperty("hf_version")
    protected String hfVersion;
    @JsonProperty("hf_time")
    protected TimePointSec hfTime;

    /**
     * @return the hfVersion
     */
    public String getHfVersion() {
        return hfVersion;
    }

    /**
     * @param hfVersion
     *            the hfVersion to set
     */
    public void setHfVersion(String hfVersion) {
        this.hfVersion = hfVersion;
    }

    /**
     * @return the hfTime
     */
    public TimePointSec getHfTime() {
        return hfTime;
    }

    /**
     * @param hfTime
     *            the hfTime to set
     */
    public void setHfTime(TimePointSec hfTime) {
        this.hfTime = hfTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
