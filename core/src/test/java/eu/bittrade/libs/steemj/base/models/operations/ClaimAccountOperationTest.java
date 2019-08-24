package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.BaseTransactionalUT;
import eu.bittrade.libs.steemj.base.models.FutureExtensions;
import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.enums.LegacyAssetSymbolType;
import eu.bittrade.libs.steemj.protocol.operations.ClaimAccountOperation;

public class ClaimAccountOperationTest extends BaseTransactionalUT {

    final String EXPECTED_BYTE_REPRESENTATION = "2a0764657a31333337102700000000000003535445454d000000";
    final String EXPECTED_TRANSACTION_HASH = "fb1798d64cfadf9ac671c74e55f8a3f297947e2e60d58ff13145be4ae41ee17a";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000000000000f68585abf4dce9c80457012a0764657a31333337102700000000000003535445454d00000000";

    private static ClaimAccountOperation claimAccountOperation;

    /**
     * Prepare the environment for this specific test.
     *
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        LegacyAsset fee = new LegacyAsset(10000, LegacyAssetSymbolType.STEEM);
        AccountName creator = new AccountName("dez1337");
        List<FutureExtensions> extensions = new ArrayList<>();

        claimAccountOperation = new ClaimAccountOperation(fee, creator, extensions);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(claimAccountOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                                                  operations, null);
        signedTransaction.sign();
    }

    @Override
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                   CryptoUtils.HEX.encode(claimAccountOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Override
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("The serialized transaction should look like expected.",
                   CryptoUtils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                   CryptoUtils.HEX.encode(Sha256Hash.of(signedTransaction.toByteArray()).getBytes()),
                   equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
