package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "chain_properties" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class MedianProps {
    @JsonProperty("account_creation_fee")
    private Asset accountCreationFee;
    @JsonProperty("maximum_block_size")
    private long maximumBlockSize;
    @JsonProperty("sbd_interest_rate")
    private int sdbInterestRate;

    /**
     * 
     * @return
     */
    public Asset getAccountCreationFee() {
        return accountCreationFee;
    }

    /**
     * 
     * @return
     */
    public long getMaximumBlockSize() {
        return maximumBlockSize;
    }

    /**
     * 
     * @return
     */
    public int getSdbInterestRate() {
        return sdbInterestRate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
