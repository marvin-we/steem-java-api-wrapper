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
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Verify the functionality of the "transfer to savings operation" under the use
 * of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferToSavingsOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c80457012007646"
            + "57a3133333706737465656d6a54dd00000000000003535445454d00000000011b64310c9be"
            + "405a03053f120109e01557a51b33858f84d96068d208b22c1c0e0352073e075f6dbb5c057a"
            + "be8639fc8a7ed8fe539237e8d22e6e921226c4fd4d1ab";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dceec804570"
            + "1200764657a3133333706737465656d6a54dd00000000000003535445454d00000000011b7"
            + "ee6ccd60007c9011dcb32c7009e77339ffe170c8a3a2931ddc33cfbabfd4a1104409f4101e"
            + "41e8f5632e0d88517188f422ed48fe5fb250945cca0d819d4dc08";

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
        AccountName to = new AccountName("steemj");
        String memo = "";

        Asset amount = new Asset(56660L, AssetSymbolType.STEEM);

        TransferToSavingsOperation transferFromSavingsOperation = new TransferToSavingsOperation(from, to, amount,
                memo);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferFromSavingsOperation);

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
