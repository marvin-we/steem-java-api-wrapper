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
public class VoteOperationIntegrationTest extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5681456;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_AUTHOR = "sarita";
    private static final String EXPECTED_VOTER = "ned";

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingVoteOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation voteOperation = blockContainingVoteOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations()[OPERATION_INDEX];

        assertThat(voteOperation, instanceOf(VoteOperation.class));
        assertThat(((VoteOperation) voteOperation).getAuthor(), equalTo(EXPECTED_AUTHOR));
        assertThat(((VoteOperation) voteOperation).getVoter(), equalTo(EXPECTED_VOTER));
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
