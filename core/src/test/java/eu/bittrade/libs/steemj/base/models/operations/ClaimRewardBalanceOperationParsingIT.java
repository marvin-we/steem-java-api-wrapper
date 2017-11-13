package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class tests if the {@link ClaimRewardBalanceOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ClaimRewardBalanceOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 12000608;
    private static final int TRANSACTION_INDEX = 4;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ACCOUNT = "lifewordmission";
    private static final double EXPECTED_VESTS = 60552.750918;

    /**
     * Prepare all required fields used by this test class.
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
    @Category({ IntegrationTest.class })
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        SignedBlockWithInfo blockContainingClaimRewardBalanceOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation claimRewardBalanceOperation = blockContainingClaimRewardBalanceOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(claimRewardBalanceOperation, instanceOf(ClaimRewardBalanceOperation.class));
        assertThat(((ClaimRewardBalanceOperation) claimRewardBalanceOperation).getAccount().getName(),
                equalTo(EXPECTED_ACCOUNT));
        assertThat(((ClaimRewardBalanceOperation) claimRewardBalanceOperation).getRewardVests().toReal(),
                equalTo(EXPECTED_VESTS));
    }
}
