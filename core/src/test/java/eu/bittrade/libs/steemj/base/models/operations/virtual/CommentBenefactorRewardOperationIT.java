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
 * Test that the {@link ProducerRewardOperation} can be parsed.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentBenefactorRewardOperationIT extends BaseITForOperationParsing {
    private static final int BLOCK_NUMBER_CONTAINING_OPERATION = 16020944;
    private static final int OPERATION_INDEX = 1;
    private static final String EXPECTED_AUTHOR = "rojasruth69";
    private static final AccountName EXPECTED_BENEFACTOR = new AccountName("chainbb");
    private static final Permlink EXPECTED_PERMLINK = new Permlink(
            "re-mayvil-que-hacer-cuando-no-sabemos-de-edicion-2017926t205055909z");
    private static final AssetSymbolType EXPECTED_REWARD_VESTS_SYMBOL = AssetSymbolType.VESTS;
    private static final double EXPECTED_REWARD_VESTS_VALUE_REAL = 4.116952;
    private static final long EXPECTED_REWARD_VESTS_VALUE = 4116952L;

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

        Operation commentBenefactorRewardOperation = operationsInBlock.get(OPERATION_INDEX).getOp();

        assertThat(commentBenefactorRewardOperation, instanceOf(CommentBenefactorRewardOperation.class));
        assertThat(((CommentBenefactorRewardOperation) commentBenefactorRewardOperation).getAuthor().getName(),
                equalTo(EXPECTED_AUTHOR));
        assertThat(((CommentBenefactorRewardOperation) commentBenefactorRewardOperation).getBenefactor(),
                equalTo(EXPECTED_BENEFACTOR));
        assertThat(((CommentBenefactorRewardOperation) commentBenefactorRewardOperation).getPermlink(),
                equalTo(EXPECTED_PERMLINK));
        assertThat(((CommentBenefactorRewardOperation) commentBenefactorRewardOperation).getReward().getSymbol(),
                equalTo(EXPECTED_REWARD_VESTS_SYMBOL));
        assertThat(((CommentBenefactorRewardOperation) commentBenefactorRewardOperation).getReward().toReal(),
                equalTo(EXPECTED_REWARD_VESTS_VALUE_REAL));
        assertThat(((CommentBenefactorRewardOperation) commentBenefactorRewardOperation).getReward().getAmount(),
                equalTo(EXPECTED_REWARD_VESTS_VALUE));
    }
}
