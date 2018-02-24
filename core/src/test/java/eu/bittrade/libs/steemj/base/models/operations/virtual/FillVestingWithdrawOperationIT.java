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
import eu.bittrade.libs.steemj.protocol.operations.virtual.FillVestingWithdrawOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.ProducerRewardOperation;

/**
 * Test that the {@link ProducerRewardOperation} can be parsed.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FillVestingWithdrawOperationIT extends BaseITForOperationParsing {
    private static final int BLOCK_NUMBER_CONTAINING_OPERATION = 16020888;
    private static final int OPERATION_INDEX = 3;
    private static final String EXPECTED_FROM = "chessmonster";
    private static final AccountName EXPECTED_TO = new AccountName("chessmonster");
    private static final AssetSymbolType EXPECTED_DEPOSIT_SYMBOL = AssetSymbolType.STEEM;
    private static final BigDecimal EXPECTED_DEPOSIT_VALUE_REAL = BigDecimal.valueOf(926.471);
    private static final long EXPECTED_DEPOSIT_VALUE = 926471;
    private static final AssetSymbolType EXPECTED_WITHDRAWN_SYMBOL = AssetSymbolType.VESTS;
    private static final BigDecimal EXPECTED_WITHDRAWN_VALUE_REAL = BigDecimal.valueOf(1907116.401647);
    private static final long EXPECTED_WITHDRAWN_VALUE = 1907116401647L;

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

        Operation fillVestingWithdrawOperation = operationsInBlock.get(OPERATION_INDEX).getOp();

        assertThat(fillVestingWithdrawOperation, instanceOf(FillVestingWithdrawOperation.class));

        assertThat(((FillVestingWithdrawOperation) fillVestingWithdrawOperation).getFromAccount().getName(),
                equalTo(EXPECTED_FROM));
        assertThat(((FillVestingWithdrawOperation) fillVestingWithdrawOperation).getToAccount(), equalTo(EXPECTED_TO));
        assertThat(((FillVestingWithdrawOperation) fillVestingWithdrawOperation).getDeposited().getSymbol(),
                equalTo(EXPECTED_DEPOSIT_SYMBOL));
        assertThat(((FillVestingWithdrawOperation) fillVestingWithdrawOperation).getDeposited().toReal(),
                equalTo(EXPECTED_DEPOSIT_VALUE_REAL));
        assertThat(((FillVestingWithdrawOperation) fillVestingWithdrawOperation).getDeposited().getAmount(),
                equalTo(EXPECTED_DEPOSIT_VALUE));
        assertThat(((FillVestingWithdrawOperation) fillVestingWithdrawOperation).getWithdrawn().getSymbol(),
                equalTo(EXPECTED_WITHDRAWN_SYMBOL));
        assertThat(((FillVestingWithdrawOperation) fillVestingWithdrawOperation).getWithdrawn().toReal(),
                equalTo(EXPECTED_WITHDRAWN_VALUE_REAL));
        assertThat(((FillVestingWithdrawOperation) fillVestingWithdrawOperation).getWithdrawn().getAmount(),
                equalTo(EXPECTED_WITHDRAWN_VALUE));
    }
}
