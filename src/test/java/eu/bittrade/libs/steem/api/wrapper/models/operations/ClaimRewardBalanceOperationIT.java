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
 * Verify the functionality of the "claim reward balance operation" under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ClaimRewardBalanceOperationIT extends BaseIntegrationTest {
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
        setupIntegrationTestEnvironment();

        ClaimRewardBalanceOperation claimRewardBalanceOperation = new ClaimRewardBalanceOperation();
        claimRewardBalanceOperation.setAccount(new AccountName("dez1337"));

        Asset rewardSbd = new Asset();
        rewardSbd.setAmount(1);
        rewardSbd.setSymbol(AssetSymbolType.SBD);

        claimRewardBalanceOperation.setRewardSbd(rewardSbd);

        Asset rewardSteem = new Asset();
        rewardSteem.setAmount(2);
        rewardSteem.setSymbol(AssetSymbolType.STEEM);

        claimRewardBalanceOperation.setRewardSteem(rewardSteem);

        Asset rewardVests = new Asset();
        rewardVests.setAmount(2);
        rewardVests.setSymbol(AssetSymbolType.VESTS);

        claimRewardBalanceOperation.setRewardVests(rewardVests);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(claimRewardBalanceOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingClaimRewardBalanceOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation claimRewardBalanceOperation = blockContainingClaimRewardBalanceOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(claimRewardBalanceOperation, instanceOf(ClaimRewardBalanceOperation.class));
        assertThat(((ClaimRewardBalanceOperation) claimRewardBalanceOperation).getAccount().getAccountName(),
                equalTo(EXPECTED_ACCOUNT));
        assertThat(((ClaimRewardBalanceOperation) claimRewardBalanceOperation).getRewardVests().getAmount(),
                equalTo(EXPECTED_VESTS));
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
