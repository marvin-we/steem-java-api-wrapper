package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalIntegrationTest;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * Verify the functionality of the "vote operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountWitnessVoteOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5716844;
    private static final int TRANSACTION_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ACCOUNT = "zentat";
    private static final boolean EXPECTED_APPROVE = true;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcebc80457010c0764657a3133"
            + "33370a676f6f642d6b61726d610100011c0bd317625d87f01e838165a689596eff95cb03d3cf2076b"
            + "a670886eb29c2ab3f5155f80e7c52d388c0fbc52e362b9f9b00d3ff7426b9e0168c0281672ad19367";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "account". If no active key is provided or the active key is not valid an
     * Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionalTests();

        AccountName account = new AccountName("dez1337");
        AccountName witness = new AccountName("good-karma");

        AccountWitnessVoteOperation accountWitnessVoteOperation = new AccountWitnessVoteOperation(account, witness);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(accountWitnessVoteOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        SignedBlockWithInfo blockContainingAccountWitnessVoteOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation accountWitnessVoteOperation = blockContainingAccountWitnessVoteOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(accountWitnessVoteOperation, instanceOf(AccountWitnessVoteOperation.class));
        assertThat(((AccountWitnessVoteOperation) accountWitnessVoteOperation).getAccount().getName(),
                equalTo(EXPECTED_ACCOUNT));
        assertThat(((AccountWitnessVoteOperation) accountWitnessVoteOperation).getApprove(), equalTo(EXPECTED_APPROVE));
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
