package dez.steemit.com.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import dez.steemit.com.models.operations.AccountAction;

public class AccountHistory extends SteemModel {
	private AccountAction[] result;

	public AccountAction[] getResult() {
		return result;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
