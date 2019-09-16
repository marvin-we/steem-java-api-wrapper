package eu.bittrade.libs.steemj.protocol.operations.virtual.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

public class FillConvertRequestOperationValue {
	@JsonProperty("owner")
    private AccountName owner;
    // Original type is uint32_t so we have to use long here.
    @JsonProperty("requestid")
    private long requestId;
    @JsonProperty("amount_in")
    private Asset amountIn;
    @JsonProperty("amount_out")
    private Asset amountOut;

    /**
     * Get the owner of this conversion request.
     * 
     * @return The owner as an AccountName instance.
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * Get the id of this request.
     * 
     * @return The id of this request.
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * Get the amount and the type of the currency that has been converted
     * within this operation.
     * 
     * @return The source asset.
     */
    public Asset getAmountIn() {
        return amountIn;
    }

    /**
     * Get the amount and the type of the target currency.
     * 
     * @return The target asset.
     */
    public Asset getAmountOut() {
        return amountOut;
    }
}
