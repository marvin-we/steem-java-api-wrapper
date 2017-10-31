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
import eu.bittrade.libs.steemj.exceptions.SteemResponseError;

/**
 * Verify the functionality of the "escrow approve operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowApproveOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 9543169;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM = "anonymtest";
    private static final int EXPECTED_ESCROW_ID = 72526562;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c80457011f0764657a313333"
            + "370764657a3133333706737465656d6a0764657a31333337220000000100011c332f9e5eb7bbe20ba13"
            + "1e73ef6b399c09b6c109b6873b6fc333570e087cd25271ce54a4070c8a012ef6e1e0f050a1f7c992a1f"
            + "4fd2b7e6a17b61f178988fbff0";

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
        boolean approve = true;

        EscrowApproveOperation escrowApproveOperation = new EscrowApproveOperation(from, to, agent, escrowId, who,
                approve);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(escrowApproveOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseError {
        SignedBlockWithInfo blockContainingApproveOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation escrowApproveOperation = blockContainingApproveOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations().get(OPERATION_INDEX);

        assertThat(escrowApproveOperation, instanceOf(EscrowApproveOperation.class));
        assertThat(((EscrowApproveOperation) escrowApproveOperation).getFrom().getName(), equalTo(EXPECTED_FROM));
        assertThat(((EscrowApproveOperation) escrowApproveOperation).getEscrowId(), equalTo(EXPECTED_ESCROW_ID));
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
