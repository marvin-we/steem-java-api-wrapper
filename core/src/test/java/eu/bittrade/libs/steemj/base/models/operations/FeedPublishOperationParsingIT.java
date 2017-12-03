package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class tests if the {@link FeedPublishOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FeedPublishOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5716934;
    private static final int TRANSACTION_INDEX = 4;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_PUBLISHER = "steve-walschot";
    private static Price EXPECTED_PRICE;

    /**
     * Prepare all required fields used by this test class.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        // Set expected objects.
        Asset expectedBase = new Asset(283, AssetSymbolType.SBD);
        Asset expectedQuote = new Asset(1000, AssetSymbolType.STEEM);

        EXPECTED_PRICE = new Price(expectedBase, expectedQuote);
    }

    @Override
    @Test
    @Category({ IntegrationTest.class })
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        SignedBlockWithInfo blockContainingFeedPublishOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation feedPublishOperation = blockContainingFeedPublishOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations().get(OPERATION_INDEX);

        assertThat(feedPublishOperation, instanceOf(FeedPublishOperation.class));
        assertThat(((FeedPublishOperation) feedPublishOperation).getPublisher().getName(), equalTo(EXPECTED_PUBLISHER));
        assertThat(((FeedPublishOperation) feedPublishOperation).getExchangeRate(), equalTo(EXPECTED_PRICE));
    }
}
