package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Verify the functionality of the "feed publish operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FeedPublishOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701070764657a31"
            + "33333773000000000000000353424400000000640000000000000003535445454d000000011b1d2"
            + "64143ac5f04d46e563aae4e657100b45a74380e9afaa5c9148a4ec77c0c3b5ef08414d0c210ed3e"
            + "f9eea860686a40a19863389ee618605a980bdb6d01a42c";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dceac80457010707"
            + "64657a3133333773000000000000000353424400000000640000000000000003535445454d00000"
            + "0011b2ddd5d7749db3f300e58e05230908e1d4cd74e4d67f1ce9188f1352f4a13484b6b31bb2b7c"
            + "d45497cdfabd4289f977e850716236ec9bf27b94c6944d72907cef";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "publisher". If no active key is provided or the active key is not valid
     * an Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        // 1 STEEM = 1.15 SBD
        Asset base = new Asset(115, AssetSymbolType.SBD);
        Asset quote = new Asset(100, AssetSymbolType.STEEM);

        Price exchangeRate = new Price(base, quote);
        AccountName publisher = new AccountName("dez1337");
        FeedPublishOperation feedPublishOperation = new FeedPublishOperation(publisher, exchangeRate);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(feedPublishOperation);

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
