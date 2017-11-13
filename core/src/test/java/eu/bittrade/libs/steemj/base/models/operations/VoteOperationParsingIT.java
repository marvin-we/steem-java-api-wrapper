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
 * This class tests if the {@link VoteOperation} can be parsed successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VoteOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5681456;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_AUTHOR = "sarita";
    private static final String EXPECTED_VOTER = "ned";

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
        SignedBlockWithInfo blockContainingVoteOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation voteOperation = blockContainingVoteOperation.getTransactions().get(TRANSACTION_INDEX).getOperations()
                .get(OPERATION_INDEX);

        assertThat(voteOperation, instanceOf(VoteOperation.class));
        assertThat(((VoteOperation) voteOperation).getAuthor().getName(), equalTo(EXPECTED_AUTHOR));
        assertThat(((VoteOperation) voteOperation).getVoter().getName(), equalTo(EXPECTED_VOTER));
    }
}