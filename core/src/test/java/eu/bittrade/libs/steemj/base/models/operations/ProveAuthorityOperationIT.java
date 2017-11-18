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
public class ProveAuthorityOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcefc8045702170764657a3133333700170764657a313333370100"
            + "011c4c882be373ff6a4a3d06bd0da932b981ac46bfc4bde38284943dc2685cc2c00a5e8196f22c5b5704cf4fd963cd3a12347306bf9ea"
            + "76e23f99c06ab74c3b8fe51";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce8c8045702170764657a3133333700170764657a3133"
            + "33370100011b5d65cce8610ce292cc722fdfe6b20d2aa83f341764ef52bfa4b1e1ae19c69d5c4d845d4dc2fb590b82046e627615314fc"
            + "60e597b5d22676f094e53452eaabc3b";

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

        ProveAuthorityOperation proveAuthorityOperationWithOwnerKey = new ProveAuthorityOperation(challengedAccount,
                true);
        ProveAuthorityOperation proveAuthorityOperationWithActiveKey = new ProveAuthorityOperation(challengedAccount);

        ArrayList<Operation> operations = new ArrayList<>();

        operations.add(proveAuthorityOperationWithActiveKey);
        operations.add(proveAuthorityOperationWithOwnerKey);

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
