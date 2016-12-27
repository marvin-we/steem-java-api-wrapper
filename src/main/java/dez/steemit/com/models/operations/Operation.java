package dez.steemit.com.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is a wrapper for the different kinds of operations that an user
 * can perform.
 * 
 * @author http://steemit.com/@dez1337
 */
public class Operation {
	private String author;
	private String permlink;
	private String voter;
	private long weight;
	private String parentAuthor;
	private String parentPermlink;
	private String title;
	private String body;
	private String jsonMetadata;

	@JsonProperty("parent_author")
	public String getParentAuthor() {
		return parentAuthor;
	}

	@JsonProperty("parent_permlink")
	public String getParentPermlink() {
		return parentPermlink;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	@JsonProperty("json_metadata")
	public String getJsonMetadata() {
		return jsonMetadata;
	}

	public String getAuthor() {
		return author;
	}

	public String getPermlink() {
		return permlink;
	}

	public String getVoter() {
		return voter;
	}

	public long getWeight() {
		return weight;
	}
}
