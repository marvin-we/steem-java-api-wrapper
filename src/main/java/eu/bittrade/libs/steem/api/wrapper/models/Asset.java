package eu.bittrade.libs.steem.api.wrapper.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.interfaces.ByteTransformable;
import eu.bittrade.libs.steem.api.wrapper.models.deserializer.AssetDeserializer;
import eu.bittrade.libs.steem.api.wrapper.models.serializer.AssetSerializer;
import eu.bittrade.libs.steem.api.wrapper.util.Utils;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/asset.hpp">Steem
 * assets object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonDeserialize(using = AssetDeserializer.class)
@JsonSerialize(using = AssetSerializer.class)
public class Asset implements ByteTransformable {
    // Type is safe<int64_t> in the original code.
    private long amount;
    // Type us uint64_t in the original code.
    private AssetSymbolType symbol;
    private char precision;

    /**
     * Create an empty Asset object.
     */
    public Asset() {

    }

    /**
     * Create a new asset object by providing all required fields.
     * 
     * @param amount
     *            The amount.
     * @param symbol
     *            One type of
     *            {@link eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType
     *            AssetSymbolType}.
     */
    public Asset(long amount, AssetSymbolType symbol) {
        this.amount = amount;
        this.symbol = symbol;
    }

    /**
     * Get the amount of this asset object.
     * 
     * @return The amount.
     */
    public double getAmount() {
        return Double.valueOf(this.amount / 1000.0);
    }

    /**
     * Get the precision of this asset object.
     * 
     * @return The precision.
     */
    public int getPrecision() {
        return (int) precision;
    }

    /**
     * Get the symbol for this asset object.
     * 
     * @return One type of
     *         {@link eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType
     *         AssetSymbolType}.
     */
    public AssetSymbolType getSymbol() {
        return symbol;
    }

    /**
     * Set the amount of this asset.
     * 
     * @param amount
     *            The amount.
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * Set the symbol of this asset.
     * 
     * @param symbol
     *            One type of
     *            {@link eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType
     *            AssetSymbolType}.
     */
    public void setSymbol(AssetSymbolType symbol) {
        if (symbol.equals(AssetSymbolType.VESTS)) {
            this.precision = 6;
        } else {
            this.precision = 3;
        }

        this.symbol = symbol;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAsset = new ByteArrayOutputStream()) {
            serializedAsset.write(Utils.transformLongToByteArray(this.amount));
            serializedAsset.write(this.precision);

            serializedAsset.write(this.symbol.name().toUpperCase()
                    .getBytes(SteemApiWrapperConfig.getInstance().getEncodingCharset()));
            String filledAssetSymbol = this.symbol.name().toUpperCase();
            byte nullByte = 0x00;
            for (int i = filledAssetSymbol.length(); i < 7; i++) {
                serializedAsset.write(nullByte);
            }

            return serializedAsset.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming an asset into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
