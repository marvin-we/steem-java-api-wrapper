package dez.steemit.com.models.operations;

public class Comment extends Operation {
	private String parent_author;
	private String parent_permlink;
	private String title;
	private String body;
	private String json_metadata;

	public String getParent_author() {
		return parent_author;
	}

	public String getParent_permlink() {
		return parent_permlink;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getJson_metadata() {
		return json_metadata;
	}
}
