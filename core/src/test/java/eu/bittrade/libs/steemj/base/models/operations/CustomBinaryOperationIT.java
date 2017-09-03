package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIntegrationTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "custom binary operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomBinaryOperationIT extends BaseIntegrationTest {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dc69fce55801230000010764657a31"
            + "33333700096578616d706c6549641154657374466f72537465656d4a3132332100011c42f7ac344c3c543"
            + "96efebd440d384fe0c2e4bc6902d632681e933297f6d886092ed53e7e729327f2d828f988b3fe3d8ae919"
            + "330caa9a25edcba251b844260d1d";

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
        setupIntegrationTestEnvironment();

        // If the default expiration date for all integration tests
        // (2016-04-06T08:29:27UTC) is used, the transaction can't be verified.
        // As a workaround new date is set that is not that far in the past.
        transaction.setExpirationDate(new TimePointSec("2017-04-06T08:29:27UTC"));

        CustomBinaryOperation customBinaryOperation = new CustomBinaryOperation();

        customBinaryOperation.setId("exampleId");
        customBinaryOperation.setData("54657374466f72537465656d4a31323321");

        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(new AccountName("dez1337"));

        customBinaryOperation.setRequiredPostingAuths(requiredPostingAuths);
        customBinaryOperation.setRequiredActiveAuths(new ArrayList<>());
        customBinaryOperation.setRequiredAuths(new ArrayList<>());
        customBinaryOperation.setRequiredOwnerAuths(new ArrayList<>());

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customBinaryOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        // TODO
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
