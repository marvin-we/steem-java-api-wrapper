package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;

/**
 * This is a copy of the Steem Piston test. Have a look at <a href=
 * "https://github.com/steemit/steem-python/blob/master/tests/steem/test_transactions.py">GitHub</a>
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperationTest extends BaseTest {
    private static final String EXPECTED_BYTE_REPRESENTATION = "f68585abf4dce7c804570101000001610161012dec1f75303030307";
    private static CommentOperation commentOperation;

    @BeforeClass
    public static void setup() {
        StringBuilder bodyBuilder = new StringBuilder();
        for (int i = 0; i <= 2048; i++) {
            bodyBuilder.append(Character.toChars(i));
        }

        commentOperation = new CommentOperation();
        commentOperation.setParentAuthor(new AccountName(""));
        commentOperation.setParentPermlink("");
        commentOperation.setAuthor(new AccountName("a"));
        commentOperation.setPermlink("a");
        commentOperation.setTitle("-");
        commentOperation.setBody(bodyBuilder.toString());
        commentOperation.setJsonMetadata("{}");
    }

    @Test
    public void testCommentOperationToByteArray() throws SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(commentOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testCommentOperationTransaction() {
        // TODO: Implement.
    }
}
