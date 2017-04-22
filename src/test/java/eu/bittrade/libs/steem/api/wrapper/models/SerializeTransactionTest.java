package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.ArrayList;
import java.util.List;

import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.Assert;
import org.junit.Test;

import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.VoteOperation;

public class SerializeTransactionTest {
    private static final String WIF = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
    private static final short REF_BLOCK_NUM = (short)34294;
    private static final long REF_BLOCK_PREFIX = 3707022213L;
    private static final String EXPIRATION_DATE = "2016-04-06T08:29:27UTC";

    @Test
    public void testTransactionSerializationWithoutChainId() throws Exception {
        final String EXPECTED_RESULT = "bd8c5fe26f45f179a8570100057865726f63057865726f6306706973746f6e102700";

        VoteOperation voteOperation = new VoteOperation();
        voteOperation.setAuthor("xeroc");
        voteOperation.setPermlink("piston");
        voteOperation.setVoter("xeroc");
        voteOperation.setWeight((short) 10000);

        Operation[] operations = { voteOperation };

        Transaction transaction = new Transaction();
        transaction.setExpirationDate("2016-08-08T12:24:17");
        transaction.setRefBlockNum((short) 36029);
        transaction.setRefBlockPrefix(1164960351);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        transaction.setOperations(operations);

        // Use 'toByteArray("")' so no chainId will be added.
        Assert.assertEquals(EXPECTED_RESULT, Utils.HEX.encode(transaction.toByteArray("")));
    }
    
    @Test
    public void testTransactionSerialization() throws Exception {
        final String EXPECTED_RESULT = "0000000000000000000000000000000000000000000000000000000000000000bd8c5fe26f45f179a8570100057865726f63057865726f6306706973746f6e102700";
        final String EXPECTED_HASH = "582176b1daf89984bc8b4fdcb24ff1433d1eb114a8c4bf20fb22ad580d035889";

        // TODO Move to before method!
        VoteOperation voteOperation = new VoteOperation();
        voteOperation.setAuthor("xeroc");
        voteOperation.setPermlink("piston");
        voteOperation.setVoter("xeroc");
        voteOperation.setWeight((short) 10000);

        Operation[] operations = { voteOperation };

        Transaction transaction = new Transaction();
        transaction.setExpirationDate("2016-08-08T12:24:17");
        transaction.setRefBlockNum((short) 36029);
        transaction.setRefBlockPrefix(1164960351);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        transaction.setOperations(operations);

        byte[] transactionAsByteArray = transaction.toByteArray();
        Assert.assertEquals(EXPECTED_RESULT, Utils.HEX.encode(transactionAsByteArray));
        Assert.assertEquals(EXPECTED_HASH, Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transactionAsByteArray)).getBytes()));
    }
}
