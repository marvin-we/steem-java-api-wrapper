package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * This class represents the Steem "fill_transfer_from_savings_operation"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FillTransferFromSavingsOperation extends Operation {
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
