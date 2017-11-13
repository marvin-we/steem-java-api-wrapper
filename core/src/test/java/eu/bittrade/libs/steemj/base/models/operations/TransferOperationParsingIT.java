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
 * This class tests if the {@link TransferOperation} can be parsed successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5716771;
    private static final int TRANSACTION_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM_ACCOUNT = "bittrex";
    private static final String EXPECTED_TO_ACCOUNT = "kurtbeil";
    private static final double EXPECTED_AMOUNT = 14.358;

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
        SignedBlockWithInfo blockContainingTransferOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation transferOperation = blockContainingTransferOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations().get(OPERATION_INDEX);

        assertThat(transferOperation, instanceOf(TransferOperation.class));
        assertThat(((TransferOperation) transferOperation).getFrom().getName(), equalTo(EXPECTED_FROM_ACCOUNT));
        assertThat(((TransferOperation) transferOperation).getTo().getName(), equalTo(EXPECTED_TO_ACCOUNT));
        assertThat(((TransferOperation) transferOperation).getAmount().toReal(), equalTo(EXPECTED_AMOUNT));
    }
}
