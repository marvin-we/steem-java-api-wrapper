package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class tests if the {@link CustomJsonOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomJsonOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 14341388;
    private static final int TRANSACTION_INDEX = 22;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ID = "follow";
    private static final String EXPECTED_ACCOUNT = "cryptoriddler";
    private static final String EXPECTED_JSON = "[\"follow\", {\"what\": [], \"follower\": \"cryptoriddler\", "
            + "\"following\": \"whatissteem\"}]";

    /**
     * Prepare all required fields used by this test class.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
    }

    @Override
    @Test
    @Category({ IntegrationTest.class })
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        SignedBlockWithInfo blockContainingCustomJsonOperationOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation customJsonOperation = blockContainingCustomJsonOperationOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(customJsonOperation, instanceOf(CustomJsonOperation.class));
        assertThat(((CustomJsonOperation) customJsonOperation).getId(), equalTo(EXPECTED_ID));
        assertThat(((CustomJsonOperation) customJsonOperation).getRequiredPostingAuths().get(0).getName(),
                equalTo(EXPECTED_ACCOUNT));
        assertThat(((CustomJsonOperation) customJsonOperation).getJson(), equalTo(EXPECTED_JSON));
    }
}
