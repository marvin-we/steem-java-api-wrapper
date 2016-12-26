package dez.steemit.com.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import dez.steemit.com.models.operations.AccountAction;

public class AccountHistory {
	private Integer requestId;
	private AccountAction[] result;

	@JsonProperty("id")
	public int getRequestId() {
		return requestId;
	}


	public AccountAction[] getResult() {
		return result;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
