package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalIntegrationTest;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "set reset account operation" under the use
 * of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetResetAccountOperationIT extends BaseTransactionalIntegrationTest {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcebc8045701260764657a313333370006737465656d6a"
            + "00011b7d16fb4917505355d0ae04ceab7aa063904b692c321c850fed56a6bedd86967f2f225260e699a5ec23984b03d81824e"
            + "8ff0ba0a16164e03a730b5575f87f1097";

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
        setupIntegrationTestEnvironmentForTransactionalTests();

        AccountName account = new AccountName("dez1337");
        AccountName currentResetAccount = new AccountName("");
        AccountName newResetAccount = new AccountName("steemj");

        SetResetAccountOperation setResetAccountOperation = new SetResetAccountOperation(account, currentResetAccount,
                newResetAccount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(setResetAccountOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        // TODO: Implement
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(signedTransaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
