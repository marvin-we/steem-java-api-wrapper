package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIntegrationTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "set reset account operation" under the use
 * of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetResetAccountOperationIT extends BaseIntegrationTest {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701260764657a3133333707646"
            + "57a313333370764657a3133333700011c6b5763051fa0e1ebac3998d38aab5de61d08e9b9d56575443f74955ed"
            + "2ea0e1a7aba8e851b6e8396a4fe4b6cfdf03374796fbe2f0ae1de6180ab169b1df94f6a";

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
        setupIntegrationTestEnvironment();

        SetResetAccountOperation setResetAccountOperation = new SetResetAccountOperation();

        setResetAccountOperation.setAccount(new AccountName("dez1337"));
        setResetAccountOperation.setCurrentResetAccount(new AccountName("dez1337"));
        setResetAccountOperation.setResetAccount(new AccountName("dez1337"));

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(setResetAccountOperation);

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
    @Ignore
    public void verifyTransaction() throws Exception {
        // TODO: Check if working
        assertThat(steemApiWrapper.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemApiWrapper.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
