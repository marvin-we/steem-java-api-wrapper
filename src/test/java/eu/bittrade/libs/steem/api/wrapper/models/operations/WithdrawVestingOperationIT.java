package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Block;

/**
 * Verify the functionality of the "vote operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WithdrawVestingOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5763343;
    private static final int TRANSACTION_INDEX = 2;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ACCOUNT = "alex90342fastn1";
    private static final AssetSymbolType EXPECTED_ASSET_SYMBOL = AssetSymbolType.VESTS;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dceac8045701040764657a31333337e8030"
            + "00000000000065645535453000000011c2817dec23e56b20d80b43e7df37fd4b56b378a12e84ddf85ead331079"
            + "8abed6d6d680300b7a9e8a2a1732f3cffd0d12589a8144e0e4344e7b08c94a80b913372";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "account". If no active key is provided or the active key is not valid an
     * Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        WithdrawVestingOperation withdrawVestingOperation = new WithdrawVestingOperation();
        withdrawVestingOperation.setAccount(new AccountName("dez1337"));

        Asset vestingShares = new Asset();
        vestingShares.setAmount(1000);
        vestingShares.setSymbol(AssetSymbolType.VESTS);

        withdrawVestingOperation.setVestingShares(vestingShares);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(withdrawVestingOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingWithdrawVestingOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation withdrawVestingOperation = blockContainingWithdrawVestingOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(withdrawVestingOperation, instanceOf(WithdrawVestingOperation.class));
        assertThat(((WithdrawVestingOperation) withdrawVestingOperation).getAccount().getAccountName(),
                equalTo(EXPECTED_ACCOUNT));
        assertThat(((WithdrawVestingOperation) withdrawVestingOperation).getVestingShares().getSymbol(),
                equalTo(EXPECTED_ASSET_SYMBOL));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemApiWrapper.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemApiWrapper.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
