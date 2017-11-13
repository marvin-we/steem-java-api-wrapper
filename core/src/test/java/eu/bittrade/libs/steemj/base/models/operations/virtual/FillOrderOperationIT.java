package eu.bittrade.libs.steemj.base.models.operations.virtual;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.AppliedOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * Test that the {@link ProducerRewardOperation} can be parsed.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FillOrderOperationIT extends BaseITForOperationParsing {
    private static final int BLOCK_NUMBER_CONTAINING_OPERATION = 16021040;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_CURRENT_OWNER = "enomujjass";
    private static final AccountName EXPECTED_OPEN_OWNER = new AccountName("oscarps");
    private static final int EXPECTED_CURRENT_ORDER_ID = 1507078540;
    private static final long EXPECTED_OPEN_ORDER_ID = 1507059984L;
    private static final AssetSymbolType EXPECTED_OPEN_PAYS_SYMBOL = AssetSymbolType.STEEM;
    private static final double EXPECTED_OPEN_PAYS_VALUE_REAL = 0.015;
    private static final long EXPECTED_OPEN_PAYS_VALUE = 15L;
    private static final AssetSymbolType EXPECTED_CURRENT_PAYS_SYMBOL = AssetSymbolType.SBD;
    private static final double EXPECTED_CURRENT_PAYS_VALUE_REAL = 0.02;
    private static final long EXPECTED_CURRENT_PAYS_VALUE = 20L;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
    }

    @Override
    @Test
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        List<AppliedOperation> operationsInBlock = steemJ.getOpsInBlock(BLOCK_NUMBER_CONTAINING_OPERATION, true);

        Operation fillOrderOperation = operationsInBlock.get(OPERATION_INDEX).getOp();

        assertThat(fillOrderOperation, instanceOf(FillOrderOperation.class));

        assertThat(((FillOrderOperation) fillOrderOperation).getCurrentOwner().getName(),
                equalTo(EXPECTED_CURRENT_OWNER));
        assertThat(((FillOrderOperation) fillOrderOperation).getOpenOwner(), equalTo(EXPECTED_OPEN_OWNER));
        assertThat(((FillOrderOperation) fillOrderOperation).getCurrentOrderId(), equalTo(EXPECTED_CURRENT_ORDER_ID));
        assertThat(((FillOrderOperation) fillOrderOperation).getOpenOrderId(), equalTo(EXPECTED_OPEN_ORDER_ID));
        assertThat(((FillOrderOperation) fillOrderOperation).getOpenPays().getSymbol(),
                equalTo(EXPECTED_OPEN_PAYS_SYMBOL));
        assertThat(((FillOrderOperation) fillOrderOperation).getOpenPays().toReal(),
                equalTo(EXPECTED_OPEN_PAYS_VALUE_REAL));
        assertThat(((FillOrderOperation) fillOrderOperation).getOpenPays().getAmount(),
                equalTo(EXPECTED_OPEN_PAYS_VALUE));
        assertThat(((FillOrderOperation) fillOrderOperation).getCurrentPays().getSymbol(),
                equalTo(EXPECTED_CURRENT_PAYS_SYMBOL));
        assertThat(((FillOrderOperation) fillOrderOperation).getCurrentPays().toReal(),
                equalTo(EXPECTED_CURRENT_PAYS_VALUE_REAL));
        assertThat(((FillOrderOperation) fillOrderOperation).getCurrentPays().getAmount(),
                equalTo(EXPECTED_CURRENT_PAYS_VALUE));
    }
}
