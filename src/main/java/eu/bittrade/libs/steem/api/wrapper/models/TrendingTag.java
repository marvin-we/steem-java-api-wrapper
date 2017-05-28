package eu.bittrade.libs.steem.api.wrapper.models;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "trending tag" api object.
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
    private String totalPayouts;
    @JsonProperty("net_votes")
    private long netVotes;
    @JsonProperty("top_posts")
    private long topPosts;
    private long comments;
    private BigInteger trending;

    public String getName() {
        return name;
    }

    public String getTotalChildrenRShares2() {
        return totalChildrenRShares2;
    }

    public String getTotalPayouts() {
        return totalPayouts;
    }

    public long getNetVotes() {
        return netVotes;
    }

    public long getTopPosts() {
        return topPosts;
    }

    public long getComments() {
        return comments;
    }

    public BigInteger getTrending() {
        return trending;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
