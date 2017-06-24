package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.BaseUnitTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.base.models.operations.WitnessUpdateOperation;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test a Steem "witness update operation" and verify the results against the
 * api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessUpdateOperationTest extends BaseUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "0b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4"
            + "064657a3133333703f4a7be25c7664b349b34c16ffc9c9ddcd44d02dd732fa789afd2f5d05c8a70a588130000000000"
            + "0003535445454d0000000001000000000000000000000003535445454d0000";
    final String EXPECTED_TRANSACTION_HASH = "5d01935dc58925f39d156fd26160049d81a4a4d669d29183150719086a296b0a";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dceac80457010b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4064"
            + "657a3133333703f4a7be25c7664b349b34c16ffc9c9ddcd44d02dd732fa789afd2f5d05c8a70a588130000000000000"
            + "3535445454d0000000001000000000000000000000003535445454d000000";

    private static WitnessUpdateOperation witnessUpdateOperation;

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironment();

        witnessUpdateOperation = new WitnessUpdateOperation();
        witnessUpdateOperation
                .setBlockSigningKey(new PublicKey("STM8gyvJtYyv5ZbT2ZxbAtgufQ5ovV2bq6EQp4YDTzQuSwyg7Ckry"));

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
    }

    @Test
    public void testWitnessUpdateOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(witnessUpdateOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testWitnessUpdateOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        transaction.sign();

        assertThat("The serialized transaction should look like expected.", Utils.HEX.encode(transaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
