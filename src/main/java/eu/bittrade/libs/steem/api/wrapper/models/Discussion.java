package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Discussion {
    private long id;
    @JsonProperty("author")
    private String author;
    @JsonProperty("permlink")
    private String permlink;
    @JsonProperty("category")
    private String category;
    @JsonProperty("parent_author")
    private String parentAuthor;
    @JsonProperty("parent_permlink")
    private String parentPermlink;
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String body;
    @JsonProperty("json_metadata")
    private String jsonMetadata;
    @JsonProperty("last_update")
    private String lastUpdate;
    @JsonProperty("created")
    private String created;
    @JsonProperty("active")
    private String active;
    @JsonProperty("last_payout")
    private String lastPayout;
    @JsonProperty("depth")
    private int depth;
    @JsonProperty("children")
    private int children;
    @JsonProperty("children_rshares2")
    private String childrenRshares2;
    @JsonProperty("net_rshares")
    private String netRshares;
    @JsonProperty("abs_rshares")
    private String absRshares;
    @JsonProperty("vote_rshares")
    private String voteRshares;
    @JsonProperty("children_abs_rshares")
    private String childrenAbsRshares;
    @JsonProperty("cashout_time")
    private String cashoutTime;
    @JsonProperty("max_cashout_time")
    private String maxCashoutTime;
    @JsonProperty("total_vote_weight")
    private String totalVoteWeight;
    @JsonProperty("reward_weight")
    private long rewardWeight;
    @JsonProperty("total_payout_value")
    private String totalPayoutValue;
    @JsonProperty("curator_payout_value")
    private String curatorPayoutValue;
    @JsonProperty("author_rewards")
    private long authorRewards;
    @JsonProperty("net_votes")
    private int netVotes;
    @JsonProperty("root_comment")
    private long rootComment;
    @JsonProperty("mode")
    private String mode;
    @JsonProperty("max_accepted_payout")
    private String maxAcceptedPayout;
    @JsonProperty("percent_steem_dollars")
    private int percentSteemDollars;
    @JsonProperty("allow_replies")
    private Boolean allowReplies;
    @JsonProperty("allow_votes")
    private Boolean allowVotes;
    @JsonProperty("allow_curation_rewards")
    private Boolean allowCurationRewards;
    @JsonProperty("url")
    private String url;
    @JsonProperty("root_title")
    private String rootTitle;
    @JsonProperty("pending_payout_value")
    private String pendingPayoutValue;
    @JsonProperty("total_pending_payout_value")
    private String totalPendingPayoutValue;
    @JsonProperty("active_votes")
    private List<ActiveVote> activeVotes;
    private List<Discussion> replies;
    @JsonProperty("author_reputation")
    private String authorReputation;
    private String promoted;

    public Discussion(@JsonProperty("replies") List<Discussion> replies,
            @JsonProperty("active_votes") List<ActiveVote> activeVotes) {
        this.replies = replies;
        this.activeVotes = activeVotes;
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getPermlink() {
        return permlink;
    }

    public String getCategory() {
        return category;
    }

    public String getParentAuthor() {
        return parentAuthor;
    }

    public String getParentPermlink() {
        return parentPermlink;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getJsonMetadata() {
        return jsonMetadata;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getCreated() {
        return created;
    }

    public String getActive() {
        return active;
    }

    public String getLastPayout() {
        return lastPayout;
    }

    public int getDepth() {
        return depth;
    }

    public int getChildren() {
        return children;
    }

    public String getChildrenRshares2() {
        return childrenRshares2;
    }

    public String getNetRshares() {
        return netRshares;
    }

    public String getAbsRshares() {
        return absRshares;
    }

    public String getVoteRshares() {
        return voteRshares;
    }

    public String getChildrenAbsRshares() {
        return childrenAbsRshares;
    }

    public String getCashoutTime() {
        return cashoutTime;
    }

    public String getMaxCashoutTime() {
        return maxCashoutTime;
    }

    public String getTotalVoteWeight() {
        return totalVoteWeight;
    }

    public long getRewardWeight() {
        return rewardWeight;
    }

    public String getTotalPayoutValue() {
        return totalPayoutValue;
    }

    public String getCuratorPayoutValue() {
        return curatorPayoutValue;
    }

    public long getAuthorRewards() {
        return authorRewards;
    }

    public int getNetVotes() {
        return netVotes;
    }

    public long getRootComment() {
        return rootComment;
    }

    public String getMode() {
        return mode;
    }

    public String getMaxAcceptedPayout() {
        return maxAcceptedPayout;
    }

    public int getPercentSteemDollars() {
        return percentSteemDollars;
    }

    public Boolean getAllowReplies() {
        return allowReplies;
    }

    public Boolean getAllowVotes() {
        return allowVotes;
    }

    public Boolean getAllowCurationRewards() {
        return allowCurationRewards;
    }

    public String getUrl() {
        return url;
    }

    public String getRootTitle() {
        return rootTitle;
    }

    public String getPendingPayoutValue() {
        return pendingPayoutValue;
    }

    public String getTotalPendingPayoutValue() {
        return totalPendingPayoutValue;
    }

    public List<ActiveVote> getActiveVotes() {
        return activeVotes;
    }

    public List<Discussion> getReplies() {
        return replies;
    }

    public String getAuthorReputation() {
        return authorReputation;
    }

    public String getPromoted() {
        return promoted;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
