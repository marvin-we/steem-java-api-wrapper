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
 * Verify the functionality of the "reset account operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DeclineVotingRightsOperationIT extends BaseIntegrationTest {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcedc8045701240764657a313333"
            + "370000011c235972a4aee54b1477ec55762d6d7e7bee4311a16b97f9d074937affd5cf0a3b3ed2ba3b0"
            + "428c8284c7c747e6237c80c2fae3284c354b05fb5c54753c0c6ba58";

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
        setupIntegrationTestEnvironment();

        DeclineVotingRightsOperation declineVotingRightsOperation = new DeclineVotingRightsOperation();

        declineVotingRightsOperation.setAccount(new AccountName("dez1337"));
        declineVotingRightsOperation.setDecline(false);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(declineVotingRightsOperation);

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
        assertThat(steemJ.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
