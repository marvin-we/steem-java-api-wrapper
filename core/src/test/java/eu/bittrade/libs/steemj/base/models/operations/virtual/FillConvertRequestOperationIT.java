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
public class FillConvertRequestOperationIT extends BaseITForOperationParsing {
    private static final int BLOCK_NUMBER_CONTAINING_OPERATION = 16020980;
    private static final int OPERATION_INDEX = 1;
    private static final String EXPECTED_OWNER = "jiminykricket";
    private static final long EXPECTED_REQUEST_ID = 1506775956L;
    private static final AssetSymbolType EXPECTED_AMOUNT_IN_SYMBOL = AssetSymbolType.SBD;
    private static final double EXPECTED_AMOUNT_IN_VALUE_REAL = 0.024;
    private static final long EXPECTED_AMOUNT_IN_VALUE = 24L;
    private static final AssetSymbolType EXPECTED_AMOUNT_OUT_SYMBOL = AssetSymbolType.STEEM;
    private static final double EXPECTED_AMOUNT_OUT_VALUE_REAL = 0.017;
    private static final long EXPECTED_AMOUNT_OUT_VALUE = 17L;

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

        Operation fillConvertRequestOperation = operationsInBlock.get(OPERATION_INDEX).getOp();

        assertThat(fillConvertRequestOperation, instanceOf(FillConvertRequestOperation.class));

        assertThat(((FillConvertRequestOperation) fillConvertRequestOperation).getOwner().getName(),
                equalTo(EXPECTED_OWNER));
        assertThat(((FillConvertRequestOperation) fillConvertRequestOperation).getRequestId(),
                equalTo(EXPECTED_REQUEST_ID));
        assertThat(((FillConvertRequestOperation) fillConvertRequestOperation).getAmountIn().getSymbol(),
                equalTo(EXPECTED_AMOUNT_IN_SYMBOL));
        assertThat(((FillConvertRequestOperation) fillConvertRequestOperation).getAmountIn().toReal(),
                equalTo(EXPECTED_AMOUNT_IN_VALUE_REAL));
        assertThat(((FillConvertRequestOperation) fillConvertRequestOperation).getAmountIn().getAmount(),
                equalTo(EXPECTED_AMOUNT_IN_VALUE));
        assertThat(((FillConvertRequestOperation) fillConvertRequestOperation).getAmountOut().getSymbol(),
                equalTo(EXPECTED_AMOUNT_OUT_SYMBOL));
        assertThat(((FillConvertRequestOperation) fillConvertRequestOperation).getAmountOut().toReal(),
                equalTo(EXPECTED_AMOUNT_OUT_VALUE_REAL));
        assertThat(((FillConvertRequestOperation) fillConvertRequestOperation).getAmountOut().getAmount(),
                equalTo(EXPECTED_AMOUNT_OUT_VALUE));
    }
}
