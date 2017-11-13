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
public class ProducerRewardOperationIT extends BaseITForOperationParsing {
    private static final int BLOCK_NUMBER_CONTAINING_OPERATION = 16212111;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_PRODUCER = "xeldal";
    private static final AssetSymbolType EXPECTED_VESTS_SYMBOL = AssetSymbolType.VESTS;
    private static final double EXPECTED_VESTS_VALUE_REAL = 390.97665;
    private static final long EXPECTED_VESTS_VALUE = 390976650L;

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

        Operation producerRewardOperation = operationsInBlock.get(OPERATION_INDEX).getOp();

        assertThat(producerRewardOperation, instanceOf(ProducerRewardOperation.class));

        assertThat(((ProducerRewardOperation) producerRewardOperation).getProducer().getName(),
                equalTo(EXPECTED_PRODUCER));
        assertThat(((ProducerRewardOperation) producerRewardOperation).getVestingShares().getSymbol(),
                equalTo(EXPECTED_VESTS_SYMBOL));
        assertThat(((ProducerRewardOperation) producerRewardOperation).getVestingShares().toReal(),
                equalTo(EXPECTED_VESTS_VALUE_REAL));
        assertThat(((ProducerRewardOperation) producerRewardOperation).getVestingShares().getAmount(),
                equalTo(EXPECTED_VESTS_VALUE));
    }
}
