package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FillOrderOperation extends Operation {
    @JsonProperty("current_owner")
    private String currentOwner;
    @JsonProperty("current_orderid")
    private int currentOrderId;
    @JsonProperty("current_pays")
    private String currentPays;
    @JsonProperty("open_owner")
    private String openOwner;
    @JsonProperty("open_orderid")
    private int openOrderId;
    @JsonProperty("open_pays")
    private String openPays;

    public String getCurrentOwner() {
        return currentOwner;
    }

    public int getCurrentOrderId() {
        return currentOrderId;
    }

    public String getCurrentPays() {
        return currentPays;
    }

    public String getOpenOwner() {
        return openOwner;
    }

    public int getOpenOrderId() {
        return openOrderId;
    }

    public String getOpenPays() {
        return openPays;
    }
}
