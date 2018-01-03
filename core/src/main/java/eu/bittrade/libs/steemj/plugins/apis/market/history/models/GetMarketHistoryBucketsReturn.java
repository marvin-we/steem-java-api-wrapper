package eu.bittrade.libs.steemj.plugins.apis.market.history.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.plugins.apis.market.history.MarketHistoryApi;

/**
 * This class implements the Steem "get_market_history_buckets_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetMarketHistoryBucketsReturn {
    @JsonProperty("bucket_sizes")
    private List<UInteger> bucketSizes;

    /**
     * Create a new {@link GetMarketHistoryBucketsReturn} instance to be passed
     * to the
     * {@link MarketHistoryApi#getMarketHistoryBuckets(CommunicationHandler)}
     * method.
     * 
     * @param bucketSizes
     */
    @JsonCreator()
    public GetMarketHistoryBucketsReturn(@JsonProperty("bucket_sizes") List<UInteger> bucketSizes) {
        this.setBucketSizes(bucketSizes);
    }

    /**
     * @return the bucketSizes
     */
    public List<UInteger> getBucketSizes() {
        return bucketSizes;
    }

    /**
     * @param bucketSizes
     *            the bucketSizes to set
     */
    public void setBucketSizes(List<UInteger> bucketSizes) {
        this.bucketSizes = bucketSizes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
