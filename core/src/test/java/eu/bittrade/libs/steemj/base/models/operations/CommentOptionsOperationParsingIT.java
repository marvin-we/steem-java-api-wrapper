package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class tests if the {@link CommentOptionsOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5716958;
    private static final int TRANSACTION_INDEX = 2;
    private static final int OPERATION_INDEX = 2;
    private static final String EXPECTED_AUTHOR = "rihchie.ebb";
    private static final String EXPECTED_PERMANENT_LINK = "giving-a-farewell-speech";
    private static final boolean EXPECTED_VOTES_ALLOWED = true;
    private static final Asset EXPECTED_ASSET = new Asset(1000000000, AssetSymbolType.SBD);

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
        SignedBlockWithInfo blockContainingCommentOptionsOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation commentOptionsOperation = blockContainingCommentOptionsOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(commentOptionsOperation, instanceOf(CommentOptionsOperation.class));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getAuthor().getName(), equalTo(EXPECTED_AUTHOR));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getAllowVotes(),
                equalTo(EXPECTED_VOTES_ALLOWED));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getPermlink().getLink(),
                equalTo(EXPECTED_PERMANENT_LINK));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getMaxAcceptedPayout(), equalTo(EXPECTED_ASSET));
    }
}
