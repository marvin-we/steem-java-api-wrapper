package eu.bittrade.libs.steemj.apis.database.models.state;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * This class represents the Steem "comment_api_obj".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Comment {
	
	private final Map<String, Object> _properties = new HashMap<>();
	@JsonAnySetter
	public void setProperty(String key, Object value) {
		this._properties.put(key, value);
	}
	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return _properties;
	}
	
    // Original type is comment_id_type.
    private long id;
    private String category;
    @JsonProperty("parent_author")
    private AccountName parentAuthor;
    @JsonProperty("parent_permlink")
    private Permlink parentPermlink;
    private AccountName author;
    private Permlink permlink;
    private String title;
    private String body;
    @JsonProperty("json_metadata")
    private String jsonMetadata;
    @JsonProperty("last_update")
    private TimePointSec lastUpdate;
    private TimePointSec created;
    private TimePointSec active;
    @JsonProperty("last_payout")
    private TimePointSec lastPayout;
    // Original type is uint8_t.
    private short depth;
    // Orignal type is uint32_t.
    private int children;
    @Deprecated
    @JsonProperty("children_rshares2")
    private String childrenRshares2;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("net_rshares")
    private long netRshares;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("abs_rshares")
    private long absRshares;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("vote_rshares")
    private long voteRshares;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("children_abs_rshares")
    private long childrenAbsRshares;
    @JsonProperty("cashout_time")
    private TimePointSec cashoutTime;
    @JsonProperty("max_cashout_time")
    private TimePointSec maxCashoutTime;
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
    // Original type is "share_type" which is a "safe<int64_t>".
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
    private boolean allowReplies;
    @JsonProperty("allow_votes")
    private boolean allowVotes;
    @JsonProperty("allow_curation_rewards")
    private boolean allowCurationRewards;
    // TODO: Fix type
    // bip::vector< beneficiary_route_type, allocator< beneficiary_route_type >
    // > beneficiaries;
    private Object[] beneficiaries;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected Comment() {
    }

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
    public Permlink getParentPermlink() {
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
    public Permlink getPermlink() {
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
    public TimePointSec getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @return the created
     */
    public TimePointSec getCreated() {
        return created;
    }

    /**
     * @return the active
     */
    public TimePointSec getActive() {
        return active;
    }

    /**
     * @return the lastPayout
     */
    public TimePointSec getLastPayout() {
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
    public TimePointSec getCashoutTime() {
        return cashoutTime;
    }

    /**
     * @return the maxCashoutTime
     */
    public TimePointSec getMaxCashoutTime() {
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
    public boolean getAllowReplies() {
        return allowReplies;
    }

    /**
     * @return the allowVotes
     */
    public boolean getAllowVotes() {
        return allowVotes;
    }

    /**
     * @return the allowCurationRewards
     */
    public boolean getAllowCurationRewards() {
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
