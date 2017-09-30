package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalUnitTest;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test a Steem "transfer operation" and verify the results against the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferOperationTest extends BaseTransactionalUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION_SBD = "020764657a313333370764657a31333337010000000000000003534244000000000b5465737420537465656d4a";
    final String EXPECTED_BYTE_REPRESENTATION_STEEM = "020764657a313333370764657a31333337010000000000000003535445454d00000b5465737420537465656d4a";
    final String EXPECTED_TRANSACTION_HASH = "678a0bebf4d619bb89c9aed10d0fef91b7265b2b16344c4d42a05a30eeccd3d0";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "00000000000000000000000000000000000000000000000000000000"
            + "00000000f68585abf4dce7c8045701020764657a313333370764657a31333337010000"
            + "000000000003534244000000000b5465737420537465656d4a00";

    private static TransferOperation transferOperationSbd;
    private static TransferOperation transferOperationSteem;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        Asset sbdAmount = new Asset();
        sbdAmount.setAmount(1L);
        sbdAmount.setSymbol(AssetSymbolType.SBD);

        Asset steemAmount = new Asset();
        steemAmount.setAmount(1L);
        steemAmount.setSymbol(AssetSymbolType.STEEM);

        AccountName from = new AccountName("dez1337");
        AccountName to = new AccountName("steemj");
        String memo = "Test SteemJ";

        transferOperationSbd = new TransferOperation(from, to, sbdAmount, memo);

        transferOperationSteem = new TransferOperation(from, to, steemAmount, memo);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferOperationSbd);

        signedTransaction.setOperations(operations);
    }

    /**
     * Test if the toByteArray method returns the expected byte array.
     * 
     * @throws UnsupportedEncodingException
     *             If something went wrong.
     * @throws SteemInvalidTransactionException
     *             If something went wrong.
     */
    @Test
    public void testSbdTransferOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(transferOperationSbd.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION_SBD));
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(transferOperationSteem.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION_STEEM));
    }

    @Override
    @Test
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(signedTransaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
