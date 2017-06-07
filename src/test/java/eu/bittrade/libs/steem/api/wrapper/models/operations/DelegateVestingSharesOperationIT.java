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
 * Verify the functionality of the "delegate vesting shares operation" under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DelegateVestingSharesOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 12614179;
    private static final int TRANSACTION_INDEX = 11;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM_ACCOUNT = "dez1337";
    private static final String EXPECTED_TO_ACCOUNT = "steemj";
    private static final double EXPECTED_AMOUNT = 4142872164L;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce9c8045701280764657a31333337067374656"
            + "56d6ac4f4f51800000000065645535453000000011c7b28a34479885c3ca12abcd180577382cf2d7d05a4f64219587"
            + "c9cc8ae87f2707ace6d2297830935a1c21922b51c2647af44f3baaf7a0a860f55f4fefdbf2cc4";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "from" account. If no active key is provided or the active key is not
     * valid an Exception will be thrown. The active key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        DelegateVestingSharesOperation delegateVestingSharesOperation = new DelegateVestingSharesOperation();

        delegateVestingSharesOperation.setDelegator(new AccountName("dez1337"));
        delegateVestingSharesOperation.setDelegatee(new AccountName("steemj"));
        delegateVestingSharesOperation.setVestingShares(new Asset(418772164L, AssetSymbolType.VESTS));

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(delegateVestingSharesOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingDelegateVestingSharesOperation = steemApiWrapper
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation delegateVestingSharesOperation = blockContainingDelegateVestingSharesOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(delegateVestingSharesOperation, instanceOf(DelegateVestingSharesOperation.class));
        assertThat(((DelegateVestingSharesOperation) delegateVestingSharesOperation).getDelegator().getAccountName(),
                equalTo(EXPECTED_FROM_ACCOUNT));
        assertThat(((DelegateVestingSharesOperation) delegateVestingSharesOperation).getDelegatee().getAccountName(),
                equalTo(EXPECTED_TO_ACCOUNT));
        assertThat(((DelegateVestingSharesOperation) delegateVestingSharesOperation).getVestingShares().getAmount(),
                equalTo(EXPECTED_AMOUNT));
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
