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
public class WitnessUpdateOperationIntegrationTest extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5717839;
    private static final int TRANSACTION_INDEX = 3;
    private static final int OPERATION_INDEX = 0;
    private static final String WITNESS_NAME = "glitterpig";
    private static final double FEE_AMOUNT = 0.0;
    private static final double ACCOUNT_CREATION_FEE = 3.0;

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingWitnessUpdateOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation witnessUpdateOperation = blockContainingWitnessUpdateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(witnessUpdateOperation, instanceOf(WitnessUpdateOperation.class));
        assertThat(((WitnessUpdateOperation) witnessUpdateOperation).getOwner().getAccountName(),
                equalTo(WITNESS_NAME));
        assertThat(((WitnessUpdateOperation) witnessUpdateOperation).getFee().getAmount(), equalTo(FEE_AMOUNT));
        assertThat(
                ((WitnessUpdateOperation) witnessUpdateOperation).getProperties().getAccountCreationFee().getAmount(),
                equalTo(ACCOUNT_CREATION_FEE));

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
