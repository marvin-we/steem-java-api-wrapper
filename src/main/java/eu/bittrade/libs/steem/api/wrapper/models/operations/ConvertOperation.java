package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ConvertOperation extends Operation {
    @JsonProperty("owner")
    private AccountName owner;
    @JsonProperty("requestid")
    private long requestId;
    @JsonProperty("amount")
    private Asset amount;

    public ConvertOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public AccountName getOwner() {
        return owner;
    }

    public void setOwner(AccountName owner) {
        this.owner = owner;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Asset getAmount() {
        return amount;
    }

    public void setAmount(Asset amount) {
        this.amount = amount;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }
}
