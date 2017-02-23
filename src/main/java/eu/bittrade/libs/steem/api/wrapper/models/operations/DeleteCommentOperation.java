package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DeleteCommentOperation extends Operation {
    @JsonProperty("author")
    private String author;
    @JsonProperty("permlink")
    private String permlink;

    public String getAuthor() {
        return author;
    }

    public String getPermlink() {
        return permlink;
    }
}
