package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TrendingTag {
    private String name;
    @JsonProperty("total_children_rshares2")
    private String totalChildrenRShares2;
    @JsonProperty("total_payouts")
    private String totalPayouts;
    @JsonProperty("net_votes")
    private long netVotes;
    @JsonProperty("top_posts")
    private long topPosts;
    private long comments;

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
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
