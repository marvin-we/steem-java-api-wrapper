/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with SteemJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UByte;
import org.joou.UInteger;
import org.joou.ULong;
import org.joou.UShort;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.BlockId;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.plugins.apis.condenser.models.ExtendedDynamicGlobalProperties;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

/**
 * This class represents the Steem "dynamic_global_property_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DynamicGlobalProperty {
    // Original type is id_type which is a uint16_t.
    private UShort id;
    @JsonProperty("head_block_number")
    private UInteger headBlockNumber;
    @JsonProperty("head_block_id")
    private BlockId headBlockId;
    @JsonProperty("time")
    private TimePointSec time;
    @JsonProperty("current_witness")
    private AccountName currentWitness;
    @JsonProperty("total_pow")
    private ULong totalPow;
    @JsonProperty("num_pow_witnesses")
    private UInteger numPowWitnesses;
    @JsonProperty("virtual_supply")
    private Asset virtualSupply;
    @JsonProperty("current_supply")
    private Asset currentSupply;
    @JsonProperty("confidential_supply")
    private Asset confidentialSupply;
    @JsonProperty("init_sbd_supply")
    private Asset initSbdSupply;
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
    @JsonProperty("sbd_interest_rate")
    /** This property defines the interest rate that SBD deposits receive. */
    private UShort sdbInterestRate;
    @JsonProperty("sbd_print_rate")
    private UShort sdbPrintRate;
    @JsonProperty("maximum_block_size")
    private UInteger maximumBlockSize;
    @JsonProperty("required_actions_partition_percent")
    private UShort requiredActionsPartitionPercent;
    @JsonProperty("current_aslot")
    private ULong currentAslot;
    // Original type is uint128 so we use BigInteger here.
    @JsonProperty("recent_slots_filled")
    private BigInteger recentSlotsFilled;
    @JsonProperty("participation_count")
    private UByte participationCount;
    @JsonProperty("last_irreversible_block_num")
    private UInteger lastIrreversibleBlockNum;
    @JsonProperty("vote_power_reserve_rate")
    private UInteger votePowerReserveRate;
    @JsonProperty("delegation_return_period")
    private UInteger delegationReturnPeriod;
    @JsonProperty("reverse_auction_seconds")
    private ULong reverseAuctionSeconds;
    @JsonProperty("available_account_subsidies")
    private long availableAccountSubsidies;
    @JsonProperty("sbd_stop_percent")
    private UShort sbdStopPercent;
    @JsonProperty("sbd_start_percent")
    private UShort sbdStartPercent;
    @JsonProperty("sbd_stop_adjust")
    private UShort sbdStopAdjust;
    @JsonProperty("next_maintenance_time")
    private TimePointSec nextMaintenanceTime;
    @JsonProperty("last_budget_time")
    private TimePointSec lastBudgetTime;
    @JsonProperty("content_reward_percent")
    private UShort contentRewardPercent;
    @JsonProperty("vesting_reward_percent")
    private UShort vestingRewardPercent;
    @JsonProperty("sps_fund_percent")
    private UShort spsFundPercent;
    @JsonProperty("sps_interval_ledger")
    private Asset spsIntervalLedger;
    @JsonProperty("downvote_pool_percent")
    private UShort downvotePoolPercent;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * 
     * Visibility <code>protected</code> as
     * {@link ExtendedDynamicGlobalProperties} is a child class.
     */
    protected DynamicGlobalProperty() {
    }

    /**
     * @return the id
     */
    public UShort getId() {
        return id;
    }

    /**
     * @return the headBlockNumber
     */
    public UInteger getHeadBlockNumber() {
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
     * @return the totalPow
     */
    public ULong getTotalPow() {
        return totalPow;
    }

    /**
     * @return the numPowWitnesses
     */
    public UInteger getNumPowWitnesses() {
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
     * @return the initSbdSupply
     */
    public Asset getInitSbdSupply() {
        return initSbdSupply;
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
     * @return the sdbInterestRate
     */
    public UShort getSdbInterestRate() {
        return sdbInterestRate;
    }

    /**
     * @return the sdbPrintRate
     */
    public UShort getSdbPrintRate() {
        return sdbPrintRate;
    }

    /**
     * @return the maximumBlockSize
     */
    public UInteger getMaximumBlockSize() {
        return maximumBlockSize;
    }

    /**
     * @return the requiredActionsPartitionPercent
     */
    public UShort getRequiredActionsPartitionPercent() {
        return requiredActionsPartitionPercent;
    }

    /**
     * @return the currentAslot
     */
    public ULong getCurrentAslot() {
        return currentAslot;
    }

    /**
     * @return the recentSlotsFilled
     */
    public BigInteger getRecentSlotsFilled() {
        return recentSlotsFilled;
    }

    /**
     * @return the participationCount
     */
    public UByte getParticipationCount() {
        return participationCount;
    }

    /**
     * @return the lastIrreversibleBlockNum
     */
    public UInteger getLastIrreversibleBlockNum() {
        return lastIrreversibleBlockNum;
    }

    /**
     * @return the votePowerReserveRate
     */
    public UInteger getVotePowerReserveRate() {
        return votePowerReserveRate;
    }

    /**
     * @return the delegationReturnPeriod
     */
    public UInteger getDelegationReturnPeriod() {
        return delegationReturnPeriod;
    }

    /**
     * @return the reverseAuctionSeconds
     */
    public ULong getReverseAuctionSeconds() {
        return reverseAuctionSeconds;
    }

    /**
     * @return the availableAccountSubsidies
     */
    public long getAvailableAccountSubsidies() {
        return availableAccountSubsidies;
    }

    /**
     * @return the sbdStopPercent
     */
    public UShort getSbdStopPercent() {
        return sbdStopPercent;
    }

    /**
     * @return the sbdStartPercent
     */
    public UShort getSbdStartPercent() {
        return sbdStartPercent;
    }

    /**
     * @return the sbdStopAdjust
     */
    public UShort getSbdStopAdjust() {
        return sbdStopAdjust;
    }

    /**
     * @return the nextMaintenanceTime
     */
    public TimePointSec getNextMaintenanceTime() {
        return nextMaintenanceTime;
    }

    /**
     * @return the lastBudgetTime
     */
    public TimePointSec getLastBudgetTime() {
        return lastBudgetTime;
    }

    /**
     * @return the contentRewardPercent
     */
    public UShort getContentRewardPercent() {
        return contentRewardPercent;
    }

    /**
     * @return the vestingRewardPercent
     */
    public UShort getVestingRewardPercent() {
        return vestingRewardPercent;
    }

    /**
     * @return the spsFundPercent
     */
    public UShort getSpsFundPercent() {
        return spsFundPercent;
    }

    /**
     * @return the spsIntervalLedger
     */
    public Asset getSpsIntervalLedger() {
        return spsIntervalLedger;
    }

    /**
     * @return the downvotePoolPercent
     */
    public UShort getDownvotePoolPercent() {
        return downvotePoolPercent;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
