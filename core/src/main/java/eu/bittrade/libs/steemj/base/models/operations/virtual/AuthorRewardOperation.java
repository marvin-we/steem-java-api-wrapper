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
 * This class represents the Steem "author_reward_operation" object.
 * 
 * This operation type occurs if the payout period is over and the author
 * finally gets his reward.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AuthorRewardOperation extends Operation {
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private Permlink permlink;
    @JsonProperty("sbd_payout")
    private Asset sbdPayout;
    @JsonProperty("steem_payout")
    private Asset steemPayout;
    @JsonProperty("vesting_payout")
    private Asset vestingPayout;

    /**
     * This operation is a virtual one and can only be created by the blockchain
     * itself. Due to that, this constructor is private.
     */
    private AuthorRewardOperation() {
        super(true);
    }

    /**
     * Get the author who received this reward.
     * 
     * @return The author who received the reward.
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * Get the permanent link of the post for which the author is rewarded.
     * 
     * @return The permanent link of the article.
     */
    public Permlink getPermlink() {
        return permlink;
    }

    /**
     * Get the SDB amount the author gets for the article.
     * 
     * @return The amount of SBD.
     */
    public Asset getSbdPayout() {
        return sbdPayout;
    }

    /**
     * Get the Steem amount the author gets for the article.
     * 
     * @return The amount of Steem.
     */
    public Asset getSteemPayout() {
        return steemPayout;
    }

    /**
     * Get the Vests amount the author gets for the article.
     * 
     * @return The amount of Vests.
     */
    public Asset getVestingPayout() {
        return vestingPayout;
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
