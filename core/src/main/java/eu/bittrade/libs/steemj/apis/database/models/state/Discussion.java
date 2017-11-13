package eu.bittrade.libs.steemj.apis.database.models.state;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.VoteState;

/**
 * This class represents the Steem "discussion" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Discussion extends Comment {
    private String url;
    @JsonProperty("root_title")
    private String rootTitle;
    @JsonProperty("pending_payout_value")
    private Asset pendingPayoutValue;
    @JsonProperty("total_pending_payout_value")
    private Asset totalPendingPayoutValue;
    // Original type is vector<vote_state>.
    @JsonProperty("active_votes")
    private List<VoteState> activeVotes;
    private List<String> replies;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("author_reputation")
    private long authorReputation;
    private Asset promoted;
    // Original type is uint32_t
    @JsonProperty("body_length")
    private String bodyLength;
    @JsonProperty("reblogged_by")
    private List<AccountName> rebloggedBy;
    @JsonProperty("first_reblogged_by")
    private AccountName firstRebloggedBy;
    @JsonProperty("first_reblogged_on")
    private Date firstRebloggedOn;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Discussion() {
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the rootTitle
     */
    public String getRootTitle() {
        return rootTitle;
    }

    /**
     * @return the pendingPayoutValue
     */
    public Asset getPendingPayoutValue() {
        return pendingPayoutValue;
    }

    /**
     * @return the totalPendingPayoutValue
     */
    public Asset getTotalPendingPayoutValue() {
        return totalPendingPayoutValue;
    }

    /**
     * @return the activeVotes
     */
    public List<VoteState> getActiveVotes() {
        return activeVotes;
    }

    /**
     * @return the replies
     */
    public List<String> getReplies() {
        return replies;
    }

    /**
     * @return the authorReputation
     */
    public long getAuthorReputation() {
        return authorReputation;
    }

    /**
     * @return the promoted
     */
    public Asset getPromoted() {
        return promoted;
    }

    /**
     * @return the bodyLength
     */
    public String getBodyLength() {
        return bodyLength;
    }

    /**
     * @return the rebloggedBy
     */
    public List<AccountName> getRebloggedBy() {
        return rebloggedBy;
    }

    /**
     * @return the firstRebloggedBy
     */
    public AccountName getFirstRebloggedBy() {
        return firstRebloggedBy;
    }

    /**
     * @return the firstRebloggedOn
     */
    public Date getFirstRebloggedOn() {
        return firstRebloggedOn;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
