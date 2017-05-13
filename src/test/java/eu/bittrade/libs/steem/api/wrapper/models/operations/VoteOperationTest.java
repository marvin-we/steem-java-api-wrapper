package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.Transaction;

/**
 * Test a Steem "vote operation" and verify the results against the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VoteOperationTest extends BaseTest {
    final String EXPECTED_BYTE_REPRESENTATION = "0007666f6f6261726107666f6f6261726307666f6f62617264e803";
    final String EXPECTED_TRANSACTION_HASH = "582176b1daf89984bc8b4fdcb24ff1433d1eb114a8c4bf20fb22ad580d035889";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "f68585abf4dce7c80457010c057865726f630a636861696e73717561"
            + "640100011f16b43411e11f47394c1624a3c4d3cf4daba700b8690f494e6add7ad9bac735ce7775d823aa66c160878cb3348e6857c5"
            + "31114d229be0202dc0857f8f03a00369";

    private static VoteOperation voteOperation;
    private static Transaction voteOperationTransaction;

    @BeforeClass
    public static void setup() throws Exception {
        voteOperation = new VoteOperation();
        voteOperation.setAuthor("foobarc");
        voteOperation.setPermlink("foobard");
        voteOperation.setVoter("foobara");
        voteOperation.setWeight((short) 1000);

        Operation[] operations = { voteOperation };

        voteOperationTransaction = new Transaction();
        voteOperationTransaction.setExpirationDate(EXPIRATION_DATE);
        voteOperationTransaction.setRefBlockNum(REF_BLOCK_NUM);
        voteOperationTransaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        voteOperationTransaction.setOperations(operations);
    }

    @Test
    public void testVoteOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(voteOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testVoteOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        voteOperationTransaction.sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(voteOperationTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(voteOperationTransaction.toByteArray())).getBytes()),
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
