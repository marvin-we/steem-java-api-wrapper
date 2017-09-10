package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.BaseUnitTest;
import eu.bittrade.libs.steemj.base.models.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.base.models.operations.VoteOperation;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;

/**
 * Test the transaction object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class SginedTransactionTest extends BaseUnitTest {
    private static VoteOperation voteOperation = new VoteOperation();
    private static CustomJsonOperation customJsonOperation = new CustomJsonOperation();

    @BeforeClass
    public static void init() {
        setupUnitTestEnvironment();

        voteOperation = new VoteOperation();
        voteOperation.setAuthor(new AccountName("xeroc"));
        voteOperation.setPermlink("piston");
        voteOperation.setVoter(new AccountName("xeroc"));
        voteOperation.setWeight((short) 10000);

        customJsonOperation = new CustomJsonOperation();

        customJsonOperation.setId("follow");
        customJsonOperation
                .setJson("[\"follow\",{\"follower\":\"dez1337\",\"following\":\"steemj\",\"what\":[\"blog\"]}]");
        customJsonOperation.setRequiredAuths(new ArrayList<>());

        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(new AccountName("dez1337"));

        customJsonOperation.setRequiredPostingAuths(requiredPostingAuths);

    }

    @Test
    public void testTransactionSerializationWithoutChainId() throws Exception {
        final String EXPECTED_BYTE_REPRESENTATION = "f68585abf4dce7c804570100057865726f63057865726f6306706973746f6e102700";
        final String EXPECTED_RESULT = "0000000000000000000000000000000000000000000000000000000000000000f68585abf4dce7c804"
                + "570100057865726f63057865726f6306706973746f6e102700";
        final String EXPECTED_HASH = "2581eb832809ca62a75871c72c275e32b5d2c320a761412a1158f52798530490";
        
        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        transaction.setOperations(operations);

        // Use 'toByteArray("")' so no chainId will be added.
        assertThat(Utils.HEX.encode(transaction.toByteArray("")), equalTo(EXPECTED_BYTE_REPRESENTATION));

        byte[] transactionAsByteArrayWithDefaultChainId = transaction.toByteArray();
        assertThat(Utils.HEX.encode(transactionAsByteArrayWithDefaultChainId), equalTo(EXPECTED_RESULT));
        assertThat(
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transactionAsByteArrayWithDefaultChainId)).getBytes()),
                equalTo(EXPECTED_HASH));
    }

    @Test
    public void testGetRequiredSignaturesSingleField() throws Exception {
        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        transaction.setOperations(operations);
        
        assertThat(transaction.getRequiredSignatures().size(), equalTo(1));
        assertThat(transaction.getRequiredSignatures().get(0), equalTo(SteemJConfig.getInstance().getPrivateKeyStorage()
                .getKeyForAccount(PrivateKeyType.POSTING, new AccountName("xeroc"))));
    }

    @Test
    public void testGetRequiredSignaturesMapField() throws Exception {
        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        transaction.setOperations(operations);

        assertThat(transaction.getRequiredSignatures().size(), equalTo(1));
        assertThat(transaction.getRequiredSignatures().get(0), equalTo(SteemJConfig.getInstance().getPrivateKeyStorage()
                .getKeyForAccount(PrivateKeyType.POSTING, new AccountName("dez1337"))));

    }
}
