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

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.operations.EscrowDisputeOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;

/**
 * Verify the functionality of the "escrow dispute operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowDisputeOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c80457011c0764657a313333370764657a"
            + "3133333706737465656d6a0764657a313333372200000000011c2deb8ab73f8e68ad02b700291a237e8dcd918d54b"
            + "0259ba51d887de8726c653f306f27c86f76891c070a84f1979935afdba31ed2a08a896b73badfc4b1050b16";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce7c80457011c0764657a3133333707646"
            + "57a3133333706737465656d6a0764657a313333372200000000011c4f2e8b76193d904d9b7397beaf4459d3d0c7c7958cf"
            + "44df7b827fa38072e19753b74932131ca5c9d0e7f3470adb2413274f460ae17ea05e415d556647c299b7e";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "from" account. If no active key is provided or the active key is not
     * valid an Exception will be thrown. The private key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        AccountName agent = new AccountName("steemj");
        AccountName from = new AccountName("dez1337");
        AccountName to = new AccountName("dez1337");
        AccountName who = new AccountName("dez1337");
        int escrowId = 34;

        EscrowDisputeOperation escrowDisputeOperation = new EscrowDisputeOperation(from, to, agent, escrowId, who);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(escrowDisputeOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
        signedTransaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(signedTransaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        if (TEST_ENDPOINT.equals(TESTNET_ENDPOINT_IDENTIFIER)) {
            assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX_TESTNET));
        } else {
            assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX));
        }
    }
}
