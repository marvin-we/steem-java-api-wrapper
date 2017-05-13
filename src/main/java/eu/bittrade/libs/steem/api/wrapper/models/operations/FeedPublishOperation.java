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
import eu.bittrade.libs.steem.api.wrapper.util.SteemUtils;

/**
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
     * @return the publisher
     */
    public AccountName getPublisher() {
        return publisher;
    }

    /**
     * @param publisher
     *            the publisher to set
     */
    public void setPublisher(AccountName publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the exchangeRate
     */
    public Price getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate
     *            the exchangeRate to set
     */
    public void setExchangeRate(Price exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedFeedPublishOperation = new ByteArrayOutputStream()) {
            serializedFeedPublishOperation
                    .write(SteemUtils.transformIntToVarIntByteArray(OperationType.FEED_PUBLISH_OPERATION.ordinal()));
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
