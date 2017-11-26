package eu.bittrade.libs.steemj;

import java.io.UnsupportedEncodingException;

import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class defines which tests should at least be performed for an operation
 * and prepares a transaction object so that it does not need to be created in
 * each sub test case.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTransactionalUT extends BaseUT {
    protected static SignedTransaction signedTransaction;

    /**
     * Setup the test environment for transaction related unit tests.
     */
    protected static void setupUnitTestEnvironmentForTransactionalTests() {
        setupUnitTestEnvironment();

        SteemJConfig.getInstance().setValidationLevel(ValidationType.SKIP_VALIDATION);
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
