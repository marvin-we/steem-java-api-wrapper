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
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Verify the functionality of the "escrow release operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowReleaseOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcecc80457011d0764657a3133333"
            + "70764657a3133333706737465656d6a0764657a313333370764657a31333337220000000100000000000"
            + "00003534244000000000a0000000000000003535445454d000000011c247493deeaf210cddf6a7d406bf"
            + "1b0c2b11a76e6227150ea4ff89d5b53ecd85b7f358f1e689b1678f5369f802e75c40d59906de4209cc0e"
            + "cea8a36958f7ace1b";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce7c80457011d0764657"
            + "a313333370764657a3133333706737465656d6a0764657a313333370764657a313333372200000001000"
            + "0000000000003534244000000000a0000000000000003535445454d000000011c78722413857c16d3e57"
            + "e304f89491414aa6f63923536862c0a742139ec896b5d131e286eb2cadbf011d77075e1c43eea92bad10"
            + "2921af0ee618f82dc71a6c3b6";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "from" account. If no active key is provided or the active key is not
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

        AccountName agent = new AccountName("steemj");
        AccountName from = new AccountName("dez1337");
        AccountName to = new AccountName("dez1337");
        AccountName who = new AccountName("dez1337");
        AccountName receiver = new AccountName("dez1337");
        long escrowId = 34;

        Asset sbdAmount = new Asset(1L, AssetSymbolType.SBD);
        Asset steemAmount = new Asset(10L, AssetSymbolType.STEEM);

        EscrowReleaseOperation escrowReleaseOperation = new EscrowReleaseOperation(from, to, agent, escrowId, who,
                receiver, sbdAmount, steemAmount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(escrowReleaseOperation);

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
