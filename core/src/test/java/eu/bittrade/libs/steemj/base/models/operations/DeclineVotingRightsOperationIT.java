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
 * Verify the functionality of the "reset account operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DeclineVotingRightsOperationIT extends BaseTransactionalIntegrationTest {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dceac8045701240764657a313333370000011c79"
            + "abd06afe01810282b23034df82c8213611311d563c75ccc9c185ab795ae86b2c40952782388455ca18267c7c7ba8474"
            + "3bde3234a12360db6d5a210a9a43c19";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "reset account". If no active key is provided or the active key is not
     * valid an Exception will be thrown. The active key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionalTests();

        AccountName account = new AccountName("dez1337");
        boolean decline = false;

        DeclineVotingRightsOperation declineVotingRightsOperation = new DeclineVotingRightsOperation(account, decline);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(declineVotingRightsOperation);

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
