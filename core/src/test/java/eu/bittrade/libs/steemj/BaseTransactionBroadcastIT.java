package eu.bittrade.libs.steemj;

/**
 * This class defines which tests should at least be performed for an operation
 * and prepares a transaction object so that it does not need to be created in
 * each sub test case.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BaseTransactionBroadcastIT extends BaseTransactionalIT {
    /**
     * Setup the test environment for transaction related tests.
     */
    protected static void setupIntegrationTestEnvironmentForTransactionBroadcastTests(String mode, String endpoint) {
        setupIntegrationTestEnvironmentForTransactionalTests(mode, endpoint);
    }
}
