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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * This class represents a Steem "witness_schedule_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessSchedule implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    // Original type is "id_type".
    private long id;
    @JsonProperty("current_virtual_time")
    // Original type is "uint128".
    private BigInteger currentVirtualTime;
    // Original type is "uint32_t".
    @JsonProperty("next_shuffle_block_num")
    private int nextShuffleBlockNum;
    @JsonProperty("current_shuffled_witnesses")
    private String currentShuffledWitnesses;
    // Original type is "uint8_t".
    @JsonProperty("num_scheduled_witnesses")
    private short numScheduledWitnesses;
    // Original type is "uint8_t".
    @JsonProperty("top19_weight")
    private short top19Weight;
    // Original type is "uint8_t".
    @JsonProperty("hardfork_required_witnesses")
    private short hardforkRequiredWitnesses;
    // Original type is "uint8_t".
    @JsonProperty("max_voted_witnesses")
    private short maxVotedWitnesses;
    // Original type is "uint8_t".
    @JsonProperty("max_runner_witnesses")
    private short maxRunnerWitnesses;
    // Original type is "uint8_t".
    @JsonProperty("max_miner_witnesses")
    private short maxMinerWitnesses;
    // Original type is "uint8_t".
    @JsonProperty("timeshare_weight")
    private short timeshareWeight;
    // Original type is "uint8_t".
    @JsonProperty("miner_weight")
    private short minerWeight;
    // Original type is "uint32_t".
    @JsonProperty("witness_pay_normalization_factor")
    private int witnessPayNormalizationFactor;
    @JsonProperty("median_props")
    private ChainProperties medianProps;
    // Original type is version which is a "uint32_t". The actual returned value
    // is the real version (e.g. 0.19.0) so we use String here.
    @JsonProperty("majority_version")
    private String majorityVersion;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private WitnessSchedule() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the currentVirtualTime
     */
    public BigInteger getCurrentVirtualTime() {
        return currentVirtualTime;
    }

    /**
     * @return the nextShuffleBlockNum
     */
    public int getNextShuffleBlockNum() {
        return nextShuffleBlockNum;
    }

    /**
     * @return the currentShuffledWitnesses
     */
    public String getCurrentShuffledWitnesses() {
        return currentShuffledWitnesses;
    }

    /**
     * @return the numScheduledWitnesses
     */
    public short getNumScheduledWitnesses() {
        return numScheduledWitnesses;
    }

    /**
     * @return the top19Weight
     */
    public short getTop19Weight() {
        return top19Weight;
    }

    /**
     * @return the hardforkRequiredWitnesses
     */
    public short getHardforkRequiredWitnesses() {
        return hardforkRequiredWitnesses;
    }

    /**
     * @return the maxVotedWitnesses
     */
    public short getMaxVotedWitnesses() {
        return maxVotedWitnesses;
    }

    /**
     * @return the maxRunnerWitnesses
     */
    public short getMaxRunnerWitnesses() {
        return maxRunnerWitnesses;
    }

    /**
     * @return the maxMinerWitnesses
     */
    public short getMaxMinerWitnesses() {
        return maxMinerWitnesses;
    }

    /**
     * @return the timeshareWeight
     */
    public short getTimeshareWeight() {
        return timeshareWeight;
    }

    /**
     * @return the minerWeight
     */
    public short getMinerWeight() {
        return minerWeight;
    }

    /**
     * @return the witnessPayNormalizationFactor
     */
    public int getWitnessPayNormalizationFactor() {
        return witnessPayNormalizationFactor;
    }

    /**
     * @return the medianProps
     */
    public ChainProperties getMedianProps() {
        return medianProps;
    }

    /**
     * @return the majorityVersion
     */
    public String getMajorityVersion() {
        return majorityVersion;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
