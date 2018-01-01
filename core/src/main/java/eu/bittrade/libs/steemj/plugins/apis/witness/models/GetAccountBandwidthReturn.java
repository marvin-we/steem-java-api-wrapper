package eu.bittrade.libs.steemj.plugins.apis.witness.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

/**
 * This class implements the Steem "get_account_bandwidth_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetAccountBandwidthReturn {
    @JsonProperty("bandwidth")
    private Optional<AccountBandwidth> bandwidth;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetAccountBandwidthReturn() {
    }

    /**
     * @return the bandwidth
     */
    public Optional<AccountBandwidth> getBandwidth() {
        return bandwidth;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
