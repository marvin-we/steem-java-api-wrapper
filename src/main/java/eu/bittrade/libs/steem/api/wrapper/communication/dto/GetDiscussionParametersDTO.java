package eu.bittrade.libs.steem.api.wrapper.communication.dto;

/**
 * A wrapper object that carries all required fields for a request.
 * 
 * @author http://steemit.com/@dez1337
 */
public class GetDiscussionParametersDTO {
	private String tag;
	private String limit;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
}
