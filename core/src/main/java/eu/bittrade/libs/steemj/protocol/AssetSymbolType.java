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

import java.security.InvalidParameterException;

import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steem/protocol/asset_symbol.hpp">Steem
 * assets object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AssetSymbolType {
    /**
     * Numerical asset identifiers.
     * 
     * An SMT is referred to by a numerical asset identifier or NAI, consisting
     * of two at-signs followed by nine decimal digits, for example @@314159265.
     * 
     */
    @JsonProperty("decimal_places")
    private byte decimalPlaces;
    @JsonProperty("asset_num")
    private UInteger assetNumber = UInteger.valueOf(0);

    /**
     * Similar to {@link AssetSymbolType#AssetSymbolType(String, byte)}, but
     * expects a <code>nai</code> number instead of the raw
     * <code>naiString</code>.
     * 
     * @param nai
     * @param decimalPlaces
     */
    public AssetSymbolType(UInteger nai, byte decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
        this.assetNumber = assetNumberFromNai(nai, decimalPlaces);
    }

    /**
     * This method transforms a given <code>naiString</code> into an
     * <code>AssetSymbolType</code>.
     * 
     * @param naiString
     * @param decimalPlaces
     */
    @JsonCreator
    public AssetSymbolType(String naiString, byte decimalPlaces) {
        this(UInteger.valueOf(parseNaiString(naiString)), decimalPlaces);
    }

    /**
     * @return the decimalPlaces
     */
    public byte getDecimalPlaces() {
        return decimalPlaces;
    }

    /**
     * @param decimalPlaces
     *            the decimalPlaces to set
     */
    public void setDecimalPlaces(byte decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    /**
     * @return the assetNumber
     */
    public UInteger getAssetNumber() {
        return assetNumber;
    }

    /**
     * @param assetNumber
     *            the assetNumber to set
     */
    public void setAssetNumber(UInteger assetNumber) {
        this.assetNumber = assetNumber;
    }

    /**
     * TODO
     * 
     * @param nai
     * @param decimalPlaces
     * @return
     */
    public static UInteger assetNumberFromNai(UInteger nai, byte decimalPlaces) {
        return null;
    }

    // TODO
    // from_asset_num

    /**
     * This method is used to transform a <code>naiString</code> into a
     * <code>nai</code> by removing the prefix of it. In example the
     * <code>naiString</code> "@@000000021" will be transformed to "000000021".
     * 
     * @param naiString
     *            The <code>naiString</code> to transform.
     * @return The parsed <code>naiString</code>.
     */
    public static String parseNaiString(String naiString) {
        if (naiString == null || naiString.isEmpty()) {
            throw new InvalidParameterException("NAI string cannot be null or empty.");
        }

        if (naiString.length() != 11) {
            throw new InvalidParameterException("NAI string length is incorrect.");
        }

        if (!naiString.startsWith("@@")) {
            throw new InvalidParameterException("Invalid NAI string prefix for string '" + naiString + "'.");
        }

        return String.valueOf(naiString.toCharArray(), 2, 9);
    }
}
