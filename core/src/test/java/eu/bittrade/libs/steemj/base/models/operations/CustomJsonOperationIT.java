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
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "custom json operation" under the use of real
 * api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomJsonOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 14341388;
    private static final int TRANSACTION_INDEX = 22;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ID = "follow";
    private static final String EXPECTED_ACCOUNT = "cryptoriddler";
    private static final String EXPECTED_JSON = "[\"follow\", {\"what\": [], \"follower\": \"cryptoriddler\", \"following\": \"whatissteem\"}]";
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dc69fce558011200010764657a3133333706666f"
            + "6c6c6f77465b22666f6c6c6f77222c7b22666f6c6c6f776572223a2264657a31333337222c22666f6c6c6f77696e672"
            + "23a22737465656d6a222c2277686174223a5b22626c6f67225d7d5d00011b06e63d3c8e756fa028261f045f4609d8a5"
            + "1e8bdcfe1c09a39766d1d3eb3a6f5872ae7d9d0f9a0049f0cac9f557204b978e70913a4062f0689528f3457141b678";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "owner". If no active key is provided or the active key is not valid an
     * Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        // If the default expiration date for all integration tests
        // (2016-04-06T08:29:27UTC) is used, the transaction can't be verified.
        // As a workaround new date is set that is not that far in the past.
        transaction.setExpirationDate(new TimePointSec("2017-04-06T08:29:27UTC"));

        CustomJsonOperation customJsonOperation = new CustomJsonOperation();

        customJsonOperation.setId("follow");
        customJsonOperation
                .setJson("[\"follow\",{\"follower\":\"dez1337\",\"following\":\"steemj\",\"what\":[\"blog\"]}]");
        customJsonOperation.setRequiredAuths(new ArrayList<>());

        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(new AccountName("dez1337"));

        customJsonOperation.setRequiredPostingAuths(requiredPostingAuths);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
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
