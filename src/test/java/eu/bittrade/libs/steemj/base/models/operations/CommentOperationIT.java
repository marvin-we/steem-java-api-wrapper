package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIntegrationTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "comment operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5688416;
    private static final int TRANSACTION_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_AUTHOR = "ladypenelope1";
    private static final String EXPECTED_JSON_METADATA = "{\"tags\":[\"food\"]}";
    private static final String EXPECTED_TITLE = "";
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701010764657a3133333728737465656d"
            + "6a2d76302d322d342d6861732d6265656e2d72656c65617365642d7570646174652d390764657a313333372b72652d737"
            + "465656d6a2d76302d322d342d6861732d6265656e2d72656c65617365642d7570646174652d39012da2094c6f72656d20"
            + "697073756d20646f6c6f722073697420616d65742c20636f6e736574657475722073616469707363696e6720656c69747"
            + "22c20736564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e766964756e74207574206c61626f"
            + "726520657420646f6c6f7265206d61676e6120616c69717579616d20657261742c20736564206469616d20766f6c75707"
            + "475612e204174207665726f20656f73206574206163637573616d206574206a7573746f2064756f20646f6c6f72657320"
            + "657420656120726562756d2e205374657420636c697461206b6173642067756265726772656e2c206e6f2073656120746"
            + "16b696d6174612073616e6374757320657374204c6f72656d20697073756d20646f6c6f722073697420616d65742e204c"
            + "6f72656d20697073756d20646f6c6f722073697420616d65742c20636f6e736574657475722073616469707363696e672"
            + "0656c6974722c20736564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e766964756e74207574"
            + "206c61626f726520657420646f6c6f7265206d61676e6120616c69717579616d20657261742c20736564206469616d207"
            + "66f6c75707475612e204174207665726f20656f73206574206163637573616d206574206a7573746f2064756f20646f6c"
            + "6f72657320657420656120726562756d2e205374657420636c697461206b6173642067756265726772656e2c206e6f207"
            + "365612074616b696d6174612073616e6374757320657374204c6f72656d20697073756d20646f6c6f722073697420616d"
            + "65742e204c6f72656d20697073756d20646f6c6f722073697420616d65742c20636f6e736574657475722073616469707"
            + "363696e6720656c6974722c20736564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e76696475"
            + "6e74207574206c61626f726520657420646f6c6f7265206d61676e6120616c69717579616d20657261742c20736564206"
            + "469616d20766f6c75707475612e204174207665726f20656f73206574206163637573616d206574206a7573746f206475"
            + "6f20646f6c6f72657320657420656120726562756d2e205374657420636c697461206b6173642067756265726772656e2"
            + "c206e6f207365612074616b696d6174612073616e6374757320657374204c6f72656d20697073756d20646f6c6f722073"
            + "697420616d65742e204475697320617574656d2076656c2065756d2069726975726520646f6c6f7220696e2068656e647"
            + "26572697420696e2076756c7075746174652076656c69742065737365206d6f6c657374696520636f6e7365717561742c"
            + "2076656c20696c6c756d20646f6c6f72652065752066657567696174206e756c6c6120666163696c69736973206174207"
            + "665726f2065726f7320657420616363756d73616e20657420697573746f206f64696f206469676e697373696d20717569"
            + "20626c616e646974207072616573656e74206c7570746174756d207a7a72696c2064656c656e697420617567756520647"
            + "5697320646f6c6f72652074652066657567616974206e756c6c6120666163696c6973692e204c6f72656d20697073756d"
            + "20646f6c6f722073697420616d65742c027b7d00011c0e783409204ae85ad612d8f2d9a57259f800c474fc8c9df257b41"
            + "f7eb5d5c2537a95bc9292304a3af97ad7dc21569cb087e6223b6ef42da28e3b8a71b4dcbb24";

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
        commentOperation.setPermlink("re-steemj-v0-2-4-has-been-released-update-9");

        commentOperation.setBody("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod temp"
                + "or invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et j"
                + "usto duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum do"
                + "lor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempo"
                + "r invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et ju"
                + "sto duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dol"
                + "or sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor"
                + " invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et jus"
                + "to duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolo"
                + "r sit amet. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat"
                + ", vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui "
                + "blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum do"
                + "lor sit amet,");
        commentOperation.setJsonMetadata("{}");
        commentOperation.setParentAuthor(new AccountName("dez1337"));
        commentOperation.setParentPermlink("steemj-v0-2-4-has-been-released-update-9");
        commentOperation.setTitle("-");

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingCommentOperationOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation commentOperation = blockContainingCommentOperationOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations().get(OPERATION_INDEX);

        assertThat(commentOperation, instanceOf(CommentOperation.class));
        assertThat(((CommentOperation) commentOperation).getAuthor().getAccountName(), equalTo(EXPECTED_AUTHOR));
        assertThat(((CommentOperation) commentOperation).getJsonMetadata(), equalTo(EXPECTED_JSON_METADATA));
        assertThat(((CommentOperation) commentOperation).getTitle(), equalTo(EXPECTED_TITLE));
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
