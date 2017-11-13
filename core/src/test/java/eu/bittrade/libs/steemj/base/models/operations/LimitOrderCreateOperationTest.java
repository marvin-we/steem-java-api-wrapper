package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.joou.UInteger;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalUnitTest;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transformation of the {@link LimitOrderCreateOperation}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreateOperationTest extends BaseTransactionalUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "050764657a31333337c3850700010000000000000003534244000000000a0000000000000003535445454d000000e7c80457";
    final String EXPECTED_TRANSACTION_HASH = "618e8970cc8829cc7768ad99dfc6c97af5de49880638450af9aa9db8e100c5f0";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000000000000f68585abf4dcedc8045701050764"
            + "657a31333337c3850700010000000000000003534244000000000a0000000000000003535445454d000000e7c8045700";

    private static LimitOrderCreateOperation limitOrderCreateOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        Asset amountToSell = new Asset();
        amountToSell.setAmount(1L);
        amountToSell.setSymbol(AssetSymbolType.SBD);

        TimePointSec expirationDate = new TimePointSec(EXPIRATION_DATE);
        boolean fillOrKill = false;

        Asset minToReceive = new Asset();
        minToReceive.setAmount(10L);
        minToReceive.setSymbol(AssetSymbolType.STEEM);

        UInteger orderId = UInteger.valueOf(492995);
        AccountName owner = new AccountName("dez1337");

        limitOrderCreateOperation = new LimitOrderCreateOperation(owner, orderId, amountToSell, minToReceive,
                fillOrKill, expirationDate);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(limitOrderCreateOperation);

        signedTransaction.setOperations(operations);
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(limitOrderCreateOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Override
    @Test
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(signedTransaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
