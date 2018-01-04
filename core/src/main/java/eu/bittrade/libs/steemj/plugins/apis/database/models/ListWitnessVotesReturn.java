package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.chain.WitnessVote;

/**
 * This class represents a Steem "list_witness_votes_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListWitnessVotesReturn {
    @JsonProperty("votes")
    private List<WitnessVote> votes;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected ListWitnessVotesReturn() {
    }

    /**
     * @return the votes
     */
    public List<WitnessVote> getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
