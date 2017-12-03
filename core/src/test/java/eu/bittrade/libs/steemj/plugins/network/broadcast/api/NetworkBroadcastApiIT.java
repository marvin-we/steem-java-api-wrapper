package eu.bittrade.libs.steemj.plugins.network.broadcast.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionBroadcastIT;
import eu.bittrade.libs.steemj.BaseTransactionalIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.apis.follow.models.operations.FollowOperation;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.DynamicGlobalProperty;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.network.broadcast.model.BroadcastTransactionSynchronousReturn;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.plugins.network.broadcast.api.NetworkBroadcastApi
 * NetworkBroadcastApi}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class NetworkBroadcastApiIT extends BaseTransactionBroadcastIT {
    private static CommunicationHandler COMMUNICATION_HANDLER;

    /**
     * Setup the test environment.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     */
    @BeforeClass
    public static void init() throws SteemCommunicationException {
        setupIntegrationTestEnvironmentForTransactionBroadcastTests(HTTP_MODE_IDENTIFIER, TESTNET_ENDPOINT_IDENTIFIER);

        COMMUNICATION_HANDLER = new CommunicationHandler();
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.network.broadcast.api.NetworkBroadcastApi#broadcastTransactionSynchronous(CommunicationHandler, eu.bittrade.libs.steemj.base.models.SignedTransaction)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     * @throws SteemInvalidTransactionException
     *             If the transaction is not valid.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testBroadcastTransactionSynchronous()
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(BaseTransactionalIT.DEZ_ACCOUNT_NAME);

        String id = "follow";
        String json = (new FollowOperation(BaseTransactionalIT.DEZ_ACCOUNT_NAME,
                BaseTransactionalIT.STEEMJ_ACCOUNT_NAME, Arrays.asList(FollowType.BLOG))).toJson();

        CustomJsonOperation customJsonOperation = new CustomJsonOperation(null, requiredPostingAuths, id, json);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        DynamicGlobalProperty globalProperties = steemJ.getDynamicGlobalProperties();

        signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations, null);

        signedTransaction.sign();

        BroadcastTransactionSynchronousReturn result = NetworkBroadcastApi
                .broadcastTransactionSynchronous(COMMUNICATION_HANDLER, signedTransaction);

        assertThat(result.getBlockNum(), greaterThan(0));
        assertThat(result.getTrxNum(), greaterThanOrEqualTo(0));
        assertThat(result.getId().toString(), not(isEmptyOrNullString()));
        assertFalse(result.isExpired());
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.network.broadcast.api.NetworkBroadcastApi#broadcastTransaction(CommunicationHandler, eu.bittrade.libs.steemj.base.models.SignedTransaction)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     * @throws SteemInvalidTransactionException
     *             If the transaction is not valid.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testBroadcastTransaction()
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(BaseTransactionalIT.STEEMJ_ACCOUNT_NAME);

        String id = "follow";
        String json = (new FollowOperation(BaseTransactionalIT.STEEMJ_ACCOUNT_NAME,
                BaseTransactionalIT.DEZ_ACCOUNT_NAME, Arrays.asList(FollowType.BLOG))).toJson();

        CustomJsonOperation customJsonOperation = new CustomJsonOperation(null, requiredPostingAuths, id, json);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        DynamicGlobalProperty globalProperties = steemJ.getDynamicGlobalProperties();

        signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations, null);

        signedTransaction.sign();

        NetworkBroadcastApi.broadcastTransaction(COMMUNICATION_HANDLER, signedTransaction);
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.network.broadcast.api.NetworkBroadcastApi#broadcastBlock(CommunicationHandler, eu.bittrade.libs.steemj.base.models.SignedBlock)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     * @throws SteemInvalidTransactionException
     *             If the transaction is not valid.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testBroadcastBlock()
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        // TODO: Implement.
    }
}
