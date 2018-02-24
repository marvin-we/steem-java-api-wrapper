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
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.protocol;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;

import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.libs.steemj.protocol.Asset;
import eu.bittrade.libs.steemj.protocol.Price;
import eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType;

/**
 * Test the Price object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class PriceTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "73000000000000000353424400000000640000000000000003535445454d0000";

    /**
     * Test if the {@link eu.bittrade.libs.steemj.protocol.Price#toByteArray}
     * method of a price object returns the expected byte array.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testPriceToByteArray() throws Exception {
        Asset base = new Asset(115, AssetSymbolType.SBD);
        Asset quote = new Asset(100, AssetSymbolType.STEEM);

        Price price = new Price(base, quote);

        assertThat("Expect that the price object has the given byte representation.",
                CryptoUtils.HEX.encode(price.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.protocol.Price#equals} method
     * of a price object.
     */
    @Test
    public void testPriceEqualsMethod() {
        Asset base = new Asset(115, AssetSymbolType.SBD);
        Asset quote = new Asset(100, AssetSymbolType.STEEM);

        Price price = new Price(base, quote);

        Asset anotherBase = new Asset(115, AssetSymbolType.SBD);
        Asset anotherQuote = new Asset(100, AssetSymbolType.STEEM);

        Price anotherPrice = new Price(anotherBase, anotherQuote);

        Asset defferentBase = new Asset(115, AssetSymbolType.SBD);
        Asset differentQuote = new Asset(1230, AssetSymbolType.STEEM);

        Price differentPrice = new Price(defferentBase, differentQuote);

        assertThat(price.equals(anotherPrice), equalTo(true));
        assertThat(anotherPrice.equals(differentPrice), equalTo(false));
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeBaseAsset() {
        Asset base = new Asset(-1, AssetSymbolType.SBD);
        base.setAmount(-1);
        base.setSymbol(AssetSymbolType.SBD);
        Asset quote = new Asset(100, AssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeQuoteAsset() {
        Asset base = new Asset(115, AssetSymbolType.SBD);
        Asset quote = new Asset(-1, AssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeSameSymbols() {
        Asset base = new Asset(115, AssetSymbolType.STEEM);
        Asset quote = new Asset(100, AssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.Price}} object by providing
     * valid assets.
     */
    @Test
    public void testPriceValidation() {
        Asset base = new Asset(115, AssetSymbolType.SBD);
        Asset quote = new Asset(100, AssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the {@link Price#multiply(Asset)} method.
     */
    @Test
    public void testMultiply() {
        Asset base = new Asset(50L, AssetSymbolType.SBD);
        Asset quote = new Asset(100L, AssetSymbolType.STEEM);

        Price exchangeRate = new Price(base, quote);

        Asset amountToSell = new Asset(2L, AssetSymbolType.SBD);

        assertTrue(exchangeRate.multiply(amountToSell).getAmount().equals(4L));
        assertTrue(exchangeRate.multiply(amountToSell).getSymbol().equals(AssetSymbolType.STEEM));
    }
}
