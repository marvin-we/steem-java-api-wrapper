package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Test the Asset object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class AssetTest {
    private final String EXPECTED_VESTS_ASSET_BYTE_REPRESENTATION = "ce040000000000000656455354530000";

    private final String EXPECTED_STEEM_ASSET_BYTE_REPRESENTATION = "78e001000000000003535445454d0000";
    private final String EXPECTED_SBD_ASSET_BYTE_REPRESENTATION = "7b000000000000000353424400000000";
    private final String EXPECTED_STMD_ASSET_BYTE_REPRESENTATION = "0cde0000000000000353544d44000000";

    private final String EXPECTED_TESTS_ASSET_BYTE_REPRESENTATION = "858f0000000000000354455354530000";
    private final String EXPECTED_TBD_ASSET_BYTE_REPRESENTATION = "c3110000000000000354424400000000";
    private final String EXPECTED_TSTD_ASSET_BYTE_REPRESENTATION = "be9bb404000000000354535444000000";

    /**
     * Test the {@link eu.bittrade.libs.steemj.base.models.Asset#toByteArray()}
     * method for the VESTS asset type.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testVestsAssetToByteArray() throws Exception {
        Asset vestsAsset = new Asset(1230, AssetSymbolType.VESTS);

        assertThat(vestsAsset.getPrecision(), equalTo(6));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(vestsAsset.toByteArray()), equalTo(EXPECTED_VESTS_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.base.models.Asset#toByteArray()}
     * method for the STEEM asset type.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testSteemAssetToByteArray() throws Exception {
        Asset steemAsset = new Asset(123000, AssetSymbolType.STEEM);

        assertThat(steemAsset.getPrecision(), equalTo(3));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(steemAsset.toByteArray()), equalTo(EXPECTED_STEEM_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.base.models.Asset#toByteArray()}
     * method for the SBD asset type.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testSbdAssetToByteArray() throws Exception {
        Asset sbdAsset = new Asset(123, AssetSymbolType.SBD);

        assertThat(sbdAsset.getPrecision(), equalTo(3));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(sbdAsset.toByteArray()), equalTo(EXPECTED_SBD_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.base.models.Asset#toByteArray()}
     * method for the STMD asset type.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testStmdAssetToByteArray() throws Exception {
        Asset sbdAsset = new Asset(56844, AssetSymbolType.STMD);

        assertThat(sbdAsset.getPrecision(), equalTo(3));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(sbdAsset.toByteArray()), equalTo(EXPECTED_STMD_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.base.models.Asset#toByteArray()}
     * method for the TESTS asset type.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testTestsAssetToByteArray() throws Exception {
        Asset sbdAsset = new Asset(36741, AssetSymbolType.TESTS);

        assertThat(sbdAsset.getPrecision(), equalTo(3));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(sbdAsset.toByteArray()), equalTo(EXPECTED_TESTS_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.base.models.Asset#toByteArray()}
     * method for the TBD asset type.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testTbdAssetToByteArray() throws Exception {
        Asset sbdAsset = new Asset(4547, AssetSymbolType.TBD);

        assertThat(sbdAsset.getPrecision(), equalTo(3));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(sbdAsset.toByteArray()), equalTo(EXPECTED_TBD_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.base.models.Asset#toByteArray()}
     * method for the TSTD asset type.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testTstdAssetToByteArray() throws Exception {
        Asset sbdAsset = new Asset(78945214, AssetSymbolType.TSTD);

        assertThat(sbdAsset.getPrecision(), equalTo(3));
        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(sbdAsset.toByteArray()), equalTo(EXPECTED_TSTD_ASSET_BYTE_REPRESENTATION));
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.base.models.Asset#equals(Object)}
     * method for the VESTS asset type.
     */
    @Test
    public void testAssetEqualsMethod() {
        Asset asset = new Asset(115, AssetSymbolType.SBD);

        Asset sameAsset = new Asset(0.115, AssetSymbolType.SBD);

        Asset differentAsset = new Asset(100, AssetSymbolType.STEEM);

        assertThat(asset.equals(sameAsset), equalTo(true));
        assertThat(sameAsset.equals(differentAsset), equalTo(false));
    }
}
