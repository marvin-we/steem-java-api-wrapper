package eu.bittrade.libs.steemj.base.models;

import java.io.UnsupportedEncodingException;

import org.joou.UInteger;
import org.joou.UShort;

import eu.bittrade.libs.steemj.BaseUnitTest;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class defines which tests should at least be performed for an operation
 * and prepares a transaction object so that it does not need to be created in
 * each sub test case.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTransactionalUnitTest extends BaseUnitTest {
    protected static SignedTransaction signedTransaction;

    /**
     * Setup the test environment for transaction related unit tests.
     */
    protected static void setupUnitTestEnvironmentForTransactionalTests() {
        setupUnitTestEnvironment();

        signedTransaction = new SignedTransaction();
        signedTransaction.setExpirationDate(new TimePointSec(EXPIRATION_DATE));
        signedTransaction.setRefBlockNum(UShort.valueOf(REF_BLOCK_NUM));
        signedTransaction.setRefBlockPrefix(UInteger.valueOf(REF_BLOCK_PREFIX));
        // Add extensions when supported.
        // signedTransaction.setExtensions(extensions);
    }

    /**
     * This is a workaround as the sign method is not visible in sub packages.
     * 
     * @throws SteemInvalidTransactionException
     *             If something went wrong.
     */
    protected static void sign() throws SteemInvalidTransactionException {
        signedTransaction.sign(true);
    }

    /**
     * Test if the toByteArray method returns the expected byte array.
     * 
     * @throws UnsupportedEncodingException
     *             If something went wrong.
     * @throws SteemInvalidTransactionException
     *             If something went wrong.
     */
    public abstract void testOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException;

    /**
     * Test if the SHA256 Hex of a transaction including the operation is the
     * expected hash.
     * 
     * @throws UnsupportedEncodingException
     *             If something went wrong.
     * @throws SteemInvalidTransactionException
     *             If something went wrong.
     */
    public abstract void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException;
}
