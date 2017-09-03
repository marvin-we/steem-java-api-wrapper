package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseUnitTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test a Steem "comment operation" and verify the results against the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperationTest extends BaseUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "010764657a3133333730737465656d2d6a6176612d6170692d76302d322d3"
            + "02d6861732d6265656e2d72656c65617365642d7570646174652d360764657a313333373372652d737465656d2d6a61"
            + "76612d6170692d76302d322d302d6861732d6265656e2d72656c65617365642d7570646174652d36012d0b546573742"
            + "0537465656d4a027b7d";
    final String EXPECTED_TRANSACTION_HASH = "543812b6ad1a913877af025deea393b8f8aa42dd50cfc4dc0771dd047a15ee0f";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "00000000000000000000000000000000000000000000000000000000"
            + "00000000f68585abf4dcecc8045701010764657a3133333730737465656d2d6a6176612d6170692d76302d322d302d68"
            + "61732d6265656e2d72656c65617365642d7570646174652d360764657a313333373372652d737465656d2d6a6176612d"
            + "6170692d76302d322d302d6861732d6265656e2d72656c65617365642d7570646174652d36012d0b5465737420537465"
            + "656d4a027b7d00";

    private static CommentOperation commentOperation;

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironment();

        commentOperation = new CommentOperation();
        commentOperation.setAuthor(new AccountName("dez1337"));
        commentOperation.setBody("Test SteemJ");
        commentOperation.setJsonMetadata("{}");
        commentOperation.setParentAuthor(new AccountName("dez1337"));
        commentOperation.setParentPermlink("steem-java-api-v0-2-0-has-been-released-update-6");
        commentOperation.setPermlink("re-steem-java-api-v0-2-0-has-been-released-update-6");
        commentOperation.setTitle("-");

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOperation);

        transaction.setOperations(operations);
    }

    @Test
    public void testCommentOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(commentOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testCommentOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        transaction.sign();

        assertThat("The serialized transaction should look like expected.", Utils.HEX.encode(transaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() {

    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() {

    }
}
