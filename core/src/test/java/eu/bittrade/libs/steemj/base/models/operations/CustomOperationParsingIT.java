package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.joou.UShort;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class tests if the {@link CustomOperation} can be parsed successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 12123047;
    private static final int TRANSACTION_INDEX = 7;
    private static final int OPERATION_INDEX = 1;
    private static final int EXPECTED_ID = 0;
    private static final String EXPECTED_ACCOUNT = "blocktrades";
    private static final String EXPECTED_DATA = "5f8ecb9276412de9";

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
        SignedBlockWithInfo blockContainingCustomOperationOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation customOperation = blockContainingCustomOperationOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations().get(OPERATION_INDEX);

        assertThat(customOperation, instanceOf(CustomOperation.class));
        assertThat(((CustomOperation) customOperation).getId(), equalTo(UShort.valueOf(EXPECTED_ID)));
        assertThat(((CustomOperation) customOperation).getRequiredAuths().get(0).getName(), equalTo(EXPECTED_ACCOUNT));
        assertThat(((CustomOperation) customOperation).getData(), equalTo(EXPECTED_DATA));
    }
}
