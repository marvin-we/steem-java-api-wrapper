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
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.BaseTransactionalUT;
import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.plugins.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.operations.FollowOperation;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;

/**
 * Test the transformation of the {@link CustomJsonOperation}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomJsonOperationTest extends BaseTransactionalUT {
    final String EXPECTED_BYTE_REPRESENTATION = "1200010764657a3133333706666f6c6c6f77465b22666f6c6c6f77222c7b2"
            + "2666f6c6c6f776572223a2264657a31333337222c22666f6c6c6f77696e67223a22737465656d6a222c227768617422"
            + "3a5b22626c6f67225d7d5d";
    final String EXPECTED_TRANSACTION_HASH = "7ad5960642b2a3e8ad10813da8d985db0a2f0063f0fea212f753af02c99192de";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dce9c80457011200010764657a3133333706666f6c6c6f77465b22666f6c6c6f77222c7b2266"
            + "6f6c6c6f776572223a2264657a31333337222c22666f6c6c6f77696e67223a22737465656d6a222c2277686174223a5"
            + "b22626c6f67225d7d5d00";

    private static CustomJsonOperation customJsonOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(new AccountName("dez1337"));

        // Steem does not allow to combine operations that require a posting key
        // with operations that require a higher key, so set the active
        // authorities to null.
        ArrayList<AccountName> requiredActiveAuths = null;

        String id = "follow";
        FollowOperation followOperation = new FollowOperation(new AccountName("dez1337"), new AccountName("steemj"),
                Arrays.asList(FollowType.BLOG));

        customJsonOperation = new CustomJsonOperation(requiredActiveAuths, requiredPostingAuths, id,
                followOperation.toJson());

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
        signedTransaction.sign();
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                CryptoUtils.HEX.encode(customJsonOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
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
