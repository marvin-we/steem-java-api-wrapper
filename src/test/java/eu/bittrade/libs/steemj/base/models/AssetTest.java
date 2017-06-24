package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.bitcoinj.core.Utils;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Test the Asset object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class AssetTest {
    private final String EXPECTED_STEEM_ASSET_BYTE_REPRESENTATION = "78e001000000000003535445454d0000";
    private final String EXPECTED_SBD_ASSET_BYTE_REPRESENTATION = "7b000000000000000353424400000000";
    private final String EXPECTED_VESTS_ASSET_BYTE_REPRESENTATION = "ce040000000000000656455354530000";

    @Test
    public void testSteemAssetToByteArray() throws Exception {
        Asset steemAsset = new Asset();
        steemAsset.setSymbol(AssetSymbolType.STEEM);
        steemAsset.setAmount(123000);

        assertThat(steemAsset.getPrecision(), equalTo(3));
        assertThat("Expect that the asset object has the given byte representation.",
                Utils.HEX.encode(steemAsset.toByteArray()), equalTo(EXPECTED_STEEM_ASSET_BYTE_REPRESENTATION));
    }

    @Test
    public void testSbdAssetToByteArray() throws Exception {
        Asset sbdAsset = new Asset();
        sbdAsset.setSymbol(AssetSymbolType.SBD);
        sbdAsset.setAmount(123);

        assertThat(sbdAsset.getPrecision(), equalTo(3));
        assertThat("Expect that the asset object has the given byte representation.",
                Utils.HEX.encode(sbdAsset.toByteArray()), equalTo(EXPECTED_SBD_ASSET_BYTE_REPRESENTATION));
    }

    @Test
    public void testVestsAssetToByteArray() throws Exception {
        Asset vestsAsset = new Asset();
        vestsAsset.setSymbol(AssetSymbolType.VESTS);
        vestsAsset.setAmount(1230);

        assertThat(vestsAsset.getPrecision(), equalTo(6));
        assertThat("Expect that the asset object has the given byte representation.",
                Utils.HEX.encode(vestsAsset.toByteArray()), equalTo(EXPECTED_VESTS_ASSET_BYTE_REPRESENTATION));
    }
    
    @Test
    public void testAssetEqualsMethod() {
        
        Asset asset = new Asset();
        asset.setAmount(115);
        asset.setSymbol(AssetSymbolType.SBD);
        
        Asset sameAsset = new Asset();
        sameAsset.setAmount(115);
        sameAsset.setSymbol(AssetSymbolType.SBD);
        
        Asset differentAsset = new Asset();
        differentAsset.setAmount(100);
        differentAsset.setSymbol(AssetSymbolType.STEEM);

        assertThat(asset.equals(sameAsset), equalTo(true));
        assertThat(sameAsset.equals(differentAsset), equalTo(false));
    }
}
