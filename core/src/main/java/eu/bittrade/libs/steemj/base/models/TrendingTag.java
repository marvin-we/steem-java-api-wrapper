package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "tag_api" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TrendingTag {
    private String name;
    /**
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("total_children_rshares2")
    private String totalChildrenRShares2;
    @JsonProperty("total_payouts")
    private Asset totalPayouts;
    // Original type is "int32_t" so we use long here.
    @JsonProperty("net_votes")
    private long netVotes;
    // Original type is "int32_t" so we use long here.
    @JsonProperty("top_posts")
    private long topPosts;
    // Original type is "int32_t" so we use long here.
    private long comments;
    // Original type is "fc::uint128" so we use BigInteger here.
    private BigInteger trending;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private TrendingTag() {
    }

    /**
     * @return The tag name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The sum of the paid amounts for posts using this tag.
     */
    public Asset getTotalPayouts() {
        return totalPayouts;
    }

    /**
     * @return The number of votes.
     */
    public long getNetVotes() {
        return netVotes;
    }

    /**
     * @return The number of top posts.
     */
    public long getTopPosts() {
        return topPosts;
    }

    /**
     * @return The number of comments.
     */
    public long getComments() {
        return comments;
    }

    /**
     * @return Trending.
     */
    public BigInteger getTrending() {
        return trending;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
