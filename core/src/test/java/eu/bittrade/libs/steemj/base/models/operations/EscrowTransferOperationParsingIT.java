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
 * This class tests if the {@link EscrowTransferOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowTransferOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 9531332;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM = "xtar";
    private static final int EXPECTED_ESCROW_ID = 20618239;
    private static final long EXPECTED_ESCROW_EXPIRATION = 1490215341000L;

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
        SignedBlockWithInfo blockContainingEscrowTransferOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation escrowTransferOperation = blockContainingEscrowTransferOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(escrowTransferOperation, instanceOf(EscrowTransferOperation.class));
        assertThat(((EscrowTransferOperation) escrowTransferOperation).getFrom().getName(), equalTo(EXPECTED_FROM));
        assertThat(((EscrowTransferOperation) escrowTransferOperation).getEscrowId(), equalTo(EXPECTED_ESCROW_ID));
        assertThat(
                ((EscrowTransferOperation) escrowTransferOperation).getEscrowExpirationDate().getDateTimeAsTimestamp(),
                equalTo(EXPECTED_ESCROW_EXPIRATION));
    }
}
