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
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.protocol.operations.Operation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.FillTransferFromSavingsOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.ProducerRewardOperation;

/**
 * Test that the {@link ProducerRewardOperation} can be parsed.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FillTransferFromSavingsOperationIT extends BaseITForOperationParsing {
    private static final int BLOCK_NUMBER_CONTAINING_OPERATION = 16021362;
    private static final int OPERATION_INDEX = 4;
    private static final String EXPECTED_FROM = "anonimous";
    private static final AccountName EXPECTED_TO = new AccountName("anonimous");
    private static final String EXPECTED_MEMO = "";
    private static final long EXPECTED_REQUEST_ID = 1506820294L;
    private static final AssetSymbolType EXPECTED_AMOUNT_SYMBOL = AssetSymbolType.SBD;
    private static final BigDecimal EXPECTED_AMOUNT_VALUE_REAL = BigDecimal.valueOf(7.5);
    private static final long EXPECTED_AMOUNT_VALUE = 7500L;

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

        Operation fillTransferFromSavingsOperation = operationsInBlock.get(OPERATION_INDEX).getOp();

        assertThat(fillTransferFromSavingsOperation, instanceOf(FillTransferFromSavingsOperation.class));

        assertThat(((FillTransferFromSavingsOperation) fillTransferFromSavingsOperation).getFrom().getName(),
                equalTo(EXPECTED_FROM));
        assertThat(((FillTransferFromSavingsOperation) fillTransferFromSavingsOperation).getTo(), equalTo(EXPECTED_TO));
        assertThat(((FillTransferFromSavingsOperation) fillTransferFromSavingsOperation).getMemo(),
                equalTo(EXPECTED_MEMO));
        assertThat(((FillTransferFromSavingsOperation) fillTransferFromSavingsOperation).getRequestId(),
                equalTo(EXPECTED_REQUEST_ID));
        assertThat(((FillTransferFromSavingsOperation) fillTransferFromSavingsOperation).getAmount().getSymbol(),
                equalTo(EXPECTED_AMOUNT_SYMBOL));
        assertThat(((FillTransferFromSavingsOperation) fillTransferFromSavingsOperation).getAmount().toReal(),
                equalTo(EXPECTED_AMOUNT_VALUE_REAL));
        assertThat(((FillTransferFromSavingsOperation) fillTransferFromSavingsOperation).getAmount().getAmount(),
                equalTo(EXPECTED_AMOUNT_VALUE));
    }
}
