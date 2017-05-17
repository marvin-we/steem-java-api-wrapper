package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steem.api.wrapper.BaseTest;
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Transaction;

/**
 * Test a Steem "transfer operation" and verify the results against the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransferToVestingOperationTest extends BaseTest {
    final String EXPECTED_BYTE_REPRESENTATION = "030764657a313333370764657a31333337010000000000000003535445454d0000";
    final String EXPECTED_TRANSACTION_HASH = "541167c321d11aa086d5c57a4489e335e303bf1e6fb5588cfa2eca34fe46434f";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dce7c8045701030764657a313333370764657a31333337010000000000000003535445454d000000";

    private static TransferToVestingOperation transferToVestingOperation;
    private static Transaction transferToVestingOperationTransaction;

    @BeforeClass
    public static void setup() throws Exception {
        Asset steemAmount = new Asset();
        steemAmount.setAmount(1L);
        steemAmount.setSymbol(AssetSymbolType.STEEM);

        transferToVestingOperation = new TransferToVestingOperation();
        transferToVestingOperation.setFrom(new AccountName("dez1337"));
        transferToVestingOperation.setTo(new AccountName("dez1337"));
        transferToVestingOperation.setAmount(steemAmount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(transferToVestingOperation);

        transferToVestingOperationTransaction = new Transaction();
        transferToVestingOperationTransaction.setExpirationDate(EXPIRATION_DATE);
        transferToVestingOperationTransaction.setRefBlockNum(REF_BLOCK_NUM);
        transferToVestingOperationTransaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        transferToVestingOperationTransaction.setOperations(operations);
    }

    @Test
    public void testTransferToVestingOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(transferToVestingOperation.toByteArray()),
                equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testTransferToVestingOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        transferToVestingOperationTransaction.sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(transferToVestingOperationTransaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat(
                "Expect that the serialized transaction results in the given hex.", Utils.HEX.encode(Sha256Hash
                        .wrap(Sha256Hash.hash(transferToVestingOperationTransaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
