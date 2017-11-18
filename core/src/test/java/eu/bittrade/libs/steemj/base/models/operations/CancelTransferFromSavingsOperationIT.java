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
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * Verify the functionality of the "cancel transfer from savings operation"
 * under the use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CancelTransferFromSavingsOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701220764657a31"
            + "3333374b02000000011c1d758dde8a13c09292041cba1fd65989dde679a53516086e06db54ce3fa"
            + "62f7524df42ea62a49ec775a854659fb6552c1b66e1e5eb1ce5c02c3eb0c8acfd7162";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce7c80457012207"
            + "64657a313333374b02000000011c31f520b0853fc899655539c77034a9e70b79d052df9aa762b53"
            + "d11298cdde7695cce2342a2c61bb4f24dda613ed9078c37e04c0503230ecc4fa36003ae35876e";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "from" - account. If no active key is provided or the active key is not
     * valid an Exception will be thrown. The active key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        AccountName from = new AccountName("dez1337");
        long requestId = 587;

        CancelTransferFromSavingsOperation cancelTransferFromSavingsOperation = new CancelTransferFromSavingsOperation(
                from, requestId);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(cancelTransferFromSavingsOperation);

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
