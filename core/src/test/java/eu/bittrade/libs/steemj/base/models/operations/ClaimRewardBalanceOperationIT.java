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
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Verify the functionality of the "claim reward balance operation" under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ClaimRewardBalanceOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dceac804570127"
            + "0764657a31333337020000000000000003535445454d0000010000000000000003534"
            + "244000000000200000000000000065645535453000000011c3143bace1dc9ad51e382"
            + "d0a30f6fa7565859154c1e337f30353f643a3af1f27e74095d7c7154bf2c973fe39c1"
            + "99fd1dffefc0986751bee498fd3cda286bafc41";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dcfac8"
            + "045701270764657a31333337020000000000000003535445454d00000100000000000"
            + "00003534244000000000200000000000000065645535453000000011c1632d5fec5ef"
            + "4deff18eafeca32ea003a4812d5f76be4e3fe96f5192d43e32037c8d142e746f66cc2"
            + "246aa1054eea7ead9571efd8496aaa8d35e8aea812aa7a4";

    /**
     * <b>Attention:</b> This test class requires a valid posting key of the
     * used "account". If no posting key is provided or the posting key is not
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

        AccountName account = new AccountName("dez1337");

        Asset rewardSbd = new Asset(1, AssetSymbolType.SBD);
        Asset rewardSteem = new Asset(2, AssetSymbolType.STEEM);
        Asset rewardVests = new Asset(2, AssetSymbolType.VESTS);

        ClaimRewardBalanceOperation claimRewardBalanceOperation = new ClaimRewardBalanceOperation(account, rewardSteem,
                rewardSbd, rewardVests);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(claimRewardBalanceOperation);

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
