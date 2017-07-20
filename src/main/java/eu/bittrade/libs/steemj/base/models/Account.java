package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "account" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Account {
    // TODO: Original type is "account_id_type".
    private long id;
    private AccountName name;
    private Authority owner;
    private Authority active;
    private Authority posting;
    @JsonProperty("memo_key")
    private PublicKey memoKey;
    @JsonProperty("json_metadata")
    private String jsonMetadata;
    private AccountName proxy;
    @JsonProperty("last_owner_update")
    private TimePointSec lastOwnerUpdate;
    @JsonProperty("last_account_update")
    private TimePointSec lastAccountUpdate;
    private TimePointSec created;
    private boolean mined;
    @JsonProperty("owner_challenged")
    private boolean ownerChallenged;
    @JsonProperty("active_challenged")
    private boolean activeChallenged;
    @JsonProperty("last_owner_proved")
    private TimePointSec lastOwnerProved;
    @JsonProperty("last_active_proved")
    private TimePointSec lastActiveProved;
    @JsonProperty("recovery_account")
    private AccountName recoveryAccount;
    @JsonProperty("reset_account")
    private AccountName resetAccount;
    @JsonProperty("last_account_recovery")
    private TimePointSec lastAccountRecovery;
    @JsonProperty("comment_count")
    private long commentCount;
    @JsonProperty("lifetime_vote_count")
    private long lifetimeVoteCount;
    @JsonProperty("post_count")
    private long postCount;
    @JsonProperty("can_vote")
    private boolean canVote;
    // Orginial type is uint16, but we have to use int here.
    @JsonProperty("voting_power")
    private int votingPower;
    @JsonProperty("last_vote_time")
    private TimePointSec lastVoteTime;
    private Asset balance;
    @JsonProperty("savings_balance")
    private Asset savingsBalance;
    @JsonProperty("sbd_balance")
    private Asset sbdBalance;
    @JsonProperty("sbd_seconds")
    private BigInteger sbdSeconds;
    @JsonProperty("sbd_seconds_last_update")
    private TimePointSec sbdSecondsLastUpdate;
    @JsonProperty("sbd_last_interest_payment")
    private TimePointSec sbdLastInterestPayment;
    @JsonProperty("savings_sbd_balance")
    private Asset savingsSbdBalance;
    @JsonProperty("savings_sbd_seconds")
    private BigInteger savingsSbdSeconds;
    @JsonProperty("savings_sbd_seconds_last_update")
    private TimePointSec savingsSbdSecondsLastUpdate;
    @JsonProperty("savings_sbd_last_interest_payment")
    private TimePointSec savingsSbdLastInterestPayment;
    // Original type is uint8_t.
    @JsonProperty("savings_withdraw_requests")
    private short SavingWithdrawRequests;
    @JsonProperty("reward_sbd_balance")
    private Asset rewardSdbBalance;
    @JsonProperty("reward_steem_balance")
    private Asset rewardSteemBalance;
    @JsonProperty("reward_vesting_balance")
    private Asset rewardVestingBalance;
    @JsonProperty("reward_vesting_steem")
    private Asset rewardVestingSteem;
    // Original type is safe<int64_t>;
    @JsonProperty("curation_rewards")
    private long curationRewards;
    // Original type is safe<int64_t>;
    @JsonProperty("posting_rewards")
    private long postingRewards;
    @JsonProperty("vesting_shares")
    private Asset vestingShares;
    @JsonProperty("delegated_vesting_shares")
    private Asset delegatedVestingShares;
    @JsonProperty("received_vesting_shares")
    private Asset receivedVestingShares;
    @JsonProperty("vesting_withdraw_rate")
    private Asset vestingWithdrawRate;
    @JsonProperty("next_vesting_withdrawal")
    private TimePointSec nextVestingWithdrawal;
    // Original type is safe<int64_t>;
    @JsonProperty("withdrawn")
    private long withdrwan;
    // Original type is safe<int64_t>;
    @JsonProperty("to_withdraw")
    private long toWithdraw;
    // Original type is uint16, but we have to use int here.
    @JsonProperty("withdraw_routes")
    private int withdrawRoutes;
    // Original type is vector<share_type>
    @JsonProperty("proxied_vsf_votes")
    private List<Long> proxiedVsfVotes;
    // Original type is uint16, but we have to use int here.
    @JsonProperty("witnesses_voted_for")
    private int witnessesVotedFor;
    // Original type is safe<int64_t>;
    @JsonProperty("average_bandwidth")
    private long averageBandwidth;
    // Original type is safe<int64_t>;
    @JsonProperty("lifetime_bandwidth")
    private long lifetimeBandwidth;
    @JsonProperty("last_bandwidth_update")
    private TimePointSec lastBandwidthUpdate;
    // Original type is safe<int64_t>;
    @JsonProperty("average_market_bandwidth")
    private long averageMarketBandwidth;
    // Original type is safe<int64_t>;
    @JsonProperty("lifetime_market_bandwidth")
    private long lifetimeMarketBandwidth;
    @JsonProperty("last_market_bandwidth_update")
    private TimePointSec lastMarketBandwidthUpdate;
    @JsonProperty("last_post")
    private TimePointSec lastPost;
    @JsonProperty("last_root_post")
    private TimePointSec lastRootPost;
    /**
     * Original type is safe<int64_t>;
     * 
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("post_bandwidth")
    private long postBandwidth;
    /**
     * Original type is safe<int64_t>;
     * 
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("new_average_bandwidth")
    private long newAverageBandwidth;
    /**
     * Original type is safe<int64_t>;
     * 
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("new_average_market_bandwidth")
    private long newAverageMarketBandwidth;

    public long getId() {
        return id;
    }

    public AccountName getName() {
        return name;
    }

    public Authority getOwner() {
        return owner;
    }

    public Authority getActive() {
        return active;
    }

    public Authority getPosting() {
        return posting;
    }

    public PublicKey getMemoKey() {
        return memoKey;
    }

    public String getJsonMetadata() {
        return jsonMetadata;
    }

    public AccountName getProxy() {
        return proxy;
    }

    public TimePointSec getLastOwnerUpdate() {
        return lastOwnerUpdate;
    }

    public TimePointSec getLastAccountUpdate() {
        return lastAccountUpdate;
    }

    public TimePointSec getCreated() {
        return created;
    }

    public boolean isMined() {
        return mined;
    }

    public boolean isOwnerChallenged() {
        return ownerChallenged;
    }

    public boolean isActiveChallenged() {
        return activeChallenged;
    }

    public TimePointSec getLastOwnerProved() {
        return lastOwnerProved;
    }

    public TimePointSec getLastActiveProved() {
        return lastActiveProved;
    }

    public AccountName getRecoveryAccount() {
        return recoveryAccount;
    }

    public AccountName getResetAccount() {
        return resetAccount;
    }

    public TimePointSec getLastAccountRecovery() {
        return lastAccountRecovery;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public long getLifetimeVoteCount() {
        return lifetimeVoteCount;
    }

    public long getPostCount() {
        return postCount;
    }

    public boolean isCanVote() {
        return canVote;
    }

    public int getVotingPower() {
        return votingPower;
    }

    public TimePointSec getLastVoteTime() {
        return lastVoteTime;
    }

    public Asset getBalance() {
        return balance;
    }

    public Asset getSavingsBalance() {
        return savingsBalance;
    }

    public Asset getSbdBalance() {
        return sbdBalance;
    }

    public BigInteger getSbdSeconds() {
        return sbdSeconds;
    }

    public TimePointSec getSbdSecondsLastUpdate() {
        return sbdSecondsLastUpdate;
    }

    public TimePointSec getSbdLastInterestPayment() {
        return sbdLastInterestPayment;
    }

    public Asset getSavingsSbdBalance() {
        return savingsSbdBalance;
    }

    public BigInteger getSavingsSbdSeconds() {
        return savingsSbdSeconds;
    }

    public TimePointSec getSavingsSbdSecondsLastUpdate() {
        return savingsSbdSecondsLastUpdate;
    }

    public TimePointSec getSavingsSbdLastInterestPayment() {
        return savingsSbdLastInterestPayment;
    }

    public short getSavingWithdrawRequests() {
        return SavingWithdrawRequests;
    }

    public Asset getRewardSdbBalance() {
        return rewardSdbBalance;
    }

    public Asset getRewardSteemBalance() {
        return rewardSteemBalance;
    }

    public Asset getRewardVestingBalance() {
        return rewardVestingBalance;
    }

    public Asset getRewardVestingSteem() {
        return rewardVestingSteem;
    }

    public long getCurationRewards() {
        return curationRewards;
    }

    public long getPostingRewards() {
        return postingRewards;
    }

    public Asset getVestingShares() {
        return vestingShares;
    }

    public Asset getDelegatedVestingShares() {
        return delegatedVestingShares;
    }

    public Asset getReceivedVestingShares() {
        return receivedVestingShares;
    }

    public Asset getVestingWithdrawRate() {
        return vestingWithdrawRate;
    }

    public TimePointSec getNextVestingWithdrawal() {
        return nextVestingWithdrawal;
    }

    public long getWithdrwan() {
        return withdrwan;
    }

    public long getToWithdraw() {
        return toWithdraw;
    }

    public int getWithdrawRoutes() {
        return withdrawRoutes;
    }

    public List<Long> getProxiedVsfVotes() {
        return proxiedVsfVotes;
    }

    public int getWitnessesVotedFor() {
        return witnessesVotedFor;
    }

    public long getAverageBandwidth() {
        return averageBandwidth;
    }

    public long getLifetimeBandwidth() {
        return lifetimeBandwidth;
    }

    public TimePointSec getLastBandwidthUpdate() {
        return lastBandwidthUpdate;
    }

    public long getAverageMarketBandwidth() {
        return averageMarketBandwidth;
    }

    public long getLifetimeMarketBandwidth() {
        return lifetimeMarketBandwidth;
    }

    public TimePointSec getLastMarketBandwidthUpdate() {
        return lastMarketBandwidthUpdate;
    }

    public TimePointSec getLastPost() {
        return lastPost;
    }

    public TimePointSec getLastRootPost() {
        return lastRootPost;
    }

    public long getPostBandwidth() {
        return postBandwidth;
    }

    public long getNewAverageBandwidth() {
        return newAverageBandwidth;
    }

    public long getNewAverageMarketBandwidth() {
        return newAverageMarketBandwidth;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
