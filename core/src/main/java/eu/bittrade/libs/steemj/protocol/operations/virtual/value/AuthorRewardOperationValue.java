package eu.bittrade.libs.steemj.protocol.operations.virtual.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

public class AuthorRewardOperationValue {

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
}
