package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/asset.hpp">Steem
 * price object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Price implements ByteTransformable {
    @JsonProperty("base")
    private Asset base;
    @ JsonProperty("quote")
    private Asset quote;

    /**
     * Create a new price object by providing a base and a quote asset.
     * 
     * @param base
     *            The base asset to set ({@link #setBase(Asset)}).
     * @param quote
     *            The quote asset to set ({@link #setQuote(Asset)}).
     */
    @JsonCreator
    public Price(@JsonProperty("base") Asset base, @ JsonProperty("quote") Asset quote) {
        this.setBase(base);
        this.setQuote(quote);
    }

    /**
     * @return The base asset.
     */
    public Asset getBase() {
        return base;
    }

    /**
     * @param base
     *            The base asset to set.
     * @throws InvalidParameterException
     *             If the asset is not present or has the same property than the
     *             {@link #quote} asset.
     */
    public void setBase(Asset base) {
        if (base == null || base. getAmount() <= 0
                || this.getQuote() != null && this.getQuote().getSymbol() == base.getSymbol()) {
            throw new InvalidParameterException(
                    "The base asset needs to be present and needs to have a different symbol than the quote asset.");
        }
        this.base = base;
    }

    /**
     * @return The quote asset.
     */
    public Asset getQuote() {
        return quote;
    }

    /**
     * @param quote
     *            The quote asset to set.
     * @throws InvalidParameterException
     *             If the asset is not present or has the same property than the
     *             {@link #quote} asset.
     */
    public void setQuote(Asset quote) {
        if (quote == null || quote.getAmount() <= 0
                || this.getBase() != null && this.getBase().getSymbol() == quote.getSymbol()) {
            throw new InvalidParameterException(
                    "The quote asset needs to be present and needs to have a different symbol than the base asset.");
        }
        this.quote = quote;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedPriceObject = new ByteArrayOutputStream()) {
            serializedPriceObject.write(this.getBase().toByteArray());
            serializedPriceObject.write(this.getQuote().toByteArray());

            return serializedPriceObject.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the object into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;
        if (otherObject == null || !(otherObject instanceof Price))
            return false;
        Price otherPrice = (Price) otherObject;
        return (this.getBase().equals(otherPrice.getBase()) && this.getQuote().equals(otherPrice.getQuote()));
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getBase() == null ? 0 : this.getBase().hashCode());
        hashCode = 31 * hashCode + (this.getQuote() == null ? 0 : this.getQuote().hashCode());
        return hashCode;
    }
}
