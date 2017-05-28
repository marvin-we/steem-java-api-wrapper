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
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Block;

/**
 * Verify the functionality of the "vote operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5716958;
    private static final int TRANSACTION_INDEX = 2;
    private static final int OPERATION_INDEX = 2;
    private static final String EXPECTED_AUTHOR = "rihchie.ebb";
    private static final String EXPECTED_PERMANENT_LINK = "giving-a-farewell-speech";
    private static final boolean EXPECTED_VOTES_ALLOWED = true;
    private static final Asset EXPECTED_ASSET = new Asset();
    private static final String EXPECTED_TRANSACTION_HEX = "f";

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

        CommentOptionsOperation commentOptionsOperation = new CommentOptionsOperation();
        commentOptionsOperation.setAuthor(new AccountName("dez1337"));
        commentOptionsOperation.setPermlink("steemj-v0-2-4-has-been-released-update-9");
        commentOptionsOperation.setAllowVotes(true);
        commentOptionsOperation.setAllowCurationRewards(true);
        commentOptionsOperation.setPercentSteemDollars((short) 10000);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOptionsOperation);

        transaction.setOperations(operations);
        transaction.sign();

        // Set expected objects.
        EXPECTED_ASSET.setAmount(1000000000);
        EXPECTED_ASSET.setSymbol(AssetSymbolType.SBD);
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingCommentOptionsOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation commentOptionsOperation = blockContainingCommentOptionsOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(commentOptionsOperation, instanceOf(CommentOptionsOperation.class));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getAuthor().getAccountName(),
                equalTo(EXPECTED_AUTHOR));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getAllowVotes(),
                equalTo(EXPECTED_VOTES_ALLOWED));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getPermlink(), equalTo(EXPECTED_PERMANENT_LINK));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getMaxAcceptedPayout(), equalTo(EXPECTED_ASSET));
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
