package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class tests if the {@link EscrowDisputeOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowDisputeOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 9543384;
    private static final int TRANSACTION_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM = "anonymtest";
    private static final int EXPECTED_ESCROW_ID = 72526562;

    /**
     * Prepare all required fields used by this test class.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
    }

    @Override
    @Test
    @Category({ IntegrationTest.class })
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        SignedBlockWithInfo blockContainingEscrowDisputeOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation escrowDisputeOperation = blockContainingEscrowDisputeOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(escrowDisputeOperation, instanceOf(EscrowDisputeOperation.class));
        assertThat(((EscrowDisputeOperation) escrowDisputeOperation).getFrom().getName(), equalTo(EXPECTED_FROM));
        assertThat(((EscrowDisputeOperation) escrowDisputeOperation).getEscrowId(), equalTo(EXPECTED_ESCROW_ID));
    }
}
