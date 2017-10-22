package eu.bittrade.libs.steemj.base.models.operations.virtual;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;

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
    private Permlink commentPermlink;

    /**
     * This operation is a virtual one and can only be created by the blockchain
     * itself. Due to that, this constructor is private.
     */
    private CurationRewardOperation() {
        super(true);
    }

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

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        // A virtual operation can't be created by the user, therefore it also
        // does not require any authority.
        return null;
    }

    @Override
    public void validate(ValidationType validationType) {
        // There is no need to validate virtual operations.
    }
}
