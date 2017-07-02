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
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "limit order create operation" under the use
 * of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreateOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5681453;
    private static final int TRANSACTION_INDEX = 3;
    private static final int OPERATION_INDEX = 0;
    private static final Asset EXPECTED_BASE_AMOUNT = new Asset();
    private static final boolean EXPECTED_FILL_OR_KILL = false;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701050764657a31333337c3850"
            + "700010000000000000003534244000000000a0000000000000003535445454d000000e7c8045700011b69776cf"
            + "d76f1e20da06f40c9df5fcc1c25156b7968b3566655ba39622bd31158798b91b16bc134740051be55e4c678a9f"
            + "a0db60b50fb4ff8f77a26cc4e6ed73c";

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
        setupIntegrationTestEnvironment();

        LimitOrderCreateOperation limitOrderCreateOperation = new LimitOrderCreateOperation();

        Asset amountToSell = new Asset();
        amountToSell.setAmount(1L);
        amountToSell.setSymbol(AssetSymbolType.SBD);

        limitOrderCreateOperation.setAmountToSell(amountToSell);
        limitOrderCreateOperation.setExpirationDate(new TimePointSec(EXPIRATION_DATE));
        limitOrderCreateOperation.setFillOrKill(false);

        Asset minToReceive = new Asset();
        minToReceive.setAmount(10L);
        minToReceive.setSymbol(AssetSymbolType.STEEM);

        limitOrderCreateOperation.setMinToReceive(minToReceive);
        limitOrderCreateOperation.setOrderId(492995);
        limitOrderCreateOperation.setOwner(new AccountName("dez1337"));

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(limitOrderCreateOperation);

        transaction.setOperations(operations);
        transaction.sign();

        // Set expected objects.
        EXPECTED_BASE_AMOUNT.setAmount(41554);
        EXPECTED_BASE_AMOUNT.setSymbol(AssetSymbolType.SBD);
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingLimitOrderCreateOperation = steemApiWrapper
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation limitOrderCreateOperation = blockContainingLimitOrderCreateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(limitOrderCreateOperation, instanceOf(LimitOrderCreateOperation.class));
        assertThat(((LimitOrderCreateOperation) limitOrderCreateOperation).getFillOrKill(),
                equalTo(EXPECTED_FILL_OR_KILL));
        assertThat(((LimitOrderCreateOperation) limitOrderCreateOperation).getAmountToSell(),
                equalTo(EXPECTED_BASE_AMOUNT));
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
