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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.BaseTransactionalUT;
import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;
import eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.protocol.operations.ClaimRewardBalanceOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;

/**
 * Test the transformation of the {@link ClaimRewardBalanceOperation}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ClaimRewardBalanceOperationTest extends BaseTransactionalUT {
    final String EXPECTED_BYTE_REPRESENTATION = "2706737465656d6a020000000000000003535445454d00000100000000000"
            + "000035342440000000003000000000000000656455354530000";
    final String EXPECTED_TRANSACTION_HASH = "6534a28421dda4ad5790f955ba72e30bee35c5838bc842bca07038fbe6fb0b09";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dce7c80457012706737465656d6a020000000000000003535445454d00000100000000000000"
            + "03534244000000000300000000000000065645535453000000";

    private static ClaimRewardBalanceOperation claimRewardBalanceOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        AccountName account = new AccountName("steemj");

        Asset sbdReward = new Asset(1L, AssetSymbolType.SBD);
        Asset steemReward = new Asset(2L, AssetSymbolType.STEEM);
        Asset vestsReward = new Asset(3L, AssetSymbolType.VESTS);

        claimRewardBalanceOperation = new ClaimRewardBalanceOperation(account, steemReward, sbdReward, vestsReward);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(claimRewardBalanceOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
        signedTransaction.sign();
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                CryptoUtils.HEX.encode(claimRewardBalanceOperation.toByteArray()),
                equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Override
    @Test
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("The serialized transaction should look like expected.",
                CryptoUtils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                CryptoUtils.HEX.encode(Sha256Hash.of(signedTransaction.toByteArray()).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
