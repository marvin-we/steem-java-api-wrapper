package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Price;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "feed_publish_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FeedPublishOperation extends Operation {
    @JsonProperty("publisher")
    private AccountName publisher;
    @JsonProperty("exchange_rate")
    private Price exchangeRate;

    /**
     * Create a new feed publish operation. Feeds can only be published by the
     * top N witnesses which are included in every round and are used to define
     * the exchange rate between steem and the dollar.
     */
    public FeedPublishOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
    }

    /**
     * Get the account name of the witness that published a new price feed.
     * 
     * @return The account name of the witness that published a new price feed.
     */
    public AccountName getPublisher() {
        return publisher;
    }

    /**
     * Set the account name of the witness that will publish a new price feed.
     * 
     * @param publisher
     *            The account name of the witness that will publish a new price
     *            feed.
     */
    public void setPublisher(AccountName publisher) {
        this.publisher = publisher;
    }

    /**
     * Get the exchange rate suggested by the witness.
     * 
     * @return The exchange rate suggested by the witness.
     */
    public Price getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Set the exchange rate suggested by the witness.
     * 
     * @param exchangeRate
     *            The exchange rate suggested by the witness.
     */
    public void setExchangeRate(Price exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedFeedPublishOperation = new ByteArrayOutputStream()) {
            serializedFeedPublishOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.FEED_PUBLISH_OPERATION.ordinal()));
            serializedFeedPublishOperation.write(this.getPublisher().toByteArray());
            serializedFeedPublishOperation.write(this.getExchangeRate().toByteArray());

            return serializedFeedPublishOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
