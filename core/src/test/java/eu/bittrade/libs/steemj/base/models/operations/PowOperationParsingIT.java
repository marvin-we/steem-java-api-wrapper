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
package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.math.BigInteger;

import org.joou.ULong;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.block.models.ExtendedSignedBlock;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.operations.Operation;
import eu.bittrade.libs.steemj.protocol.operations.PowOperation;

/**
 * This class tests if the {@link PowOperation} can be parsed successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class PowOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 3585;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final int EXPECTED_BLOCK_ID = 3584;
    private static final BigInteger EXPECTED_NONCE = new BigInteger("12704523629717466956");
    private static final AccountName EXPECTED_WORKER_ACCOUNT = new AccountName("steemit63");
    private static final long EXPECTED_MAXIMUM_BLOCK_SIZE = 131072L;

    /**
     * Prepare all required fields used by this test class.
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
    @Category({ IntegrationTest.class })
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        ExtendedSignedBlock blockContainingPowOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION).get();

        Operation powOperation = blockContainingPowOperation.getTransactions().get(TRANSACTION_INDEX).getOperations()
                .get(OPERATION_INDEX);

        assertThat(powOperation, instanceOf(PowOperation.class));
        assertThat(((PowOperation) powOperation).getBlockId().getNumberFromHash(), equalTo(EXPECTED_BLOCK_ID));
        assertThat(((PowOperation) powOperation).getNonce(), equalTo(ULong.valueOf(EXPECTED_NONCE)));
        assertThat(((PowOperation) powOperation).getWorkerAccount(), equalTo(EXPECTED_WORKER_ACCOUNT));
        assertThat(((PowOperation) powOperation).getProperties().getMaximumBlockSize(),
                equalTo(EXPECTED_MAXIMUM_BLOCK_SIZE));
        // TODO:
        // Test getWork
    }
}
