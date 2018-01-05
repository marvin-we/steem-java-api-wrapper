package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "discussion_query_result" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DiscussionQueryResult {
    @JsonProperty("discussions")
    private List<Discussion> discussions;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private DiscussionQueryResult() {
    }

    /**
     * @return the discussions
     */
    public List<Discussion> getDiscussions() {
        return discussions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
