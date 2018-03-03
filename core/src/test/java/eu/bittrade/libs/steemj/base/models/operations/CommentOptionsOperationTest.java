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
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transformation of the {@link CommentOptionsOperation}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsOperationTest extends BaseTransactionalUT {
    final String EXPECTED_BYTE_REPRESENTATION = "1303666f6f0f72652d666f6f626172646f6f62617200ca9a3b000000000353424400000000a709000100";
    final String EXPECTED_TRANSACTION_HASH = "045068dd57f3d3b82c1392c25f3601697c7b96e260ca34cef39cca6918ab5f7b";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "000000000000000000000000000000000000000000000000000000000000"
            + "0000f68585abf4dce8c80457011303666f6f0f72652d666f6f626172646f6f62617200ca9a3b000000000353424400000000"
            + "a70900010000";

    private static CommentOptionsOperation commentOptionsOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        AccountName author = new AccountName("foo");
        Permlink permlink = new Permlink("re-foobardoobar");
        boolean allowVotes = false;
        boolean allowCurationRewards = true;
        short percentSteemDollars = (short) 2471;
        Asset maxAcceptedPayout = new Asset(1000000000, AssetSymbolType.SBD);

        commentOptionsOperation = new CommentOptionsOperation(author, permlink, maxAcceptedPayout, (int) percentSteemDollars,
                allowVotes, allowCurationRewards, null);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOptionsOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
        signedTransaction.sign();
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                CryptoUtils.HEX.encode(commentOptionsOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
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
