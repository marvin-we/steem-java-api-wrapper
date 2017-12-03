package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.joou.UInteger;
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
 * Verify the functionality of the "limit order create operation" under the use
 * of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreateOperationIT extends BaseTransactionVerificationIT {

    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701050764657a31333337c3850"
            + "700010000000000000003534244000000000a0000000000000003535445454d000000e7c8045700011b69776cf"
            + "d76f1e20da06f40c9df5fcc1c25156b7968b3566655ba39622bd31158798b91b16bc134740051be55e4c678a9f"
            + "a0db60b50fb4ff8f77a26cc4e6ed73c";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce9c8045701050764657a31333"
            + "337c3850700010000000000000003534244000000000a0000000000000003535445454d000000e7c8045700011"
            + "b1b58a0f1bc4cb2b9ad85191ac3111e4bfebfb966b2d45da962b3dff9f848e47b56eb4b5ad81ab90a8eeb921d2"
            + "8304fcba675a8a672309a2558ea032a45674dfc";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "owner". If no active key is provided or the active key is not valid an
     * Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        Asset amountToSell = new Asset(1L, AssetSymbolType.SBD);

        TimePointSec expirationDate = new TimePointSec(EXPIRATION_DATE);
        boolean fillOrKill = false;

        Asset minToReceive = new Asset(10L, AssetSymbolType.STEEM);

        UInteger orderId = UInteger.valueOf(492995);
        AccountName owner = new AccountName("dez1337");

        LimitOrderCreateOperation limitOrderCreateOperation = new LimitOrderCreateOperation(owner, orderId,
                amountToSell, minToReceive, fillOrKill, expirationDate);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(limitOrderCreateOperation);

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
