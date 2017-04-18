package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ChainProperties {
    @JsonProperty("account_creation_fee")
    private String accountCreationFee;
    @JsonProperty("maximum_block_size")
    private long maximumBlockSize;
    @JsonProperty("sbd_interest_rate")
    private int sdbInterestRate;

    public String getAccountCreationFee() {
        return accountCreationFee;
    }

    public long getMaximumBlockSize() {
        return maximumBlockSize;
    }

    public int getSdbInterestRate() {
        return sdbInterestRate;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
