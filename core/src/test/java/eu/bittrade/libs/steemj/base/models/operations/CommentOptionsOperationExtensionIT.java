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
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.BeneficiaryRouteType;
import eu.bittrade.libs.steemj.base.models.CommentOptionsExtension;
import eu.bittrade.libs.steemj.base.models.CommentPayoutBeneficiaries;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Verify the functionality of the "comment options operation" under the use of
 * real api calls. In this test, an Extension is added / parsed.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsOperationExtensionIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dc6fd3865901130764657a313333372873746"
            + "5656d6a2d76302d322d342d6861732d6265656e2d72656c65617365642d7570646174652d3900ca9a3b000000000"
            + "3534244000000001027010101000106737465656d6af40100011c5a395bb3f457b120c7a55c4450f3e89bba93d1c"
            + "737d1fc28d56de03dec07a82a0656243704c0905025b5510b414128d853ca4f3bc325f2280a34d3c7c405871b";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dc6ad3865901130764657a3133333728737465"
            + "656d6a2d76302d322d342d6861732d6265656e2d72656c65617365642d7570646174652d3900ca9a3b0000000003"
            + "534244000000001027010101000106737465656d6af40100011b7fea9c420c74f6375c0dbc613382e76f2f860730"
            + "5aa43362a0395811849b5b61013d02eceed1e3b44157a2a880e6fa02fc4fa04c33fdbea516b615a2480dac54";

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
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        // If the default expiration date for all integration tests
        // (2016-04-06T08:29:27UTC) is used, the transaction can't be verified.
        // As a workaround new date is set that is not that far in the past.
        signedTransaction.setExpirationDate(new TimePointSec("2017-08-06T08:29:27UTC"));

        AccountName author = new AccountName("dez1337");
        Permlink permlink = new Permlink("steemj-v0-2-4-has-been-released-update-9");
        boolean allowVotes = true;
        boolean allowCurationRewards = true;
        short percentSteemDollars = (short) 10000;

        BeneficiaryRouteType beneficiaryRouteType = new BeneficiaryRouteType(new AccountName("steemj"), (short) 500);

        ArrayList<BeneficiaryRouteType> beneficiaryRouteTypes = new ArrayList<>();
        beneficiaryRouteTypes.add(beneficiaryRouteType);

        CommentPayoutBeneficiaries commentPayoutBeneficiaries = new CommentPayoutBeneficiaries();
        commentPayoutBeneficiaries.setBeneficiaries(beneficiaryRouteTypes);

        ArrayList<CommentOptionsExtension> commentOptionsExtensions = new ArrayList<>();
        commentOptionsExtensions.add(commentPayoutBeneficiaries);

        CommentOptionsOperation commentOptionsOperation = new CommentOptionsOperation(author, permlink,
                new Asset(1000000000, AssetSymbolType.SBD), percentSteemDollars, allowVotes, allowCurationRewards,
                commentOptionsExtensions);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOptionsOperation);

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
