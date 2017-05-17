package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.Block;

/**
 * Verify the functionality of the "vote operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferToVestingOperationIntegrationTest extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5716852;
    private static final int TRANSACTION_INDEX = 8;
    private static final int OPERATION_INDEX = 0;
    private static final double EXPECTED_AMOUNT = 14.438;
    private static final String EXPECTED_FROM = "kurtbeil";
    private static final String EXPECTED_TO = "kurtbeil";

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingTransferToVestingOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation transferToVestingOperation = blockContainingTransferToVestingOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(transferToVestingOperation, instanceOf(TransferToVestingOperation.class));
        assertThat(((TransferToVestingOperation) transferToVestingOperation).getAmount().getAmount(),
                equalTo(EXPECTED_AMOUNT));
        assertThat(((TransferToVestingOperation) transferToVestingOperation).getFrom().getAccountName(),
                equalTo(EXPECTED_FROM));
        assertThat(((TransferToVestingOperation) transferToVestingOperation).getTo().getAccountName(),
                equalTo(EXPECTED_TO));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() {
        // TODO: Verify a Transaction containing a vote operation against the
        // api.
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() {
        // TODO: Use an API call to get the hex value of the transaction.
    }
}
