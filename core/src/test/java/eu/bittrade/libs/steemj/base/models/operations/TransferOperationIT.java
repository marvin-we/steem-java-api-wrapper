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
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalIntegrationTest;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "transfer operation" under the use of real
 * api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5716771;
    private static final int TRANSACTION_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM_ACCOUNT = "bittrex";
    private static final String EXPECTED_TO_ACCOUNT = "kurtbeil";
    private static final double EXPECTED_AMOUNT = 14.358;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701020764657a3133"
            + "33370764657a31333337010000000000000003535445454d00001354657374203420537465656d4a2"
            + "0302e322e3200011c0b8e4977d4f049050219ea8d3d1078be7cba2e25341f5b0678c749799191ba7c"
            + "75f5693cbe955c4a1cfeef1866084dd40509ef5a9cb7d706fa6493cc86fe5fd7";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "from" account. If no active key is provided or the active key is not
     * valid an Exception will be thrown. The active key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionalTests();

        AccountName from = new AccountName("dez1337");
        AccountName to = new AccountName("steemj");

        Asset amount = new Asset();
        amount.setAmount(1L);
        amount.setSymbol(AssetSymbolType.STEEM);

        String memo = "Test 4 SteemJ 0.2.2";

        TransferOperation transferOperation = new TransferOperation(from, to, amount, memo);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingTransferOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation transferOperation = blockContainingTransferOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations().get(OPERATION_INDEX);

        assertThat(transferOperation, instanceOf(TransferOperation.class));
        assertThat(((TransferOperation) transferOperation).getFrom().getName(), equalTo(EXPECTED_FROM_ACCOUNT));
        assertThat(((TransferOperation) transferOperation).getTo().getName(), equalTo(EXPECTED_TO_ACCOUNT));
        assertThat(((TransferOperation) transferOperation).getAmount().toReal(), equalTo(EXPECTED_AMOUNT));
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
