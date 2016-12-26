package dez.steemit.com.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCount {
	private int requestId;
	private int count;

	@JsonProperty("id")
	public int getRequestId() {
		return requestId;
	}

	@JsonProperty("result")
	public int getCount() {
		return count;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
