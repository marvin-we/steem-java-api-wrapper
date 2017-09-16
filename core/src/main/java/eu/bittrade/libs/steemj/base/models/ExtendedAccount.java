package eu.bittrade.libs.steemj.base.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "extended_account" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ExtendedAccount extends Account {
    @JsonProperty("vesting_balance")
    /** Convert vesting_shares to vesting steem. */
    private Asset vestingBalance;
    // Original type is "share_type" which is a "safe<int64_t>".
    private long reputation;
    /*
     * TODO: Fix types for: map<uint64_t,applied_operation> transfer_history;
     * /// transfer to/from vesting map<uint64_t,applied_operation>
     * market_history; /// limit order / cancel / fill
     * map<uint64_t,applied_operation> post_history;
     * map<uint64_t,applied_operation> vote_history;
     * map<uint64_t,applied_operation> other_history;
     * vector<pair<string,uint32_t>> tags_usage;
     * vector<pair<account_name_type,uint32_t>> guest_bloggers;
     * 
     * optional<map<uint32_t,extended_limit_order>> open_orders;
     * optional<vector<string>> comments; /// permlinks for this user
     * optional<vector<string>> blog; /// blog posts for this user
     * optional<vector<string>> feed; /// feed posts for this user
     * optional<vector<string>> recent_replies; /// blog posts for this user
     * map<string,vector<string>> blog_category; /// blog posts for this user
     * optional<vector<string>> recommended; /// posts recommened for this user
     */
    @JsonProperty("transfer_history")
    private Object[] transferHistory;
    @JsonProperty("market_history")
    private Object[] marketHistory;
    @JsonProperty("post_history")
    private Object[] postHistory;
    @JsonProperty("vote_history")
    private Object[] voteHistory;
    @JsonProperty("other_history")
    private Object[] otherHistory;
    // Original type is set<string>.
    @JsonProperty("witness_votes")
    private List<String> witnessVotes;
    @JsonProperty("tags_usage")
    private Object[] tagsUsage;
    @JsonProperty("guest_bloggers")
    private Object[] guestBloggers;
    @JsonProperty("open_orders")
    private Object[] openOrders;
    @JsonProperty("comments")
    private Object[] comments;
    @JsonProperty("blog")
    private Object[] blog;
    @JsonProperty("feed")
    private Object[] feed;
    @JsonProperty("recent_replies")
    private Object[] recentReplies;
    @JsonProperty("recommended")
    private Object[] recommended;
    @JsonProperty("blog_category")
    private Object[] blogCategory;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ExtendedAccount() {
    }

    public Asset getVestingBalance() {
        return vestingBalance;
    }

    public long getReputation() {
        return reputation;
    }

    public Object[] getTransferHistory() {
        return transferHistory;
    }

    public Object[] getMarketHistory() {
        return marketHistory;
    }

    public Object[] getPostHistory() {
        return postHistory;
    }

    public Object[] getVoteHistory() {
        return voteHistory;
    }

    public Object[] getOtherHistory() {
        return otherHistory;
    }

    public List<String> getWitnessVotes() {
        return witnessVotes;
    }

    public Object[] getTagsUsage() {
        return tagsUsage;
    }

    public Object[] getGuestBloggers() {
        return guestBloggers;
    }

    public Object[] getOpenOrders() {
        return openOrders;
    }

    public Object[] getComments() {
        return comments;
    }

    public Object[] getBlog() {
        return blog;
    }

    public Object[] getFeed() {
        return feed;
    }

    public Object[] getRecentReplies() {
        return recentReplies;
    }

    public Object[] getRecommended() {
        return recommended;
    }

    public Object[] getBlogCategory() {
        return blogCategory;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
