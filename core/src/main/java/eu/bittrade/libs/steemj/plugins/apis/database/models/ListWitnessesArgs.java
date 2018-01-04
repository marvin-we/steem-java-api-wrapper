package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.plugins.apis.database.enums.SortOrderType;

/**
 * This class represents a Steem "list_witnesses_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListWitnessesArgs {
    // TODO: Original type is: fc::variant.
    @JsonProperty("start")
    private Object start;
    @JsonProperty("limit")
    private UInteger limit;
    @JsonProperty("order")
    private SortOrderType order;

    public ListWitnessesArgs() {

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
        this.start = start;
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
        this.limit = limit;
    }

    /**
     * @return the order
     */
    public SortOrderType getOrder() {
        return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public void setOrder(SortOrderType order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
