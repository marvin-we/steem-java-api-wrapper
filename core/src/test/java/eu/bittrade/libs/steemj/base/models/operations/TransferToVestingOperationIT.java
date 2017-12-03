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
 * Verify the functionality of the "transfer to vesting operation" under the use
 * of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferToVestingOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701030764657a3"
            + "13333370764657a31333337010000000000000003535445454d000000011b45762246b1f9df3c3"
            + "5294487ca772a5d685a9846eb0d5472ee643da66b94d3461b49fbfa326cb108be041174ca3d944"
            + "3990572b0ac1de38ea3914b179aaffbca";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce8c8045701030"
            + "764657a313333370764657a31333337010000000000000003535445454d000000011b53014cdd6"
            + "01ffbfd58adf717a6557a94b9b57ee2ff9417f0b703f3435fb3cf646aed0f20070d4d70eb6249d"
            + "2261adb1d440c430252f94f0b9fa70bae72f51bc4";

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
        AccountName to = new AccountName("dez1337");

        Asset steemAmount = new Asset(1L, AssetSymbolType.STEEM);

        TransferToVestingOperation transferToVestingOperation = new TransferToVestingOperation(from, to, steemAmount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferToVestingOperation);

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
