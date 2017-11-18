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
public class DeleteCommentOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701110764657a313333"
            + "377c72652d72652d627573696e6573737772692d72652d64657a313333372d686f772d746f2d7365637"
            + "572652d796f75722d7765622d736572766963652d776974682d612d76616c69642d73736c2d63657274"
            + "696669636174652d666f722d667265652d706172742d332d32303137303532377431363137303231313"
            + "87a00011b74fb5ec4f498b091172f3c15ee2d442408d7bf226697d7f538f1e8bc6f1b164869e56c65b9"
            + "1ae028417d7ae2b46ffae9e1a6655caa3c8583c8401f0f503848a8";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce7c804570111076465"
            + "7a313333377c72652d72652d627573696e6573737772692d72652d64657a313333372d686f772d746f2"
            + "d7365637572652d796f75722d7765622d736572766963652d776974682d612d76616c69642d73736c2d"
            + "63657274696669636174652d666f722d667265652d706172742d332d323031373035323774313631373"
            + "0323131387a00011b25c96472eb80f177bd76ad5a60dd9db6591112b40b790ed3d2279f0620c9c43401"
            + "abcba6a70c0830e583bf5e702b45647850fe7789003763dd8ab6dc41602e26";

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

        AccountName author = new AccountName("dez1337");
        Permlink permlink = new Permlink(
                "re-re-businesswri-re-dez1337-how-to-secure-your-web-service-with-a-valid-ssl-certificate-for-free-part-3-20170527t161702118z");

        DeleteCommentOperation deleteCommentOperation = new DeleteCommentOperation(author, permlink);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(deleteCommentOperation);

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
