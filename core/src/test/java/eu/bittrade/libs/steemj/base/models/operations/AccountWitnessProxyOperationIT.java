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
import eu.bittrade.libs.steemj.protocol.operations.AccountWitnessProxyOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;

/**
 * Verify the functionality of the "account witness proxy operation" under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountWitnessProxyOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcecc80457010d0764657a313"
            + "3333706737465656d6a00011b0272a590d9302a4b49ab85683ec714fc85c8c4da928770185301686"
            + "7fb834f6e4d28fea91a6e6f00690b8ac1c54fbc3db4d7c1d798ee66a638a431ab87640b0a";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce8c80457010d0764"
            + "657a3133333706737465656d6a00011c058df1390432ddf4d54e7f1d847bd304946926184bcc1550f"
            + "daca71c1f6022752ee1a18e5d00a59cef16d320c19e582ae29ac3e42867f7941fc3ba406276f75e";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "account". If no active key is provided or the active key is not valid an
     * Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        AccountName account = new AccountName("dez1337");
        AccountName proxy = new AccountName("steemj");

        AccountWitnessProxyOperation accountWitnessProxyOperation = new AccountWitnessProxyOperation(account, proxy);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(accountWitnessProxyOperation);

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
