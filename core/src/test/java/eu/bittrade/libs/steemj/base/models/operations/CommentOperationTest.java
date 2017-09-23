package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalUnitTest;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test a Steem "comment operation" and verify the results against the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperationTest extends BaseTransactionalUnitTest {
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
        Permlink permlink = new Permlink("re-steem-java-api-v0-2-0-has-been-released-update-6");
        String body = "Test SteemJ";
        String jsonMetadata = "{}";
        AccountName parentAuthor = new AccountName("dez1337");
        Permlink parentPermlink = new Permlink("steem-java-api-v0-2-0-has-been-released-update-6");
        String title = "-";

        commentOperation = new CommentOperation(parentAuthor, parentPermlink, author, permlink, title, body,
                jsonMetadata);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOperation);

        signedTransaction.setOperations(operations);
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(commentOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Override
    @Test
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(signedTransaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
