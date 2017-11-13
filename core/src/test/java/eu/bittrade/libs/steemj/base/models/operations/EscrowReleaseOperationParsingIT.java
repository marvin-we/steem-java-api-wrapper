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
 * This class tests if the {@link EscrowReleaseOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowReleaseOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 9544320;
    private static final int TRANSACTION_INDEX = 8;
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
        SignedBlockWithInfo blockContainingEscrowReleaseOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation escrowReleaseOperation = blockContainingEscrowReleaseOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(escrowReleaseOperation, instanceOf(EscrowReleaseOperation.class));
        assertThat(((EscrowReleaseOperation) escrowReleaseOperation).getFrom().getName(), equalTo(EXPECTED_FROM));
        assertThat(((EscrowReleaseOperation) escrowReleaseOperation).getEscrowId(), equalTo(EXPECTED_ESCROW_ID));
    }
}
