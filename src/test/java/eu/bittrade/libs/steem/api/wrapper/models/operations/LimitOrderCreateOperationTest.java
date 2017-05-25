package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steem.api.wrapper.BaseUnitTest;
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;

/**
 * Test a Steem "limit order create operation" and verify the results against
 * the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreateOperationTest extends BaseUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "050764657a31333337c3850700010000000000000003534244000000000a0000000000000003535445454d000000e7c80457";
    final String EXPECTED_TRANSACTION_HASH = "618e8970cc8829cc7768ad99dfc6c97af5de49880638450af9aa9db8e100c5f0";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000000000000f68585abf4dcedc8045701050764"
            + "657a31333337c3850700010000000000000003534244000000000a0000000000000003535445454d000000e7c8045700";

    private static LimitOrderCreateOperation limitOrderCreateOperation;

    @BeforeClass
    public static void setup() throws Exception {
        limitOrderCreateOperation = new LimitOrderCreateOperation();

        Asset amountToSell = new Asset();
        amountToSell.setAmount(1L);
        amountToSell.setSymbol(AssetSymbolType.SBD);

        limitOrderCreateOperation.setAmountToSell(amountToSell);
        limitOrderCreateOperation.setExpirationDate(EXPIRATION_DATE);
        limitOrderCreateOperation.setFillOrKill(false);

        Asset minToReceive = new Asset();
        minToReceive.setAmount(10L);
        minToReceive.setSymbol(AssetSymbolType.STEEM);

        limitOrderCreateOperation.setMinToReceive(minToReceive);
        limitOrderCreateOperation.setOrderId(492995);
        limitOrderCreateOperation.setOwner(new AccountName("dez1337"));

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(limitOrderCreateOperation);

        transaction.setOperations(operations);
    }

    @Test
    public void testLimitOrderCreateOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(limitOrderCreateOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testLimitOrderCreateOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        transaction.sign();

        assertThat("The serialized transaction should look like expected.", Utils.HEX.encode(transaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
