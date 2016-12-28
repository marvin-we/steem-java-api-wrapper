package dez.steemit.com.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author http://steemit.com/@dez1337
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
}
