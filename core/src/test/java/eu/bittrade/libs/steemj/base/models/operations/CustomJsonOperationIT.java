package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.apis.follow.models.operations.FollowOperation;
import eu.bittrade.libs.steemj.apis.follow.models.operations.ReblogOperation;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * Verify the functionality of the "custom json operation" under the use of real
 * api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomJsonOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dc67fce558041200010764657a3133333706666f6c6c6f77"
            + "465b22666f6c6c6f77222c7b22666f6c6c6f776572223a2264657a31333337222c22666f6c6c6f77696e67223a22737465656d6"
            + "a222c2277686174223a5b22626c6f67225d7d5d1200010764657a3133333706666f6c6c6f77425b22666f6c6c6f77222c7b2266"
            + "6f6c6c6f776572223a2264657a31333337222c22666f6c6c6f77696e67223a22737465656d6a222c2277686174223a5b22225d7"
            + "d5d1200010764657a3133333706666f6c6c6f77485b22666f6c6c6f77222c7b22666f6c6c6f776572223a2264657a3133333722"
            + "2c22666f6c6c6f77696e67223a22737465656d6a222c2277686174223a5b2269676e6f7265225d7d5d1200010764657a3133333"
            + "7067265626c6f67755b227265626c6f67222c7b226163636f756e74223a2264657a31333337222c22617574686f72223a227374"
            + "65656d6a222c227065726d6c696e6b223a22737465656d6a2d76302d342d302d666561747572652d707265766965772d73696d7"
            + "06c69666965642d7472616e73616374696f6e73227d5d00011b24f4e525a759327b710c27ab34176f062b2611507d963064a225"
            + "8619c4c9dd350190b5ebc73ac08dba3f00e9c6ec762205e86fb06d9fe2a9a2800878f7c126e8";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dc71fce558041200010764657a3133333706666f"
            + "6c6c6f77465b22666f6c6c6f77222c7b22666f6c6c6f776572223a2264657a31333337222c22666f6c6c6f77696e67223a22737"
            + "465656d6a222c2277686174223a5b22626c6f67225d7d5d1200010764657a3133333706666f6c6c6f77425b22666f6c6c6f7722"
            + "2c7b22666f6c6c6f776572223a2264657a31333337222c22666f6c6c6f77696e67223a22737465656d6a222c2277686174223a5"
            + "b22225d7d5d1200010764657a3133333706666f6c6c6f77485b22666f6c6c6f77222c7b22666f6c6c6f776572223a2264657a31"
            + "333337222c22666f6c6c6f77696e67223a22737465656d6a222c2277686174223a5b2269676e6f7265225d7d5d1200010764657"
            + "a31333337067265626c6f67755b227265626c6f67222c7b226163636f756e74223a2264657a31333337222c22617574686f7222"
            + "3a22737465656d6a222c227065726d6c696e6b223a22737465656d6a2d76302d342d302d666561747572652d707265766965772"
            + "d73696d706c69666965642d7472616e73616374696f6e73227d5d00011c0e678ad549add722ad72c6f03887b4fceb2fbe4f79de"
            + "9052ce505f3784ceb02c76e49f4f6f5ab7f2bc5a108fa11d51119a5fc21884063a2da6a546cae6866191";

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
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(new AccountName("dez1337"));

        // Steem does not allow to combine operations that require a posting key
        // with operations that require a higher key, so set the active
        // authorities to null.
        ArrayList<AccountName> requiredActiveAuths = null;

        String id = "follow";

        FollowOperation followOperation = new FollowOperation(new AccountName("dez1337"), new AccountName("steemj"),
                Arrays.asList(FollowType.BLOG));

        CustomJsonOperation customJsonFollowOperation = new CustomJsonOperation(requiredActiveAuths,
                requiredPostingAuths, id, followOperation.toJson());

        FollowOperation unfollowOperation = new FollowOperation(new AccountName("dez1337"), new AccountName("steemj"),
                Arrays.asList(FollowType.UNDEFINED));

        CustomJsonOperation customJsonUnfollowOperation = new CustomJsonOperation(requiredActiveAuths,
                requiredPostingAuths, id, unfollowOperation.toJson());

        FollowOperation ignoreOperation = new FollowOperation(new AccountName("dez1337"), new AccountName("steemj"),
                Arrays.asList(FollowType.IGNORE));

        CustomJsonOperation customJsonIgnoreOperation = new CustomJsonOperation(requiredActiveAuths,
                requiredPostingAuths, id, ignoreOperation.toJson());

        id = "reblog";

        ReblogOperation reblogOperation = new ReblogOperation(new AccountName("dez1337"), new AccountName("steemj"),
                new Permlink("steemj-v0-4-0-feature-preview-simplified-transactions"));

        CustomJsonOperation customJsonReblogOperation = new CustomJsonOperation(requiredActiveAuths,
                requiredPostingAuths, id, reblogOperation.toJson());

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonFollowOperation);
        operations.add(customJsonUnfollowOperation);
        operations.add(customJsonIgnoreOperation);
        operations.add(customJsonReblogOperation);

        // If the default expiration date for all integration tests
        // (2016-04-06T08:29:27UTC) is used, the transaction can't be verified.
        // As a workaround new date is set that is not that far in the past.
        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX,
                new TimePointSec("2017-04-06T08:29:27UTC"), operations, null);
        signedTransaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(signedTransaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        if (TEST_ENDPOINT.equals(TESTNET_ENDPOINT_IDENTIFIER)) {
            assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX_TESTNET));
        } else {
            assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX));
        }
    }
}
