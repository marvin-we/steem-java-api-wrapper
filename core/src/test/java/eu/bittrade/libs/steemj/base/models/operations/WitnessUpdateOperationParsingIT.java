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
 * This class tests if the {@link WitnessUpdateOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessUpdateOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5717839;
    private static final int TRANSACTION_INDEX = 3;
    private static final int OPERATION_INDEX = 0;
    private static final String WITNESS_NAME = "glitterpig";
    private static final double FEE_AMOUNT = 0.0;
    private static final String URL = "https://steemit.com/witness-category/@glitterpig/witness-glitterpig-because-everything-is-better-with-a-bit-of-bling";
    private static final double ACCOUNT_CREATION_FEE = 3.0;

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
        SignedBlockWithInfo blockContainingWitnessUpdateOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation witnessUpdateOperation = blockContainingWitnessUpdateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(witnessUpdateOperation, instanceOf(WitnessUpdateOperation.class));
        assertThat(((WitnessUpdateOperation) witnessUpdateOperation).getOwner().getName(), equalTo(WITNESS_NAME));
        assertThat(((WitnessUpdateOperation) witnessUpdateOperation).getFee().toReal(), equalTo(FEE_AMOUNT));
        assertThat(((WitnessUpdateOperation) witnessUpdateOperation).getUrl().toString(), equalTo(URL));
        assertThat(((WitnessUpdateOperation) witnessUpdateOperation).getProperties().getAccountCreationFee().toReal(),
                equalTo(ACCOUNT_CREATION_FEE));
    }
}
