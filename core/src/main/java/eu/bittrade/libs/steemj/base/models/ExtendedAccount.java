package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "extended_account" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ExtendedAccount extends Account {
    /** Convert vesting_shares to vesting Steem. */
    @JsonProperty("vesting_balance")
    private Asset vestingBalance;
    // Original type is "share_type" which is a "safe<int64_t>".
    private long reputation;
    // The original tpye is map<uint64_t,applied_operation>
    /** Transfer to/from vesting. */
    @JsonProperty("transfer_history")
    private Map<BigInteger, AppliedOperation> transferHistory;
    // The original tpye is map<uint64_t,applied_operation>
    /** Limit order / cancel / fill. */
    @JsonProperty("market_history")
    private Map<BigInteger, AppliedOperation> marketHistory;
    // The original tpye is map<uint64_t,applied_operation>
    @JsonProperty("post_history")
    private Map<BigInteger, AppliedOperation> postHistory;
    // The original tpye is map<uint64_t,applied_operation>
    @JsonProperty("vote_history")
    private Map<BigInteger, AppliedOperation> voteHistory;
    // The original tpye is map<uint64_t,applied_operation>
    @JsonProperty("other_history")
    private Map<BigInteger, AppliedOperation> otherHistory;
    // Original type is set<string>.
    @JsonProperty("witness_votes")
    private List<AccountName> witnessVotes;
    // Original type is "vector<pair<string,uint32_t>>".
    @JsonProperty("tags_usage")
    private List<Pair<String, Long>> tagsUsage;
    // Original type is "vector<pair<account_name_type,uint32_t>>".
    @JsonProperty("guest_bloggers")
    private List<Pair<AccountName, Long>> guestBloggers;
    // Original type is "optional<map<uint32_t,extended_limit_order>>".
    @JsonProperty("open_orders")
    private Object[] openOrders;
    // Original type is "optional<vector<string>>".
    /** Permlinks for this user. */
    @JsonProperty("comments")
    private Object[] comments;
    // Original type is "optional<vector<string>>".
    /** Blog posts for this user. */
    @JsonProperty("blog")
    private Object[] blog;
    // Original type is "optional<vector<string>>".
    /** Feed posts for this user. */
    @JsonProperty("feed")
    private Object[] feed;
    // Original type is "optional<vector<string>>".
    /** Blog posts for this user. */
    @JsonProperty("recent_replies")
    private Object[] recentReplies;
    // Original type is "optional<vector<string>>".
    /** Posts recommened for this user. */
    @JsonProperty("recommended")
    private Object[] recommended;
    /**
     * @deprecated This field is deprecated since HF 0.19.0 and may be null.
     */
    @JsonProperty("blog_category")
    private Object[] blogCategory;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ExtendedAccount() {
    }

    /**
     * @return the vestingBalance
     */
    public Asset getVestingBalance() {
        return vestingBalance;
    }

    /**
     * @return the reputation
     */
    public long getReputation() {
        return reputation;
    }

    /**
     * @return the transferHistory
     */
    public Map<BigInteger, AppliedOperation> getTransferHistory() {
        return transferHistory;
    }

    /**
     * @return the marketHistory
     */
    public Map<BigInteger, AppliedOperation> getMarketHistory() {
        return marketHistory;
    }

    /**
     * @return the postHistory
     */
    public Map<BigInteger, AppliedOperation> getPostHistory() {
        return postHistory;
    }

    /**
     * @return the voteHistory
     */
    public Map<BigInteger, AppliedOperation> getVoteHistory() {
        return voteHistory;
    }

    /**
     * @return the otherHistory
     */
    public Map<BigInteger, AppliedOperation> getOtherHistory() {
        return otherHistory;
    }

    /**
     * @return the witnessVotes
     */
    public List<AccountName> getWitnessVotes() {
        return witnessVotes;
    }

    /**
     * @return the tagsUsage
     */
    public List<Pair<String, Long>> getTagsUsage() {
        return tagsUsage;
    }

    /**
     * @return the guestBloggers
     */
    public List<Pair<AccountName, Long>> getGuestBloggers() {
        return guestBloggers;
    }

    /**
     * @return the openOrders
     */
    public Object[] getOpenOrders() {
        return openOrders;
    }

    /**
     * @return the comments
     */
    public Object[] getComments() {
        return comments;
    }

    /**
     * @return the blog
     */
    public Object[] getBlog() {
        return blog;
    }

    /**
     * @return the feed
     */
    public Object[] getFeed() {
        return feed;
    }

    /**
     * @return the recentReplies
     */
    public Object[] getRecentReplies() {
        return recentReplies;
    }

    /**
     * @return the recommended
     */
    public Object[] getRecommended() {
        return recommended;
    }

    /**
     * @return the blogCategory
     */
    public Object[] getBlogCategory() {
        return blogCategory;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
