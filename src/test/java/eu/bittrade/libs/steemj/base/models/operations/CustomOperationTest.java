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
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test a Steem "custom operation" and verify the results against the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomOperationTest extends BaseUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "0f0107666f6f62617263d8101154657374466f72537465656d4a31323321";
    final String EXPECTED_TRANSACTION_HASH = "519f39f1f6a8938d9bd94d62de4300d1685f250443de520b4708a7d2ff1bff10";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dcebc80457010f0107666f6f62617263d8101154657374466f72537465656d4a3132332100";

    private static CustomOperation customOperation;

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironment();

        customOperation = new CustomOperation();

        customOperation.setId(4312);
        customOperation.setData("54657374466f72537465656d4a31323321");

        ArrayList<AccountName> requiredAuths = new ArrayList<>();
        requiredAuths.add(new AccountName("foobarc"));

        customOperation.setRequiredAuths(requiredAuths);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customOperation);

        transaction.setOperations(operations);
    }

    @Test
    public void testCustomOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(customOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testCustomOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        transaction.sign();

        assertThat("The serialized transaction should look like expected.", Utils.HEX.encode(transaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
