package eu.bittrade.libs.steemj.plugins.apis.account.history;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.joou.UInteger;
import org.joou.ULong;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.BlockHeaderExtensions;
import eu.bittrade.libs.steemj.base.models.HardforkVersionVote;
import eu.bittrade.libs.steemj.base.models.Version;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.AppliedOperation;
import eu.bittrade.libs.steemj.plugins.apis.block.BlockApi;
import eu.bittrade.libs.steemj.plugins.apis.block.models.ExtendedSignedBlock;
import eu.bittrade.libs.steemj.plugins.apis.block.models.GetBlockArgs;
import eu.bittrade.libs.steemj.plugins.apis.block.models.GetBlockHeaderArgs;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.AnnotatedSignedTransaction;
import eu.bittrade.libs.steemj.protocol.BlockHeader;
import eu.bittrade.libs.steemj.protocol.TransactionId;
import eu.bittrade.libs.steemj.protocol.operations.AccountCreateOperation;
import eu.bittrade.libs.steemj.protocol.operations.AccountCreateWithDelegationOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;

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
     * {@link eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi#getOpsInBlock(CommunicationHandler, long, boolean)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetBlock() throws SteemCommunicationException, SteemResponseException {
        final ExtendedSignedBlock signedBlockWithInfo = BlockApi
                .getBlock(COMMUNICATION_HANDLER, new GetBlockArgs(UInteger.valueOf(13310401))).getBlock().get();

        assertThat(signedBlockWithInfo.getTimestamp().getDateTime(), equalTo("2017-07-01T19:24:42"));
        assertThat(signedBlockWithInfo.getWitness(), equalTo(new AccountName("riverhead")));

        final ExtendedSignedBlock signedBlockWithInfoWithExtension = BlockApi
                .getBlock(COMMUNICATION_HANDLER, new GetBlockArgs(UInteger.valueOf(12615532))).getBlock().get();

        assertThat(signedBlockWithInfoWithExtension.getTimestamp().getDateTime(),
                equalTo(new TimePointSec("2017-06-07T15:33:27").getDateTime()));
        assertThat(signedBlockWithInfoWithExtension.getWitness(), equalTo(new AccountName("dragosroua")));

        BlockHeaderExtensions versionExtension = signedBlockWithInfoWithExtension.getExtensions().get(0);
        BlockHeaderExtensions hardforkVersionVoteExtension = signedBlockWithInfoWithExtension.getExtensions().get(1);

        assertThat(versionExtension, instanceOf(Version.class));
        assertThat(hardforkVersionVoteExtension, instanceOf(HardforkVersionVote.class));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi#getTransaction(CommunicationHandler, eu.bittrade.libs.steemj.protocol.TransactionId)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetTransaction() throws SteemCommunicationException, SteemResponseException {
        // TODO: Check also null case of optional
        final AnnotatedSignedTransaction annotatedSignedTransaction = AccountHistoryApi
                .getTransaction(COMMUNICATION_HANDLER, new TransactionId("bd8069e6544f658da560b72e93b605dfe2cb0aaf"));

        //assertThat(annotatedSignedTransaction.getTimestamp().getDateTime(), equalTo("2017-07-02T19:15:06"));
        //assertThat(blockHeader.getWitness(), equalTo(new AccountName("clayop")));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi#getAccountHistory(CommunicationHandler, AccountName, org.joou.ULong, UInteger)}
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
        final Map<Integer, AppliedOperation> accountHistorySetOne = AccountHistoryApi.getAccountHistory(
                COMMUNICATION_HANDLER, new AccountName("dez1337"), ULong.valueOf(10), UInteger.valueOf(10));
        assertEquals("expect response to contain 10 results", 11, accountHistorySetOne.size());

        Operation firstOperation = accountHistorySetOne.get(0).getOp();
        assertTrue("the first operation for each account is the 'account_create_operation'",
                firstOperation instanceof AccountCreateOperation);

        final Map<Integer, AppliedOperation> accountHistorySetTwo = AccountHistoryApi.getAccountHistory(
                COMMUNICATION_HANDLER, new AccountName("randowhale"), ULong.valueOf(1000), UInteger.valueOf(1000));
        assertEquals("expect response to contain 1001 results", 1001, accountHistorySetTwo.size());

        assertThat(accountHistorySetTwo.get(0).getOp(), instanceOf(AccountCreateWithDelegationOperation.class));
        assertThat(((AccountCreateWithDelegationOperation) accountHistorySetTwo.get(0).getOp()).getCreator().getName(),
                equalTo(new AccountName("anonsteem").getName()));
    }
}
