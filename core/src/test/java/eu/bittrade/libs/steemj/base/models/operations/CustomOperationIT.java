package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.joou.UShort;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalIntegrationTest;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseError;

/**
 * Verify the functionality of the "custom operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 12123047;
    private static final int TRANSACTION_INDEX = 7;
    private static final int OPERATION_INDEX = 1;
    private static final int EXPECTED_ID = 0;
    private static final String EXPECTED_ACCOUNT = "blocktrades";
    private static final String EXPECTED_DATA = "5f8ecb9276412de9";
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dc6afce558010f010764657a31333337d810115465"
            + "7374466f72537465656d4a3132332100011c772ec477ef96ee85b7b397e061a3659e2aa49b5c8d6e68a719900079f9715"
            + "71b3b19421af788636c6ecb30e4ac950d63841108bc8dd8de9e8947a94c656f2604";

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
        setupIntegrationTestEnvironmentForTransactionalTests();

        // If the default expiration date for all integration tests
        // (2016-04-06T08:29:27UTC) is used, the transaction can't be verified.
        // As a workaround new date is set that is not that far in the past.
        signedTransaction.setExpirationDate(new TimePointSec("2017-04-06T08:29:27UTC"));

        ArrayList<AccountName> requiredAuths = new ArrayList<>();
        requiredAuths.add(new AccountName("dez1337"));

        short id = 4312;
        String data = "54657374466f72537465656d4a31323321";

        CustomOperation customOperation = new CustomOperation(requiredAuths, id, data);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseError {
        SignedBlockWithInfo blockContainingCustomOperationOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation customOperation = blockContainingCustomOperationOperation.getTransactions().get(TRANSACTION_INDEX)
                .getOperations().get(OPERATION_INDEX);

        assertThat(customOperation, instanceOf(CustomOperation.class));
        assertThat(((CustomOperation) customOperation).getId(), equalTo(UShort.valueOf(EXPECTED_ID)));
        assertThat(((CustomOperation) customOperation).getRequiredAuths().get(0).getName(), equalTo(EXPECTED_ACCOUNT));
        assertThat(((CustomOperation) customOperation).getData(), equalTo(EXPECTED_DATA));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(signedTransaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
