package eu.bittrade.libs.steem.api.wrapper.models;

import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the Steem "comment_api_obj".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Comment {
    // Original type is comment_id_type.
    private long id;
    private String category;
    @JsonProperty("parent_author")
    private AccountName parentAuthor;
    @JsonProperty("parent_permlink")
    private String parentPermlink;
    private AccountName author;
    private String permlink;
    private String title;
    private String body;
    @JsonProperty("json_metadata")
    private String jsonMetadata;
    @JsonProperty("last_update")
    private Date lastUpdate;
    private Date created;
    private Date active;
    @JsonProperty("last_payout")
    private Date lastPayout;
    // Original type is uint8_t.
    private short depth;
    // Orignal type is uint32_t.
    private int children;
    @Deprecated
    @JsonProperty("children_rshares2")
    private String childrenRshares2;
    // Original type is safe<int64_t>.
    @JsonProperty("net_rshares")
    private long netRshares;
    // Original type is safe<int64_t>.
    @JsonProperty("abs_rshares")
    private long absRshares;
    // Original type is safe<int64_t>.
    @JsonProperty("vote_rshares")
    private long voteRshares;
    // Original type is safe<int64_t>.
    @JsonProperty("children_abs_rshares")
    private long childrenAbsRshares;
    @JsonProperty("cashout_time")
    private Date cashoutTime;
    @JsonProperty("max_cashout_time")
    private Date maxCashoutTime;
    // TODO: Original type is uint64_t, but long seems to be not enough here -->
    // "Can not deserialize value of type long from String
    // "16089511318360462253": not a valid Long value"
    @JsonProperty("total_vote_weight")
    private BigInteger totalVoteWeight;
    // Original type is uint64_t.
    @JsonProperty("reward_weight")
    private long rewardWeight;
    @JsonProperty("total_payout_value")
    private Asset totalPayoutValue;
    @JsonProperty("curator_payout_value")
    private Asset curatorPayoutValue;
    // Original type is safe<int64_t>.
    @JsonProperty("author_rewards")
    private long authorRewards;
    // Original type is int32_t.
    @JsonProperty("net_votes")
    private int netVotes;
    // Original type is comment_id_type.
    @JsonProperty("root_comment")
    private long rootComment;
    @Deprecated
    private String mode;
    @JsonProperty("max_accepted_payout")
    private Asset maxAcceptedPayout;
    // Original type is uint16_t.
    @JsonProperty("percent_steem_dollars")
    private short percentSteemDollars;
    @JsonProperty("allow_replies")
    private Boolean allowReplies;
    @JsonProperty("allow_votes")
    private Boolean allowVotes;
    @JsonProperty("allow_curation_rewards")
    private Boolean allowCurationRewards;
    // TODO: Fix type
    // bip::vector< beneficiary_route_type, allocator< beneficiary_route_type >
    // > beneficiaries;
    private Object[] beneficiaries;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the parentAuthor
     */
    public AccountName getParentAuthor() {
        return parentAuthor;
    }

    /**
     * @return the parentPermlink
     */
    public String getParentPermlink() {
        return parentPermlink;
    }

    /**
     * @return the author
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * @return the permlink
     */
    public String getPermlink() {
        return permlink;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @return the jsonMetadata
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * @return the lastUpdate
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @return the active
     */
    public Date getActive() {
        return active;
    }

    /**
     * @return the lastPayout
     */
    public Date getLastPayout() {
        return lastPayout;
    }

    /**
     * @return the depth
     */
    public short getDepth() {
        return depth;
    }

    /**
     * @return the children
     */
    public int getChildren() {
        return children;
    }

    /**
     * @return the childrenRshares2
     */
    public String getChildrenRshares2() {
        return childrenRshares2;
    }

    /**
     * @return the netRshares
     */
    public long getNetRshares() {
        return netRshares;
    }

    /**
     * @return the absRshares
     */
    public long getAbsRshares() {
        return absRshares;
    }

    /**
     * @return the voteRshares
     */
    public long getVoteRshares() {
        return voteRshares;
    }

    /**
     * @return the childrenAbsRshares
     */
    public long getChildrenAbsRshares() {
        return childrenAbsRshares;
    }

    /**
     * @return the cashoutTime
     */
    public Date getCashoutTime() {
        return cashoutTime;
    }

    /**
     * @return the maxCashoutTime
     */
    public Date getMaxCashoutTime() {
        return maxCashoutTime;
    }

    /**
     * @return the totalVoteWeight
     */
    public BigInteger getTotalVoteWeight() {
        return totalVoteWeight;
    }

    /**
     * @return the rewardWeight
     */
    public long getRewardWeight() {
        return rewardWeight;
    }

    /**
     * @return the totalPayoutValue
     */
    public Asset getTotalPayoutValue() {
        return totalPayoutValue;
    }

    /**
     * @return the curatorPayoutValue
     */
    public Asset getCuratorPayoutValue() {
        return curatorPayoutValue;
    }

    /**
     * @return the authorRewards
     */
    public long getAuthorRewards() {
        return authorRewards;
    }

    /**
     * @return the netVotes
     */
    public int getNetVotes() {
        return netVotes;
    }

    /**
     * @return the rootComment
     */
    public long getRootComment() {
        return rootComment;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @return the maxAcceptedPayout
     */
    public Asset getMaxAcceptedPayout() {
        return maxAcceptedPayout;
    }

    /**
     * @return the percentSteemDollars
     */
    public short getPercentSteemDollars() {
        return percentSteemDollars;
    }

    /**
     * @return the allowReplies
     */
    public Boolean getAllowReplies() {
        return allowReplies;
    }

    /**
     * @return the allowVotes
     */
    public Boolean getAllowVotes() {
        return allowVotes;
    }

    /**
     * @return the allowCurationRewards
     */
    public Boolean getAllowCurationRewards() {
        return allowCurationRewards;
    }

    /**
     * @return the beneficiaries
     */
    public Object[] getBeneficiaries() {
        return beneficiaries;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
