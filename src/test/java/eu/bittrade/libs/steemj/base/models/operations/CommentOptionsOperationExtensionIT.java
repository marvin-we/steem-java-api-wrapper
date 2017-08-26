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
import eu.bittrade.libs.steemj.base.models.BeneficiaryRouteType;
import eu.bittrade.libs.steemj.base.models.CommentOptionsExtension;
import eu.bittrade.libs.steemj.base.models.CommentPayoutBeneficiaries;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "comment options operation" under the use of
 * real api calls. In this test, an Extension is added / parsed.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsOperationExtensionIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION_WITH_EXTENSION = 12615224;
    private static final int TRANSACTION_INDEX_WITH_EXTENSION = 9;
    private static final int OPERATION_INDEX_WITH_EXTENSION = 1;
    private static final String EXPECTED_AUTHOR_WITH_EXTENSION = "malay11";
    private static final String EXPECTED_PERMANENT_LINK_WITH_EXTENSION = "re-bart2305-201767t204737942z";
    private static final boolean EXPECTED_VOTES_ALLOWED_WITH_EXTENSION = true;
    private static final int BENEFICIARIES_ID = 0;
    private static final String EXPECTED_BENEFICIARY_ACCOUNT = "esteemapp";
    private static final short EXPECTED_BENEFICIARY_WEIGHT = 500;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dc6fd3865901130764657a313333372873746"
            + "5656d6a2d76302d322d342d6861732d6265656e2d72656c65617365642d7570646174652d3900ca9a3b000000000"
            + "3534244000000001027010101000106737465656d6af40100011c5a395bb3f457b120c7a55c4450f3e89bba93d1c"
            + "737d1fc28d56de03dec07a82a0656243704c0905025b5510b414128d853ca4f3bc325f2280a34d3c7c405871b";

    /**
     * <b>Attention:</b> This test class requires a valid posting key of the
     * used "author". If no posting key is provided or the posting key is not
     * valid an Exception will be thrown. The private key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        // If the default expiration date for all integration tests
        // (2016-04-06T08:29:27UTC) is used, the transaction can't be verified.
        // As a workaround new date is set that is not that far in the past.
        transaction.setExpirationDate(new TimePointSec("2017-08-06T08:29:27UTC"));

        CommentOptionsOperation commentOptionsOperation = new CommentOptionsOperation();
        commentOptionsOperation.setAuthor(new AccountName("dez1337"));
        commentOptionsOperation.setPermlink("steemj-v0-2-4-has-been-released-update-9");
        commentOptionsOperation.setAllowVotes(true);
        commentOptionsOperation.setAllowCurationRewards(true);
        commentOptionsOperation.setPercentSteemDollars((short) 10000);

        BeneficiaryRouteType beneficiaryRouteType = new BeneficiaryRouteType();
        beneficiaryRouteType.setAccount(new AccountName("SteemJ"));
        beneficiaryRouteType.setWeight((short) 500);

        ArrayList<BeneficiaryRouteType> beneficiaryRouteTypes = new ArrayList<>();
        beneficiaryRouteTypes.add(beneficiaryRouteType);

        CommentPayoutBeneficiaries commentPayoutBeneficiaries = new CommentPayoutBeneficiaries();
        commentPayoutBeneficiaries.setBeneficiaries(beneficiaryRouteTypes);

        CommentOptionsExtension commentOptionsExtension = new CommentOptionsExtension(commentPayoutBeneficiaries);

        ArrayList<CommentOptionsExtension> commentOptionsExtensions = new ArrayList<>();
        commentOptionsExtensions.add(commentOptionsExtension);

        commentOptionsOperation.setExtensions(commentOptionsExtensions);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOptionsOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingCommentOptionsOperation = steemApiWrapper
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION_WITH_EXTENSION);

        Operation commentOptionsOperation = blockContainingCommentOptionsOperation.getTransactions()
                .get(TRANSACTION_INDEX_WITH_EXTENSION).getOperations().get(OPERATION_INDEX_WITH_EXTENSION);

        assertThat(commentOptionsOperation, instanceOf(CommentOptionsOperation.class));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getAuthor().getAccountName(),
                equalTo(EXPECTED_AUTHOR_WITH_EXTENSION));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getAllowVotes(),
                equalTo(EXPECTED_VOTES_ALLOWED_WITH_EXTENSION));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getPermlink(),
                equalTo(EXPECTED_PERMANENT_LINK_WITH_EXTENSION));

        assertThat(((CommentOptionsOperation) commentOptionsOperation).getExtensions().get(0)
                .getCommentPayoutBeneficiaries().getBeneficiaries().size(), equalTo(1));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getExtensions().get(0)
                .getCommentPayoutBeneficiaries().getBeneficiaries().get(BENEFICIARIES_ID).getAccount().getAccountName(),
                equalTo(EXPECTED_BENEFICIARY_ACCOUNT));
        assertThat(
                ((CommentOptionsOperation) commentOptionsOperation).getExtensions().get(0)
                        .getCommentPayoutBeneficiaries().getBeneficiaries().get(BENEFICIARIES_ID).getWeight(),
                equalTo(EXPECTED_BENEFICIARY_WEIGHT));
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
