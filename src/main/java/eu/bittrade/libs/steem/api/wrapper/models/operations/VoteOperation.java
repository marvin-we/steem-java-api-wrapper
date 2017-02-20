package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VoteOperation extends Operation {
    @JsonProperty("voter")
    private String voter;
    @JsonProperty("author")
    private String author;
    @JsonProperty("permlink")
    private String permlink;
    @JsonProperty("weight")
    private int weight;

    String getVoter() {
        return voter;
    }
    
    String getAuthor() {
        return author;
    }
    
    String getPermlink() {
        return permlink;
    }
    
    int getWeight() {
        return weight;
    }
}
