package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.net.URL;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalIntegrationTest;
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
public class WitnessUpdateOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5717839;
    private static final int TRANSACTION_INDEX = 3;
    private static final int OPERATION_INDEX = 0;
    private static final String WITNESS_NAME = "glitterpig";
    private static final double FEE_AMOUNT = 0.0;
    private static final String URL = "https://steemit.com/witness-category/@glitterpig/witness-glitterpig-because-everything-is-better-with-a-bit-of-bling";
    private static final double ACCOUNT_CREATION_FEE = 3.0;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dcebc80457010b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4064657a31333"
            + "33702e5127bd7d41f01d9981a5a2c2524a60706040bbec8838a39719550ea25071000881300000000000003535445454d0000000001000000010000000000000003535445454"
            + "d000000011c2125f9ad6d2a0a9b5f75ee042f71d44029f361df17be8da017f784b75ebe33152404af3e11c1e2ed853e0e85a58fc9cea4cc9f2d17803766edd5d917a9b21081";

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
        setupIntegrationTestEnvironmentForTransactionalTests();

        PublicKey blockSigningKey = new PublicKey("STM6dNhJF7K7MnVvrjvb9x6B6FP5ztr4pkq9JXyzG9PQHdhsYeLkb");

        Asset fee = new Asset();
        fee.setAmount(1L);
        fee.setSymbol(AssetSymbolType.STEEM);

        AccountName owner = new AccountName("dez1337");

        Asset accountCreationFee = new Asset();
        accountCreationFee.setAmount(5000L);
        accountCreationFee.setSymbol(AssetSymbolType.STEEM);

        long maximumBlockSize = 65536;
        int sbdInterestRate = 0;

        ChainProperties chainProperties = new ChainProperties(accountCreationFee, maximumBlockSize, sbdInterestRate);

        URL url = new URL("https://steemit.com/@dez1337");

        WitnessUpdateOperation witnessUpdateOperation = new WitnessUpdateOperation(owner, url, blockSigningKey,
                chainProperties, fee);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(witnessUpdateOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
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
