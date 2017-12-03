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
 * Verify the functionality of the "transfer operation" under the use of real
 * api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcecc8045701020764657a3133333706737465656d"
            + "6a010000000000000003535445454d00001354657374203420537465656d4a20302e322e3200011c658003b815daf2f16"
            + "628698bfcdfae003fb437554465c8241af5276066110e07066a4f27130e1b6da3e4c9e0d996138893ef7e8b6e4bd9acf6"
            + "15963040996fc3";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce8c8045701020764657a313333370673"
            + "7465656d6a010000000000000003535445454d00001354657374203420537465656d4a20302e322e3200011c566da7002"
            + "efbf0b263bbfee655cba098729b8ff237ce40d91537940738ddbf543e72e8d4adfa674386871362fbfe6ddcdc00148ca2"
            + "e9d53134a44adca471208a";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "from" account. If no active key is provided or the active key is not
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

        Asset amount = new Asset(1L, AssetSymbolType.STEEM);

        String memo = "Test 4 SteemJ 0.2.2";

        TransferOperation transferOperation = new TransferOperation(from, to, amount, memo);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferOperation);

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
