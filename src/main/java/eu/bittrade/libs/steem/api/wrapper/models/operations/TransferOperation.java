package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferOperation extends Operation {
    @JsonProperty("from")
    private AccountName from;
    @JsonProperty("to")
    private AccountName to;
    @JsonProperty("amount")
    private Asset amount;
    @JsonProperty("memo")
    private String memo;

    public TransferOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public AccountName getFrom() {
        return from;
    }

    public void setFrom(AccountName from) {
        this.from = from;
    }

    public AccountName getTo() {
        return to;
    }

    public void setTo(AccountName to) {
        this.to = to;
    }

    public Asset getAmount() {
        return amount;
    }

    public void setAmount(Asset amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }
}
