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
import eu.bittrade.libs.steemj.exceptions.SteemResponseError;

/**
 * Verify the functionality of the "transfer to vesting operation" under the use
 * of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferToVestingOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5716852;
    private static final int TRANSACTION_INDEX = 8;
    private static final int OPERATION_INDEX = 0;
    private static final double EXPECTED_AMOUNT = 14.438;
    private static final String EXPECTED_FROM = "kurtbeil";
    private static final String EXPECTED_TO = "kurtbeil";
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701030764657a3"
            + "13333370764657a31333337010000000000000003535445454d000000011b45762246b1f9df3c3"
            + "5294487ca772a5d685a9846eb0d5472ee643da66b94d3461b49fbfa326cb108be041174ca3d944"
            + "3990572b0ac1de38ea3914b179aaffbca";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "from" - account. If no active key is provided or the active key is not
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
        AccountName to = new AccountName("dez1337");

        Asset steemAmount = new Asset();
        steemAmount.setAmount(1L);
        steemAmount.setSymbol(AssetSymbolType.STEEM);

        TransferToVestingOperation transferToVestingOperation = new TransferToVestingOperation(from, to, steemAmount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferToVestingOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseError {
        SignedBlockWithInfo blockContainingTransferToVestingOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation transferToVestingOperation = blockContainingTransferToVestingOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(transferToVestingOperation, instanceOf(TransferToVestingOperation.class));
        assertThat(((TransferToVestingOperation) transferToVestingOperation).getAmount().toReal(),
                equalTo(EXPECTED_AMOUNT));
        assertThat(((TransferToVestingOperation) transferToVestingOperation).getFrom().getName(),
                equalTo(EXPECTED_FROM));
        assertThat(((TransferToVestingOperation) transferToVestingOperation).getTo().getName(), equalTo(EXPECTED_TO));
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
