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
public class ProveAuthorityOperationTest extends BaseTransactionalUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "0b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4"
            + "064657a3133333702e5127bd7d41f01d9981a5a2c2524a60706040bbec8838a39719550ea2507100088130000000000"
            + "0003535445454d0000000001000000010000000000000003535445454d0000";
    final String EXPECTED_TRANSACTION_HASH = "5d01935dc58925f39d156fd26160049d81a4a4d669d29183150719086a296b0a";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dceac80457010b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4064"
            + "657a3133333702e5127bd7d41f01d9981a5a2c2524a60706040bbec8838a39719550ea2507100088130000000000000"
            + "3535445454d0000000001000000010000000000000003535445454d000000";

    private static ProveAuthorityOperation proveAuthorityOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironment();

        AccountName challengedAccount = new AccountName("dez1337");

        ProveAuthorityOperation proveAuthorityOperationWithOwnerKey = new ProveAuthorityOperation(challengedAccount,
                true);
        ProveAuthorityOperation proveAuthorityOperationWithActiveKey = new ProveAuthorityOperation(challengedAccount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(proveAuthorityOperationWithOwnerKey);
        operations.add(proveAuthorityOperationWithActiveKey);

        signedTransaction.setOperations(operations);
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(proveAuthorityOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
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
