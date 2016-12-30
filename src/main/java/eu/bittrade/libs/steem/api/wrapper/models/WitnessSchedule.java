package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author http://steemit.com/@dez1337
 */
public class WitnessSchedule {
    private long id;
    @JsonProperty("current_virtual_time")
    private String currentVirtualTime;
    @JsonProperty("next_shuffle_block_num")
    private String nextShuffleBlockNum;
    @JsonProperty("current_shuffled_witnesses")
    private String currentShuffledWitnesses;
    @JsonProperty("num_scheduled_witnesses")
    private int numScheduledWitnesses;
    @JsonProperty("top19_weight")
    private int top19Weight;
    @JsonProperty("timeshare_weight")
    private int timeshareWeight;
    @JsonProperty("miner_weight")
    private int minerWeight;
    @JsonProperty("witness_pay_normalization_factor")
    private int witnessPayNormalizationFactor;
    @JsonProperty("median_props")
    private MedianProps medianProps;
    @JsonProperty("majority_version")
    private String majorityVersion;

    public long getId() {
        return id;
    }

    public String getCurrentVirtualTime() {
        return currentVirtualTime;
    }

    public String getNextShuffleBlockNum() {
        return nextShuffleBlockNum;
    }

    public String getCurrentShuffledWitnesses() {
        return currentShuffledWitnesses;
    }

    public int getNumScheduledWitnesses() {
        return numScheduledWitnesses;
    }

    public int getTop19Weight() {
        return top19Weight;
    }

    public int getTimeshareWeight() {
        return timeshareWeight;
    }

    public int getMinerWeight() {
        return minerWeight;
    }

    public int getWitnessPayNormalizationFactor() {
        return witnessPayNormalizationFactor;
    }

    public MedianProps getMedianProps() {
        return medianProps;
    }

    public String getMajorityVersion() {
        return majorityVersion;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
