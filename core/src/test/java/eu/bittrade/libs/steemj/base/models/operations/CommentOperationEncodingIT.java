package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;

/**
 * Test if the "comment operation" can handle different encoding types under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperationEncodingIT extends BaseTransactionVerificationIT {
    private static final Charset ORIGINAL_CHARSET_BEFORE_TEST = SteemJConfig.getInstance().getEncodingCharset();

    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701010764657a3133333728737465656d6a2d76302d322d342d6861732d62656"
            + "56e2d72656c65617365642d7570646174652d390764657a313333372b72652d737465656d6a2d76302d322d342d6861732d6265656e2d72656c65617365642d7"
            + "570646174652d39012dcd095465737420415343494920546573742077697468206d6f7265207468616e20313237206469676974733a204c6f72656d206970737"
            + "56d20646f6c6f722073697420616d65742c20636f6e736574657475722073616469707363696e6720656c6974722c20736564206469616d206e6f6e756d79206"
            + "569726d6f642074656d706f7220696e766964756e74207574206c61626f726520657420646f6c6f7265206d61676e6120616c69717579616d20657261742c207"
            + "36564206469616d20766f6c75707475612e204174207665726f20656f73206574206163637573616d206574206a7573746f2064756f20646f6c6f72657320657"
            + "420656120726562756d2e205374657420636c697461206b6173642067756265726772656e2c206e6f207365612074616b696d6174612073616e6374757320657"
            + "374204c6f72656d20697073756d20646f6c6f722073697420616d65742e204c6f72656d20697073756d20646f6c6f722073697420616d65742c20636f6e73657"
            + "4657475722073616469707363696e6720656c6974722c20736564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e766964756e7420757"
            + "4206c61626f726520657420646f6c6f7265206d61676e6120616c69717579616d20657261742c20736564206469616d20766f6c75707475612e2041742076657"
            + "26f20656f73206574206163637573616d206574206a7573746f2064756f20646f6c6f72657320657420656120726562756d2e205374657420636c697461206b6"
            + "173642067756265726772656e2c206e6f207365612074616b696d6174612073616e6374757320657374204c6f72656d20697073756d20646f6c6f72207369742"
            + "0616d65742e204c6f72656d20697073756d20646f6c6f722073697420616d65742c20636f6e736574657475722073616469707363696e6720656c6974722c207"
            + "36564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e766964756e74207574206c61626f726520657420646f6c6f7265206d61676e612"
            + "0616c69717579616d20657261742c20736564206469616d20766f6c75707475612e204174207665726f20656f73206574206163637573616d206574206a75737"
            + "46f2064756f20646f6c6f72657320657420656120726562756d2e205374657420636c697461206b6173642067756265726772656e2c206e6f207365612074616"
            + "b696d6174612073616e6374757320657374204c6f72656d20697073756d20646f6c6f722073697420616d65742e204475697320617574656d2076656c2065756"
            + "d2069726975726520646f6c6f7220696e2068656e64726572697420696e2076756c7075746174652076656c69742065737365206d6f6c657374696520636f6e7"
            + "365717561742c2076656c20696c6c756d20646f6c6f72652065752066657567696174206e756c6c6120666163696c69736973206174207665726f2065726f732"
            + "0657420616363756d73616e20657420697573746f206f64696f206469676e697373696d2071756920626c616e646974207072616573656e74206c75707461747"
            + "56d207a7a72696c2064656c656e6974206175677565206475697320646f6c6f72652074652066657567616974206e756c6c6120666163696c6973692e204c6f7"
            + "2656d20697073756d20646f6c6f722073697420616d65742c027b7d00011c321bf09771acb494bcdf4f62226126bcef873e8efacf6548fda29a238262551c3d8"
            + "a71fbf9e1d3265eec7fe3db263b1377cdf6348539301b9cc5fc08636a19e9";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce8c8045701010764657a3133333728737465656d6a2d76302d322d342d68617"
            + "32d6265656e2d72656c65617365642d7570646174652d390764657a313333372b72652d737465656d6a2d76302d322d342d6861732d6265656e2d72656c65617"
            + "365642d7570646174652d39012dcd095465737420415343494920546573742077697468206d6f7265207468616e20313237206469676974733a204c6f72656d2"
            + "0697073756d20646f6c6f722073697420616d65742c20636f6e736574657475722073616469707363696e6720656c6974722c20736564206469616d206e6f6e7"
            + "56d79206569726d6f642074656d706f7220696e766964756e74207574206c61626f726520657420646f6c6f7265206d61676e6120616c69717579616d2065726"
            + "1742c20736564206469616d20766f6c75707475612e204174207665726f20656f73206574206163637573616d206574206a7573746f2064756f20646f6c6f726"
            + "57320657420656120726562756d2e205374657420636c697461206b6173642067756265726772656e2c206e6f207365612074616b696d6174612073616e63747"
            + "57320657374204c6f72656d20697073756d20646f6c6f722073697420616d65742e204c6f72656d20697073756d20646f6c6f722073697420616d65742c20636"
            + "f6e736574657475722073616469707363696e6720656c6974722c20736564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e766964756"
            + "e74207574206c61626f726520657420646f6c6f7265206d61676e6120616c69717579616d20657261742c20736564206469616d20766f6c75707475612e20417"
            + "4207665726f20656f73206574206163637573616d206574206a7573746f2064756f20646f6c6f72657320657420656120726562756d2e205374657420636c697"
            + "461206b6173642067756265726772656e2c206e6f207365612074616b696d6174612073616e6374757320657374204c6f72656d20697073756d20646f6c6f722"
            + "073697420616d65742e204c6f72656d20697073756d20646f6c6f722073697420616d65742c20636f6e736574657475722073616469707363696e6720656c697"
            + "4722c20736564206469616d206e6f6e756d79206569726d6f642074656d706f7220696e766964756e74207574206c61626f726520657420646f6c6f7265206d6"
            + "1676e6120616c69717579616d20657261742c20736564206469616d20766f6c75707475612e204174207665726f20656f73206574206163637573616d2065742"
            + "06a7573746f2064756f20646f6c6f72657320657420656120726562756d2e205374657420636c697461206b6173642067756265726772656e2c206e6f2073656"
            + "12074616b696d6174612073616e6374757320657374204c6f72656d20697073756d20646f6c6f722073697420616d65742e204475697320617574656d2076656"
            + "c2065756d2069726975726520646f6c6f7220696e2068656e64726572697420696e2076756c7075746174652076656c69742065737365206d6f6c65737469652"
            + "0636f6e7365717561742c2076656c20696c6c756d20646f6c6f72652065752066657567696174206e756c6c6120666163696c69736973206174207665726f206"
            + "5726f7320657420616363756d73616e20657420697573746f206f64696f206469676e697373696d2071756920626c616e646974207072616573656e74206c757"
            + "0746174756d207a7a72696c2064656c656e6974206175677565206475697320646f6c6f72652074652066657567616974206e756c6c6120666163696c6973692"
            + "e204c6f72656d20697073756d20646f6c6f722073697420616d65742c027b7d00011b33e1d70182db4a15fca39ecd1cf2d07f421359ca2c0bcbf0f2202854ab0"
            + "02d740ddfed35ec650e3132d479aac3cfdf1f074a322e9812308c436ac091755690a6";

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

        SteemJConfig.getInstance().setEncodingCharset(StandardCharsets.US_ASCII);

        AccountName author = new AccountName("dez1337");
        Permlink permlink = new Permlink("re-steemj-v0-2-4-has-been-released-update-9");

        String body = "Test ASCII Test with more than 127 digits: Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod temp"
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

    /**
     * Reset to the original encoding.
     */
    @After
    public void cleanUp() {
        SteemJConfig.getInstance().setEncodingCharset(ORIGINAL_CHARSET_BEFORE_TEST);
    }
}
