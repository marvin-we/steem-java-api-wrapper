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
 * Verify the functionality of the "escrow release operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowReleaseOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 9544320;
    private static final int TRANSACTION_INDEX = 8;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM = "anonymtest";
    private static final int EXPECTED_ESCROW_ID = 72526562;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcecc80457011d0764657a3133333"
            + "70764657a3133333706737465656d6a0764657a313333370764657a31333337220000000100000000000"
            + "00003534244000000000a0000000000000003535445454d000000011c247493deeaf210cddf6a7d406bf"
            + "1b0c2b11a76e6227150ea4ff89d5b53ecd85b7f358f1e689b1678f5369f802e75c40d59906de4209cc0e"
            + "cea8a36958f7ace1b";

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
        AccountName who = new AccountName("dez1337");
        AccountName receiver = new AccountName("dez1337");
        long escrowId = 34;

        Asset sbdAmount = new Asset();
        sbdAmount.setAmount(1L);
        sbdAmount.setSymbol(AssetSymbolType.SBD);

        Asset steemAmount = new Asset();
        steemAmount.setAmount(10L);
        steemAmount.setSymbol(AssetSymbolType.STEEM);

        EscrowReleaseOperation escrowReleaseOperation = new EscrowReleaseOperation(from, to, agent, escrowId, who,
                receiver, sbdAmount, steemAmount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(escrowReleaseOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseError {
        SignedBlockWithInfo blockContainingEscrowReleaseOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation escrowReleaseOperation = blockContainingEscrowReleaseOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(escrowReleaseOperation, instanceOf(EscrowReleaseOperation.class));
        assertThat(((EscrowReleaseOperation) escrowReleaseOperation).getFrom().getName(), equalTo(EXPECTED_FROM));
        assertThat(((EscrowReleaseOperation) escrowReleaseOperation).getEscrowId(), equalTo(EXPECTED_ESCROW_ID));
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
