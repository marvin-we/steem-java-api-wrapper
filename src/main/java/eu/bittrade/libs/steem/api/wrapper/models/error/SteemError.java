package eu.bittrade.libs.steem.api.wrapper.models.error;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemError {
    private int responseId;
    private SteemErrorDetails steemErrorDetails;

    @JsonProperty("id")
    public int getResponseId() {
        return responseId;
    }

    @JsonProperty("error")
    public SteemErrorDetails getSteemErrorDetails() {
        return steemErrorDetails;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
