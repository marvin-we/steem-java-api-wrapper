package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIntegrationTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;

/**
 * Verify the functionality of the "comment operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperationToPostIT extends BaseIntegrationTest {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcecc8045701010018612d6e65772d706f73742d6861732d6265656e2d626f726e07"
            + "64657a31333337086d61696e2d7461671f41206e657720706f737420686173206265656e20626f726e207469746c6521800154686973206973206e65772"
            + "0706f7374206f6e2074686520626c6f636b636861696e2e205768696368206973207573656420617320616e206578616d706c652e20417320796f752063"
            + "616e207365652062656c6f772c2061206e657720706f737420646f6573206e6f742068617665206120706172656e7420617574686f722e637b227461677"
            + "3223a5b226d61696e2d746167222c226c697465726174757265222c22626c6f67222c2277726974696e67225d2c22696d616765223a5b22225d2c226170"
            + "70223a22737465656d6a222c22666f726d6174223a226d61726b646f776e227d00011c5d1af3babbfecf7bf4872a2076c26f5c5d82b43a58cf059a5d89b"
            + "60a388f3e08715b8c4ab17dfadeef274f06a04f355aae952467ae6ac20db533f5b684dca1ea";

    /**
     * <b>Attention:</b> This test class requires a valid posting key of the
     * used "author". If no posting key is provided or the posting key is not
     * valid an Exception will be thrown. The private key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        CommentOperation commentOperation = new CommentOperation();
        commentOperation.setAuthor(new AccountName("dez1337"));
        commentOperation.setPermlink("main-tag");
        commentOperation.setBody(
                "This is new post on the blockchain. Which is used as an example. As you can see below, a new post does not have a parent author.");
        commentOperation.setParentAuthor(new AccountName(""));
        commentOperation.setJsonMetadata(
                "{\"tags\":[\"main-tag\",\"literature\",\"blog\",\"writing\"],\"image\":[\"\"],\"app\":\"steemj\",\"format\":\"markdown\"}");
        commentOperation.setParentPermlink("a-new-post-has-been-born");
        commentOperation.setTitle("A new post has been born title!");

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
