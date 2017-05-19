package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.ChainProperties;
import eu.bittrade.libs.steem.api.wrapper.models.PublicKey;
import eu.bittrade.libs.steem.api.wrapper.models.Transaction;

/**
 * Test a Steem "witness update operation" and verify the results against the
 * api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessUpdateOperationTest extends BaseTest {
    final String EXPECTED_BYTE_REPRESENTATION = "0b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4"
            + "064657a3133333703f4a7be25c7664b349b34c16ffc9c9ddcd44d02dd732fa789afd2f5d05c8a70a588130000000000"
            + "0003535445454d0000000001000000000000000000000003535445454d0000";
    final String EXPECTED_TRANSACTION_HASH = "5d01935dc58925f39d156fd26160049d81a4a4d669d29183150719086a296b0a";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dceac80457010b0764657a313333371c68747470733a2f2f737465656d69742e636f6d2f4064"
            + "657a3133333703f4a7be25c7664b349b34c16ffc9c9ddcd44d02dd732fa789afd2f5d05c8a70a588130000000000000"
            + "3535445454d0000000001000000000000000000000003535445454d000000";

    private static WitnessUpdateOperation witnessUpdateOperation;
    private static Transaction witnessUpdateOperationTransaction;

    @BeforeClass
    public static void setup() throws Exception {
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

        witnessUpdateOperationTransaction = new Transaction();
        witnessUpdateOperationTransaction.setExpirationDate(EXPIRATION_DATE);
        witnessUpdateOperationTransaction.setRefBlockNum(REF_BLOCK_NUM);
        witnessUpdateOperationTransaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        witnessUpdateOperationTransaction.setOperations(operations);
    }

    @Test
    public void testWitnessUpdateOperationOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(witnessUpdateOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testWitnessUpdateOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        witnessUpdateOperationTransaction.sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(witnessUpdateOperationTransaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(
                        Sha256Hash.wrap(Sha256Hash.hash(witnessUpdateOperationTransaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() {

    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() {

    }
}
