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

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.block.models.ExtendedSignedBlock;
import eu.bittrade.libs.steemj.protocol.operations.DelegateVestingSharesOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;

/**
 * This class tests if the {@link DelegateVestingSharesOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DelegateVestingSharesOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 12614179;
    private static final int TRANSACTION_INDEX = 11;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM_ACCOUNT = "dez1337";
    private static final String EXPECTED_TO_ACCOUNT = "steemj";
    private static final BigDecimal EXPECTED_AMOUNT = BigDecimal.valueOf(4142.872164);

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
        ExtendedSignedBlock blockContainingDelegateVestingSharesOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION).get();

        Operation delegateVestingSharesOperation = blockContainingDelegateVestingSharesOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(delegateVestingSharesOperation, instanceOf(DelegateVestingSharesOperation.class));
        assertThat(((DelegateVestingSharesOperation) delegateVestingSharesOperation).getDelegator().getName(),
                equalTo(EXPECTED_FROM_ACCOUNT));
        assertThat(((DelegateVestingSharesOperation) delegateVestingSharesOperation).getDelegatee().getName(),
                equalTo(EXPECTED_TO_ACCOUNT));
        assertThat(((DelegateVestingSharesOperation) delegateVestingSharesOperation).getVestingShares().toReal(),
                equalTo(EXPECTED_AMOUNT));
    }
}
