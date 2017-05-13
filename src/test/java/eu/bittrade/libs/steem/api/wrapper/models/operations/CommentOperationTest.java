package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Transaction;

/**
 * Test a Steem "comment operation" and verify the results against the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperationTest extends BaseTest {
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
    private static Transaction commentOperationTransaction;

    @BeforeClass
    public static void setup() throws Exception {
        commentOperation = new CommentOperation();
        commentOperation.setAuthor(new AccountName("dez1337"));
        commentOperation.setBody("Test SteemJ");
        commentOperation.setJsonMetadata("{}");
        commentOperation.setParentAuthor(new AccountName("dez1337"));
        commentOperation.setParentPermlink("steem-java-api-v0-2-0-has-been-released-update-6");
        commentOperation.setPermlink("re-steem-java-api-v0-2-0-has-been-released-update-6");
        commentOperation.setTitle("-");

        Operation[] operations = { commentOperation };

        commentOperationTransaction = new Transaction();
        commentOperationTransaction.setExpirationDate(EXPIRATION_DATE);
        commentOperationTransaction.setRefBlockNum(REF_BLOCK_NUM);
        commentOperationTransaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        commentOperationTransaction.setOperations(operations);
    }

    @Test
    public void testcommentOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(commentOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testcommentOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        commentOperationTransaction.sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(commentOperationTransaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX
                        .encode(Sha256Hash.wrap(Sha256Hash.hash(commentOperationTransaction.toByteArray())).getBytes()),
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
