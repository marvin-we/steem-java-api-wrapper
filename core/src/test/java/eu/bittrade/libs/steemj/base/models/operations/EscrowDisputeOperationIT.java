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
 * Verify the functionality of the "escrow dispute operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowDisputeOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 9543384;
    private static final int TRANSACTION_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM = "anonymtest";
    private static final int EXPECTED_ESCROW_ID = 72526562;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c80457011c0764657a31333337076"
            + "4657a3133333706737465656d6a0764657a313333372200000000011c2deb8ab73f8e68ad02b700291a237e8"
            + "dcd918d54b0259ba51d887de8726c653f306f27c86f76891c070a84f1979935afdba31ed2a08a896b73badfc" + "4b1050b16";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "from" account. If no active key is provided or the active key is not
     * valid an Exception will be thrown. The private key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionalTests();

        AccountName agent = new AccountName("steemj");
        AccountName from = new AccountName("dez1337");
        AccountName to = new AccountName("dez1337");
        AccountName who = new AccountName("dez1337");
        int escrowId = 34;

        EscrowDisputeOperation escrowDisputeOperation = new EscrowDisputeOperation(from, to, agent, escrowId, who);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(escrowDisputeOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        SignedBlockWithInfo blockContainingEscrowDisputeOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation escrowDisputeOperation = blockContainingEscrowDisputeOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(escrowDisputeOperation, instanceOf(EscrowDisputeOperation.class));
        assertThat(((EscrowDisputeOperation) escrowDisputeOperation).getFrom().getName(), equalTo(EXPECTED_FROM));
        assertThat(((EscrowDisputeOperation) escrowDisputeOperation).getEscrowId(), equalTo(EXPECTED_ESCROW_ID));
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
