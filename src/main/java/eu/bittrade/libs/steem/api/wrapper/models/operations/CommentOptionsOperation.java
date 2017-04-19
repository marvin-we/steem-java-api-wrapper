package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsOperation extends Operation {
    @JsonProperty("author")
    private String author;
    @JsonProperty("permlink")
    private String permlink;
    @JsonProperty("max_accepted_payout")
    private String maxAcceptedPayout;
    @JsonProperty("allow_votes")
    private Boolean allowVotes;
    @JsonProperty("allow_curation_rewards")
    private Boolean allowCurationRewards;
    @JsonProperty("percent_steem_dollars")
    private Short percentSteemDollars;
    // TODO: Fix type
    @JsonProperty("extensions")
    private Object[] extensions;

    public String getAuthor() {
        return author;
    }

    public String getPermlink() {
        return permlink;
    }

    public String getMaxAcceptedPayout() {
        return maxAcceptedPayout;
    }

    public Boolean getAllowVotes() {
        return allowVotes;
    }

    public Boolean getAllowCurationRewards() {
        return allowCurationRewards;
    }

    public Short getPercentSteemDollars() {
        return percentSteemDollars;
    }

    public Object[] getExtensions() {
        return extensions;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
