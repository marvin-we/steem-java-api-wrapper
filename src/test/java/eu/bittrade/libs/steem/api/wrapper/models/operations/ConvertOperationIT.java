package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Block;

/**
 * Verify the functionality of the "vote operation" under the use of real api
 * calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ConvertOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5764515;
    private static final int TRANSACTION_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_OWNER = "mindhunter";
    private static final Asset EXPECTED_AMOUNT = new Asset();
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dceec8045701080764657a31333337390500"
            + "000100000000000000035342440000000000011b39df7757e8d202e850d45ac9f7de49cce804ed0cb3ace0cbe87"
            + "f34e9be7ee33f4f50c4212e551983a29f6f4827b96432a253400ecef29e468c1b31e33c559f2d";

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

        ConvertOperation convertOperation = new ConvertOperation();

        Asset amount = new Asset();
        amount.setAmount(1L);
        amount.setSymbol(AssetSymbolType.SBD);

        convertOperation.setAmount(amount);
        convertOperation.setOwner(new AccountName("dez1337"));
        convertOperation.setRequestId(1337L);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(convertOperation);

        transaction.setOperations(operations);
        transaction.sign();

        // Set expected objects.
        EXPECTED_AMOUNT.setAmount(24);
        EXPECTED_AMOUNT.setSymbol(AssetSymbolType.SBD);
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockConvertOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation convertOperation = blockConvertOperation.getTransactions().get(TRANSACTION_INDEX).getOperations()
                .get(OPERATION_INDEX);

        assertThat(convertOperation, instanceOf(ConvertOperation.class));
        assertThat(((ConvertOperation) convertOperation).getOwner().getAccountName(), equalTo(EXPECTED_OWNER));
        assertThat(((ConvertOperation) convertOperation).getAmount(), equalTo(EXPECTED_AMOUNT));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemApiWrapper.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemApiWrapper.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
