package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.Block;

/**
 * Verify the functionality of the "escrow transfer operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowTransferOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 9531332;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM = "xtar";
    private static final long EXPECTED_ESCROW_ID = 20618239L;
    private static final int EXPECTED_ESCROW_EXPIRATION = 1490215341;
    
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingEscrowTransferOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation escrowTransferOperation = blockContainingEscrowTransferOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(escrowTransferOperation, instanceOf(EscrowTransferOperation.class));
        assertThat(((EscrowTransferOperation) escrowTransferOperation).getFrom().getAccountName(),
                equalTo(EXPECTED_FROM));
        assertThat(((EscrowTransferOperation) escrowTransferOperation).getEscrowId(), equalTo(EXPECTED_ESCROW_ID));
        assertThat(((EscrowTransferOperation) escrowTransferOperation).getEscrowExpirationDateAsInt(), equalTo(EXPECTED_ESCROW_EXPIRATION));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        // TODO
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        // TODO
    }
}
