/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.network.broadcast;

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
import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.database.models.DynamicGlobalProperty;
import eu.bittrade.libs.steemj.plugins.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.operations.FollowOperation;
import eu.bittrade.libs.steemj.plugins.apis.network.broadcast.api.NetworkBroadcastApi;
import eu.bittrade.libs.steemj.plugins.apis.network.broadcast.models.BroadcastTransactionSynchronousReturn;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.SignedBlock;
import eu.bittrade.libs.steemj.protocol.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.plugins.apis.network.broadcast.api.NetworkBroadcastApi
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
     * {@link eu.bittrade.libs.steemj.plugins.apis.network.broadcast.api.NetworkBroadcastApi#broadcastTransactionSynchronous(CommunicationHandler, eu.bittrade.libs.steemj.chain.SignedTransaction)}
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
     * {@link eu.bittrade.libs.steemj.plugins.apis.network.broadcast.api.NetworkBroadcastApi#broadcastTransaction(CommunicationHandler, eu.bittrade.libs.steemj.chain.SignedTransaction)}
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
     * {@link eu.bittrade.libs.steemj.plugins.apis.network.broadcast.api.NetworkBroadcastApi#broadcastBlock(CommunicationHandler, SignedBlock)}
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
