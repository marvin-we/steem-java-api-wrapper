package dez.steemit.com.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WitnessCount extends SteemModel {
	private int count;

	@JsonProperty("result")
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
