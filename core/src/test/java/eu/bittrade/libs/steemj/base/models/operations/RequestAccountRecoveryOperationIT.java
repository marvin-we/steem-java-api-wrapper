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
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Authority;
import eu.bittrade.libs.steemj.protocol.PublicKey;
import eu.bittrade.libs.steemj.protocol.operations.Operation;
import eu.bittrade.libs.steemj.protocol.operations.RequestAccountRecoveryOperation;

/**
 * Verify the functionality of the "request account recovery operation" under
 * the use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RequestAccountRecoveryOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce9c8045701180764657a313333"
            + "3706737465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b09d9ea6eede"
            + "c21de6fa4c401000000011c64dca26c66c15e1ae1f9d6f3f2c94b9189ac9e46adc309deda2a8570c253"
            + "492f0dddd864b72a0dc3c48842f46866127c43d4a298644ca70787655aa0281ae779";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dcecc804570118076465"
            + "7a3133333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b09d"
            + "9ea6eedec21de6fa4c401000000011c60feca9ce1caa3fc3ec9754d1e0e8db4cf1410de409c5948b538"
            + "a7aa48ddb22d18ee3dbc98f1a247cfc7671e3069fc31948083fb223839ce7b411e26397532d2";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "recovery account". If no active key is provided or the active key is not
     * valid an Exception will be thrown. The active key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        AccountName accountToRecover = new AccountName("steemj");
        AccountName recoveryAccount = new AccountName("dez1337");

        Authority newOwnerAuthority = new Authority();
        newOwnerAuthority.setAccountAuths(new HashMap<AccountName, Integer>());
        Map<PublicKey, Integer> ownerKeyAuth = new HashMap<>();
        ownerKeyAuth.put(new PublicKey(SteemJConfig.getInstance().getAddressPrefix().name().toUpperCase()
                + "5jYVokmZHdEpwo5oCG3ES2Ca4VYzy6tM8pWWkGdgVnwo2mFLFq"), 1);
        newOwnerAuthority.setKeyAuths(ownerKeyAuth);
        newOwnerAuthority.setWeightThreshold(1);

        // Test Extensions when they are supported.
        RequestAccountRecoveryOperation requestAccountRecoveryOperation = new RequestAccountRecoveryOperation(
                recoveryAccount, accountToRecover, newOwnerAuthority);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(requestAccountRecoveryOperation);

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
