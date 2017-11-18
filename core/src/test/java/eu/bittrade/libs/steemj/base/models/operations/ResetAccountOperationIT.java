package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;

/**
 * Verify the functionality of the "reset account operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ResetAccountOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701250764657a313"
            + "3333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b09d9e"
            + "a6eedec21de6fa4c4010000011c79f3dfadf424b2f5b7eb5e49b1ea559b1f5f3efec747df108abf7"
            + "5e34313dd15046d7c1645755b215395cc7d8a04b72aabdffbfdcb77bbbe4bd2501aeb453fb2";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce7c804570125076"
            + "4657a3133333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b"
            + "09d9ea6eedec21de6fa4c4010000011b68ddddf7b218924efd6bb4299d878c0baaad59238dea11ea27a"
            + "5ce4e235d33407f9bffb521ed8f15fc6f23e7445f741c32f3577a7094bf19b729d7e5790d198d";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "reset account". If no active key is provided or the active key is not
     * valid an Exception will be thrown. The active key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        AccountName accountToReset = new AccountName("steemj");
        AccountName resetAccount = new AccountName("dez1337");

        Authority newOwnerAuthority = new Authority();
        newOwnerAuthority.setAccountAuths(new HashMap<AccountName, Integer>());
        Map<PublicKey, Integer> ownerKeyAuth = new HashMap<>();
        ownerKeyAuth.put(new PublicKey(SteemJConfig.getInstance().getAddressPrefix().name().toUpperCase()
                + "5jYVokmZHdEpwo5oCG3ES2Ca4VYzy6tM8pWWkGdgVnwo2mFLFq"), 1);
        newOwnerAuthority.setKeyAuths(ownerKeyAuth);
        newOwnerAuthority.setWeightThreshold(1);

        ResetAccountOperation resetAccountOperation = new ResetAccountOperation(resetAccount, accountToReset,
                newOwnerAuthority);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(resetAccountOperation);

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
