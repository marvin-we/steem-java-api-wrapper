package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "get_tags_used_by_author_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetTagsUsedByAuthorReturn {
    @JsonProperty("tags")
    private List<TagCount> tags;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetTagsUsedByAuthorReturn() {
    }

    /**
     * @return the tags
     */
    public List<TagCount> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
