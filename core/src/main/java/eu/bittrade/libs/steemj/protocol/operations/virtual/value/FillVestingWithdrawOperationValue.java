package eu.bittrade.libs.steemj.protocol.operations.virtual.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

public class FillVestingWithdrawOperationValue {
	@JsonProperty("from_account")
    private AccountName fromAccount;
    @JsonProperty("to_account")
    private AccountName toAccount;
    private Asset withdrawn;
    private Asset deposited;
	public AccountName getFromAccount() {
		return fromAccount;
	}
	public AccountName getToAccount() {
		return toAccount;
	}
	public Asset getWithdrawn() {
		return withdrawn;
	}
	public Asset getDeposited() {
		return deposited;
	}
    
    

}
