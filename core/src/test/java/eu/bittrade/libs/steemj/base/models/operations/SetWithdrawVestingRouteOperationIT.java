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
 * Verify the functionality of the "set withdraw vesting route operation" under
 * the use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetWithdrawVestingRouteOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701140764657a3133333"
            + "706737465656d6a10270100011c30579bb23bc91cd01af4c54820dc3d76573cfb2a2973871ae5bd2824b"
            + "9811c1017ae23d98f135d79418db21933727df866013321794b388ad29f96154d28bddc";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dcf1c8045701140764657"
            + "a3133333706737465656d6a10270100011c12aed09e8b56fe3db9aa425354feacf96809812523244127d"
            + "35b6605552c5b2f1211d101478bc76a4b065bda8e9d7ddc54ed37e829445bbc6afb8367c659e877";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "from account". If no active key is provided or the active key is not
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

        AccountName fromAccount = new AccountName("dez1337");
        AccountName toAccount = new AccountName("steemj");
        int percentage = 10000;
        boolean autoVest = true;

        SetWithdrawVestingRouteOperation setWithdrawVestingRouteOperation = new SetWithdrawVestingRouteOperation(
                fromAccount, toAccount, percentage, autoVest);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(setWithdrawVestingRouteOperation);

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
