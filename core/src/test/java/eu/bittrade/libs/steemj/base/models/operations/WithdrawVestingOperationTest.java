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
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.base.models.operations.WithdrawVestingOperation;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test a Steem "withdraw vesting operation" and verify the results against the
 * api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WithdrawVestingOperationTest extends BaseUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "040764657a31333337e8030000000000000656455354530000";
    final String EXPECTED_TRANSACTION_HASH = "f695518ab66b5d27cfe7560b483e3dc3a1d1cc87512862c75c4345d14b7d03fb";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "00000000000000000000000000000000000000000000"
            + "00000000000000000000f68585abf4dce9c8045701040764657a31333337e803000000000000065645535453000000";

    private static WithdrawVestingOperation withdrawVestingOperation;

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironment();

        withdrawVestingOperation = new WithdrawVestingOperation();
        withdrawVestingOperation.setAccount(new AccountName("dez1337"));

        Asset vestingShares = new Asset();
        vestingShares.setAmount(1000);
        vestingShares.setSymbol(AssetSymbolType.VESTS);

        withdrawVestingOperation.setVestingShares(vestingShares);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(withdrawVestingOperation);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        transaction.setOperations(operations);
    }

    @Test
    public void testWithdrawVestingOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(withdrawVestingOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testWithdrawVestingOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        transaction.sign();

        assertThat("The serialized transaction should look like expected.", Utils.HEX.encode(transaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
