package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "list_owner_histories_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListOwnerHistoriesArgs {
    // TODO: Original type is "fc::variant".
    @JsonProperty("start")
    private Object start;
    @JsonProperty("limit")
    private UInteger limit;

    /**
     * 
     * @param start
     * @param limit
     */
    @JsonCreator
    public ListOwnerHistoriesArgs(@JsonProperty("start") Object start, @JsonProperty("limit") UInteger limit) {
        this.setStart(start);
        this.setLimit(limit);
    }

    /**
     * @return the start
     */
    public Object getStart() {
        return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(Object start) {
        this.start = SteemJUtils.setIfNotNull(start, "Start must be provided.");
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
    public void setLimit(UInteger limit) {
        this.limit = SteemJUtils.setIfNotNull(limit, "The limit must be provided.");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
