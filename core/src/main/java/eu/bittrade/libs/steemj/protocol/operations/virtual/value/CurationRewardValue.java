package eu.bittrade.libs.steemj.protocol.operations.virtual.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

public class CurationRewardValue {

    @JsonProperty("curator")
    private AccountName curator;
    @JsonProperty("reward")
    private Asset reward;
    @JsonProperty("comment_author")
    private AccountName commentAuthor;
    @JsonProperty("comment_permlink")
    private Permlink commentPermlink;
    
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
     * @return The permanent link of the post or comment that this curation
     *         reward is for.
     */
    public Permlink getCommentPermlink() {
        return commentPermlink;
    }


}
