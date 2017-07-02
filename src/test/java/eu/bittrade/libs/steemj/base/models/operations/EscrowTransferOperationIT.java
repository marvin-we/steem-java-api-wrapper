package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIntegrationTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
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
public class EscrowTransferOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 9531332;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM = "xtar";
    private static final int EXPECTED_ESCROW_ID = 20618239;
    private static final long EXPECTED_ESCROW_EXPIRATION = 1490215341000L;
    private static final String EXPECTED_TRANSACTION_HEX = "0";

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        EscrowTransferOperation escrowTransferOperation = new EscrowTransferOperation();

        escrowTransferOperation.setAgent(new AccountName("steemj"));
        escrowTransferOperation.setFrom(new AccountName("dez1337"));
        escrowTransferOperation.setTo(new AccountName("dez1337"));
        escrowTransferOperation.setEscrowExpirationDate(new TimePointSec(1490215341));
        escrowTransferOperation.setEscrowId(34);
        escrowTransferOperation.setRatificationDeadlineDate(new TimePointSec(1490215340));
        escrowTransferOperation.setJsonMeta("");

        Asset sbdAmount = new Asset();
        sbdAmount.setAmount(1L);
        sbdAmount.setSymbol(AssetSymbolType.SBD);

        escrowTransferOperation.setSbdAmount(sbdAmount);

        Asset steemAmount = new Asset();
        steemAmount.setAmount(10L);
        steemAmount.setSymbol(AssetSymbolType.STEEM);

        escrowTransferOperation.setSteemAmount(steemAmount);

        Asset fee = new Asset();
        fee.setAmount(1L);
        fee.setSymbol(AssetSymbolType.STEEM);

        escrowTransferOperation.setFee(fee);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(escrowTransferOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingEscrowTransferOperation = steemApiWrapper
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation escrowTransferOperation = blockContainingEscrowTransferOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(escrowTransferOperation, instanceOf(EscrowTransferOperation.class));
        assertThat(((EscrowTransferOperation) escrowTransferOperation).getFrom().getAccountName(),
                equalTo(EXPECTED_FROM));
        assertThat(((EscrowTransferOperation) escrowTransferOperation).getEscrowId(), equalTo(EXPECTED_ESCROW_ID));
        assertThat(
                ((EscrowTransferOperation) escrowTransferOperation).getEscrowExpirationDate().getDateTimeAsTimestamp(),
                equalTo(EXPECTED_ESCROW_EXPIRATION));
    }

    @Category({ IntegrationTest.class })
    @Test
    @Ignore
    public void verifyTransaction() throws Exception {
        assertThat(steemApiWrapper.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    @Ignore
    public void getTransactionHex() throws Exception {
        assertThat(steemApiWrapper.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
