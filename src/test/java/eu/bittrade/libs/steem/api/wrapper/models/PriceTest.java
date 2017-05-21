package eu.bittrade.libs.steem.api.wrapper.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.bitcoinj.core.Utils;
import org.junit.Test;

import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;

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
}
