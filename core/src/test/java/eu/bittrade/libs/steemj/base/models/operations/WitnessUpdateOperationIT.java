package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.net.URL;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Verify the functionality of the "witness update operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessUpdateOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcebc80457010b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4064657a31333"
            + "33702e5127bd7d41f01d9981a5a2c2524a60706040bbec8838a39719550ea25071000881300000000000003535445454d0000000001000000010000000000000003535445454"
            + "d000000011c2125f9ad6d2a0a9b5f75ee042f71d44029f361df17be8da017f784b75ebe33152404af3e11c1e2ed853e0e85a58fc9cea4cc9f2d17803766edd5d917a9b21081";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce9c80457010b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f40646"
            + "57a3133333702e5127bd7d41f01d9981a5a2c2524a60706040bbec8838a39719550ea25071000881300000000000003535445454d00000000010000000100000000000000035"
            + "35445454d000000011c2adb5ec52cfae10ec4e512a5fcb486542ceb66de36be42ab2a8d187a12840204108307e556fa79396d4add3b8f1d83b63047f9f2cda16e73b850238f6"
            + "40bde55";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "account". If no active key is provided or the active key is not valid an
     * Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        PublicKey blockSigningKey = new PublicKey(SteemJConfig.getInstance().getAddressPrefix().name().toUpperCase()
                + "6dNhJF7K7MnVvrjvb9x6B6FP5ztr4pkq9JXyzG9PQHdhsYeLkb");

        Asset fee = new Asset(1L, AssetSymbolType.STEEM);

        AccountName owner = new AccountName("dez1337");

        Asset accountCreationFee = new Asset(5000L, AssetSymbolType.STEEM);

        long maximumBlockSize = 65536;
        int sbdInterestRate = 0;

        ChainProperties chainProperties = new ChainProperties(accountCreationFee, maximumBlockSize, sbdInterestRate);

        URL url = new URL("https://steemit.com/@dez1337");

        WitnessUpdateOperation witnessUpdateOperation = new WitnessUpdateOperation(owner, url, blockSigningKey,
                chainProperties, fee);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(witnessUpdateOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);

        signedTransaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(signedTransaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        if (TEST_ENDPOINT.equals(TESTNET_ENDPOINT_IDENTIFIER)) {
            assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX_TESTNET));
        } else {
            assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX));
        }
    }
}
