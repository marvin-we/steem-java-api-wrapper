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
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.operations.Operation;
import eu.bittrade.libs.steemj.protocol.operations.VoteOperation;

/**
 * Test the transformation of the {@link VoteOperation}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VoteOperationTest extends BaseTransactionalUT {
    final String EXPECTED_BYTE_REPRESENTATION = "000764657a313333370764657a3133333728737465656d6a2d76302d322d342"
            + "d6861732d6265656e2d72656c65617365642d7570646174652d39e803";
    final String EXPECTED_TRANSACTION_HASH = "2c8d2c29eb0ee309df9de900fc65b68f9492a5f54f6c6a6f7a94360e585d5348";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "00000000000000000000000000000000000000000000000000000000"
            + "00000000f68585abf4dcefc8045701000764657a313333370764657a3133333728737465656d6a2d76302d322d342d68"
            + "61732d6265656e2d72656c65617365642d7570646174652d39e80300";

    private static VoteOperation voteOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        AccountName author = new AccountName("dez1337");
        Permlink permlink = new Permlink("steemj-v0-2-4-has-been-released-update-9");
        AccountName voter = new AccountName("dez1337");
        short weight = 1000;

        voteOperation = new VoteOperation(author, voter, permlink, weight);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
        signedTransaction.sign();
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                CryptoUtils.HEX.encode(voteOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
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
