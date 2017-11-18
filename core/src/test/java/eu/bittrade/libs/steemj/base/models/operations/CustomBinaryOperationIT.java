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
 * Verify the functionality of the "custom binary operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomBinaryOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dc69fce55801230000010764657a31"
            + "33333700096578616d706c6549641154657374466f72537465656d4a3132332100011c42f7ac344c3c543"
            + "96efebd440d384fe0c2e4bc6902d632681e933297f6d886092ed53e7e729327f2d828f988b3fe3d8ae919"
            + "330caa9a25edcba251b844260d1d";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dc6afce558012300000107"
            + "64657a3133333700096578616d706c6549641154657374466f72537465656d4a3132332100011c2e88ab9"
            + "ebf7a100548700829fb09e59cb8514930c8c1f60511e1105133a2213213d9bbeeb2c77fe1b454b686a879"
            + "b521c56f64faf18c174471bb1df4056062df";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "owner". If no active key is provided or the active key is not valid an
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

        // If the default expiration date for all integration tests
        // (2016-04-06T08:29:27UTC) is used, the transaction can't be verified.
        // As a workaround new date is set that is not that far in the past.
        signedTransaction.setExpirationDate(new TimePointSec("2017-04-06T08:29:27UTC"));

        String id = "exampleId";
        String data = "54657374466f72537465656d4a31323321";

        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(new AccountName("dez1337"));

        CustomBinaryOperation customBinaryOperation = new CustomBinaryOperation(null, null, requiredPostingAuths, null,
                id, data);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customBinaryOperation);

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
