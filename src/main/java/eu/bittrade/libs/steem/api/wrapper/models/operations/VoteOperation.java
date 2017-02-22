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

    public String getVoter() {
        return voter;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getPermlink() {
        return permlink;
    }
    
    public int getWeight() {
        return weight;
    }
}
