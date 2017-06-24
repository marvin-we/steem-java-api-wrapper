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
import eu.bittrade.libs.steemj.base.models.operations.TransferToVestingOperation;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test a Steem "transfer to vesting operation" and verify the results against
 * the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferToVestingOperationTest extends BaseUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "030764657a313333370764657a31333337010000000000000003535445454d0000";
    final String EXPECTED_TRANSACTION_HASH = "541167c321d11aa086d5c57a4489e335e303bf1e6fb5588cfa2eca34fe46434f";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dce7c8045701030764657a313333370764657a31333337010000000000000003535445454d000000";

    private static TransferToVestingOperation transferToVestingOperation;

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironment();

        Asset steemAmount = new Asset();
        steemAmount.setAmount(1L);
        steemAmount.setSymbol(AssetSymbolType.STEEM);

        transferToVestingOperation = new TransferToVestingOperation();
        transferToVestingOperation.setFrom(new AccountName("dez1337"));
        transferToVestingOperation.setTo(new AccountName("dez1337"));
        transferToVestingOperation.setAmount(steemAmount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferToVestingOperation);

        transaction.setOperations(operations);
    }

    @Test
    public void testTransferToVestingOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(transferToVestingOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testTransferToVestingOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        transaction.sign();

        assertThat("The serialized transaction should look like expected.", Utils.HEX.encode(transaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
