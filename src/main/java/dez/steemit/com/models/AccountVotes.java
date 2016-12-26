package dez.steemit.com.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import dez.steemit.com.models.votes.Vote;

public class AccountVotes {
	private int requestId;
	private Vote[] votes;

	@JsonProperty("id")
	public int getRequestId() {
		return requestId;
	}

	@JsonProperty("result")
	public Vote[] getVotes() {
		return votes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
