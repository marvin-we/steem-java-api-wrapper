package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
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

    public CurationRewardOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String getCurator() {
        return curator;
    }

    public String getReward() {
        return reward;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public String getCommentPermlink() {
        return commentPermlink;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }
}
