package dez.steemit.com.models.operations;

public class Vote extends Operation {
	private String voter;
	private long weight;

	public String getVoter() {
		return voter;
	}

	public long getWeight() {
		return weight;
	}
}
