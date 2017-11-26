package eu.bittrade.libs.steemj.plugins.witness.model.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.base.models.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;

/**
 * Verify the functionality of the {@link EnableContentEditingOperation} under
 * the use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */

public class EnableContentEditingOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcf33ed759011200010764657a31333337077769746e6573"
            + "735b5b22456e61626c65436f6e74656e7445646974696e674f7065726174696f6e222c7b226163636f756e74223a2264657a313"
            + "33337222c2272656c6f636b5f74696d65223a22323031372d31312d32375430383a32393a3237227d5d00011c47186762f9171a"
            + "8df9af273c09bcfcc7947c9325285ec688a4144f88c763940b30e6df6ea688d7b16341cd6b03f56f6d5524521172418ffda96aa"
            + "cfff0492dc6";
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
                TESTNET_ENDPOINT_IDENTIFIER);

        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(new AccountName("dez1337"));

        // Steem does not allow to combine operations that require a posting key
        // with operations that require a higher key, so set the active
        // authorities to null.
        ArrayList<AccountName> requiredActiveAuths = null;

        String id = "witness";

        EnableContentEditingOperation enableContentEditingOperation = new EnableContentEditingOperation(
                new AccountName("dez1337"), new TimePointSec("2017-11-27T08:29:27UTC"));

        CustomJsonOperation customJsonEnableContentEditingOperationn = new CustomJsonOperation(requiredActiveAuths,
                requiredPostingAuths, id, enableContentEditingOperation.toJson());

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonEnableContentEditingOperationn);

        // If the default expiration date for all integration tests
        // (2016-04-06T08:29:27UTC) is used, the transaction can't be verified.
        // As a workaround new date is set that is not that far in the past.
        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX,
                new TimePointSec("2017-10-06T08:29:27UTC"), operations, null);
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
