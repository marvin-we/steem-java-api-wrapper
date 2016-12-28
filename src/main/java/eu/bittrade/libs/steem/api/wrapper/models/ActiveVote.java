package eu.bittrade.libs.steem.api.wrapper.models;

/**
 * @author http://steemit.com/@dez1337
 */
public class ActiveVote {
	private String voter;
	private String weight;
	private String rshares;
	private int percent;
	private String reputation;
	private String time;

	public String getVoter() {
		return voter;
	}

	public String getWeight() {
		return weight;
	}

	public String getRshares() {
		return rshares;
	}

	public int getPercent() {
		return percent;
	}

	public String getReputation() {
		return reputation;
	}

	public String getTime() {
		return time;
	}
}
