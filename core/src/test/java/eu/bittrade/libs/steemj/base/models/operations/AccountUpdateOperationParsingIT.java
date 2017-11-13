package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class tests if the {@link AccountUpdateOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountUpdateOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5681154;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ACCOUNT = "virekolosa";
    private static final PublicKey EXPECTED_PUBLIC_KEY = new PublicKey(
            SteemJConfig.getInstance().getAddressPrefix().name().toUpperCase()
                    + "5PcXipEAThkBhkXawSqL1mqTVU9iRNLasAw9sbYnkRApAxbTWR");
    private static final int EXPECTED_WEIGHT_THRESHOLD = 0;

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
        SignedBlockWithInfo blockContainingAccountUpdateOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation accountUpdateOperation = blockContainingAccountUpdateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(accountUpdateOperation, instanceOf(AccountUpdateOperation.class));
        assertThat(((AccountUpdateOperation) accountUpdateOperation).getAccount().getName().toString(),
                equalTo(EXPECTED_ACCOUNT));
        assertThat(((AccountUpdateOperation) accountUpdateOperation).getActive().getKeyAuths().get(EXPECTED_PUBLIC_KEY),
                equalTo(EXPECTED_WEIGHT_THRESHOLD));
    }
}
