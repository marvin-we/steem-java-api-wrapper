package eu.bittrade.libs.steemj.plugins.apis.condenser.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.plugins.apis.tags.models.TagName;

/**
 * This class represents a Steem "tag_index" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TagIndex {
    @JsonProperty("trending")
    List<TagName> trending;
    /// --> pending payouts??

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected TagIndex() {
    }

    /**
     * @return the trending
     */
    public List<TagName> getTrending() {
        return trending;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
