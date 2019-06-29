package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.chain.DeclineVotingRightsRequestObject;

/**
 * This class represents a Steem "list_decline_voting_rights_requests_return"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListDeclineVotingRightsRequestsReturn {
    @JsonProperty("requests")
    private List<DeclineVotingRightsRequestObject> requests;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * 
     * Visibility set to <code>protected</code> as this is the parent of the
     * {@link FindDeclineVotingRightsRequestsReturn} class.
     */
    protected ListDeclineVotingRightsRequestsReturn() {
    }

    /**
     * @return the requests
     */
    public List<DeclineVotingRightsRequestObject> getRequests() {
        return requests;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
