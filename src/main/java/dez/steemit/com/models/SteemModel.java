package dez.steemit.com.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SteemModel {
	private int requestId;
	
	@JsonProperty("id")
	public int getRequestId() {
		return requestId;
	}
}
