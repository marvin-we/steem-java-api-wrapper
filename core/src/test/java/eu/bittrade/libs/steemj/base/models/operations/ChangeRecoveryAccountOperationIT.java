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
 * Verify the functionality of the "change recovery account operation" under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ChangeRecoveryAccountOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcebc80457011a0764657a3133333706737465656"
            + "d6a0000011b6a2574daa46d4e964d37a2b129fbfff8db4e4e49eaf12f7c072e940833a6181c5c961d46ddc3eaaaf6e25"
            + "00c7f954b06386f888e1f22d2ec5554a3cd9629770a";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dcebc80457011a0764657a31333337067"
            + "37465656d6a0000011c10beb285d677f26b26cbf569c6aaeba84fdb00c2743ff9898ccf90cef49943150c6eaba4486da"
            + "ec983591e3b41c58c019aa3e8a5a7f1e24e37e61cf78442d695";

    /**
     * <b>Attention:</b> This test class requires a valid owner key of the used
     * "account to recover". If no owner key is provided or the owner key is not
     * valid an Exception will be thrown. The owner key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        AccountName accountToRecover = new AccountName("dez1337");
        AccountName newRecoveryAccount = new AccountName("steemj");

        ChangeRecoveryAccountOperation changeRecoveryAccountOperation = new ChangeRecoveryAccountOperation(
                accountToRecover, newRecoveryAccount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(changeRecoveryAccountOperation);

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
