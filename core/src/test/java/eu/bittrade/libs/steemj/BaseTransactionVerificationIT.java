package eu.bittrade.libs.steemj;

/**
 * This class defines which tests should at least be performed for an operation
 * and prepares a transaction object so that it does not need to be created in
 * each sub test case.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTransactionVerificationIT extends BaseTransactionalIT {
    /**
     * Setup the test environment for transaction related tests.
     */
    protected static void setupIntegrationTestEnvironmentForTransactionVerificationTests(String mode, String endpoint) {
        setupIntegrationTestEnvironmentForTransactionalTests(mode, endpoint);
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
