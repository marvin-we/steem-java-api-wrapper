package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
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
        if (publisher == null) {
            throw new InvalidParameterException("The publisher can't be null.");
        }

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
     * @throws InvalidParameterException
     *             If no account name has been provided.
     */
    public void setExchangeRate(Price exchangeRate) {
        if (exchangeRate == null) {
            throw new InvalidParameterException("The price feed can't be null");
        } else if (!(exchangeRate.getBase().getSymbol().equals(AssetSymbolType.STEEM)
                && exchangeRate.getQuote().getSymbol().equals(AssetSymbolType.SBD)
                || exchangeRate.getBase().getSymbol().equals(AssetSymbolType.SBD)
                        && exchangeRate.getQuote().getSymbol().equals(AssetSymbolType.STEEM))) {
            throw new InvalidParameterException("The Price feed must be a STEEM/SBD price");
        }

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

    @Override
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getPublisher(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        // TODO Auto-generated method stub

    }
}
