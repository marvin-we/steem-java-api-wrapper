package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Config {
    @JsonProperty("IS_TEST_NET")
    private Boolean isTestNet;
    /**
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("GRAPHENE_CURRENT_DB_VERSION")
    private String grapheneCurrentDbVersion;
    @JsonProperty("SBD_SYMBOL")
    private long sdbSymbol;
    @JsonProperty("STEEMIT_100_PERCENT")
    private short steemit100Percent;
    @JsonProperty("STEEMIT_1_PERCENT")
    private short steemit1Percent;
    @JsonProperty("STEEMIT_1_TENTH_PERCENT")
    private short steemit1TenthPercent;
    @JsonProperty("STEEMIT_ACCOUNT_RECOVERY_REQUEST_EXPIRATION_PERIOD")
    private String steemitAccountRecoveryRequestExpirationPeriod;
    @JsonProperty("STEEMIT_ACTIVE_CHALLENGE_COOLDOWN")
    private String steemitActiveChallengeCooldown;
    @JsonProperty("STEEMIT_ACTIVE_CHALLENGE_FEE")
    private Asset steemitActiveChallengeFee;
    @JsonProperty("STEEMIT_ADDRESS_PREFIX")
    private String steemitAddressPrefix;
    @JsonProperty("STEEMIT_APR_PERCENT_MULTIPLY_PER_BLOCK")
    private String steemitAprPercentMultiplyPerBlock;
    @JsonProperty("STEEMIT_APR_PERCENT_MULTIPLY_PER_HOUR")
    private String steemitAprPercentMultiplyPerHour;
    @JsonProperty("STEEMIT_APR_PERCENT_MULTIPLY_PER_ROUND")
    private String steemitAprPercentMultiplyPerRound;
    @JsonProperty("STEEMIT_APR_PERCENT_SHIFT_PER_BLOCK")
    private int steemitAprPercentShiftPerBlock;
    @JsonProperty("STEEMIT_APR_PERCENT_SHIFT_PER_HOUR")
    private int steemitAprPercentShiftPerHour;
    @JsonProperty("STEEMIT_APR_PERCENT_SHIFT_PER_ROUND")
    private int steemitAprPercentShiftPerRound;
    @JsonProperty("STEEMIT_BANDWIDTH_AVERAGE_WINDOW_SECONDS")
    private int steemitBrandwithAvarageWindowSeconds;
    @JsonProperty("STEEMIT_BANDWIDTH_PRECISION")
    private int steemitBrandwithPrecision;
    @JsonProperty("STEEMIT_BLOCKCHAIN_PRECISION")
    private int steemitBlockchainPrecision;
    @JsonProperty("STEEMIT_BLOCKCHAIN_PRECISION_DIGITS")
    private int steemitBlockchainPrecisionDigits;
    @JsonProperty("STEEMIT_BLOCKCHAIN_HARDFORK_VERSION")
    private String steemitBlockchainHardforkVersion;
    @JsonProperty("STEEMIT_BLOCKCHAIN_VERSION")
    private String steemitBlockchainVersion;
    @JsonProperty("STEEMIT_BLOCK_INTERVAL")
    private int steemitBlockInterval;
    @JsonProperty("STEEMIT_BLOCKS_PER_DAY")
    private int steemitBlocksPerDay;
    @JsonProperty("STEEMIT_BLOCKS_PER_HOUR")
    private int steemitBlocksPerHour;
    @JsonProperty("STEEMIT_BLOCKS_PER_YEAR")
    private long steemitBlocksPerYear;
    @JsonProperty("STEEMIT_CASHOUT_WINDOW_SECONDS")
    private int steemitCashoutWindowSeconds;
    @JsonProperty("STEEMIT_CASHOUT_WINDOW_SECONDS_PRE_HF12")
    private int steemitCashoutWindowSecondsPreHf12;
    @JsonProperty("STEEMIT_CASHOUT_WINDOW_SECONDS_PRE_HF17")
    private int steemitCashoutWindowSecondsPreHf17;
    @JsonProperty("STEEMIT_CHAIN_ID")
    private String steemitChainId;
    @JsonProperty("STEEMIT_COMMENT_REWARD_FUND_NAME")
    private String steemitCommentRewardFundName;
    @JsonProperty("STEEMIT_TEMP_LINEAR_REWARD_FUND_NAME")
    private String steemitTempLinearRewardFundName;
    @JsonProperty("STEEMIT_TEMP_LINEAR_REWARD_FUND_ID")
    private int steemitTempLinearRewardFundId;
    @JsonProperty("STEEMIT_CONTENT_APR_PERCENT")
    private int steemitContentAprPercent;
    @JsonProperty("STEEMIT_CONTENT_CONSTANT_HF0")
    private String steemitContentConstantHf0;
    @JsonProperty("STEEMIT_CONTENT_REWARD_PERCENT")
    private short steemitContentRewardPercent;
    @JsonProperty("STEEMIT_CONVERSION_DELAY")
    private long steemitConversionDelay;
    @JsonProperty("STEEMIT_CONVERSION_DELAY_PRE_HF_16")
    private long steemitConversionDelayPreHf16;
    @JsonProperty("STEEMIT_CREATE_ACCOUNT_DELEGATION_RATIO")
    private int steemitCreateAccountDelegationRatio;
    @JsonProperty("STEEMIT_CREATE_ACCOUNT_DELEGATION_TIME")
    private long steemitCreateAccountDelegationTime;
    @JsonProperty("STEEMIT_CREATE_ACCOUNT_WITH_STEEM_MODIFIER")
    private boolean steemitCreateAccountWithSteemModifier;
    @JsonProperty("STEEMIT_CURATE_APR_PERCENT")
    private int steemitCurateAprPercent;
    @JsonProperty("STEEMIT_DEFAULT_SBD_INTEREST_RATE")
    private int steemitDefaultSbdInterestRate;
    @JsonProperty("STEEMIT_EQUIHASH_K")
    private String steemitEquihashK;
    @JsonProperty("STEEMIT_EQUIHASH_N")
    private String steemitEquihashN;
    @JsonProperty("STEEMIT_FEED_HISTORY_WINDOW")
    private int steemitFeedHistoryWindow;
    @JsonProperty("STEEMIT_FEED_HISTORY_WINDOW_PRE_HF_16")
    private String steemitFeedHistoryWindowPreHf16;
    @JsonProperty("STEEMIT_FEED_INTERVAL_BLOCKS")
    private int steemitFeedIntervalBlocks;
    @JsonProperty("STEEMIT_FREE_TRANSACTIONS_WITH_NEW_ACCOUNT")
    private int steemitFreeTransactionsWithNewAccount;
    @JsonProperty("STEEMIT_GENESIS_TIME")
    private String steemitGenesisTime;
    @JsonProperty("STEEMIT_HARDFORK_REQUIRED_WITNESSES")
    private int steemitHardforkRequiredWitness;
    @JsonProperty("STEEMIT_INFLATION_NARROWING_PERIOD")
    private String steemitInflationNarrowingPeriod;
    @JsonProperty("STEEMIT_INFLATION_RATE_START_PERCENT")
    private short steemitInflationRateStartPercent;
    @JsonProperty("STEEMIT_INFLATION_RATE_STOP_PERCENT")
    private short steemitInflationRateStopPercent;
    @JsonProperty("STEEMIT_INIT_MINER_NAME")
    private String steemitInitMinerName;
    @JsonProperty("STEEMIT_INIT_PUBLIC_KEY_STR")
    private String steemitInitPublicKeyStr;
    @JsonProperty("STEEMIT_INIT_SUPPLY")
    private int steemitInitSupply;
    @JsonProperty("STEEMIT_INIT_TIME")
    private TimePointSec steemitInitTime;
    @JsonProperty("STEEMIT_IRREVERSIBLE_THRESHOLD")
    private int steemitIrreversibleThreshold;
    @JsonProperty("STEEMIT_LIQUIDITY_APR_PERCENT")
    private int steemitLiquidityAprPercent;
    @JsonProperty("STEEMIT_LIQUIDITY_REWARD_BLOCKS")
    private int steemitLiquidityRewardBlocks;
    @JsonProperty("STEEMIT_LIQUIDITY_REWARD_PERIOD_SEC")
    private long steemitLiquidityRewardPeriodSec;
    @JsonProperty("STEEMIT_LIQUIDITY_TIMEOUT_SEC")
    private String steemitLiquidityTimeoutSec;
    @JsonProperty("STEEMIT_MAX_ACCOUNT_NAME_LENGTH")
    private int steemitMaxAccountNameLength;
    @JsonProperty("STEEMIT_MAX_ACCOUNT_WITNESS_VOTES")
    private int steemitMaxAccountWitnessVotes;
    @JsonProperty("STEEMIT_MAX_ASSET_WHITELIST_AUTHORITIES")
    private int steemitMaxAssetWhitelistAuthorities;
    @JsonProperty("STEEMIT_MAX_AUTHORITY_MEMBERSHIP")
    private int steemitMaxAuthorityMembership;
    @JsonProperty("STEEMIT_MAX_BLOCK_SIZE")
    private long steemitMaxBlockSize;
    @JsonProperty("STEEMIT_MAX_CASHOUT_WINDOW_SECONDS")
    private long steemitMaxCashoutWindowSeconds;
    @JsonProperty("STEEMIT_MAX_COMMENT_DEPTH")
    private int steemitMaxCommentDepth;
    @JsonProperty("STEEMIT_MAX_COMMENT_DEPTH_PRE_HF17")
    private int steemitMaxCommentDepthPreHf17;
    @JsonProperty("STEEMIT_MAX_FEED_AGE_SECONDS")
    private long steemitMaxFeedAgeSeconds;
    /**
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("STEEMIT_MAX_FEED_AGE")
    private String steemitMaxFeedAge;
    @JsonProperty("STEEMIT_MAX_INSTANCE_ID")
    private String steemitMaxInstanceId;
    @JsonProperty("STEEMIT_MAX_MEMO_SIZE")
    private int steemitMaxMemoSize;
    @JsonProperty("STEEMIT_MAX_WITNESSES")
    private int steemitMaxWitnesses;
    /**
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("STEEMIT_MAX_MINER_WITNESSES")
    private int steemitMaxMinerWitnesses;
    @JsonProperty("STEEMIT_MAX_MINER_WITNESSES_HF0")
    private int steemitMaxMinerWitnessesHf0;
    @JsonProperty("STEEMIT_MAX_MINER_WITNESSES_HF17")
    private int steemitMaxMinerWitnessesHf17;
    @JsonProperty("STEEMIT_MAX_PERMLINK_LENGTH")
    private int steemitMaxPermlinkLength;
    @JsonProperty("STEEMIT_MAX_PROXY_RECURSION_DEPTH")
    private int steemitMaxProxyRecursionDepth;
    @JsonProperty("STEEMIT_MAX_RATION_DECAY_RATE")
    private long steemitMaxRationDecayRate;
    @JsonProperty("STEEMIT_MAX_RESERVE_RATIO")
    private int steemitMaxReserveRatio;
    /**
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("STEEMIT_MAX_RUNNER_WITNESSES")
    private int steemitMaxRunnerWitnesses;
    @JsonProperty("STEEMIT_MAX_RUNNER_WITNESSES_HF0")
    private int steemitMaxRunnerWitnessesHf0;
    @JsonProperty("STEEMIT_MAX_RUNNER_WITNESSES_HF17")
    private int steemitMaxRunnerWitnessesHf17;
    @JsonProperty("STEEMIT_MAX_SHARE_SUPPLY")
    private String steemitMAxShareSupply;
    @JsonProperty("STEEMIT_MAX_SIG_CHECK_DEPTH")
    private int steemitMaxSigCheckDepth;
    @JsonProperty("STEEMIT_MAX_TIME_UNTIL_EXPIRATION")
    private int steemitMaxTimeUntilExpiration;
    @JsonProperty("STEEMIT_MAX_TRANSACTION_SIZE")
    private long steemitMaxTransactionSize;
    @JsonProperty("STEEMIT_MAX_UNDO_HISTORY")
    private int steemitMaxUndoHistory;
    @JsonProperty("STEEMIT_MAX_URL_LENGTH")
    private int steemitMaxUrlLength;
    @JsonProperty("STEEMIT_MAX_VOTE_CHANGES")
    private int steemitMaxVoteChanges;
    /**
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("STEEMIT_MAX_VOTED_WITNESSES")
    private int steemitMaxVotedWitnesses;
    @JsonProperty("STEEMIT_MAX_VOTED_WITNESSES_HF0")
    private int steemitMaxVotedWitnessesHf0;
    @JsonProperty("STEEMIT_MAX_VOTED_WITNESSES_HF17")
    private int steemitMaxVotedWitnessesHf17;
    @JsonProperty("STEEMIT_MAX_WITHDRAW_ROUTES")
    private int steemitMaxWithdrawRoutes;
    @JsonProperty("STEEMIT_MAX_WITNESS_URL_LENGTH")
    private int steemitMaxWitnessUrlLength;
    @JsonProperty("STEEMIT_MIN_ACCOUNT_CREATION_FEE")
    private int steemitMinAccountCreationFee;
    @JsonProperty("STEEMIT_MIN_ACCOUNT_NAME_LENGTH")
    private int steemitMinAccountNameLength;
    @JsonProperty("STEEMIT_MIN_BLOCK_SIZE_LIMIT")
    private int steemitMinBlockSizeLimit;
    @JsonProperty("STEEMIT_MIN_CONTENT_REWARD")
    private String steemitMinContentReward;
    @JsonProperty("STEEMIT_MIN_CURATE_REWARD")
    private String steemitMinCurateReward;
    @JsonProperty("STEEMIT_MIN_PERMLINK_LENGTH")
    private int steemitMinPermlinkLength;
    @JsonProperty("STEEMIT_MIN_REPLY_INTERVAL")
    private int steemitMinReplyInterval;
    @JsonProperty("STEEMIT_MIN_ROOT_COMMENT_INTERVAL")
    private int steemitMinRootCommentInterval;
    @JsonProperty("STEEMIT_MIN_VOTE_INTERVAL_SEC")
    private long steemitMinVoteIntervalSec;
    @JsonProperty("STEEMIT_MINER_ACCOUNT")
    private String steemitMinerAccount;
    @JsonProperty("STEEMIT_MINER_PAY_PERCENT")
    private int steemitMinerPayPercent;
    @JsonProperty("STEEMIT_MIN_FEEDS")
    private int steemitMinFeeds;
    @JsonProperty("STEEMIT_MINING_REWARD")
    private String steemitMiningReward;
    @JsonProperty("STEEMIT_MINING_TIME")
    private String steemitMiningTime;
    @JsonProperty("STEEMIT_MIN_LIQUIDITY_REWARD")
    private String steemitMinLiquidityReward;
    @JsonProperty("STEEMIT_MIN_LIQUIDITY_REWARD_PERIOD_SEC")
    private long steemitMinLiquidityRewardPeriodSec;
    @JsonProperty("STEEMIT_MIN_PAYOUT_SBD")
    private String steemitMinPayoutSdb;
    @JsonProperty("STEEMIT_MIN_POW_REWARD")
    private String steemitMinPowReward;
    @JsonProperty("STEEMIT_MIN_PRODUCER_REWARD")
    private String steemitMinProducterReward;
    @JsonProperty("STEEMIT_MIN_RATION")
    private long steemitMinRation;
    @JsonProperty("STEEMIT_MIN_TRANSACTION_EXPIRATION_LIMIT")
    private int steemitMinTransactionExpirationLimit;
    @JsonProperty("STEEMIT_MIN_TRANSACTION_SIZE_LIMIT")
    private int steemitMinTransactionSizeLimit;
    @JsonProperty("STEEMIT_MIN_UNDO_HISTORY")
    private int steemitMinUndoHistory;
    @JsonProperty("STEEMIT_NULL_ACCOUNT")
    private String steemitNullAccount;
    @JsonProperty("STEEMIT_NUM_INIT_MINERS")
    private int steemitNumInitMiners;
    @JsonProperty("STEEMIT_ORIGINAL_MIN_ACCOUNT_CREATION_FEE")
    private long steemitOriginalMinAccountCreationFee;
    @JsonProperty("STEEMIT_OWNER_AUTH_HISTORY_TRACKING_START_BLOCK_NUM")
    private long steemitOwnerAuthHistoryTrackingStartBlockNum;
    @JsonProperty("STEEMIT_OWNER_AUTH_RECOVERY_PERIOD")
    private long steemitOwnerAuthRecoveryPeriod;
    @JsonProperty("STEEMIT_OWNER_CHALLENGE_COOLDOWN")
    private long steemitOwnerChallengeCooldown;
    @JsonProperty("STEEMIT_OWNER_CHALLENGE_FEE")
    private Asset steemitOwnerChallengeFee;
    @JsonProperty("STEEMIT_OWNER_UPDATE_LIMIT")
    private int steemitOwnerUpdateLimit;
    @JsonProperty("STEEMIT_POST_AVERAGE_WINDOW")
    private int steemitPostAvarageWindow;
    @JsonProperty("STEEMIT_POST_MAX_BANDWIDTH")
    private long steemitPostMaxBandwidth;
    @JsonProperty("STEEMIT_POST_REWARD_FUND_NAME")
    private String steemitPostRewardFundName;
    @JsonProperty("STEEMIT_POST_WEIGHT_CONSTANT")
    private int steemitPostWeightConstant;
    @JsonProperty("STEEMIT_POW_APR_PERCENT")
    private int steemitPowAprPercent;
    @JsonProperty("STEEMIT_PRODUCER_APR_PERCENT")
    private int steemitProducerAprPercent;
    @JsonProperty("STEEMIT_PROXY_TO_SELF_ACCOUNT")
    private String steemitProxyToSelfAccount;
    @JsonProperty("STEEMIT_SBD_INTEREST_COMPOUND_INTERVAL_SEC")
    private long steemitSDBInterestCompoundIntervalSec;
    @JsonProperty("STEEMIT_SECONDS_PER_YEAR")
    private long steemitSecondsPerYear;
    /**
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("STEEMIT_RECENT_RSHARES_DECAY_RATE")
    private long steemitRecentRSharesDecayRate;
    @JsonProperty("STEEMIT_RECENT_RSHARES_DECAY_RATE_HF19")
    private long steemitRecentRSharesDecayRateHf19;
    @JsonProperty("STEEMIT_RECENT_RSHARES_DECAY_RATE_HF17")
    private long steemitRecentRSharesDecayRateHf17;
    @JsonProperty("STEEMIT_REVERSE_AUCTION_WINDOW_SECONDS")
    private int steemitReverseAuctionWindowSeconds;
    @JsonProperty("STEEMIT_ROOT_POST_PARENT")
    private String steemitRootPostParent;
    @JsonProperty("STEEMIT_SAVINGS_WITHDRAW_REQUEST_LIMIT")
    private long steemitSavingsWithdrawRequestLimit;
    @JsonProperty("STEEMIT_SAVINGS_WITHDRAW_TIME")
    private long steemitSavingsWithdrawTime;
    @JsonProperty("STEEMIT_SBD_START_PERCENT")
    private short steemitSbdStartPercent;
    @JsonProperty("STEEMIT_SBD_STOP_PERCENT")
    private short steemitSbdStopPercent;
    @JsonProperty("STEEMIT_SECOND_CASHOUT_WINDOW")
    private long steemitSecondCashcoutWindow;
    @JsonProperty("STEEMIT_SOFT_MAX_COMMENT_DEPTH")
    private int steemitSoftMaxCommentDepth;
    @JsonProperty("STEEMIT_START_MINER_VOTING_BLOCK")
    private long steemitStartMinerVotingBlock;
    @JsonProperty("STEEMIT_START_VESTING_BLOCK")
    private int steemitStartVestingBlock;
    @JsonProperty("STEEMIT_SYMBOL")
    private String steemitSymbol;
    @JsonProperty("STEEMIT_TEMP_ACCOUNT")
    private String steemitTempAccount;
    /**
     * @deprecated Has been removed with HF 19. Depending on the version of the
     *             used Steem Node the value of this field may be null.
     */
    @Deprecated
    @JsonProperty("STEEMIT_UPVOTE_LOCKOUT")
    private long steemitUpvoteLockout;
    @JsonProperty("STEEMIT_UPVOTE_LOCKOUT_HF7")
    private long steemitUpvoteLockoutHf7;
    @JsonProperty("STEEMIT_UPVOTE_LOCKOUT_HF17")
    private long steemitUpvoteLockoutHf17;
    @JsonProperty("STEEMIT_VESTING_FUND_PERCENT")
    private short steemitVestingFundPercent;
    @JsonProperty("STEEMIT_VESTING_WITHDRAW_INTERVALS")
    private int steemitVestingWithdrawIntervals;
    @JsonProperty("STEEMIT_VESTING_WITHDRAW_INTERVALS_PRE_HF_16")
    private int steemitVestingWithdrawIntervalsPreHf16;
    @JsonProperty("STEEMIT_VESTING_WITHDRAW_INTERVAL_SECONDS")
    private int steemitVestingWithdrawIntervalSeconds;
    @JsonProperty("STEEMIT_VOTE_CHANGE_LOCKOUT_PERIOD")
    private int steemitVoteChangeLockoutPeriod;
    @JsonProperty("STEEMIT_VOTE_DUST_THRESHOLD")
    private int steemitVoteDustThreshold;
    @JsonProperty("STEEMIT_VOTE_REGENERATION_SECONDS")
    private int steemitVoteRegenerationSeconds;
    @JsonProperty("STEEM_SYMBOL")
    private long steemSymbol;
    @JsonProperty("STMD_SYMBOL")
    private long stmdSymbol;
    @JsonProperty("VESTS_SYMBOL")
    private long vestsSymbol;
    @JsonProperty("VIRTUAL_SCHEDULE_LAP_LENGTH")
    private BigInteger virtualScheduleLapLength;
    @JsonProperty("VIRTUAL_SCHEDULE_LAP_LENGTH2")
    private BigInteger virtualScheduleLapLength2;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Config() {
    }

    public Boolean getIsTestNet() {
        return isTestNet;
    }

    public String getGrapheneCurrentDbVersion() {
        return grapheneCurrentDbVersion;
    }

    public long getSdbSymbol() {
        return sdbSymbol;
    }

    public short getSteemit100Percent() {
        return steemit100Percent;
    }

    public short getSteemit1Percent() {
        return steemit1Percent;
    }

    public short getSteemit1TenthPercent() {
        return steemit1TenthPercent;
    }

    public String getSteemitAccountRecoveryRequestExpirationPeriod() {
        return steemitAccountRecoveryRequestExpirationPeriod;
    }

    public String getSteemitActiveChallengeCooldown() {
        return steemitActiveChallengeCooldown;
    }

    public Asset getSteemitActiveChallengeFee() {
        return steemitActiveChallengeFee;
    }

    public String getSteemitAddressPrefix() {
        return steemitAddressPrefix;
    }

    public String getSteemitAprPercentMultiplyPerBlock() {
        return steemitAprPercentMultiplyPerBlock;
    }

    public String getSteemitAprPercentMultiplyPerHour() {
        return steemitAprPercentMultiplyPerHour;
    }

    public String getSteemitAprPercentMultiplyPerRound() {
        return steemitAprPercentMultiplyPerRound;
    }

    public int getSteemitAprPercentShiftPerBlock() {
        return steemitAprPercentShiftPerBlock;
    }

    public int getSteemitAprPercentShiftPerHour() {
        return steemitAprPercentShiftPerHour;
    }

    public int getSteemitAprPercentShiftPerRound() {
        return steemitAprPercentShiftPerRound;
    }

    public int getSteemitBrandwithAvarageWindowSeconds() {
        return steemitBrandwithAvarageWindowSeconds;
    }

    public int getSteemitBrandwithPrecision() {
        return steemitBrandwithPrecision;
    }

    public int getSteemitBlockchainPrecision() {
        return steemitBlockchainPrecision;
    }

    public int getSteemitBlockchainPrecisionDigits() {
        return steemitBlockchainPrecisionDigits;
    }

    public String getSteemitBlockchainHardforkVersion() {
        return steemitBlockchainHardforkVersion;
    }

    public String getSteemitBlockchainVersion() {
        return steemitBlockchainVersion;
    }

    public int getSteemitBlockInterval() {
        return steemitBlockInterval;
    }

    public int getSteemitBlocksPerDay() {
        return steemitBlocksPerDay;
    }

    public int getSteemitBlocksPerHour() {
        return steemitBlocksPerHour;
    }

    public long getSteemitBlocksPerYear() {
        return steemitBlocksPerYear;
    }

    public int getSteemitCashoutWindowSeconds() {
        return steemitCashoutWindowSeconds;
    }

    public int getSteemitCashoutWindowSecondsPreHf12() {
        return steemitCashoutWindowSecondsPreHf12;
    }

    public int getSteemitCashoutWindowSecondsPreHf17() {
        return steemitCashoutWindowSecondsPreHf17;
    }

    public String getSteemitChainId() {
        return steemitChainId;
    }

    public String getSteemitCommentRewardFundName() {
        return steemitCommentRewardFundName;
    }

    public String getSteemitTempLinearRewardFundName() {
        return steemitTempLinearRewardFundName;
    }

    public int getSteemitTempLinearRewardFundId() {
        return steemitTempLinearRewardFundId;
    }

    public int getSteemitContentAprPercent() {
        return steemitContentAprPercent;
    }

    public String getSteemitContentConstantHf0() {
        return steemitContentConstantHf0;
    }

    public short getSteemitContentRewardPercent() {
        return steemitContentRewardPercent;
    }

    public long getSteemitConversionDelay() {
        return steemitConversionDelay;
    }

    public long getSteemitConversionDelayPreHf16() {
        return steemitConversionDelayPreHf16;
    }

    public int getSteemitCreateAccountDelegationRatio() {
        return steemitCreateAccountDelegationRatio;
    }

    public long getSteemitCreateAccountDelegationTime() {
        return steemitCreateAccountDelegationTime;
    }

    public boolean isSteemitCreateAccountWithSteemModifier() {
        return steemitCreateAccountWithSteemModifier;
    }

    public int getSteemitCurateAprPercent() {
        return steemitCurateAprPercent;
    }

    public int getSteemitDefaultSbdInterestRate() {
        return steemitDefaultSbdInterestRate;
    }

    public String getSteemitEquihashK() {
        return steemitEquihashK;
    }

    public String getSteemitEquihashN() {
        return steemitEquihashN;
    }

    public int getSteemitFeedHistoryWindow() {
        return steemitFeedHistoryWindow;
    }

    public String getSteemitFeedHistoryWindowPreHf16() {
        return steemitFeedHistoryWindowPreHf16;
    }

    public int getSteemitFeedIntervalBlocks() {
        return steemitFeedIntervalBlocks;
    }

    public int getSteemitFreeTransactionsWithNewAccount() {
        return steemitFreeTransactionsWithNewAccount;
    }

    public String getSteemitGenesisTime() {
        return steemitGenesisTime;
    }

    public int getSteemitHardforkRequiredWitness() {
        return steemitHardforkRequiredWitness;
    }

    public String getSteemitInflationNarrowingPeriod() {
        return steemitInflationNarrowingPeriod;
    }

    public short getSteemitInflationRateStartPercent() {
        return steemitInflationRateStartPercent;
    }

    public short getSteemitInflationRateStopPercent() {
        return steemitInflationRateStopPercent;
    }

    public String getSteemitInitMinerName() {
        return steemitInitMinerName;
    }

    public String getSteemitInitPublicKeyStr() {
        return steemitInitPublicKeyStr;
    }

    public int getSteemitInitSupply() {
        return steemitInitSupply;
    }

    public TimePointSec getSteemitInitTime() {
        return steemitInitTime;
    }

    public int getSteemitIrreversibleThreshold() {
        return steemitIrreversibleThreshold;
    }

    public int getSteemitLiquidityAprPercent() {
        return steemitLiquidityAprPercent;
    }

    public int getSteemitLiquidityRewardBlocks() {
        return steemitLiquidityRewardBlocks;
    }

    public long getSteemitLiquidityRewardPeriodSec() {
        return steemitLiquidityRewardPeriodSec;
    }

    public String getSteemitLiquidityTimeoutSec() {
        return steemitLiquidityTimeoutSec;
    }

    public int getSteemitMaxAccountNameLength() {
        return steemitMaxAccountNameLength;
    }

    public int getSteemitMaxAccountWitnessVotes() {
        return steemitMaxAccountWitnessVotes;
    }

    public int getSteemitMaxAssetWhitelistAuthorities() {
        return steemitMaxAssetWhitelistAuthorities;
    }

    public int getSteemitMaxAuthorityMembership() {
        return steemitMaxAuthorityMembership;
    }

    public long getSteemitMaxBlockSize() {
        return steemitMaxBlockSize;
    }

    public long getSteemitMaxCashoutWindowSeconds() {
        return steemitMaxCashoutWindowSeconds;
    }

    public int getSteemitMaxCommentDepth() {
        return steemitMaxCommentDepth;
    }

    public int getSteemitMaxCommentDepthPreHf17() {
        return steemitMaxCommentDepthPreHf17;
    }

    public long getSteemitMaxFeedAgeSeconds() {
        return steemitMaxFeedAgeSeconds;
    }

    public String getSteemitMaxFeedAge() {
        return steemitMaxFeedAge;
    }

    public String getSteemitMaxInstanceId() {
        return steemitMaxInstanceId;
    }

    public int getSteemitMaxMemoSize() {
        return steemitMaxMemoSize;
    }

    public int getSteemitMaxWitnesses() {
        return steemitMaxWitnesses;
    }

    public int getSteemitMaxMinerWitnesses() {
        return steemitMaxMinerWitnesses;
    }

    public int getSteemitMaxMinerWitnessesHf0() {
        return steemitMaxMinerWitnessesHf0;
    }

    public int getSteemitMaxMinerWitnessesHf17() {
        return steemitMaxMinerWitnessesHf17;
    }

    public int getSteemitMaxPermlinkLength() {
        return steemitMaxPermlinkLength;
    }

    public int getSteemitMaxProxyRecursionDepth() {
        return steemitMaxProxyRecursionDepth;
    }

    public long getSteemitMaxRationDecayRate() {
        return steemitMaxRationDecayRate;
    }

    public int getSteemitMaxReserveRatio() {
        return steemitMaxReserveRatio;
    }

    public int getSteemitMaxRunnerWitnesses() {
        return steemitMaxRunnerWitnesses;
    }

    public int getSteemitMaxRunnerWitnessesHf0() {
        return steemitMaxRunnerWitnessesHf0;
    }

    public int getSteemitMaxRunnerWitnessesHf17() {
        return steemitMaxRunnerWitnessesHf17;
    }

    public String getSteemitMAxShareSupply() {
        return steemitMAxShareSupply;
    }

    public int getSteemitMaxSigCheckDepth() {
        return steemitMaxSigCheckDepth;
    }

    public int getSteemitMaxTimeUntilExpiration() {
        return steemitMaxTimeUntilExpiration;
    }

    public long getSteemitMaxTransactionSize() {
        return steemitMaxTransactionSize;
    }

    public int getSteemitMaxUndoHistory() {
        return steemitMaxUndoHistory;
    }

    public int getSteemitMaxUrlLength() {
        return steemitMaxUrlLength;
    }

    public int getSteemitMaxVoteChanges() {
        return steemitMaxVoteChanges;
    }

    public int getSteemitMaxVotedWitnesses() {
        return steemitMaxVotedWitnesses;
    }

    public int getSteemitMaxVotedWitnessesHf0() {
        return steemitMaxVotedWitnessesHf0;
    }

    public int getSteemitMaxVotedWitnessesHf17() {
        return steemitMaxVotedWitnessesHf17;
    }

    public int getSteemitMaxWithdrawRoutes() {
        return steemitMaxWithdrawRoutes;
    }

    public int getSteemitMaxWitnessUrlLength() {
        return steemitMaxWitnessUrlLength;
    }

    public int getSteemitMinAccountCreationFee() {
        return steemitMinAccountCreationFee;
    }

    public int getSteemitMinAccountNameLength() {
        return steemitMinAccountNameLength;
    }

    public int getSteemitMinBlockSizeLimit() {
        return steemitMinBlockSizeLimit;
    }

    public String getSteemitMinContentReward() {
        return steemitMinContentReward;
    }

    public String getSteemitMinCurateReward() {
        return steemitMinCurateReward;
    }

    public int getSteemitMinPermlinkLength() {
        return steemitMinPermlinkLength;
    }

    public int getSteemitMinReplyInterval() {
        return steemitMinReplyInterval;
    }

    public int getSteemitMinRootCommentInterval() {
        return steemitMinRootCommentInterval;
    }

    public long getSteemitMinVoteIntervalSec() {
        return steemitMinVoteIntervalSec;
    }

    public String getSteemitMinerAccount() {
        return steemitMinerAccount;
    }

    public int getSteemitMinerPayPercent() {
        return steemitMinerPayPercent;
    }

    public int getSteemitMinFeeds() {
        return steemitMinFeeds;
    }

    public String getSteemitMiningReward() {
        return steemitMiningReward;
    }

    public String getSteemitMiningTime() {
        return steemitMiningTime;
    }

    public String getSteemitMinLiquidityReward() {
        return steemitMinLiquidityReward;
    }

    public long getSteemitMinLiquidityRewardPeriodSec() {
        return steemitMinLiquidityRewardPeriodSec;
    }

    public String getSteemitMinPayoutSdb() {
        return steemitMinPayoutSdb;
    }

    public String getSteemitMinPowReward() {
        return steemitMinPowReward;
    }

    public String getSteemitMinProducterReward() {
        return steemitMinProducterReward;
    }

    public long getSteemitMinRation() {
        return steemitMinRation;
    }

    public int getSteemitMinTransactionExpirationLimit() {
        return steemitMinTransactionExpirationLimit;
    }

    public int getSteemitMinTransactionSizeLimit() {
        return steemitMinTransactionSizeLimit;
    }

    public int getSteemitMinUndoHistory() {
        return steemitMinUndoHistory;
    }

    public String getSteemitNullAccount() {
        return steemitNullAccount;
    }

    public int getSteemitNumInitMiners() {
        return steemitNumInitMiners;
    }

    public long getSteemitOriginalMinAccountCreationFee() {
        return steemitOriginalMinAccountCreationFee;
    }

    public long getSteemitOwnerAuthHistoryTrackingStartBlockNum() {
        return steemitOwnerAuthHistoryTrackingStartBlockNum;
    }

    public long getSteemitOwnerAuthRecoveryPeriod() {
        return steemitOwnerAuthRecoveryPeriod;
    }

    public long getSteemitOwnerChallengeCooldown() {
        return steemitOwnerChallengeCooldown;
    }

    public Asset getSteemitOwnerChallengeFee() {
        return steemitOwnerChallengeFee;
    }

    public int getSteemitOwnerUpdateLimit() {
        return steemitOwnerUpdateLimit;
    }

    public int getSteemitPostAvarageWindow() {
        return steemitPostAvarageWindow;
    }

    public long getSteemitPostMaxBandwidth() {
        return steemitPostMaxBandwidth;
    }

    public String getSteemitPostRewardFundName() {
        return steemitPostRewardFundName;
    }

    public int getSteemitPostWeightConstant() {
        return steemitPostWeightConstant;
    }

    public int getSteemitPowAprPercent() {
        return steemitPowAprPercent;
    }

    public int getSteemitProducerAprPercent() {
        return steemitProducerAprPercent;
    }

    public String getSteemitProxyToSelfAccount() {
        return steemitProxyToSelfAccount;
    }

    public long getSteemitSDBInterestCompoundIntervalSec() {
        return steemitSDBInterestCompoundIntervalSec;
    }

    public long getSteemitSecondsPerYear() {
        return steemitSecondsPerYear;
    }

    public long getSteemitRecentRSharesDecayRate() {
        return steemitRecentRSharesDecayRate;
    }

    public long getSteemitRecentRSharesDecayRateHf19() {
        return steemitRecentRSharesDecayRateHf19;
    }

    public long getSteemitRecentRSharesDecayRateHf17() {
        return steemitRecentRSharesDecayRateHf17;
    }

    public int getSteemitReverseAuctionWindowSeconds() {
        return steemitReverseAuctionWindowSeconds;
    }

    public String getSteemitRootPostParent() {
        return steemitRootPostParent;
    }

    public long getSteemitSavingsWithdrawRequestLimit() {
        return steemitSavingsWithdrawRequestLimit;
    }

    public long getSteemitSavingsWithdrawTime() {
        return steemitSavingsWithdrawTime;
    }

    public short getSteemitSbdStartPercent() {
        return steemitSbdStartPercent;
    }

    public short getSteemitSbdStopPercent() {
        return steemitSbdStopPercent;
    }

    public long getSteemitSecondCashcoutWindow() {
        return steemitSecondCashcoutWindow;
    }

    public int getSteemitSoftMaxCommentDepth() {
        return steemitSoftMaxCommentDepth;
    }

    public long getSteemitStartMinerVotingBlock() {
        return steemitStartMinerVotingBlock;
    }

    public int getSteemitStartVestingBlock() {
        return steemitStartVestingBlock;
    }

    public String getSteemitSymbol() {
        return steemitSymbol;
    }

    public String getSteemitTempAccount() {
        return steemitTempAccount;
    }

    public long getSteemitUpvoteLockout() {
        return steemitUpvoteLockout;
    }

    public long getSteemitUpvoteLockoutHf7() {
        return steemitUpvoteLockoutHf7;
    }

    public long getSteemitUpvoteLockoutHf17() {
        return steemitUpvoteLockoutHf17;
    }

    public short getSteemitVestingFundPercent() {
        return steemitVestingFundPercent;
    }

    public int getSteemitVestingWithdrawIntervals() {
        return steemitVestingWithdrawIntervals;
    }

    public int getSteemitVestingWithdrawIntervalsPreHf16() {
        return steemitVestingWithdrawIntervalsPreHf16;
    }

    public int getSteemitVestingWithdrawIntervalSeconds() {
        return steemitVestingWithdrawIntervalSeconds;
    }

    public int getSteemitVoteChangeLockoutPeriod() {
        return steemitVoteChangeLockoutPeriod;
    }

    public int getSteemitVoteDustThreshold() {
        return steemitVoteDustThreshold;
    }

    public int getSteemitVoteRegenerationSeconds() {
        return steemitVoteRegenerationSeconds;
    }

    public long getSteemSymbol() {
        return steemSymbol;
    }

    public long getStmdSymbol() {
        return stmdSymbol;
    }

    public long getVestsSymbol() {
        return vestsSymbol;
    }

    public BigInteger getVirtualScheduleLapLength() {
        return virtualScheduleLapLength;
    }

    public BigInteger getVirtualScheduleLapLength2() {
        return virtualScheduleLapLength2;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
