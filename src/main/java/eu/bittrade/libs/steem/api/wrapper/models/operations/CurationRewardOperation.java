package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CurationRewardOperation extends Operation {
    @JsonProperty("curator")
    private String curator;
    @JsonProperty("reward")
    private String reward;
    @JsonProperty("comment_author")
    private String commentAuthor;
    @JsonProperty("comment_permlink")
    private String commentPermlink;

    public String getCurator() {
        return curator;
    }

    public String getReward() {
        return reward;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public String getCommentPermlink() {
        return commentPermlink;
    }
}
