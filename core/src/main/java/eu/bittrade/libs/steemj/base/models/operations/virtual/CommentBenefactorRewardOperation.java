package eu.bittrade.libs.steemj.base.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class represents the Steem "comment_benefactor_reward_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentBenefactorRewardOperation extends Operation {
    private AccountName benefactor;
    private AccountName author;
    private String permlink;
    private Asset reward;

    /**
     * Create a new comment benefactor reward operation.
     */
    public CommentBenefactorRewardOperation() {
        super(true);
    }

    /**
     * @return the benefactor
     */
    public AccountName getBenefactor() {
        return benefactor;
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
    public String getPermlink() {
        return permlink;
    }

    /**
     * @return the reward
     */
    public Asset getReward() {
        return reward;
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
