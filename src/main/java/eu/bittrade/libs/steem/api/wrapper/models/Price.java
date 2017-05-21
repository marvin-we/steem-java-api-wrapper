package eu.bittrade.libs.steem.api.wrapper.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.interfaces.ByteTransformable;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/asset.hpp">Steem
 * price object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Price implements ByteTransformable {
    private Asset base;
    private Asset quote;

    /**
     * @return the base
     */
    public Asset getBase() {
        return base;
    }

    /**
     * @param base
     *            the base to set
     */
    public void setBase(Asset base) {
        this.base = base;
    }

    /**
     * @return the quote
     */
    public Asset getQuote() {
        return quote;
    }

    /**
     * @param quote
     *            the quote to set
     */
    public void setQuote(Asset quote) {
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

}
