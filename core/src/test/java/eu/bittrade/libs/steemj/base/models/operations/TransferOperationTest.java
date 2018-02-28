package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.BaseTransactionalUT;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transformation of the {@link TransferOperation}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferOperationTest extends BaseTransactionalUT {
    final String EXPECTED_BYTE_REPRESENTATION_SBD = "020764657a3133333706737465656d6a01000000000000000353424"
            + "4000000000b5465737420537465656d4a";
    final String EXPECTED_BYTE_REPRESENTATION_STEEM = "020764657a3133333706737465656d6a0100000000000000035354454"
            + "54d00000b5465737420537465656d4a";
    final String EXPECTED_TRANSACTION_HASH = "053448f814a70ebdc4fedd79a8f55de832cb4f5f66b42ce405f7c59064e60532";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dce9c8045701020764657a3133333706737465656d6a01000000000000000353424400000000"
            + "0b5465737420537465656d4a00";

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

        Asset sbdAmount = new Asset(1L, AssetSymbolType.SBD);
        Asset steemAmount = new Asset(1L, AssetSymbolType.STEEM);

        AccountName from = new AccountName("dez1337");
        AccountName to = new AccountName("steemj");
        String memo = "Test SteemJ";

        transferOperationSbd = new TransferOperation(from, to, sbdAmount, memo);

        transferOperationSteem = new TransferOperation(from, to, steemAmount, memo);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferOperationSbd);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
        signedTransaction.sign();
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
                CryptoUtils.HEX.encode(transferOperationSbd.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION_SBD));
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                CryptoUtils.HEX.encode(transferOperationSteem.toByteArray()),
                equalTo(EXPECTED_BYTE_REPRESENTATION_STEEM));
    }

    @Override
    @Test
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("The serialized transaction should look like expected.",
                CryptoUtils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                CryptoUtils.HEX.encode(Sha256Hash.of(signedTransaction.toByteArray()).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
