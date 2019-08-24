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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;

import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.libs.steemj.protocol.enums.LegacyAssetSymbolType;

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
        LegacyAsset base = new LegacyAsset(115, LegacyAssetSymbolType.SBD);
        LegacyAsset quote = new LegacyAsset(100, LegacyAssetSymbolType.STEEM);

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
        LegacyAsset base = new LegacyAsset(115, LegacyAssetSymbolType.SBD);
        LegacyAsset quote = new LegacyAsset(100, LegacyAssetSymbolType.STEEM);

        Price price = new Price(base, quote);

        LegacyAsset anotherBase = new LegacyAsset(115, LegacyAssetSymbolType.SBD);
        LegacyAsset anotherQuote = new LegacyAsset(100, LegacyAssetSymbolType.STEEM);

        Price anotherPrice = new Price(anotherBase, anotherQuote);

        LegacyAsset defferentBase = new LegacyAsset(115, LegacyAssetSymbolType.SBD);
        LegacyAsset differentQuote = new LegacyAsset(1230, LegacyAssetSymbolType.STEEM);

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
        LegacyAsset base = new LegacyAsset(-1, LegacyAssetSymbolType.SBD);
        base.setAmount(-1);
        base.setSymbol(LegacyAssetSymbolType.SBD);
        LegacyAsset quote = new LegacyAsset(100, LegacyAssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeQuoteAsset() {
        LegacyAsset base = new LegacyAsset(115, LegacyAssetSymbolType.SBD);
        LegacyAsset quote = new LegacyAsset(-1, LegacyAssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeSameSymbols() {
        LegacyAsset base = new LegacyAsset(115, LegacyAssetSymbolType.STEEM);
        LegacyAsset quote = new LegacyAsset(100, LegacyAssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.Price}} object by providing
     * valid assets.
     */
    @Test
    public void testPriceValidation() {
        LegacyAsset base = new LegacyAsset(115, LegacyAssetSymbolType.SBD);
        LegacyAsset quote = new LegacyAsset(100, LegacyAssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the {@link Price#multiply(LegacyAsset)} method.
     */
    @Test
    public void testMultiply() {
        LegacyAsset base = new LegacyAsset(50L, LegacyAssetSymbolType.SBD);
        LegacyAsset quote = new LegacyAsset(100L, LegacyAssetSymbolType.STEEM);

        Price exchangeRate = new Price(base, quote);

        LegacyAsset amountToSell = new LegacyAsset(2L, LegacyAssetSymbolType.SBD);

        assertTrue(exchangeRate.multiply(amountToSell).getAmount().equals(4L));
        assertTrue(exchangeRate.multiply(amountToSell).getSymbol().equals(LegacyAssetSymbolType.STEEM));
    }
}
