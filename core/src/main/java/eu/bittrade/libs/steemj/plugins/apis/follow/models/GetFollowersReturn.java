package eu.bittrade.libs.steemj.plugins.apis.follow.models;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.bittrade.libs.steemj.plugins.apis.account.history.models.AppliedOperation;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.deserializer.AppliedOperationHashMapDeserializer;

/**
 * This class implements the Steem "get_followers_return" object.
 * 
 * @author <a href="http://steemit.com/@paatrick">paatrick</a>
 */
public class GetFollowersReturn {
    @JsonProperty("followers")
    private List<FollowApiObject> followers;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetFollowersReturn() {
    }

    /**
     * Get the requested history for the requested account. The history is
     * represented by a list of all operations ever made by an account. The map
     * <code>key</code> represents the <code>id</code> of the operation and the
     * map <code>value</code> is the operation itself.
     * 
     * @return A map of operations and their id.
     */
    public List<FollowApiObject> getFollowers() {
        return followers;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
