package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * This class represents a "curation_reward_operation" object.
 * 
 * This operation type occurs if the payout period of a post is over and the
 * persons who liked that post receive their curation reward.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CurationRewardOperation extends Operation {
    @JsonProperty("curator")
    private AccountName curator;
    @JsonProperty("reward")
    private Asset reward;
    @JsonProperty("comment_author")
    private AccountName commentAuthor;
    @JsonProperty("comment_permlink")
    private String commentPermlink;

    /**
     * Get the person that receives the reward.
     * 
     * @return The person that receives the reward.
     */
    public AccountName getCurator() {
        return curator;
    }

    /**
     * Get the amount and the currency the curator receives.
     * 
     * @return The reward.
     */
    public Asset getReward() {
        return reward;
    }

    /**
     * Get the author of the post or comment that this curation reward is for.
     * 
     * @return The author of the post or comment.
     */
    public AccountName getCommentAuthor() {
        return commentAuthor;
    }

    /**
     * Get the permanent link of the post or comment that this curation reward
     * is for.
     * 
     * @return
     */
    public String getCommentPermlink() {
        return commentPermlink;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // The byte representation is not needed for virtual operations as we
        // can't broadcast them.
        return new byte[0];
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
