package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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

    @Test
    public void testPriceToByteArray() throws Exception {
        Asset base = new Asset();
        base.setAmount(115);
        base.setSymbol(AssetSymbolType.SBD);
        Asset quote = new Asset();
        quote.setAmount(100);
        quote.setSymbol(AssetSymbolType.STEEM);

        Price price = new Price();
        price.setBase(base);
        price.setQuote(quote);

        assertThat("Expect that the price object has the given byte representation.",
                Utils.HEX.encode(price.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testPriceEqualsMethod() {
        Asset base = new Asset();
        base.setAmount(115);
        base.setSymbol(AssetSymbolType.SBD);
        Asset quote = new Asset();
        quote.setAmount(100);
        quote.setSymbol(AssetSymbolType.STEEM);

        Price price = new Price();
        price.setBase(base);
        price.setQuote(quote);

        Asset anotherBase = new Asset();
        anotherBase.setAmount(115);
        anotherBase.setSymbol(AssetSymbolType.SBD);
        Asset anotherQuote = new Asset();
        anotherQuote.setAmount(100);
        anotherQuote.setSymbol(AssetSymbolType.STEEM);

        Price anotherPrice = new Price();
        anotherPrice.setBase(anotherBase);
        anotherPrice.setQuote(anotherQuote);

        Asset defferentBase = new Asset();
        defferentBase.setAmount(115);
        defferentBase.setSymbol(AssetSymbolType.SBD);
        Asset differentQuote = new Asset();
        differentQuote.setAmount(1230);
        differentQuote.setSymbol(AssetSymbolType.STEEM);

        Price differentPrice = new Price();
        differentPrice.setBase(defferentBase);
        differentPrice.setQuote(differentQuote);

        assertThat(price.equals(anotherPrice), equalTo(true));
        assertThat(anotherPrice.equals(differentPrice), equalTo(false));
    }
}
