package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * Verify the functionality of the "vote operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VoteOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701000"
            + "764657a313333370764657a3133333728737465656d6a2d76302d322d342d6861732d6"
            + "265656e2d72656c65617365642d7570646174652d39e80300011b2c91031ff0d1e1e56"
            + "607644da79f7c837af4f23415519babeea2061538aed5461e15475edaa2d7ee6134693"
            + "6ca276ed0a1444464cd25e947956ba9f15496e28a";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dcfbc80"
            + "45701000764657a313333370764657a3133333728737465656d6a2d76302d322d342d6"
            + "861732d6265656e2d72656c65617365642d7570646174652d39e80300011c2056936b1"
            + "0e8381428d73a660ffc41e282813be6680e614d868697ceadd75812719e4386c532bc4"
            + "d5ea2d2ff24cf8d15ee9a0f48b9d7afd4ea779a373999141d";

    /**
     * <b>Attention:</b> This test class requires a valid posting key of the
     * used "voter". If no posting key is provided or the posting key is not
     * valid an Exception will be thrown. The private key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        AccountName author = new AccountName("dez1337");
        Permlink permlink = new Permlink("steemj-v0-2-4-has-been-released-update-9");
        AccountName voter = new AccountName("dez1337");
        short weight = 1000;

        VoteOperation voteOperation = new VoteOperation(author, voter, permlink, weight);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
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
