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
import eu.bittrade.libs.steemj.base.models.operations.ChangeRecoveryAccountOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "change recovery account operation" under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ChangeRecoveryAccountOperationIT extends BaseIntegrationTest {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcf3c80457011a06737465656d6a07646"
            + "57a313333370000011c5f10b6238b7783f0e99fc6a610a5b7188a66afa72ccc633a460c9683285df7371bbad"
            + "cad04ae7407f87fb0ff24bd3cc787fec0a85037ec88c76579deef009b7c";

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
        setupIntegrationTestEnvironment();

        ChangeRecoveryAccountOperation changeRecoveryAccountOperation = new ChangeRecoveryAccountOperation();

        changeRecoveryAccountOperation.setAccountToRecover(new AccountName("steemj"));
        changeRecoveryAccountOperation.setNewRecoveryAccount(new AccountName("dez1337"));

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(changeRecoveryAccountOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        // TODO: Implement
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemApiWrapper.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemApiWrapper.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
