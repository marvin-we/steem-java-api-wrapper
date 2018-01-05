package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "get_active_votes_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetActiveVotesReturn {
    @JsonProperty("votes")
    private List<VoteState> votes;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetActiveVotesReturn() {
    }

    /**
     * @return the votes
     */
    public List<VoteState> getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
