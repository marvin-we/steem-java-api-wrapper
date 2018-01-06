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
package eu.bittrade.libs.steemj.plugins.apis.block;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.joou.UInteger;
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
import eu.bittrade.libs.steemj.plugins.apis.block.models.ExtendedSignedBlock;
import eu.bittrade.libs.steemj.plugins.apis.block.models.GetBlockArgs;
import eu.bittrade.libs.steemj.plugins.apis.block.models.GetBlockHeaderArgs;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.BlockHeader;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.plugins.apis.block.BlockApi BlockApi}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlockApiIT extends BaseIT {
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
     * {@link eu.bittrade.libs.steemj.plugins.apis.block.BlockApi#getBlock(CommunicationHandler, GetBlockArgs )}
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
     * {@link eu.bittrade.libs.steemj.plugins.apis.block.BlockApi#getBlockHeader(CommunicationHandler, GetBlockHeaderArgs)}
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
        // TODO: Check also null case of optional
        final BlockHeader blockHeader = BlockApi
                .getBlockHeader(COMMUNICATION_HANDLER, new GetBlockHeaderArgs(UInteger.valueOf(13339001))).getHeader()
                .get();

        assertThat(blockHeader.getTimestamp().getDateTime(), equalTo("2017-07-02T19:15:06"));
        assertThat(blockHeader.getWitness(), equalTo(new AccountName("clayop")));
    }
}
