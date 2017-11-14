package eu.bittrade.libs.steemj.apis.network.broadcast;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalIntegrationTest;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.operations.CommentOptionsOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.apis.network.broadcast.NetworkBroadcastApi
 * NetworkBroadcastApi}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class NetworkBroadcastApiIT extends BaseTransactionalIntegrationTest {
    private static CommunicationHandler COMMUNICATION_HANDLER;

    /**
     * Setup the test environment.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     */
    @BeforeClass
    public static void init() throws SteemCommunicationException {
        setupIntegrationTestEnvironmentForTransactionalTests();

        COMMUNICATION_HANDLER = new CommunicationHandler();
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.network.broadcast.NetworkBroadcastApi#broadcastTransactionWithCallback(CommunicationHandler, eu.bittrade.libs.steemj.base.models.SignedTransaction)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     * @throws SteemInvalidTransactionException
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testBroadcastTransactionWithCallback()
            throws SteemCommunicationException, SteemResponseException, SteemInvalidTransactionException {
        AccountName author = new AccountName("dez1337");
        Permlink permlink = new Permlink("steemj-v0-2-4-has-been-released-update-9");
        boolean allowVotes = true;
        boolean allowCurationRewards = true;
        short percentSteemDollars = (short) 10000;
        Asset maxAcceptedPayout = new Asset(1000000000, AssetSymbolType.SBD);

        CommentOptionsOperation commentOptionsOperation = new CommentOptionsOperation(author, permlink,
                maxAcceptedPayout, percentSteemDollars, allowVotes, allowCurationRewards, null);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOptionsOperation);

        signedTransaction.setOperations(operations);

        sign();

        NetworkBroadcastApi.broadcastTransactionSynchronous(COMMUNICATION_HANDLER, signedTransaction);
        NetworkBroadcastApi.broadcastTransactionWithCallback(COMMUNICATION_HANDLER, signedTransaction);

        assertThat(1, equalTo(2));
    }

    @Override
    public void verifyTransaction() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void getTransactionHex() throws Exception {
        // TODO Auto-generated method stub

    }
}
