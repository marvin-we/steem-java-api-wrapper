package eu.bittrade.libs.steemj.apis.database.models.state;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "tag_index" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TagIndex {
    @JsonProperty("trending")
    List<String> trending;
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
    public List<String> getTrending() {
        return trending;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
