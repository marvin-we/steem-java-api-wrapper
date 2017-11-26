package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

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
     * 
     * @param publisher
     *            Set the account name of the account publishing this price feed
     *            (see {@link #setPublisher(AccountName)}).
     * @param exchangeRate
     *            Set the STEEM/SBD Price feed (see
     *            {@link #setExchangeRate(Price)}).
     * @throws InvalidParameterException
     *             If the arguments do not fulfill the requirements.
     */
    @JsonCreator
    public FeedPublishOperation(@JsonProperty("publisher") AccountName publisher,
            @JsonProperty("exchange_rate") Price exchangeRate) {
        super(false);

        this.setPublisher(publisher);
        this.setExchangeRate(exchangeRate);
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
     * <b>Notice:</b> The private active key of this account needs to be stored
     * in the key storage.
     * 
     * @param publisher
     *            The account name of the witness that will publish a new price
     *            feed.
     * @throws InvalidParameterException
     *             If no account name has been provided.
     */
    public void setPublisher(AccountName publisher) {
        this.publisher = setIfNotNull(publisher, "The publisher can't be null.");
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
     * @throws InvalidParameterException
     *             If no account name has been provided.
     */
    public void setExchangeRate(Price exchangeRate) {
        this.exchangeRate = setIfNotNull(exchangeRate, "The price feed can't be null");
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedFeedPublishOperation = new ByteArrayOutputStream()) {
            serializedFeedPublishOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.FEED_PUBLISH_OPERATION.getOrderId()));
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

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getPublisher(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)
                && (!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType))
                && (!(exchangeRate.getBase().getSymbol().equals(SteemJConfig.getInstance().getTokenSymbol())
                        && exchangeRate.getQuote().getSymbol().equals(SteemJConfig.getInstance().getDollarSymbol())
                        || exchangeRate.getBase().getSymbol().equals(SteemJConfig.getInstance().getDollarSymbol())
                                && exchangeRate.getQuote().getSymbol()
                                        .equals(SteemJConfig.getInstance().getTokenSymbol())))) {
            throw new InvalidParameterException("The Price feed must be a STEEM/SBD price.");
        }
    }
}
