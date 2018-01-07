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
package eu.bittrade.libs.steemj.base.models.operations.virtual;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.math.BigDecimal;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.AppliedOperation;
import eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.protocol.operations.Operation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.ProducerRewardOperation;

/**
 * Test that the {@link ProducerRewardOperation} can be parsed.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ProducerRewardOperationIT extends BaseITForOperationParsing {
    private static final int BLOCK_NUMBER_CONTAINING_OPERATION = 16212111;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_PRODUCER = "xeldal";
    private static final AssetSymbolType EXPECTED_VESTS_SYMBOL = AssetSymbolType.VESTS;
    private static final BigDecimal EXPECTED_VESTS_VALUE_REAL = BigDecimal.valueOf(390.97665);
    private static final long EXPECTED_VESTS_VALUE = 390976650L;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
    }

    @Override
    @Test
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        List<AppliedOperation> operationsInBlock = steemJ.getOpsInBlock(BLOCK_NUMBER_CONTAINING_OPERATION, true);

        Operation producerRewardOperation = operationsInBlock.get(OPERATION_INDEX).getOp();

        assertThat(producerRewardOperation, instanceOf(ProducerRewardOperation.class));

        assertThat(((ProducerRewardOperation) producerRewardOperation).getProducer().getName(),
                equalTo(EXPECTED_PRODUCER));
        assertThat(((ProducerRewardOperation) producerRewardOperation).getVestingShares().getSymbol(),
                equalTo(EXPECTED_VESTS_SYMBOL));
        assertThat(((ProducerRewardOperation) producerRewardOperation).getVestingShares().toReal(),
                equalTo(EXPECTED_VESTS_VALUE_REAL));
        assertThat(((ProducerRewardOperation) producerRewardOperation).getVestingShares().getAmount(),
                equalTo(EXPECTED_VESTS_VALUE));
    }
}
