package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalIntegrationTest;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "claim reward balance operation" under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ClaimRewardBalanceOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 12000608;
    private static final int TRANSACTION_INDEX = 4;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ACCOUNT = "lifewordmission";
    private static final double EXPECTED_VESTS = 60552.750918;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dceac804570127"
            + "0764657a31333337020000000000000003535445454d0000010000000000000003534"
            + "244000000000200000000000000065645535453000000011c3143bace1dc9ad51e382"
            + "d0a30f6fa7565859154c1e337f30353f643a3af1f27e74095d7c7154bf2c973fe39c1"
            + "99fd1dffefc0986751bee498fd3cda286bafc41";

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
        setupIntegrationTestEnvironmentForTransactionalTests();

        AccountName account = new AccountName("dez1337");

        Asset rewardSbd = new Asset();
        rewardSbd.setAmount(1);
        rewardSbd.setSymbol(AssetSymbolType.SBD);

        Asset rewardSteem = new Asset();
        rewardSteem.setAmount(2);
        rewardSteem.setSymbol(AssetSymbolType.STEEM);

        Asset rewardVests = new Asset();
        rewardVests.setAmount(2);
        rewardVests.setSymbol(AssetSymbolType.VESTS);

        ClaimRewardBalanceOperation claimRewardBalanceOperation = new ClaimRewardBalanceOperation(account, rewardSteem,
                rewardSbd, rewardVests);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(claimRewardBalanceOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingClaimRewardBalanceOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation claimRewardBalanceOperation = blockContainingClaimRewardBalanceOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(claimRewardBalanceOperation, instanceOf(ClaimRewardBalanceOperation.class));
        assertThat(((ClaimRewardBalanceOperation) claimRewardBalanceOperation).getAccount().getName(),
                equalTo(EXPECTED_ACCOUNT));
        assertThat(((ClaimRewardBalanceOperation) claimRewardBalanceOperation).getRewardVests().toReal(),
                equalTo(EXPECTED_VESTS));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(signedTransaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
