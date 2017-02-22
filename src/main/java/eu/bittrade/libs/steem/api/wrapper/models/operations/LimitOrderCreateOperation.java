package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreateOperation extends Operation {
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("orderid")
    private int orderId;
    @JsonProperty("amount_to_sell")
    private String amountToSell;
    @JsonProperty("min_to_receive")
    private String minToReceive;
    @JsonProperty("fill_or_kill")
    private Boolean fillOrKill;
    @JsonProperty("expiration")
    private Date expiration;

    public String getOwner() {
        return owner;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getAmountToSell() {
        return amountToSell;
    }

    public String getMinToReceive() {
        return minToReceive;
    }

    public Boolean getFillOrKill() {
        return fillOrKill;
    }

    public Date getExpiration() {
        return expiration;
    }
}
