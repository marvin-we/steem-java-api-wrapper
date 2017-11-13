package eu.bittrade.libs.steemj.base.models.operations.virtual;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
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
public class ReturnVestingDelegationOperationIT extends BaseITForOperationParsing {
    private static final int BLOCK_NUMBER_CONTAINING_OPERATION = 16022132;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ACCOUNT = "minnowbooster";
    private static final AssetSymbolType EXPECTED_VESTS_SYMBOL = AssetSymbolType.VESTS;
    private static final double EXPECTED_VESTS_VALUE_REAL = 2362500.0;
    private static final long EXPECTED_VESTS_VALUE = 2362500000000L;

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

        Operation returnVestingDelegationOperation = operationsInBlock.get(OPERATION_INDEX).getOp();

        assertThat(returnVestingDelegationOperation, instanceOf(ReturnVestingDelegationOperation.class));

        assertThat(((ReturnVestingDelegationOperation) returnVestingDelegationOperation).getAccount().getName(),
                equalTo(EXPECTED_ACCOUNT));
        assertThat(((ReturnVestingDelegationOperation) returnVestingDelegationOperation).getVestingShares().getSymbol(),
                equalTo(EXPECTED_VESTS_SYMBOL));
        assertThat(((ReturnVestingDelegationOperation) returnVestingDelegationOperation).getVestingShares().toReal(),
                equalTo(EXPECTED_VESTS_VALUE_REAL));
        assertThat(((ReturnVestingDelegationOperation) returnVestingDelegationOperation).getVestingShares().getAmount(),
                equalTo(EXPECTED_VESTS_VALUE));
    }
}
