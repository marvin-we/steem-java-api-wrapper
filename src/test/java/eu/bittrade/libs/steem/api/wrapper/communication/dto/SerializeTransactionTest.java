package eu.bittrade.libs.steem.api.wrapper.communication.dto;

import org.junit.Assert;
import org.junit.Test;

public class SerializeTransactionTest {
    @Test
    public void testTransactionSerialization() throws Exception {
        final String EXPECTED_RESULT = "bd8c5fe26f45f179a8570100057865726f63057865726f6306706973746f6e102700";

        VoteDTO voteOperation = new VoteDTO();
        voteOperation.setAuthor("xeroc");
        voteOperation.setPermlink("piston");
        voteOperation.setVoter("xeroc");
        voteOperation.setWeight((short) 10000);

        OperationDTO[] operations = { voteOperation };

        TransactionDTO transaction = new TransactionDTO();
        transaction.setExpirationDate("2016-08-08T12:24:17");
        transaction.setRefBlockNum((short) 36029);
        transaction.setRefBlockPrefix(1164960351);
        // TODO: Add extensions when supported. 
        // transaction.setExtensions(extensions);
        transaction.setOperations(operations);

        // Build a String to compare them.
        StringBuilder result = new StringBuilder();
        for (byte b : transaction.serialize()) {
            result.append(String.format("%02x", b));
        }
        Assert.assertEquals(result.toString(), EXPECTED_RESULT);
    }
}
