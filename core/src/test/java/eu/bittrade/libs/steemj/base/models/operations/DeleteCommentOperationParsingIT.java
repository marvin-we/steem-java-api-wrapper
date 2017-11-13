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
 * This class tests if the {@link DeleteCommentOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DeleteCommentOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5717145;
    private static final int TRANSACTION_INDEX = 4;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_AUTHOR = "mindhunter";
    private static final String EXPECTED_PERMANENT_LINK = "re-mindhunter-re-mindhunter-re-biophil-"
            + "re-mindhunter-why-does-steemit-need-to-reach-a-unanimous-agreement-to-hard-fork-lik"
            + "e-ethereum-to-smoke-out-all-that-blatantly-pre-mined-steem-20161010t184945348z";

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
        SignedBlockWithInfo blockContainingDeleteCommentOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation deleteCommentOperation = blockContainingDeleteCommentOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(deleteCommentOperation, instanceOf(DeleteCommentOperation.class));
        assertThat(((DeleteCommentOperation) deleteCommentOperation).getAuthor().getName(), equalTo(EXPECTED_AUTHOR));
        assertThat(((DeleteCommentOperation) deleteCommentOperation).getPermlink().getLink(),
                equalTo(EXPECTED_PERMANENT_LINK));
    }
}
