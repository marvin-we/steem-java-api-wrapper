package eu.bittrade.libs.steemj.base.models;

import eu.bittrade.libs.steemj.BaseOperationParsingIT;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class defines which tests should at least be performed for an operation
 * and prepares a transaction object so that it does not need to be created in
 * each sub test case.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTransactionalIntegrationTest extends BaseOperationParsingIT {
    protected static SignedTransaction signedTransaction;

    /**
     * Setup the test environment for transaction related tests.
     */
    protected static void setupIntegrationTestEnvironmentForTransactionalTests() {
        setupIntegrationTestEnvironment();

        signedTransaction = new SignedTransaction();
        signedTransaction.setExpirationDate(new TimePointSec(EXPIRATION_DATE));
        signedTransaction.setRefBlockNum(REF_BLOCK_NUM);
        signedTransaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
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
     * Verify that a transaction is signed correctly by using the verify
     * transaction method of a Steem Node.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    public abstract void verifyTransaction() throws Exception;

    /**
     * Verify that a transaction has the expected byte representation.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    public abstract void getTransactionHex() throws Exception;
}
