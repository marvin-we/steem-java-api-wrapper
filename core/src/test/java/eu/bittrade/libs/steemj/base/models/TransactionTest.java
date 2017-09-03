package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.base.models.operations.VoteOperation;

/**
 * Test the transaction object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class TransactionTest {
    @Test
    public void testTransactionSerializationWithoutChainId() throws Exception {
        final String EXPECTED_BYTE_REPRESENTATION = "bd8c5fe26f45f179a8570100057865726f63057865726f6306706973746f6e102700";
        final String EXPECTED_RESULT = "0000000000000000000000000000000000000000000000000000000000000000bd8c5fe26f45f179a8570100057865726f63057865726f6306706973746f6e102700";
        final String EXPECTED_HASH = "582176b1daf89984bc8b4fdcb24ff1433d1eb114a8c4bf20fb22ad580d035889";

        VoteOperation voteOperation = new VoteOperation();
        voteOperation.setAuthor(new AccountName("xeroc"));
        voteOperation.setPermlink("piston");
        voteOperation.setVoter(new AccountName("xeroc"));
        voteOperation.setWeight((short) 10000);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        SignedTransaction transaction = new SignedTransaction();
        transaction.setExpirationDate(new TimePointSec("2016-08-08T12:24:17"));
        transaction.setRefBlockNum((short) 36029);
        transaction.setRefBlockPrefix(1164960351);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        transaction.setOperations(operations);

        // Use 'toByteArray("")' so no chainId will be added.
        assertThat(Utils.HEX.encode(transaction.toByteArray("")), equalTo(EXPECTED_BYTE_REPRESENTATION));

        byte[] transactionAsByteArrayWithDefaultChainId = transaction.toByteArray();
        assertThat(Utils.HEX.encode(transactionAsByteArrayWithDefaultChainId), equalTo(EXPECTED_RESULT));
        assertThat(
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transactionAsByteArrayWithDefaultChainId)).getBytes()),
                equalTo(EXPECTED_HASH));
    }
}
