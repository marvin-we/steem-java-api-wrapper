package eu.bittrade.libs.steemj.apis.database;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.AppliedOperation;
import eu.bittrade.libs.steemj.base.models.BlockHeader;
import eu.bittrade.libs.steemj.base.models.BlockHeaderExtensions;
import eu.bittrade.libs.steemj.base.models.HardforkVersionVote;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.base.models.Tag;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.base.models.Version;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.ProducerRewardOperation;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.apis.database.DatabaseApi DatabaseApi}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DatabaseApiIT extends BaseIT {
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
     * {@link eu.bittrade.libs.steemj.apis.database.DatabaseApi#getTrendingTags(CommunicationHandler, String, int)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetTrendingTags() throws SteemCommunicationException, SteemResponseException {
        final String REQUESTED_TAG = "steemit";

        final List<Tag> trendingTags = DatabaseApi.getTrendingTags(COMMUNICATION_HANDLER, REQUESTED_TAG, 2);

        assertNotNull(trendingTags);
        assertThat(trendingTags.size(), greaterThan(0));
        assertTrue(trendingTags.get(0).getName().equals(REQUESTED_TAG));
        assertThat(trendingTags.get(0).getComments(), greaterThan(0L));
        assertThat(trendingTags.get(0).getNetVotes(), greaterThan(0L));
        assertThat(trendingTags.get(0).getTopPosts(), greaterThan(0L));
        assertThat(trendingTags.get(0).getTotalPayouts().getSymbol(), equalTo(AssetSymbolType.VESTS));
        assertThat(trendingTags.get(0).getTotalPayouts().getAmount(), greaterThan(0L));
        assertThat(trendingTags.get(0).getTrending().intValue(), greaterThan(0));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.database.DatabaseApi#getState(CommunicationHandler, eu.bittrade.libs.steemj.base.models.Permlink)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetState() throws SteemCommunicationException, SteemResponseException {
        // TODO: Implement.
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.database.DatabaseApi#getActiveWitnesses(CommunicationHandler)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetActiveWitnesses() throws SteemCommunicationException, SteemResponseException {
        final List<AccountName> activeWitnesses = DatabaseApi.getActiveWitnesses(COMMUNICATION_HANDLER);

        // The active witness changes from time to time, so we just check if
        // something is returned.
        assertThat(activeWitnesses.size(), greaterThan(0));
        assertThat(activeWitnesses.get(0).getName(), not(isEmptyOrNullString()));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.database.DatabaseApi#getMinerQueue(CommunicationHandler)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetMinerQueue() throws SteemCommunicationException, SteemResponseException {
        final List<AccountName> minerQueue = DatabaseApi.getMinerQueue(COMMUNICATION_HANDLER);

        assertThat("Expect the number of miners greater than 0", minerQueue.size(), greaterThan(0));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.database.DatabaseApi#getBlock(CommunicationHandler, long)}
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
        final SignedBlockWithInfo signedBlockWithInfo = DatabaseApi.getBlock(COMMUNICATION_HANDLER, 13310401L);

        assertThat(signedBlockWithInfo.getTimestamp().getDateTime(), equalTo("2017-07-01T19:24:42"));
        assertThat(signedBlockWithInfo.getWitness(), equalTo("riverhead"));

        final SignedBlockWithInfo signedBlockWithInfoWithExtension = DatabaseApi.getBlock(COMMUNICATION_HANDLER,
                12615532L);

        assertThat(signedBlockWithInfoWithExtension.getTimestamp().getDateTime(),
                equalTo(new TimePointSec("2017-06-07T15:33:27").getDateTime()));
        assertThat(signedBlockWithInfoWithExtension.getWitness(), equalTo("dragosroua"));

        BlockHeaderExtensions versionExtension = signedBlockWithInfoWithExtension.getExtensions().get(0);
        BlockHeaderExtensions hardforkVersionVoteExtension = signedBlockWithInfoWithExtension.getExtensions().get(1);

        assertThat(versionExtension, instanceOf(Version.class));
        assertThat(hardforkVersionVoteExtension, instanceOf(HardforkVersionVote.class));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.database.DatabaseApi#getBlockHeader(CommunicationHandler, long)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetBlockHeader() throws SteemCommunicationException, SteemResponseException {
        final BlockHeader blockHeader = DatabaseApi.getBlockHeader(COMMUNICATION_HANDLER, 13339001L);

        assertThat(blockHeader.getTimestamp().getDateTime(), equalTo("2017-07-02T19:15:06"));
        assertThat(blockHeader.getWitness(), equalTo("clayop"));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.database.DatabaseApi#getOpsInBlock(CommunicationHandler, long, boolean)}
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
        final List<AppliedOperation> appliedOperationsOnlyVirtual = DatabaseApi.getOpsInBlock(COMMUNICATION_HANDLER,
                13138393, true);

        assertThat(appliedOperationsOnlyVirtual.size(), equalTo(6));
        assertThat(appliedOperationsOnlyVirtual.get(0).getOpInTrx(), equalTo(1));
        assertThat(appliedOperationsOnlyVirtual.get(0).getTrxInBlock(), equalTo(41));
        assertThat(appliedOperationsOnlyVirtual.get(0).getVirtualOp(), equalTo(0L));
        assertThat(appliedOperationsOnlyVirtual.get(0).getOp(), instanceOf(ProducerRewardOperation.class));

        final List<AppliedOperation> appliedOperations = DatabaseApi.getOpsInBlock(COMMUNICATION_HANDLER, 13138393,
                false);

        assertThat(appliedOperations.size(), equalTo(51));
        assertThat(appliedOperations.get(1).getOpInTrx(), equalTo(0));
        assertThat(appliedOperations.get(1).getTrxInBlock(), equalTo(1));
        assertThat(appliedOperations.get(1).getVirtualOp(), equalTo(0L));
        assertThat(appliedOperations.get(1).getOp(), instanceOf(CommentOperation.class));
    }

}
