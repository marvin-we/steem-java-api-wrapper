package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.BaseTransactionalUT;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transformation of the {@link WitnessUpdateOperation}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessUpdateOperationTest extends BaseTransactionalUT {
    final String EXPECTED_BYTE_REPRESENTATION = "0b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4"
            + "064657a3133333702e5127bd7d41f01d9981a5a2c2524a60706040bbec8838a39719550ea2507100088130000000000"
            + "0003535445454d0000000001000000010000000000000003535445454d0000";
    final String EXPECTED_TRANSACTION_HASH = "32072376b387b4b22b9bd23ca487be12341a48646a8c6d68812b2a25140c524b";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dceac80457010b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4064"
            + "657a3133333702e5127bd7d41f01d9981a5a2c2524a60706040bbec8838a39719550ea2507100088130000000000000"
            + "3535445454d0000000001000000010000000000000003535445454d000000";

    private static WitnessUpdateOperation witnessUpdateOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        PublicKey blockSigningKey = new PublicKey(SteemJConfig.getInstance().getAddressPrefix().name().toUpperCase()
                + "6dNhJF7K7MnVvrjvb9x6B6FP5ztr4pkq9JXyzG9PQHdhsYeLkb");

        Asset fee = new Asset(1L, AssetSymbolType.STEEM);

        AccountName owner = new AccountName("dez1337");

        Asset accountCreationFee = new Asset(5000L, AssetSymbolType.STEEM);

        long maximumBlockSize = 65536;
        int sbdInterestRate = 0;

        ChainProperties chainProperties = new ChainProperties(accountCreationFee, maximumBlockSize, sbdInterestRate);

        URL url = new URL("https://steemit.com/@dez1337");

        witnessUpdateOperation = new WitnessUpdateOperation(owner, url, blockSigningKey, chainProperties, fee);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(witnessUpdateOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
        signedTransaction.sign();
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                CryptoUtils.HEX.encode(witnessUpdateOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
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
