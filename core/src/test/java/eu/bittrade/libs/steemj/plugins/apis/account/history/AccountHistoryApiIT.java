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
 *     along with SteemJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.account.history;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.joou.UInteger;
import org.joou.ULong;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.AppliedOperation;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.EnumVirtualOps;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.EnumVirtualOpsArgs;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.GetAccountHistoryArgs;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.GetOpsInBlockArgs;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.AnnotatedSignedTransaction;
import eu.bittrade.libs.steemj.protocol.TransactionId;
import eu.bittrade.libs.steemj.protocol.operations.AccountCreateOperation;
import eu.bittrade.libs.steemj.protocol.operations.AccountCreateWithDelegationOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.ProducerRewardOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi
 * AccountHistoryApi}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountHistoryApiIT extends BaseIT {
    private static CommunicationHandler COMMUNICATION_HANDLER;

    /**
     * Setup the test environment.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     */
    @BeforeClass
    public static void init() throws SteemCommunicationException {
        setupIntegrationTestEnvironment();

        COMMUNICATION_HANDLER = new CommunicationHandler();
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi#getOpsInBlock(CommunicationHandler, GetOpsInBlockArgs)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetOpsInBlock() throws SteemCommunicationException, SteemResponseException {
        final List<AppliedOperation> operations = AccountHistoryApi
                .getOpsInBlock(COMMUNICATION_HANDLER, new GetOpsInBlockArgs(UInteger.valueOf(13310401), true))
                .getOperations();

        assertThat(operations.size(), equalTo(4));
        assertThat(operations.get(3).getBlock(), equalTo(UInteger.valueOf(13310401)));
        assertThat(operations.get(0).getOperationWrapper().getOperation(), instanceOf(ProducerRewardOperation.class));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi#getTransaction(CommunicationHandler, TransactionId)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    // Ignored as the API call is deprecated and not supported by the public
    // Steem API nodes provided by Steemit.
    @Ignore
    @Deprecated
    public void testGetTransaction() throws SteemCommunicationException, SteemResponseException {
        final TransactionId TRANSACTION_ID_TO_SEARCH = new TransactionId("bd8069e6544f658da560b72e93b605dfe2cb0aaf");
        final AnnotatedSignedTransaction annotatedSignedTransaction = AccountHistoryApi
                .getTransaction(COMMUNICATION_HANDLER, TRANSACTION_ID_TO_SEARCH);

        assertThat(annotatedSignedTransaction.getBlockNum(), equalTo(1));
        assertThat(annotatedSignedTransaction.getTransactionId(), equalTo(TRANSACTION_ID_TO_SEARCH));
        assertThat(annotatedSignedTransaction.getTransactionNum(), equalTo(1));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi#getAccountHistory(CommunicationHandler, GetAccountHistoryArgs)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetAccountHistory() throws SteemCommunicationException, SteemResponseException {
        final Map<Long, AppliedOperation> accountHistorySetOne = AccountHistoryApi
                .getAccountHistory(COMMUNICATION_HANDLER,
                        new GetAccountHistoryArgs(new AccountName("dez1337"), ULong.valueOf(10), UInteger.valueOf(10)))
                .getHistory();

        assertEquals("expect response to contain 10 results", 11, accountHistorySetOne.size());

        Operation firstOperation = accountHistorySetOne.get(0L).getOperationWrapper().getOperation();
        assertTrue("the first operation for each account is the 'account_create_operation'",
                firstOperation instanceof AccountCreateOperation);

        final Map<Long, AppliedOperation> accountHistorySetTwo = AccountHistoryApi.getAccountHistory(
                COMMUNICATION_HANDLER,
                new GetAccountHistoryArgs(new AccountName("randowhale"), ULong.valueOf(1000), UInteger.valueOf(1000)))
                .getHistory();
        assertEquals("expect response to contain 1001 results", 1001, accountHistorySetTwo.size());

        assertThat(accountHistorySetTwo.get(0L).getOperationWrapper().getOperation(),
                instanceOf(AccountCreateWithDelegationOperation.class));
        assertThat(((AccountCreateWithDelegationOperation) accountHistorySetTwo.get(0L).getOperationWrapper()
                .getOperation()).getCreator().getName(), equalTo(new AccountName("anonsteem").getName()));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi#enumVirtualOps(CommunicationHandler, EnumVirtualOpsArgs)}
     * method by searching for all virtual operations between block 1 and block
     * 1000.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testEnumVirtualOps() throws SteemCommunicationException, SteemResponseException {
        final EnumVirtualOps enumVirtualOps = AccountHistoryApi.enumVirtualOps(COMMUNICATION_HANDLER,
                new EnumVirtualOpsArgs.Builder().startAt(UInteger.valueOf(1)).endAt(UInteger.valueOf(1000)).build());

        // Next block range.
        assertThat(enumVirtualOps.getNextBlockRangeBegin(), equalTo(UInteger.valueOf(1093)));
        assertThat(enumVirtualOps.getOperations().size(), equalTo(1));
        assertThat(enumVirtualOps.getOperations().get(0).getOperationWrapper().getOperation(),
                instanceOf(ProducerRewardOperation.class));
    }
}
