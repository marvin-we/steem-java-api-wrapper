package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;

/**
 * This class represents the Steem "comment_options_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsOperation extends Operation {
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private String permlink;
    @JsonProperty("max_accepted_payout")
    private Asset maxAcceptedPayout;
    @JsonProperty("allow_votes")
    private Boolean allowVotes;
    @JsonProperty("allow_curation_rewards")
    private Boolean allowCurationRewards;
    @JsonProperty("percent_steem_dollars")
    private Short percentSteemDollars;
    // TODO: Fix type: comment_options_extensions_type
    @JsonProperty("extensions")
    private Object[] extensions;

    public CommentOptionsOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    /**
     * Get the author of the comment.
     * 
     * @return The account name of the author.
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * Set the author of the comment.
     * 
     * @param author
     *            The account name of the author.
     */
    public void setAuthor(AccountName author) {
        this.author = author;
    }

    /**
     * Get the permanent link of this comment.
     * 
     * @return The permanent link.
     */
    public String getPermlink() {
        return permlink;
    }

    /**
     * Set the permanent link of this comment.
     * 
     * @param permlink
     *            The permanent link.
     */
    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    /**
     * Get the maximum payout accepted for this comment.
     * 
     * @return The maximum payout accepted for this comment.
     */
    public Asset getMaxAcceptedPayout() {
        return maxAcceptedPayout;
    }

    /**
     * Set the maximum payout accepted for this comment.
     * 
     * @param maxAcceptedPayout
     *            The maximum payout accepted for this comment.
     */
    public void setMaxAcceptedPayout(Asset maxAcceptedPayout) {
        this.maxAcceptedPayout = maxAcceptedPayout;
    }

    /**
     * 
     * @return
     */
    public Boolean getAllowVotes() {
        return allowVotes;
    }

    /**
     * 
     * @param allowVotes
     */
    public void setAllowVotes(Boolean allowVotes) {
        this.allowVotes = allowVotes;
    }

    /**
     * 
     * @return
     */
    public Boolean getAllowCurationRewards() {
        return allowCurationRewards;
    }

    /**
     * 
     * @param allowCurationRewards
     */
    public void setAllowCurationRewards(Boolean allowCurationRewards) {
        this.allowCurationRewards = allowCurationRewards;
    }

    /**
     * 
     * @return
     */
    public Short getPercentSteemDollars() {
        return percentSteemDollars;
    }

    /**
     * 
     * @param percentSteemDollars
     */
    public void setPercentSteemDollars(Short percentSteemDollars) {
        this.percentSteemDollars = percentSteemDollars;
    }

    /**
     * 
     * @return
     */
    public Object[] getExtensions() {
        return extensions;
    }

    /**
     * 
     * @param extensions
     */
    public void setExtensions(Object[] extensions) {
        this.extensions = extensions;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
