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
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "feed publish operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FeedPublishOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5716934;
    private static final int TRANSACTION_INDEX = 4;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_PUBLISHER = "steve-walschot";
    private static final Price EXPECTED_PRICE = new Price();
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701070764657a31"
            + "33333773000000000000000353424400000000640000000000000003535445454d000000011b1d2"
            + "64143ac5f04d46e563aae4e657100b45a74380e9afaa5c9148a4ec77c0c3b5ef08414d0c210ed3e"
            + "f9eea860686a40a19863389ee618605a980bdb6d01a42c";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "publisher". If no active key is provided or the active key is not valid
     * an Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        FeedPublishOperation feedPublishOperation = new FeedPublishOperation();
        feedPublishOperation.setPublisher(new AccountName("dez1337"));

        // 1 STEEM = 1.15 SBD
        Asset base = new Asset();
        base.setAmount(115);
        base.setSymbol(AssetSymbolType.SBD);
        Asset quote = new Asset();
        quote.setAmount(100);
        quote.setSymbol(AssetSymbolType.STEEM);

        Price exchangeRate = new Price();
        exchangeRate.setBase(base);
        exchangeRate.setQuote(quote);

        feedPublishOperation.setExchangeRate(exchangeRate);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(feedPublishOperation);

        transaction.setOperations(operations);
        transaction.sign();

        // Set expected objects.
        Asset expectedBase = new Asset();
        expectedBase.setAmount(283);
        expectedBase.setSymbol(AssetSymbolType.SBD);
        Asset expectedQuote = new Asset();
        expectedQuote.setAmount(1000);
        expectedQuote.setSymbol(AssetSymbolType.STEEM);

        EXPECTED_PRICE.setBase(expectedBase);
        EXPECTED_PRICE.setQuote(expectedQuote);
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingFeedPublishOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation feedPublishOperation = blockContainingFeedPublishOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations().get(OPERATION_INDEX);

        assertThat(feedPublishOperation, instanceOf(FeedPublishOperation.class));
        assertThat(((FeedPublishOperation) feedPublishOperation).getPublisher().getAccountName(),
                equalTo(EXPECTED_PUBLISHER));
        assertThat(((FeedPublishOperation) feedPublishOperation).getExchangeRate(), equalTo(EXPECTED_PRICE));
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
