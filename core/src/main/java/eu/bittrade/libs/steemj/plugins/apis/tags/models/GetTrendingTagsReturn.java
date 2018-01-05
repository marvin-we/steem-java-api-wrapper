package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "get_trending_tags_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetTrendingTagsReturn {
    @JsonProperty("tags")
    private List<Tag> tags;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetTrendingTagsReturn() {
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
