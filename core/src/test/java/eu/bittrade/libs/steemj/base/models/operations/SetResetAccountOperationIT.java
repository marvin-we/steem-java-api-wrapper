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
 * Verify the functionality of the "set reset account operation" under the use
 * of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetResetAccountOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcebc8045701260764657a313333370006737465656d6a"
            + "00011b7d16fb4917505355d0ae04ceab7aa063904b692c321c850fed56a6bedd86967f2f225260e699a5ec23984b03d81824e"
            + "8ff0ba0a16164e03a730b5575f87f1097";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce8c8045701260764657a3133333700067374"
            + "65656d6a00011c5b01313cf44b76c0211009b7ccb806a5a2b5aba0302c2318c316f053696f473c72f6fad5d275962d5772249"
            + "d807cdebc1cc1132cb60cfda63a773a67947ef945";

    /**
     * <b>Attention:</b> This test class requires a valid owner key of the used
     * "account". If no owner key is provided or the owner key is not valid an
     * Exception will be thrown. The owner key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        AccountName account = new AccountName("dez1337");
        AccountName currentResetAccount = new AccountName("");
        AccountName newResetAccount = new AccountName("steemj");

        SetResetAccountOperation setResetAccountOperation = new SetResetAccountOperation(account, currentResetAccount,
                newResetAccount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(setResetAccountOperation);

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
