package eu.bittrade.libs.steemj.protocol.operations.virtual.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

public class ProducerRewardOperationValue {
	

    @JsonProperty("producer")
    private AccountName producer;
    @JsonProperty("vesting_shares")
    private Asset vestingShares;
    
    /**
     * Get the block producer.
     * 
     * @return The block producer.
     */
    public AccountName getProducer() {
        return producer;
    }

    /**
     * Get the amount of VESTS the <code>producer</code> got.
     * 
     * @return The vesting shares paid to the <code>producer</code>.
     */
    public Asset getVestingShares() {
        return vestingShares;
    }


}
