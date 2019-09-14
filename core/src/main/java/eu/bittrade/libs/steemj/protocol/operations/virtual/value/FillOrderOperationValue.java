package eu.bittrade.libs.steemj.protocol.operations.virtual.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

public class FillOrderOperationValue {

    @JsonProperty("current_owner")
    private AccountName currentOwner;
    @JsonProperty("current_orderid")
    // Original type is uint32_t here so we have to use long.
    private int currentOrderId;
    @JsonProperty("current_pays")
    private Asset currentPays;
    @JsonProperty("open_owner")
    private AccountName openOwner;
    @JsonProperty("open_orderid")
    // Original type is uint32_t here so we have to use long.
    private long openOrderId;
    @JsonProperty("open_pays")
    private Asset openPays;
    
    /**
     * @return The current owner.
     */
    public AccountName getCurrentOwner() {
        return currentOwner;
    }

    /**
     * @return The current order id.
     */
    public int getCurrentOrderId() {
        return currentOrderId;
    }

    /**
     * @return The current pays.
     */
    public Asset getCurrentPays() {
        return currentPays;
    }

    /**
     * @return The open owner.
     */
    public AccountName getOpenOwner() {
        return openOwner;
    }

    /**
     * @return The open order id.
     */
    public long getOpenOrderId() {
        return openOrderId;
    }

    /**
     * @return The open pays.
     */
    public Asset getOpenPays() {
        return openPays;
    }
}
