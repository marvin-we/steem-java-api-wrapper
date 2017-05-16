package eu.bittrade.libs.steem.api.wrapper.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.bitcoinj.core.Utils;
import org.junit.Test;

import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;

/**
 * Test the ChainProperties object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class ChainPropertiesTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "881300000000000003535445454d0000ffff000000000000";

    @Test
    public void testSteemChainPropertiesToByteArray() throws Exception {
        ChainProperties chainProperties = new ChainProperties();

        Asset accountCreationFee = new Asset();
        accountCreationFee.setAmount(5000);
        accountCreationFee.setSymbol(AssetSymbolType.STEEM);

        chainProperties.setAccountCreationFee(accountCreationFee);
        chainProperties.setMaximumBlockSize(65535);
        chainProperties.setSdbInterestRate(0);

        assertThat("Expect that the asset object has the given byte representation.",
                Utils.HEX.encode(chainProperties.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));

    }
}
