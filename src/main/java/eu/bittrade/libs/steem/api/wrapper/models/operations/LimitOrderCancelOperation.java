package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCancelOperation extends Operation {
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("orderid")
    private int orderId;

    public String getOwner() {
        return owner;
    }

    public int getOrderId() {
        return orderId;
    }
}
