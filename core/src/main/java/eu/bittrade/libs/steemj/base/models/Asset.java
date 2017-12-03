package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.deserializer.AssetDeserializer;
import eu.bittrade.libs.steemj.base.models.serializer.AssetSerializer;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.util.SteemJUtils;

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
    // Original type is "share_type" which is a "safe<int64_t>".
    private long amount;
    // Type us uint64_t in the original code.
    private AssetSymbolType symbol;
    private byte precision;

    /**
     * Create a new asset object by providing all required fields.
     * 
     * @param amount
     *            The amount.
     * @param symbol
     *            One type of
     *            {@link eu.bittrade.libs.steemj.enums.AssetSymbolType
     *            AssetSymbolType}.
     */
    public Asset(double amount, AssetSymbolType symbol) {
        this.setSymbol(symbol);
        this.setAmount(amount);
    }

    /**
     * Create a new asset object by providing all required fields.
     * 
     * @param amount
     *            The amount.
     * @param symbol
     *            One type of
     *            {@link eu.bittrade.libs.steemj.enums.AssetSymbolType
     *            AssetSymbolType}.
     */
    public Asset(long amount, AssetSymbolType symbol) {
        this.setSymbol(symbol);
        this.setAmount(amount);
    }

    /**
     * Get the amount stored in this asset object.
     * 
     * @return The amount.
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * Get the precision of this asset object.
     * 
     * @return The precision.
     */
    public Integer getPrecision() {
        return (int) precision;
    }

    /**
     * Get the symbol for this asset object.
     * 
     * @return One type of {@link eu.bittrade.libs.steemj.enums.AssetSymbolType
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
     * Set the amount of this asset.
     * 
     * @param amount
     *            The amount.
     */
    public void setAmount(double amount) {
        this.amount = (long) (amount * Math.pow(10.0, this.getPrecision()));
    }

    /**
     * Set the symbol of this asset.
     * 
     * @param symbol
     *            One type of
     *            {@link eu.bittrade.libs.steemj.enums.AssetSymbolType
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

    /**
     * Transform this asset into its double representation.
     * 
     * @return The value of this asset in its double representation.
     */
    public Double toReal() {
        return Double.valueOf(this.amount / Math.pow(10.0, this.getPrecision()));
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAsset = new ByteArrayOutputStream()) {
            serializedAsset.write(SteemJUtils.transformLongToByteArray(this.amount));
            serializedAsset.write(SteemJUtils.transformByteToLittleEndian(this.precision));

            serializedAsset
                    .write(this.symbol.name().toUpperCase().getBytes(SteemJConfig.getInstance().getEncodingCharset()));
            String filledAssetSymbol = this.symbol.name().toUpperCase();

            for (int i = filledAssetSymbol.length(); i < 7; i++) {
                serializedAsset.write(0x00);
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

    @Override
    public boolean equals(Object otherAsset) {
        if (this == otherAsset)
            return true;
        if (otherAsset == null || !(otherAsset instanceof Asset))
            return false;
        Asset other = (Asset) otherAsset;
        return (this.getAmount().equals(other.getAmount()) && this.getSymbol().equals(other.getSymbol())
                && this.getPrecision().equals(other.getPrecision()));
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getAmount() == null ? 0 : this.getAmount().hashCode());
        hashCode = 31 * hashCode + (this.getSymbol() == null ? 0 : this.getSymbol().hashCode());
        hashCode = 31 * hashCode + (this.getPrecision() == null ? 0 : this.getPrecision().hashCode());
        return hashCode;
    }
}
