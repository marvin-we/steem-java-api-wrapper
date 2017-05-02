package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * This class represents a Steem "fill_order_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
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

    /**
     * 
     * @return
     */
    public String getCurrentOwner() {
        return currentOwner;
    }

    /**
     * 
     * @return
     */
    public int getCurrentOrderId() {
        return currentOrderId;
    }

    /**
     * 
     * @return
     */
    public String getCurrentPays() {
        return currentPays;
    }

    /**
     * 
     * @return
     */
    public String getOpenOwner() {
        return openOwner;
    }

    /**
     * 
     * @return
     */
    public int getOpenOrderId() {
        return openOrderId;
    }

    /**
     * 
     * @return
     */
    public String getOpenPays() {
        return openPays;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // The byte representation is not needed for virtual operations as we
        // can't broadcast them.
        return new byte[0];
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
