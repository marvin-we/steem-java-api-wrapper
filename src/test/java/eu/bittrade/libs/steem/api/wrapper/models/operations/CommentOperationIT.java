package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Block;

/**
 * Verify the functionality of the "comment operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5688416;
    private static final int TRANSACTION_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_AUTHOR = "ladypenelope1";
    private static final String EXPECTED_JSON_METADATA = "{\"tags\":[\"food\"]}";
    private static final String EXPECTED_TITLE = "";
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcf0c8045701010764657a31"
            + "33333728737465656d6a2d76302d322d342d6861732d6265656e2d72656c65617365642d7570646"
            + "174652d390764657a313333372b72652d737465656d6a2d76302d322d342d6861732d6265656e2d"
            + "72656c65617365642d7570646174652d39012d0b5465737420537465656d4a027b7d00011c0d31e"
            + "dd635c19ca38bac4f5c3d8e2eff1bad79b4b35b6c8c8f93d237298b5666798eca80acf63585a6ef"
            + "944a50032fe35351e2343bbc1e33ea84afe410eeb295";

    /**
     * <b>Attention:</b> This test class requires a valid posting key of the
     * used "author". If no posting key is provided or the posting key is not
     * valid an Exception will be thrown. The private key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        CommentOperation commentOperation = new CommentOperation();
        commentOperation.setAuthor(new AccountName("dez1337"));
        commentOperation.setPermlink("re-steemj-v0-2-4-has-been-released-update-9");

        commentOperation.setBody("Test SteemJ");
        commentOperation.setJsonMetadata("{}");
        commentOperation.setParentAuthor(new AccountName("dez1337"));
        commentOperation.setParentPermlink("steemj-v0-2-4-has-been-released-update-9");
        commentOperation.setTitle("-");

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingCommentOperationOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation commentOperation = blockContainingCommentOperationOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations().get(OPERATION_INDEX);

        assertThat(commentOperation, instanceOf(CommentOperation.class));
        assertThat(((CommentOperation) commentOperation).getAuthor().getAccountName(), equalTo(EXPECTED_AUTHOR));
        assertThat(((CommentOperation) commentOperation).getJsonMetadata(), equalTo(EXPECTED_JSON_METADATA));
        assertThat(((CommentOperation) commentOperation).getTitle(), equalTo(EXPECTED_TITLE));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemApiWrapper.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemApiWrapper.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
