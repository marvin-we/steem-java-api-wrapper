package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * Verify the functionality of the "comment operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701010764657a3133333728737465656d6a2d7630"
            + "2d322d342d6861732d6265656e2d72656c65617365642d7570646174652d390764657a313333372b72652d737465656d6a2d76302"
            + "d322d342d6861732d6265656e2d72656c65617365642d7570646174652d39012db109ec9588eb8595ed9598ec84b8ec9a944c6f72"
            + "656d20697073756d20646f6c6f722073697420616d65742c20636f6e736574657475722073616469707363696e6720656c6974722"
            + "c20736564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e766964756e74207574206c61626f7265206574"
            + "20646f6c6f7265206d61676e6120616c69717579616d20657261742c20736564206469616d20766f6c75707475612e20417420766"
            + "5726f20656f73206574206163637573616d206574206a7573746f2064756f20646f6c6f72657320657420656120726562756d2e20"
            + "5374657420636c697461206b6173642067756265726772656e2c206e6f207365612074616b696d6174612073616e6374757320657"
            + "374204c6f72656d20697073756d20646f6c6f722073697420616d65742e204c6f72656d20697073756d20646f6c6f722073697420"
            + "616d65742c20636f6e736574657475722073616469707363696e6720656c6974722c20736564206469616d206e6f6e756d7920656"
            + "9726d6f642074656d706f7220696e766964756e74207574206c61626f726520657420646f6c6f7265206d61676e6120616c697175"
            + "79616d20657261742c20736564206469616d20766f6c75707475612e204174207665726f20656f73206574206163637573616d206"
            + "574206a7573746f2064756f20646f6c6f72657320657420656120726562756d2e205374657420636c697461206b61736420677562"
            + "65726772656e2c206e6f207365612074616b696d6174612073616e6374757320657374204c6f72656d20697073756d20646f6c6f7"
            + "22073697420616d65742e204c6f72656d20697073756d20646f6c6f722073697420616d65742c20636f6e73657465747572207361"
            + "6469707363696e6720656c6974722c20736564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e766964756"
            + "e74207574206c61626f726520657420646f6c6f7265206d61676e6120616c69717579616d20657261742c20736564206469616d20"
            + "766f6c75707475612e204174207665726f20656f73206574206163637573616d206574206a7573746f2064756f20646f6c6f72657"
            + "320657420656120726562756d2e205374657420636c697461206b6173642067756265726772656e2c206e6f207365612074616b69"
            + "6d6174612073616e6374757320657374204c6f72656d20697073756d20646f6c6f722073697420616d65742e20447569732061757"
            + "4656d2076656c2065756d2069726975726520646f6c6f7220696e2068656e64726572697420696e2076756c707574617465207665"
            + "6c69742065737365206d6f6c657374696520636f6e7365717561742c2076656c20696c6c756d20646f6c6f7265206575206665756"
            + "7696174206e756c6c6120666163696c69736973206174207665726f2065726f7320657420616363756d73616e2065742069757374"
            + "6f206f64696f206469676e697373696d2071756920626c616e646974207072616573656e74206c7570746174756d207a7a72696c2"
            + "064656c656e6974206175677565206475697320646f6c6f72652074652066657567616974206e756c6c6120666163696c6973692e"
            + "204c6f72656d20697073756d20646f6c6f722073697420616d65742c027b7d00011c1c0bd8cae545a7828b777a2ca7b99b24f72a5"
            + "d4190450d304ee7282986f8817d79985119db565e10b925d6872e5eb7862ef03d9b2b28d3b41aa2ebd600dea845";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce8c8045701010764657a3133333728737465656d"
            + "6a2d76302d322d342d6861732d6265656e2d72656c65617365642d7570646174652d390764657a313333372b72652d737465656d6"
            + "a2d76302d322d342d6861732d6265656e2d72656c65617365642d7570646174652d39012db109ec9588eb8595ed9598ec84b8ec9a"
            + "944c6f72656d20697073756d20646f6c6f722073697420616d65742c20636f6e736574657475722073616469707363696e6720656"
            + "c6974722c20736564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e766964756e74207574206c61626f72"
            + "6520657420646f6c6f7265206d61676e6120616c69717579616d20657261742c20736564206469616d20766f6c75707475612e204"
            + "174207665726f20656f73206574206163637573616d206574206a7573746f2064756f20646f6c6f72657320657420656120726562"
            + "756d2e205374657420636c697461206b6173642067756265726772656e2c206e6f207365612074616b696d6174612073616e63747"
            + "57320657374204c6f72656d20697073756d20646f6c6f722073697420616d65742e204c6f72656d20697073756d20646f6c6f7220"
            + "73697420616d65742c20636f6e736574657475722073616469707363696e6720656c6974722c20736564206469616d206e6f6e756"
            + "d79206569726d6f642074656d706f7220696e766964756e74207574206c61626f726520657420646f6c6f7265206d61676e612061"
            + "6c69717579616d20657261742c20736564206469616d20766f6c75707475612e204174207665726f20656f7320657420616363757"
            + "3616d206574206a7573746f2064756f20646f6c6f72657320657420656120726562756d2e205374657420636c697461206b617364"
            + "2067756265726772656e2c206e6f207365612074616b696d6174612073616e6374757320657374204c6f72656d20697073756d206"
            + "46f6c6f722073697420616d65742e204c6f72656d20697073756d20646f6c6f722073697420616d65742c20636f6e736574657475"
            + "722073616469707363696e6720656c6974722c20736564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e7"
            + "66964756e74207574206c61626f726520657420646f6c6f7265206d61676e6120616c69717579616d20657261742c207365642064"
            + "69616d20766f6c75707475612e204174207665726f20656f73206574206163637573616d206574206a7573746f2064756f20646f6"
            + "c6f72657320657420656120726562756d2e205374657420636c697461206b6173642067756265726772656e2c206e6f2073656120"
            + "74616b696d6174612073616e6374757320657374204c6f72656d20697073756d20646f6c6f722073697420616d65742e204475697"
            + "320617574656d2076656c2065756d2069726975726520646f6c6f7220696e2068656e64726572697420696e2076756c7075746174"
            + "652076656c69742065737365206d6f6c657374696520636f6e7365717561742c2076656c20696c6c756d20646f6c6f72652065752"
            + "066657567696174206e756c6c6120666163696c69736973206174207665726f2065726f7320657420616363756d73616e20657420"
            + "697573746f206f64696f206469676e697373696d2071756920626c616e646974207072616573656e74206c7570746174756d207a7"
            + "a72696c2064656c656e6974206175677565206475697320646f6c6f72652074652066657567616974206e756c6c6120666163696c"
            + "6973692e204c6f72656d20697073756d20646f6c6f722073697420616d65742c027b7d00011b29b2f98940ab7ee9239793ee29a48"
            + "fc2a6d567e1e86f8ed6562c7dcc0c2e2c710cb80c1ef99ff39fa4f744601771e18ed5ca0177059b12c6188ce17f442d77e3";

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
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        AccountName author = new AccountName("dez1337");
        Permlink permlink = new Permlink("re-steemj-v0-2-4-has-been-released-update-9");
        String body = "안녕하세요Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod temp"
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
                + "lor sit amet,";
        String jsonMetadata = "{}";
        AccountName parentAuthor = new AccountName("dez1337");
        Permlink parentPermlink = new Permlink("steemj-v0-2-4-has-been-released-update-9");
        String title = "-";

        CommentOperation commentOperation = new CommentOperation(parentAuthor, parentPermlink, author, permlink, title,
                body, jsonMetadata);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(commentOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
        signedTransaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(signedTransaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        if (TEST_ENDPOINT.equals(TESTNET_ENDPOINT_IDENTIFIER)) {
            assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX_TESTNET));
        } else {
            assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX));
        }
    }
}
