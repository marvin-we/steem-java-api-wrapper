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
 * Verify the functionality of the "transfer from savings operation" under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferFromSavingsOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcf0c8045701210764657a313333"
            + "374b02000006737465656d6a54dd00000000000003535445454d00000000011b40a2fc29dcdcf61408e"
            + "6b5fbc8ec5280c3ec511161b7c40c4675f358352cc452661c770dadf8cafae320356b7c705cfbeaedfc"
            + "64b3e0f45173ecd8f5966a5f15";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce8c804570121076465"
            + "7a313333374b02000006737465656d6a54dd00000000000003535445454d00000000011b61273d40506"
            + "715a0a5e67fd165dcbb4fcfe41d8059c280774459ca501be1881f01ba2f8ba7ee91801a9dce283ebf1c"
            + "6514f110d2ba7982b5eeb18352d71ac1cb";

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
        long requestId = 587;

        Asset amount = new Asset(56660L, AssetSymbolType.STEEM);

        TransferFromSavingsOperation transferFromSavingsOperation = new TransferFromSavingsOperation(from, to, amount,
                requestId, memo);

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
