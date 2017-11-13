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
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * Test that the {@link CurationRewardOperation} can be parsed.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CurationRewardOperationIT extends BaseITForOperationParsing {
    private static final int BLOCK_NUMBER_CONTAINING_OPERATION = 16212111;
    private static final int OPERATION_INDEX = 1;
    private static final String EXPECTED_AUTHOR = "joearnold";
    private static final AccountName EXPECTED_CURATOR = new AccountName("quinneaker");
    private static final Permlink EXPECTED_PERMLINK = new Permlink(
            "re-quinneaker-re-joearnold-re-quinneaker-bounties-of-the-land-episode-7-preparing-for-winter-final-harvests-soon-20171003t161412134z");
    private static final AssetSymbolType EXPECTED_REWARD_SYMBOL = AssetSymbolType.VESTS;
    private static final double EXPECTED_REWARD_VALUE_REAL = 6.173331;
    private static final long EXPECTED_REWARD_VALUE = 6173331L;

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

        Operation curationRewardOperation = operationsInBlock.get(OPERATION_INDEX).getOp();

        assertThat(curationRewardOperation, instanceOf(CurationRewardOperation.class));

        assertThat(((CurationRewardOperation) curationRewardOperation).getCommentAuthor().getName(),
                equalTo(EXPECTED_AUTHOR));
        assertThat(((CurationRewardOperation) curationRewardOperation).getCurator(), equalTo(EXPECTED_CURATOR));
        assertThat(((CurationRewardOperation) curationRewardOperation).getCommentPermlink(),
                equalTo(EXPECTED_PERMLINK));
        assertThat(((CurationRewardOperation) curationRewardOperation).getReward().getSymbol(),
                equalTo(EXPECTED_REWARD_SYMBOL));
        assertThat(((CurationRewardOperation) curationRewardOperation).getReward().toReal(),
                equalTo(EXPECTED_REWARD_VALUE_REAL));
        assertThat(((CurationRewardOperation) curationRewardOperation).getReward().getAmount(),
                equalTo(EXPECTED_REWARD_VALUE));
    }

}
