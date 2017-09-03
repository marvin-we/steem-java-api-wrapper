package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.BaseUnitTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transformation of a Steem "delete comment operation".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DeleteCommentOperationTest extends BaseUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "1107666f6f626172630a72652d666f6f62617264";
    final String EXPECTED_TRANSACTION_HASH = "44bf9b3ec6aa4430ee7d9da3baf9e4d276758fca7fcef00ad433559d4f54155a";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dce9c80457011107666f6f626172630a72652d666f6f6261726400";

    private static DeleteCommentOperation deleteCommentOperation;

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironment();

        deleteCommentOperation = new DeleteCommentOperation();
        deleteCommentOperation.setAuthor(new AccountName("foobarc"));
        deleteCommentOperation.setPermlink("re-foobard");

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(deleteCommentOperation);

        transaction.setOperations(operations);
    }

    @Test
    public void testDeleteCommentOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(deleteCommentOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testDeleteCommentOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        transaction.sign();

        assertThat("The serialized transaction should look like expected.", Utils.HEX.encode(transaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
