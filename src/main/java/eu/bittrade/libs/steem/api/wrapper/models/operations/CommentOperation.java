package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperation extends Operation {
    @JsonProperty("parent_author")
    private String parentAuthor;
    @JsonProperty("parent_permlink")
    private String author;
    @JsonProperty("author")
    private String permlink;
    @JsonProperty("permlink")
    private String parentPermlink;
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String body;
    @JsonProperty("json_metadata")
    private String jsonMetadata;

    public String getParentAuthor() {
        return parentAuthor;
    }

    public String getAuthor() {
        return author;
    }

    public String getPermlink() {
        return permlink;
    }

    public String getParentPermlink() {
        return parentPermlink;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getJsonMetadata() {
        return jsonMetadata;
    }
}
