package dez.steemit.com.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import dez.steemit.com.models.operations.Operation;

public class AccountHistory {
	private Operation op;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
