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
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "limit order cancel operation" under the use
 * of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCancelOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5681453;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_AUTHOR = "fnait";
    private static final int EXPECTED_ORDER_ID = -1721858468;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce9c8045701060764657a313333372"
            + "04e000000011b1044e4094a14f65d84a8da327d5fec0c740ce4f39b892105786686911eac09051373042e9"
            + "6fce01763a9cb2a004019dc38b278e8fae12f86198f60e28c981f2c";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "owner". If no active key is provided or the active key is not valid an
     * Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionalTests();

        int orderId = 20000;
        AccountName owner = new AccountName("dez1337");

        LimitOrderCancelOperation limitOrderCancelOperation = new LimitOrderCancelOperation(owner, orderId);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(limitOrderCancelOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingVoteOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation voteOperation = blockContainingVoteOperation.getTransactions().get(TRANSACTION_INDEX).getOperations()
                .get(OPERATION_INDEX);

        assertThat(voteOperation, instanceOf(LimitOrderCancelOperation.class));
        assertThat(((LimitOrderCancelOperation) voteOperation).getOwner().getName(), equalTo(EXPECTED_AUTHOR));
        assertThat(((LimitOrderCancelOperation) voteOperation).getOrderId(), equalTo(EXPECTED_ORDER_ID));
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
