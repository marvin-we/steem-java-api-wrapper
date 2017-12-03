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
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Verify the functionality of the "limit order create 2 operation" under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreate2OperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701150764657a31333337"
            + "bf85070001000000000000000353424400000000010000000000000003534244000000000a00000000000"
            + "00003535445454d000000e7c8045700011b682f18ada39a8194c5408bbd3a7b6185cd85e75f94e1d533f6"
            + "29c76a9ac5a1723257562c699d267f4428b17c682106f846fd973539abb55f7e2c7423cb48af5a";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce7c8045701150764657a31"
            + "333337bf85070001000000000000000353424400000000010000000000000003534244000000000a0000000"
            + "000000003535445454d000000e7c8045700011b13a5bebf929bc3ad5232d4ec558149bfa00c9b3956f0b2d5"
            + "ca55810d964c681257c941b73bd69c2a9b4c5177145bb9716f6f2f734f1a47ccdd61c1fa8ae28a1e";

    /**
     * <b>Attention:</b> This test class requires a valid posting key of the
     * used "voter". If no posting key is provided or the posting key is not
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

        Asset base = new Asset(1L, AssetSymbolType.SBD);
        Asset quote = new Asset(10L, AssetSymbolType.STEEM);

        Price exchangeRate = new Price(base, quote);

        Asset amountToSell = new Asset(1L, AssetSymbolType.SBD);

        TimePointSec expirationDate = new TimePointSec(EXPIRATION_DATE);
        boolean fillOrKill = false;
        UInteger orderId = UInteger.valueOf(492991);
        AccountName owner = new AccountName("dez1337");

        LimitOrderCreate2Operation limitOrderCreate2Operation = new LimitOrderCreate2Operation(owner, orderId,
                amountToSell, fillOrKill, exchangeRate, expirationDate);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(limitOrderCreate2Operation);

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
