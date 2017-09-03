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
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "witness update operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessUpdateOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5717839;
    private static final int TRANSACTION_INDEX = 3;
    private static final int OPERATION_INDEX = 0;
    private static final String WITNESS_NAME = "glitterpig";
    private static final double FEE_AMOUNT = 0.0;
    private static final double ACCOUNT_CREATION_FEE = 3.0;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c80457010b0764657a313333371c68747470733a2f2f737465"
            + "656d69742e636f6d2f4064657a3133333702e5127bd7d41f01d9981a5a2c2524a60706040bbec8838a39719550ea25071000881300000"
            + "000000003535445454d0000000001000000000000000000000003535445454d000000011c247941168ef8c2a1407bbf3eba8163d95ef5"
            + "babcf02993bb7b01c5c21af0db5934d993c7f4e8f464e0d88c850310bb2a52a50c1f72f54a58e9240cad53940108";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "account". If no active key is provided or the active key is not valid an
     * Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        WitnessUpdateOperation witnessUpdateOperation = new WitnessUpdateOperation();
        witnessUpdateOperation
                .setBlockSigningKey(new PublicKey("STM6dNhJF7K7MnVvrjvb9x6B6FP5ztr4pkq9JXyzG9PQHdhsYeLkb"));

        Asset fee = new Asset();
        fee.setAmount(0L);
        fee.setSymbol(AssetSymbolType.STEEM);
        witnessUpdateOperation.setFee(fee);
        witnessUpdateOperation.setOwner(new AccountName("dez1337"));

        ChainProperties chainProperties = new ChainProperties();
        Asset accountCreationFee = new Asset();
        accountCreationFee.setAmount(5000L);
        accountCreationFee.setSymbol(AssetSymbolType.STEEM);
        chainProperties.setAccountCreationFee(accountCreationFee);
        chainProperties.setMaximumBlockSize(65536);
        chainProperties.setSdbInterestRate(0);

        witnessUpdateOperation.setProperties(chainProperties);
        witnessUpdateOperation.setUrl("https://steemit.com/@dez1337");

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(witnessUpdateOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingWitnessUpdateOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation witnessUpdateOperation = blockContainingWitnessUpdateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(witnessUpdateOperation, instanceOf(WitnessUpdateOperation.class));
        assertThat(((WitnessUpdateOperation) witnessUpdateOperation).getOwner().getAccountName(),
                equalTo(WITNESS_NAME));
        assertThat(((WitnessUpdateOperation) witnessUpdateOperation).getFee().getAmount(), equalTo(FEE_AMOUNT));
        assertThat(
                ((WitnessUpdateOperation) witnessUpdateOperation).getProperties().getAccountCreationFee().getAmount(),
                equalTo(ACCOUNT_CREATION_FEE));
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
