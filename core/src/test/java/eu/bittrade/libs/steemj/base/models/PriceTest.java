package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.security.InvalidParameterException;

import org.bitcoinj.core.Utils;
import org.junit.Test;

import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Test the Price object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class PriceTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "73000000000000000353424400000000640000000000000003535445454d0000";

    /**
     * Test if the {@link eu.bittrade.libs.steemj.base.models.Price#toByteArray}
     * method of a price object returns the expected byte array.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testPriceToByteArray() throws Exception {
        Asset base = new Asset();
        base.setAmount(115);
        base.setSymbol(AssetSymbolType.SBD);
        Asset quote = new Asset();
        quote.setAmount(100);
        quote.setSymbol(AssetSymbolType.STEEM);

        Price price = new Price(base, quote);

        assertThat("Expect that the price object has the given byte representation.",
                Utils.HEX.encode(price.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.base.models.Price#equals} method
     * of a price object.
     */
    @Test
    public void testPriceEqualsMethod() {
        Asset base = new Asset();
        base.setAmount(115);
        base.setSymbol(AssetSymbolType.SBD);
        Asset quote = new Asset();
        quote.setAmount(100);
        quote.setSymbol(AssetSymbolType.STEEM);

        Price price = new Price(base, quote);

        Asset anotherBase = new Asset();
        anotherBase.setAmount(115);
        anotherBase.setSymbol(AssetSymbolType.SBD);
        Asset anotherQuote = new Asset();
        anotherQuote.setAmount(100);
        anotherQuote.setSymbol(AssetSymbolType.STEEM);

        Price anotherPrice = new Price(anotherBase, anotherQuote);

        Asset defferentBase = new Asset();
        defferentBase.setAmount(115);
        defferentBase.setSymbol(AssetSymbolType.SBD);
        Asset differentQuote = new Asset();
        differentQuote.setAmount(1230);
        differentQuote.setSymbol(AssetSymbolType.STEEM);

        Price differentPrice = new Price(defferentBase, differentQuote);

        assertThat(price.equals(anotherPrice), equalTo(true));
        assertThat(anotherPrice.equals(differentPrice), equalTo(false));
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeBaseAsset() {
        Asset base = new Asset();
        base.setAmount(-1);
        base.setSymbol(AssetSymbolType.SBD);
        Asset quote = new Asset();
        quote.setAmount(100);
        quote.setSymbol(AssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeQuoteAsset() {
        Asset base = new Asset();
        base.setAmount(115);
        base.setSymbol(AssetSymbolType.SBD);
        Asset quote = new Asset();
        quote.setAmount(-1);
        quote.setSymbol(AssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.Price} object by providing
     * invalid assets.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPriceValidationNegativeSameSymbols() {
        Asset base = new Asset();
        base.setAmount(115);
        base.setSymbol(AssetSymbolType.STEEM);
        Asset quote = new Asset();
        quote.setAmount(100);
        quote.setSymbol(AssetSymbolType.STEEM);

        new Price(base, quote);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.Price}} object by providing
     * valid assets.
     */
    @Test
    public void testPriceValidation() {
        Asset base = new Asset();
        base.setAmount(115);
        base.setSymbol(AssetSymbolType.SBD);
        Asset quote = new Asset();
        quote.setAmount(100);
        quote.setSymbol(AssetSymbolType.STEEM);

        new Price(base, quote);
    }
}
