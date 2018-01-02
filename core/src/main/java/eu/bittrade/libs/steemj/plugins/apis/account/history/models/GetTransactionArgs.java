package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi;
import eu.bittrade.libs.steemj.protocol.TransactionId;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class implements the Steem "get_transaction_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetTransactionArgs {
    @JsonProperty("id")
    private TransactionId id;

    /**
     * Create a new {@link GetAccountHistoryArgs} instance to be passed to the
     * {@link AccountHistoryApi#getAccountHistory(eu.bittrade.libs.steemj.communication.CommunicationHandler, GetAccountHistoryArgs)}
     * method.
     * 
     * @param id
     */
    @JsonCreator()
    public GetTransactionArgs(@JsonProperty("id") TransactionId id) {
        this.setId(id);
    }

    /**
     * @return the id
     */
    public TransactionId getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(TransactionId id) {
        this.id = SteemJUtils.setIfNotNull(id, "The id cannot be null.");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
