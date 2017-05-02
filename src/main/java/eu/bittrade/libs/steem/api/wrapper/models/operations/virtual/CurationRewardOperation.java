package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * This class represents a "curation_reward_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CurationRewardOperation extends Operation {
    @JsonProperty("curator")
    private String curator;
    @JsonProperty("reward")
    private String reward;
    @JsonProperty("comment_author")
    private String commentAuthor;
    @JsonProperty("comment_permlink")
    private String commentPermlink;

    /**
     * 
     * @return
     */
    public String getCurator() {
        return curator;
    }

    /**
     * 
     * @return
     */
    public String getReward() {
        return reward;
    }

    /**
     * 
     * @return
     */
    public String getCommentAuthor() {
        return commentAuthor;
    }

    /**
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
