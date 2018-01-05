package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "get_trending_tags_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetTrendingTagsArgs {
    @JsonProperty("start_tag")
    private String startTag;
    @JsonProperty("limit")
    private UInteger limit;

    /**
     * 
     * @param startTag
     * @param limit
     */
    @JsonCreator
    public GetTrendingTagsArgs(@JsonProperty("start_tag") String startTag,
            @Nullable @JsonProperty("limit") UInteger limit) {
        this.setStartTag(startTag);
        this.setLimit(limit);
    }

    /**
     * @return the startTag
     */
    public String getStartTag() {
        return startTag;
    }

    /**
     * @param startTag
     *            the startTag to set
     */
    public void setStartTag(String startTag) {
        this.startTag = SteemJUtils.setIfNotNull(startTag, "The startTag needs to be provided.");
    }

    /**
     * @return the limit
     */
    public UInteger getLimit() {
        return limit;
    }

    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(@Nullable UInteger limit) {
        this.limit = SteemJUtils.setIfNotNull(limit, UInteger.valueOf(100));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
