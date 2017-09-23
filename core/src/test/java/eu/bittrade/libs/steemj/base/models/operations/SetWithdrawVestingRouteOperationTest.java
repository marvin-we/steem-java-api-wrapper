package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalUnitTest;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transformation of a Steem "prove authority operation".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetWithdrawVestingRouteOperationTest extends BaseTransactionalUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "140764657a3133333706737465656d6a102701";
    final String EXPECTED_TRANSACTION_HASH = "95fad2ebfc257c604b9cd8b4dab49c5a7f7b4b064f6cf75d8fe5dbd78000f9b3";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dcecc8045701140764657a3133333706737465656d6a10270100";

    private static SetWithdrawVestingRouteOperation setWithdrawVestingRouteOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        AccountName fromAccount = new AccountName("dez1337");
        AccountName toAccount = new AccountName("steemj");
        int percentage = 10000;
        boolean autoVest = true;

        setWithdrawVestingRouteOperation = new SetWithdrawVestingRouteOperation(fromAccount, toAccount, percentage,
                autoVest);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(setWithdrawVestingRouteOperation);

        signedTransaction.setOperations(operations);

        signedTransaction.setOperations(operations);
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(setWithdrawVestingRouteOperation.toByteArray()),
                equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Override
    @Test
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        // Sign the transaction.
        sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(signedTransaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
