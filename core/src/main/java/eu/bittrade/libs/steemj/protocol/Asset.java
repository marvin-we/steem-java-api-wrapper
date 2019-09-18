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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steem/protocol/asset.hpp">Steem
 * assets object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Asset implements ByteTransformable {
    // Original type is "share_type" which is a "safe<int64_t>".
    @JsonIgnore
    private long amount;
    @JsonIgnore
    private AssetSymbolType assetSymbolType;

    /**
     * Constructs a new Asset object based on the given <code>amount</code>,
     * <code>precision</code> and the <code>nai</code>.
     * 
     * A sample JSON String can look like this:
     * 
     * <code>"asset": { "amount": "0", "precision": 3, "nai": "@@000000021" }</code>
     * 
     * @param amount
     * @param precision
     * @param nai
     */
    @JsonCreator
    public Asset(@JsonProperty("amount") long amount, @JsonProperty("precision") byte precision,
            @JsonProperty("nai") String nai) {
        this.amount = amount;
        this.assetSymbolType = new AssetSymbolType(nai, precision);
    }

    /**
     * TODO
     * 
     * @param amount
     * @param assetSymbolType
     */
    public Asset(long amount, AssetSymbolType assetSymbolType) {
        this.amount = amount;
        this.assetSymbolType = assetSymbolType;
    }

    /**
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * @return the assetSymbolType
     */
    public AssetSymbolType getAssetSymbolType() {
        return assetSymbolType;
    }

    /**
     * @param assetSymbolType
     *            the assetSymbolType to set
     */
    public void setAssetSymbolType(AssetSymbolType assetSymbolType) {
        this.assetSymbolType = assetSymbolType;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAsset = new ByteArrayOutputStream()) {
            // TODO
            return null;
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
        // TODO
        return false;
    }

    @Override
    public int hashCode() {
        // TODO
        return 0;
    }
}
