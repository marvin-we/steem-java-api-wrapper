package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Transaction;

/**
 * Test a Steem "limit order cancel operation" and verify the results against
 * the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCancelOperationTest extends BaseTest {
    final String EXPECTED_BYTE_REPRESENTATION = "060764657a31333337c3850700";
    final String EXPECTED_TRANSACTION_HASH = "8206897b01c87e6ef07fcf39259ce27b19050d2f2a5603c1c1b075a1498a0c05";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "00000000000000000000000000000000000000000000000000000"
            + "00000000000f68585abf4dcf0c8045701060764657a31333337c385070000";

    private static LimitOrderCancelOperation limitOrderCancelOperation;
    private static Transaction limitOrderCancelOperationTransaction;

    @BeforeClass
    public static void setup() throws Exception {
        limitOrderCancelOperation = new LimitOrderCancelOperation();

        limitOrderCancelOperation.setOwner(new AccountName("dez1337"));
        limitOrderCancelOperation.setOrderId(492995L);

        Operation[] operations = { limitOrderCancelOperation };

        limitOrderCancelOperationTransaction = new Transaction();
        limitOrderCancelOperationTransaction.setExpirationDate(EXPIRATION_DATE);
        limitOrderCancelOperationTransaction.setRefBlockNum(REF_BLOCK_NUM);
        limitOrderCancelOperationTransaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        limitOrderCancelOperationTransaction.setOperations(operations);
    }

    @Test
    public void testLimitOrderCreateOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(limitOrderCancelOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testLimitOrderCreateOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        limitOrderCancelOperationTransaction.sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(limitOrderCancelOperationTransaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat(
                "Expect that the serialized transaction results in the given hex.", Utils.HEX.encode(Sha256Hash
                        .wrap(Sha256Hash.hash(limitOrderCancelOperationTransaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() {

    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() {

    }
}
