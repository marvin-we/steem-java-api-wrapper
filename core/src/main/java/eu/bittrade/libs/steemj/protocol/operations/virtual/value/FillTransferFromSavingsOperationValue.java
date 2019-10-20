package eu.bittrade.libs.steemj.protocol.operations.virtual.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

public class FillTransferFromSavingsOperationValue {
	private AccountName from;
    private AccountName to;
    private Asset amount;
    // Original type is uint32_t here so we have to use long.
    @JsonProperty("request_id")
    private long requestId;
    private String memo;
    
    /**
     * @return the from
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * @return the to
     */
    public AccountName getTo() {
        return to;
    }

    /**
     * @return the amount
     */
    public Asset getAmount() {
        return amount;
    }

    /**
     * @return the requestId
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }


}
