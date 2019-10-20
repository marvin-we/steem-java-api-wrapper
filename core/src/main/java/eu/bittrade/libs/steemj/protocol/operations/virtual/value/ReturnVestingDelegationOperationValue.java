package eu.bittrade.libs.steemj.protocol.operations.virtual.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

public class ReturnVestingDelegationOperationValue {
	
	private AccountName account;
    @JsonProperty("vesting_shares")
    private Asset vestingShares;
	public AccountName getAccount() {
		return account;
	}
	public Asset getVestingShares() {
		return vestingShares;
	}
    
    

}
