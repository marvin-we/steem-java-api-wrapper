package eu.bittrade.libs.steemj.plugins.apis.market.history.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class implements the Steem "get_ops_in_block_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetMarketHistoryReturn {
    @JsonProperty("buckets")
    private List<Bucket> buckets;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetMarketHistoryReturn() {
    }

    /**
     * @return the buckets
     */
    public List<Bucket> getBuckets() {
        return buckets;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
