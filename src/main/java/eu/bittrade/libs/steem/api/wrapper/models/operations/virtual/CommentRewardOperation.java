package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * This class represents the Steem "comment_reward_operation" object.
 * 
 * This operation type occurs if the payout period is over and the author of
 * comment finally gets his reward.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentRewardOperation extends Operation {
    private AccountName author;
    private String permlink;
    private Asset payout;

    /**
     * Get the author of the comment.
     * 
     * @return The author of the comment.
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * Get the permanent link to the comment.
     * 
     * @return The permanent link.
     */
    public String getPermlink() {
        return permlink;
    }

    /**
     * Get the amount and the currency that the author of the comment receives.
     * 
     * @return The payout.
     */
    public Asset getPayout() {
        return payout;
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
