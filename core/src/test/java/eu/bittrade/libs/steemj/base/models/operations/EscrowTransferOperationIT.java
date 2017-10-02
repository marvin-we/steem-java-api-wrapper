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
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "escrow transfer operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowTransferOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 9531332;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM = "xtar";
    private static final int EXPECTED_ESCROW_ID = 20618239;
    private static final long EXPECTED_ESCROW_EXPIRATION = 1490215341000L;
    private static final String EXPECTED_TRANSACTION_HEX = "0";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "from" account. If no active key is provided or the active key is not
     * valid an Exception will be thrown. The private key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionalTests();

        AccountName agent = new AccountName("steemj");
        AccountName from = new AccountName("dez1337");
        AccountName to = new AccountName("dez1337");
        TimePointSec escrowExpirationDate = new TimePointSec(1490215341);
        long escrowId = 34;
        TimePointSec ratificationDeadlineDate = new TimePointSec(1490215340);
        String jsonMeta = "";

        Asset sbdAmount = new Asset();
        sbdAmount.setAmount(1L);
        sbdAmount.setSymbol(AssetSymbolType.SBD);

        Asset steemAmount = new Asset();
        steemAmount.setAmount(10L);
        steemAmount.setSymbol(AssetSymbolType.STEEM);

        Asset fee = new Asset();
        fee.setAmount(1L);
        fee.setSymbol(AssetSymbolType.STEEM);

        EscrowTransferOperation escrowTransferOperation = new EscrowTransferOperation(from, to, agent, escrowId,
                sbdAmount, steemAmount, fee, ratificationDeadlineDate, escrowExpirationDate, jsonMeta);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(escrowTransferOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingEscrowTransferOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation escrowTransferOperation = blockContainingEscrowTransferOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(escrowTransferOperation, instanceOf(EscrowTransferOperation.class));
        assertThat(((EscrowTransferOperation) escrowTransferOperation).getFrom().getName(), equalTo(EXPECTED_FROM));
        assertThat(((EscrowTransferOperation) escrowTransferOperation).getEscrowId(), equalTo(EXPECTED_ESCROW_ID));
        assertThat(
                ((EscrowTransferOperation) escrowTransferOperation).getEscrowExpirationDate().getDateTimeAsTimestamp(),
                equalTo(EXPECTED_ESCROW_EXPIRATION));
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
