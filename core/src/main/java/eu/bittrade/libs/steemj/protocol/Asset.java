/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with SteemJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.deserializer.AssetDeserializer;
import eu.bittrade.libs.steemj.base.models.serializer.AssetSerializer;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType;
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
     *            {@link eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType
     *            AssetSymbolType}.
     */
    public Asset(BigDecimal amount, AssetSymbolType symbol) {
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
     *            {@link eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType
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
     * @return One type of
     *         {@link eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType
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
    public void setAmount(BigDecimal amount) {
        if (amount.scale() > this.getPrecision()) {
            throw new InvalidParameterException("The provided 'amount' has a 'scale' of " + amount.scale()
                    + ", but needs to have a 'scale' of " + this.getPrecision() + " when " + this.getSymbol().name()
                    + " is used as a AssetSymbolType.");
        }

        this.amount = amount.multiply(BigDecimal.valueOf(Math.pow(10, this.getPrecision()))).longValue();
    }

    /**
     * Set the symbol of this asset.
     * 
     * @param symbol
     *            One type of
     *            {@link eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType
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
     * Transform this asset into its {@link BigDecimal} representation.
     * 
     * @return The value of this asset in its {@link BigDecimal} representation.
     */
    public BigDecimal toReal() {
        BigDecimal transformedValue = new BigDecimal(this.getAmount());
        return transformedValue.setScale(this.getPrecision());
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
