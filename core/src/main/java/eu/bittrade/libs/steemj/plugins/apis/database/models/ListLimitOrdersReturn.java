package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.chain.LimitOrder;

/**
 * This class represents a Steem "list_limit_orders_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListLimitOrdersReturn {
    @JsonProperty("orders")
    private List<LimitOrder> orders;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * 
     * Visibility set to <code>protected</code> as this is the parent of the
     * {@link FindLimitOrdersReturn} class.
     */
    protected ListLimitOrdersReturn() {
    }

    /**
     * @return the orders
     */
    public List<LimitOrder> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
