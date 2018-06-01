package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * This class represents the Steem "dynamic_global_property_api_obj" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DynamicGlobalProperty implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    // Original type is id_type which is a uint16_t so we use int here.
    private int id;
    // Original type is uint32_t so we use long here.
    @JsonProperty("head_block_number")
    private long headBlockNumber;
    @JsonProperty("head_block_id")
    private BlockId headBlockId;
    @JsonProperty("time")
    private TimePointSec time;
    @JsonProperty("current_witness")
    private AccountName currentWitness;
    // The original type is uint64_t so we use BigInteger here.
    @JsonProperty("total_pow")
    private BigInteger totalPow;
    // Original type is uint32_t so we use long here.
    @JsonProperty("num_pow_witnesses")
    private long numPowWitnesses;
    @JsonProperty("virtual_supply")
    private Asset virtualSupply;
    @JsonProperty("current_supply")
    private Asset currentSupply;
    @JsonProperty("confidential_supply")
    private Asset confidentialSupply;
    @JsonProperty("current_sbd_supply")
    private Asset currentSdbSupply;
    @JsonProperty("confidential_sbd_supply")
    private Asset confientialSdbSupply;
    @JsonProperty("total_vesting_fund_steem")
    private Asset totalVestingFundSteem;
    @JsonProperty("total_vesting_shares")
    private Asset totalVestingShares;
    @JsonProperty("total_reward_fund_steem")
    private Asset totalRewardFundSteem;
    // Original type is uint128 so we use BigInteger here.
    @JsonProperty("total_reward_shares2")
    private BigInteger totalRewardShares2;
    @JsonProperty("pending_rewarded_vesting_shares")
    private Asset pendingRewardedVestingShares;
    @JsonProperty("pending_rewarded_vesting_steem")
    private Asset pendingRewardedVestingSteem;
    // Original type is uint16_t so we use int here.
    @JsonProperty("sbd_interest_rate")
    private int sdbInterestRate;
    // Original type is uint16_t so we use int here.
    @JsonProperty("sbd_print_rate")
    private int sdbPrintRate;
    // Original type is uint32_t so we use long here.
    @JsonProperty("maximum_block_size")
    private long maximumBlockSize;
    // Original type is uint64_t so we use BigInteger here.
    @JsonProperty("current_aslot")
    private BigInteger currentAslot;
    // Original type is uint128 so we use BigInteger here.
    @JsonProperty("recent_slots_filled")
    private BigInteger recentSlotsFilled;
    // Original type is uint8_t so we use short here.
    @JsonProperty("participation_count")
    private short participationCount;
    // Original type is uint32_t so we use long here.
    @JsonProperty("last_irreversible_block_num")
    private long lastIrreversibleBlockNum;
    // Original type is uint32_t so we use long here.
    @JsonProperty("vote_power_reserve_rate")
    private long votePowerReserveRate;
    // Original type is uint32_t so we use long here.
    @JsonProperty("current_reserve_ratio")
    private long currentReserveRatio;
    // Original type is uint64_t so we use BigInteger here.
    @JsonProperty("average_block_size")
    private int avarageBlockSize;
    // Original type is uint128_t so we use BigInteger here.
    @JsonProperty("max_virtual_bandwidth")
    private String maxVirtualBandwidth;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private DynamicGlobalProperty() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the headBlockNumber
     */
    public long getHeadBlockNumber() {
        return headBlockNumber;
    }

    /**
     * @return the headBlockId
     */
    public BlockId getHeadBlockId() {
        return headBlockId;
    }

    /**
     * @return the time
     */
    public TimePointSec getTime() {
        return time;
    }

    /**
     * @return the currentWitness
     */
    public AccountName getCurrentWitness() {
        return currentWitness;
    }

    /**
     * @return The total POW accumulated, aka the sum of num_pow_witness at the
     *         time new POW is added.
     */
    public BigInteger getTotalPow() {
        return totalPow;
    }

    /**
     * @return The current count of how many pending POW witnesses there are,
     *         determines the difficulty of doing pow.
     */
    public long getNumPowWitnesses() {
        return numPowWitnesses;
    }

    /**
     * @return the virtualSupply
     */
    public Asset getVirtualSupply() {
        return virtualSupply;
    }

    /**
     * @return the currentSupply
     */
    public Asset getCurrentSupply() {
        return currentSupply;
    }

    /**
     * @return the confidentialSupply
     */
    public Asset getConfidentialSupply() {
        return confidentialSupply;
    }

    /**
     * @return the currentSdbSupply
     */
    public Asset getCurrentSdbSupply() {
        return currentSdbSupply;
    }

    /**
     * @return the confientialSdbSupply
     */
    public Asset getConfientialSdbSupply() {
        return confientialSdbSupply;
    }

    /**
     * @return the totalVestingFundSteem
     */
    public Asset getTotalVestingFundSteem() {
        return totalVestingFundSteem;
    }

    /**
     * @return the totalVestingShares
     */
    public Asset getTotalVestingShares() {
        return totalVestingShares;
    }

    /**
     * @return the totalRewardFundSteem
     */
    public Asset getTotalRewardFundSteem() {
        return totalRewardFundSteem;
    }

    /**
     * @return the totalRewardShares2
     */
    public BigInteger getTotalRewardShares2() {
        return totalRewardShares2;
    }

    /**
     * @return the pendingRewardedVestingShares
     */
    public Asset getPendingRewardedVestingShares() {
        return pendingRewardedVestingShares;
    }

    /**
     * @return the pendingRewardedVestingSteem
     */
    public Asset getPendingRewardedVestingSteem() {
        return pendingRewardedVestingSteem;
    }

    /**
     * @return This property defines the interest rate that SBD deposits
     *         receive.
     */
    public int getSdbInterestRate() {
        return sdbInterestRate;
    }

    /**
     * @return the sdbPrintRate
     */
    public int getSdbPrintRate() {
        return sdbPrintRate;
    }

    /**
     * @return Maximum block size is decided by the set of active witnesses
     *         which change every round. Each witness posts what they think the
     *         maximum size should be as part of their witness properties, the
     *         median size is chosen to be the maximum block size for the round.
     *         <b>Notice:</b> The minimum value for maximum_block_size is
     *         defined by the protocol to prevent the network from getting stuck
     *         by witnesses attempting to set this too low.
     */
    public long getMaximumBlockSize() {
        return maximumBlockSize;
    }

    /**
     * @return The current absolute slot number. Equal to the total number of
     *         slots since genesis. Also equal to the total number of missed
     *         slots plus head_block_number.
     */
    public BigInteger getCurrentAslot() {
        return currentAslot;
    }

    /**
     * @return The recentSlotsFilled - Used to compute witness participation.
     */
    public BigInteger getRecentSlotsFilled() {
        return recentSlotsFilled;
    }

    /**
     * @return the participationCount
     */
    public short getParticipationCount() {
        return participationCount;
    }

    /**
     * @return the lastIrreversibleBlockNum
     */
    public long getLastIrreversibleBlockNum() {
        return lastIrreversibleBlockNum;
    }

    /**
     * @return The number of votes regenerated per day. Any user voting slower
     *         than this rate will be "wasting" voting power through spillover;
     *         any user voting faster than this rate will have their votes
     *         reduced.
     */
    public long getVotePowerReserveRate() {
        return votePowerReserveRate;
    }

    /**
     * @return the currentReserveRatio
     */
    public long getCurrentReserveRatio() {
        return currentReserveRatio;
    }

    /**
     * @return the avarageBlockSize
     */
    public int getAvarageBlockSize() {
        return avarageBlockSize;
    }

    /**
     * @return the maxVirtualBandwidth
     */
    public String getMaxVirtualBandwidth() {
        return maxVirtualBandwidth;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
