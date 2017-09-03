package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIntegrationTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "set withdraw vesting route operation" under
 * the use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetWithdrawVestingRouteOperationIT extends BaseIntegrationTest {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701140764657a3133333"
            + "706737465656d6a10270100011c30579bb23bc91cd01af4c54820dc3d76573cfb2a2973871ae5bd2824b"
            + "9811c1017ae23d98f135d79418db21933727df866013321794b388ad29f96154d28bddc";

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
        setupIntegrationTestEnvironment();

        SetWithdrawVestingRouteOperation setWithdrawVestingRouteOperation = new SetWithdrawVestingRouteOperation();
        setWithdrawVestingRouteOperation.setFromAccount(new AccountName("dez1337"));
        setWithdrawVestingRouteOperation.setToAccount(new AccountName("steemj"));
        setWithdrawVestingRouteOperation.setPercent(10000);
        setWithdrawVestingRouteOperation.setAutoVest(true);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(setWithdrawVestingRouteOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        // TODO: Implement
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
