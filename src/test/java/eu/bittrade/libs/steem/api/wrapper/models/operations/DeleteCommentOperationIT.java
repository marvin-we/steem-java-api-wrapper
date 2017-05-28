package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Block;

/**
 * Verify the functionality of the "vote operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DeleteCommentOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5717145;
    private static final int TRANSACTION_INDEX = 4;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_AUTHOR = "mindhunter";
    private static final String EXPECTED_PERMANENT_LINK = "re-mindhunter-re-mindhunter-re-biophil-"
            + "re-mindhunter-why-does-steemit-need-to-reach-a-unanimous-agreement-to-hard-fork-lik"
            + "e-ethereum-to-smoke-out-all-that-blatantly-pre-mined-steem-20161010t184945348z";
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701110764657a313333"
            + "377c72652d72652d627573696e6573737772692d72652d64657a313333372d686f772d746f2d7365637"
            + "572652d796f75722d7765622d736572766963652d776974682d612d76616c69642d73736c2d63657274"
            + "696669636174652d666f722d667265652d706172742d332d32303137303532377431363137303231313"
            + "87a00011b74fb5ec4f498b091172f3c15ee2d442408d7bf226697d7f538f1e8bc6f1b164869e56c65b9"
            + "1ae028417d7ae2b46ffae9e1a6655caa3c8583c8401f0f503848a8";

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

        DeleteCommentOperation deleteCommentOperation = new DeleteCommentOperation();
        deleteCommentOperation.setAuthor(new AccountName("dez1337"));
        deleteCommentOperation.setPermlink(
                "re-re-businesswri-re-dez1337-how-to-secure-your-web-service-with-a-valid-ssl-certificate-for-free-part-3-20170527t161702118z");

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(deleteCommentOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingDeleteCommentOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation deleteCommentOperation = blockContainingDeleteCommentOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(deleteCommentOperation, instanceOf(DeleteCommentOperation.class));
        assertThat(((DeleteCommentOperation) deleteCommentOperation).getAuthor().getAccountName(),
                equalTo(EXPECTED_AUTHOR));
        assertThat(((DeleteCommentOperation) deleteCommentOperation).getPermlink(), equalTo(EXPECTED_PERMANENT_LINK));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemApiWrapper.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemApiWrapper.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
