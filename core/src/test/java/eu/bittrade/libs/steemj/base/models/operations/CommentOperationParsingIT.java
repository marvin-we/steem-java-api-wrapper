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
 * This class tests if the {@link CommentOperation} can be parsed successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5688416;
    private static final int TRANSACTION_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_AUTHOR = "ladypenelope1";
    private static final String EXPECTED_JSON_METADATA = "{\"tags\":[\"food\"]}";
    private static final String EXPECTED_TITLE = "";

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
        SignedBlockWithInfo blockContainingCommentOperationOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation commentOperation = blockContainingCommentOperationOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations().get(OPERATION_INDEX);

        assertThat(commentOperation, instanceOf(CommentOperation.class));
        assertThat(((CommentOperation) commentOperation).getAuthor().getName(), equalTo(EXPECTED_AUTHOR));
        assertThat(((CommentOperation) commentOperation).getJsonMetadata(), equalTo(EXPECTED_JSON_METADATA));
        assertThat(((CommentOperation) commentOperation).getTitle(), equalTo(EXPECTED_TITLE));
    }
}
