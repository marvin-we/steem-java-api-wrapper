package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIntegrationTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.base.models.operations.CommentOptionsOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "comment options operation" under the use of
 * real api calls.
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
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcedc8045701130764657a"
            + "3133333728737465656d6a2d76302d322d342d6861732d6265656e2d72656c65617365642d757"
            + "0646174652d3900ca9a3b000000000353424400000000102701010000011c06dfac5938938a83"
            + "85cbc3aec6f27ae22993820349d93b4060af92386305517c0f3c85a04b34b074226b8b5bb6384"
            + "2ee3cb208a52b6c0c01e6b8d0d838a38aba";

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
        SignedBlockWithInfo blockContainingCommentOptionsOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

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
        assertThat(steemJ.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
