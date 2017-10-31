package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalIntegrationTest;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseError;

/**
 * Verify the functionality of the "vote operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VoteOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5681456;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_AUTHOR = "sarita";
    private static final String EXPECTED_VOTER = "ned";
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701000"
            + "764657a313333370764657a3133333728737465656d6a2d76302d322d342d6861732d6"
            + "265656e2d72656c65617365642d7570646174652d39e80300011b2c91031ff0d1e1e56"
            + "607644da79f7c837af4f23415519babeea2061538aed5461e15475edaa2d7ee6134693"
            + "6ca276ed0a1444464cd25e947956ba9f15496e28a";

    /**
     * <b>Attention:</b> This test class requires a valid posting key of the
     * used "voter". If no posting key is provided or the posting key is not
     * valid an Exception will be thrown. The private key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionalTests();

        AccountName author = new AccountName("dez1337");
        Permlink permlink = new Permlink("steemj-v0-2-4-has-been-released-update-9");
        AccountName voter = new AccountName("dez1337");
        short weight = 1000;

        VoteOperation voteOperation = new VoteOperation(author, voter, permlink, weight);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        signedTransaction.setOperations(operations);
        // Sign the transaction without validation.
        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseError {
        SignedBlockWithInfo blockContainingVoteOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation voteOperation = blockContainingVoteOperation.getTransactions().get(TRANSACTION_INDEX).getOperations()
                .get(OPERATION_INDEX);

        assertThat(voteOperation, instanceOf(VoteOperation.class));
        assertThat(((VoteOperation) voteOperation).getAuthor().getName(), equalTo(EXPECTED_AUTHOR));
        assertThat(((VoteOperation) voteOperation).getVoter().getName(), equalTo(EXPECTED_VOTER));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(signedTransaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
