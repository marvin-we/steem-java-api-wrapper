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
    private short savingWithdrawRequests;
    @JsonProperty("reward_sbd_balance")
    private Asset rewardSdbBalance;
    @JsonProperty("reward_steem_balance")
    private Asset rewardSteemBalance;
    @JsonProperty("reward_vesting_balance")
    private Asset rewardVestingBalance;
    @JsonProperty("reward_vesting_steem")
    private Asset rewardVestingSteem;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("curation_rewards")
    private long curationRewards;
    // Original type is "share_type" which is a "safe<int64_t>".
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
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("withdrawn")
    private long withdrwan;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("to_withdraw")
    private long toWithdraw;
    // Original type is uint16, but we have to use int here.
    @JsonProperty("withdraw_routes")
    private int withdrawRoutes;
    // Original type is vector<share_type> while a share_type is a int64_t so we
    // use long here.
    @JsonProperty("proxied_vsf_votes")
    private List<Long> proxiedVsfVotes;
    // Original type is uint16, but we have to use int here.
    @JsonProperty("witnesses_voted_for")
    private int witnessesVotedFor;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("average_bandwidth")
    private long averageBandwidth;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("lifetime_bandwidth")
    private long lifetimeBandwidth;
    @JsonProperty("last_bandwidth_update")
    private TimePointSec lastBandwidthUpdate;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("average_market_bandwidth")
    private long averageMarketBandwidth;
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonProperty("lifetime_market_bandwidth")
    private long lifetimeMarketBandwidth;
    @JsonProperty("last_market_bandwidth_update")
    private TimePointSec lastMarketBandwidthUpdate;
    @JsonProperty("last_post")
    private TimePointSec lastPost;
    @JsonProperty("last_root_post")
    private TimePointSec lastRootPost;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected Account() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public AccountName getName() {
        return name;
    }

    /**
     * @return the owner
     */
    public Authority getOwner() {
        return owner;
    }

    /**
     * @return the active
     */
    public Authority getActive() {
        return active;
    }

    /**
     * @return the posting
     */
    public Authority getPosting() {
        return posting;
    }

    /**
     * @return the memoKey
     */
    public PublicKey getMemoKey() {
        return memoKey;
    }

    /**
     * @return the jsonMetadata
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * @return the proxy
     */
    public AccountName getProxy() {
        return proxy;
    }

    /**
     * @return the lastOwnerUpdate
     */
    public TimePointSec getLastOwnerUpdate() {
        return lastOwnerUpdate;
    }

    /**
     * @return the lastAccountUpdate
     */
    public TimePointSec getLastAccountUpdate() {
        return lastAccountUpdate;
    }

    /**
     * @return the created
     */
    public TimePointSec getCreated() {
        return created;
    }

    /**
     * @return the mined
     */
    public boolean isMined() {
        return mined;
    }

    /**
     * @return the ownerChallenged
     */
    public boolean isOwnerChallenged() {
        return ownerChallenged;
    }

    /**
     * @return the activeChallenged
     */
    public boolean isActiveChallenged() {
        return activeChallenged;
    }

    /**
     * @return the lastOwnerProved
     */
    public TimePointSec getLastOwnerProved() {
        return lastOwnerProved;
    }

    /**
     * @return the lastActiveProved
     */
    public TimePointSec getLastActiveProved() {
        return lastActiveProved;
    }

    /**
     * @return the recoveryAccount
     */
    public AccountName getRecoveryAccount() {
        return recoveryAccount;
    }

    /**
     * @return the resetAccount
     */
    public AccountName getResetAccount() {
        return resetAccount;
    }

    /**
     * @return the lastAccountRecovery
     */
    public TimePointSec getLastAccountRecovery() {
        return lastAccountRecovery;
    }

    /**
     * @return the commentCount
     */
    public long getCommentCount() {
        return commentCount;
    }

    /**
     * @return the lifetimeVoteCount
     */
    public long getLifetimeVoteCount() {
        return lifetimeVoteCount;
    }

    /**
     * @return the postCount
     */
    public long getPostCount() {
        return postCount;
    }

    /**
     * @return the canVote
     */
    public boolean isCanVote() {
        return canVote;
    }

    /**
     * @return the votingPower
     */
    public int getVotingPower() {
        return votingPower;
    }

    /**
     * @return the lastVoteTime
     */
    public TimePointSec getLastVoteTime() {
        return lastVoteTime;
    }

    /**
     * @return the balance
     */
    public Asset getBalance() {
        return balance;
    }

    /**
     * @return the savingsBalance
     */
    public Asset getSavingsBalance() {
        return savingsBalance;
    }

    /**
     * @return the sbdBalance
     */
    public Asset getSbdBalance() {
        return sbdBalance;
    }

    /**
     * @return the sbdSeconds
     */
    public BigInteger getSbdSeconds() {
        return sbdSeconds;
    }

    /**
     * @return the sbdSecondsLastUpdate
     */
    public TimePointSec getSbdSecondsLastUpdate() {
        return sbdSecondsLastUpdate;
    }

    /**
     * @return the sbdLastInterestPayment
     */
    public TimePointSec getSbdLastInterestPayment() {
        return sbdLastInterestPayment;
    }

    /**
     * @return the savingsSbdBalance
     */
    public Asset getSavingsSbdBalance() {
        return savingsSbdBalance;
    }

    /**
     * @return the savingsSbdSeconds
     */
    public BigInteger getSavingsSbdSeconds() {
        return savingsSbdSeconds;
    }

    /**
     * @return the savingsSbdSecondsLastUpdate
     */
    public TimePointSec getSavingsSbdSecondsLastUpdate() {
        return savingsSbdSecondsLastUpdate;
    }

    /**
     * @return the savingsSbdLastInterestPayment
     */
    public TimePointSec getSavingsSbdLastInterestPayment() {
        return savingsSbdLastInterestPayment;
    }

    /**
     * @return the savingWithdrawRequests
     */
    public short getSavingWithdrawRequests() {
        return savingWithdrawRequests;
    }

    /**
     * @return the rewardSdbBalance
     */
    public Asset getRewardSdbBalance() {
        return rewardSdbBalance;
    }

    /**
     * @return the rewardSteemBalance
     */
    public Asset getRewardSteemBalance() {
        return rewardSteemBalance;
    }

    /**
     * @return the rewardVestingBalance
     */
    public Asset getRewardVestingBalance() {
        return rewardVestingBalance;
    }

    /**
     * @return the rewardVestingSteem
     */
    public Asset getRewardVestingSteem() {
        return rewardVestingSteem;
    }

    /**
     * @return the curationRewards
     */
    public long getCurationRewards() {
        return curationRewards;
    }

    /**
     * @return the postingRewards
     */
    public long getPostingRewards() {
        return postingRewards;
    }

    /**
     * @return the vestingShares
     */
    public Asset getVestingShares() {
        return vestingShares;
    }

    /**
     * @return the delegatedVestingShares
     */
    public Asset getDelegatedVestingShares() {
        return delegatedVestingShares;
    }

    /**
     * @return the receivedVestingShares
     */
    public Asset getReceivedVestingShares() {
        return receivedVestingShares;
    }

    /**
     * @return the vestingWithdrawRate
     */
    public Asset getVestingWithdrawRate() {
        return vestingWithdrawRate;
    }

    /**
     * @return the nextVestingWithdrawal
     */
    public TimePointSec getNextVestingWithdrawal() {
        return nextVestingWithdrawal;
    }

    /**
     * @return the withdrwan
     */
    public long getWithdrwan() {
        return withdrwan;
    }

    /**
     * @return the toWithdraw
     */
    public long getToWithdraw() {
        return toWithdraw;
    }

    /**
     * @return the withdrawRoutes
     */
    public int getWithdrawRoutes() {
        return withdrawRoutes;
    }

    /**
     * @return the proxiedVsfVotes
     */
    public List<Long> getProxiedVsfVotes() {
        return proxiedVsfVotes;
    }

    /**
     * @return the witnessesVotedFor
     */
    public int getWitnessesVotedFor() {
        return witnessesVotedFor;
    }

    /**
     * @return the averageBandwidth
     */
    public long getAverageBandwidth() {
        return averageBandwidth;
    }

    /**
     * @return the lifetimeBandwidth
     */
    public long getLifetimeBandwidth() {
        return lifetimeBandwidth;
    }

    /**
     * @return the lastBandwidthUpdate
     */
    public TimePointSec getLastBandwidthUpdate() {
        return lastBandwidthUpdate;
    }

    /**
     * @return the averageMarketBandwidth
     */
    public long getAverageMarketBandwidth() {
        return averageMarketBandwidth;
    }

    /**
     * @return the lifetimeMarketBandwidth
     */
    public long getLifetimeMarketBandwidth() {
        return lifetimeMarketBandwidth;
    }

    /**
     * @return the lastMarketBandwidthUpdate
     */
    public TimePointSec getLastMarketBandwidthUpdate() {
        return lastMarketBandwidthUpdate;
    }

    /**
     * @return the lastPost
     */
    public TimePointSec getLastPost() {
        return lastPost;
    }

    /**
     * @return the lastRootPost
     */
    public TimePointSec getLastRootPost() {
        return lastRootPost;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
