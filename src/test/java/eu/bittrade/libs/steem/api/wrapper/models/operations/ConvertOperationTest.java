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
import eu.bittrade.libs.steem.api.wrapper.models.Transaction;

/**
 * Test a Steem "comment operation" and verify the results against the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ConvertOperationTest extends BaseTest {
    final String EXPECTED_BYTE_REPRESENTATION = "080764657a313333373905000001000000000000000353424400000000";
    final String EXPECTED_TRANSACTION_HASH = "c17ce69d8d6acc37912e9f6ea5a5c89b9303086df684d0590154c40df75b82e1";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000"
            + "000000000000f68585abf4dce7c8045701080764657a31333337390500000100000000000000035342440000000000";

    private static ConvertOperation convertOperation;
    private static Transaction convertOperationTransaction;

    @BeforeClass
    public static void setup() throws Exception {
        convertOperation = new ConvertOperation();

        Asset amount = new Asset();
        amount.setAmount(1L);
        amount.setSymbol(AssetSymbolType.SBD);

        convertOperation.setAmount(amount);
        convertOperation.setOwner(new AccountName("dez1337"));
        convertOperation.setRequestId(1337L);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(convertOperation);

        convertOperationTransaction = new Transaction();
        convertOperationTransaction.setExpirationDate(EXPIRATION_DATE);
        convertOperationTransaction.setRefBlockNum(REF_BLOCK_NUM);
        convertOperationTransaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        convertOperationTransaction.setOperations(operations);
    }

    @Test
    public void testConvertOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(convertOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testConvertOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        convertOperationTransaction.sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(convertOperationTransaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX
                        .encode(Sha256Hash.wrap(Sha256Hash.hash(convertOperationTransaction.toByteArray())).getBytes()),
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
