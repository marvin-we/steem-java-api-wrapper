package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class InterestOperation extends Operation {
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("interest")
    private String interest;

    public String getOwner() {
        return owner;
    }

    public String getInterest() {
        return interest;
    }
}
