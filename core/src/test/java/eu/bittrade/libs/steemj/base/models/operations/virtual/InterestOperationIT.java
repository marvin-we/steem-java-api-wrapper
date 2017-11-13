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
 * Test that the {@link CurationRewardOperation} can be parsed.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class InterestOperationIT extends BaseITForOperationParsing {
    private static final int BLOCK_NUMBER_CONTAINING_OPERATION = 16022103;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_OWNER = "eric818";
    private static final AssetSymbolType EXPECTED_INTEREST_SYMBOL = AssetSymbolType.SBD;
    private static final double EXPECTED_INTEREST_VALUE_REAL = 0.003;
    private static final long EXPECTED_INTEREST_VALUE = 3L;

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

        Operation interestOperation = operationsInBlock.get(OPERATION_INDEX).getOp();

        assertThat(interestOperation, instanceOf(InterestOperation.class));

        assertThat(((InterestOperation) interestOperation).getOwner().getName(), equalTo(EXPECTED_OWNER));
        assertThat(((InterestOperation) interestOperation).getInterest().getSymbol(),
                equalTo(EXPECTED_INTEREST_SYMBOL));
        assertThat(((InterestOperation) interestOperation).getInterest().toReal(),
                equalTo(EXPECTED_INTEREST_VALUE_REAL));
        assertThat(((InterestOperation) interestOperation).getInterest().getAmount(), equalTo(EXPECTED_INTEREST_VALUE));
    }
}
