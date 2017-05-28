package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.Block;
import eu.bittrade.libs.steem.api.wrapper.models.PublicKey;

/**
 * Verify the functionality of the "account update operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountUpdateOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5681154;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ACCOUNT = "virekolosa";
    private static final PublicKey EXPECTED_PUBLIC_KEY = new PublicKey(
            "STM5PcXipEAThkBhkXawSqL1mqTVU9iRNLasAw9sbYnkRApAxbTWR");
    private static final int EXPECTED_WEIGHT_THRESHOLD = 0;

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingAccountUpdateOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation accountUpdateOperation = blockContainingAccountUpdateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(accountUpdateOperation, instanceOf(AccountUpdateOperation.class));
        assertThat(((AccountUpdateOperation) accountUpdateOperation).getAccount().getAccountName().toString(),
                equalTo(EXPECTED_ACCOUNT));
        System.out.println(((PublicKey) (((AccountUpdateOperation) accountUpdateOperation).getActive().getKeyAuths()
                .keySet().toArray())[0]).getAddressFromPublicKey());
        assertThat(((AccountUpdateOperation) accountUpdateOperation).getActive().getKeyAuths().get(EXPECTED_PUBLIC_KEY),
                equalTo(EXPECTED_WEIGHT_THRESHOLD));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() {
        // TODO: Verify a Transaction containing a vote operation against the
        // api.
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() {
        // TODO: Use an API call to get the hex value of the transaction.
    }

}
