package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class tests if the {@link WithdrawVestingOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WithdrawVestingOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5763343;
    private static final int TRANSACTION_INDEX = 2;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ACCOUNT = "alex90342fastn1";
    private static final AssetSymbolType EXPECTED_ASSET_SYMBOL = AssetSymbolType.VESTS;

    /**
     * Prepare all required fields used by this test class.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
    }

    @Override
    @Test
    @Category({ IntegrationTest.class })
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        SignedBlockWithInfo blockContainingWithdrawVestingOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation withdrawVestingOperation = blockContainingWithdrawVestingOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(withdrawVestingOperation, instanceOf(WithdrawVestingOperation.class));
        assertThat(((WithdrawVestingOperation) withdrawVestingOperation).getAccount().getName(),
                equalTo(EXPECTED_ACCOUNT));
        assertThat(((WithdrawVestingOperation) withdrawVestingOperation).getVestingShares().getSymbol(),
                equalTo(EXPECTED_ASSET_SYMBOL));
    }
}
