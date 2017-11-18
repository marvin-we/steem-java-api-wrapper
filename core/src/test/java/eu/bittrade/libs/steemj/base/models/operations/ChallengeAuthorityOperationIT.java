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
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * Verify the functionality of the "prove authority operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ChallengeAuthorityOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcebc80457010b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4064657a31333"
            + "33702e5127bd7d41f01d9981a5a2c2524a60706040bbec8838a39719550ea25071000881300000000000003535445454d0000000001000000010000000000000003535445454"
            + "d000000011c2125f9ad6d2a0a9b5f75ee042f71d44029f361df17be8da017f784b75ebe33152404af3e11c1e2ed853e0e85a58fc9cea4cc9f2d17803766edd5d917a9b21081";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dcefc80457021606737465656d6a0764657a31333337011606737465656d6a0764657a3133333"
            + "70000011b145dce53f11e99c5089fe760b56324f4b1a35416c7af3c35e6917dea73b7ac677f8aa616aedc4f8bc60335d55be71a828259d6f59104f3ae55ae26c1dc183a71";

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

        AccountName challengedAccount = new AccountName("dez1337");
        AccountName challengerAccount = new AccountName("steemj");

        ChallengeAuthorityOperation challengeAuthorityOperationWithOwnerKey = new ChallengeAuthorityOperation(
                challengerAccount, challengedAccount, true);
        ChallengeAuthorityOperation challengeAuthorityOperationWithActiveKey = new ChallengeAuthorityOperation(
                challengerAccount, challengedAccount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(challengeAuthorityOperationWithOwnerKey);
        operations.add(challengeAuthorityOperationWithActiveKey);

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
