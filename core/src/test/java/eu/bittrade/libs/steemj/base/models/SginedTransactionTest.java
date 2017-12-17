package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.BaseTransactionalUT;
import eu.bittrade.libs.steemj.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.apis.follow.models.operations.FollowOperation;
import eu.bittrade.libs.steemj.base.models.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.base.models.operations.VoteOperation;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transaction object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class SginedTransactionTest extends BaseTransactionalUT {
    private static VoteOperation voteOperation;
    private static CustomJsonOperation customJsonOperation;

    /**
     * Prepare the environment for the test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass
    public static void init() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        AccountName voter = new AccountName("xeroc");
        AccountName author = new AccountName("xeroc");
        Permlink permlink = new Permlink("piston");
        short weight = 10000;
        voteOperation = new VoteOperation(voter, author, permlink, weight);

        ArrayList<AccountName> requiredAuth = new ArrayList<>();

        ArrayList<AccountName> requiredPostingAuths = new ArrayList<>();
        requiredPostingAuths.add(new AccountName("dez1337"));

        String id = "follow";
        String json = (new FollowOperation(new AccountName("dez1337"), new AccountName("steemj"),
                Arrays.asList(FollowType.BLOG))).toJson();

        customJsonOperation = new CustomJsonOperation(requiredAuth, requiredPostingAuths, id, json);
    }

    /**
     * Test the transaction serialization without providing a chain id.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testTransactionSerializationWithoutChainId() throws Exception {
        final String EXPECTED_BYTE_REPRESENTATION = "f68585abf4dce7c804570100057865726f63057865726f6306706973746f6e102700";
        final String EXPECTED_RESULT = "0000000000000000000000000000000000000000000000000000000000000000f68585abf4dce7c804"
                + "570100057865726f63057865726f6306706973746f6e102700";
        final String EXPECTED_HASH = "2581eb832809ca62a75871c72c275e32b5d2c320a761412a1158f52798530490";

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);

        // Use 'toByteArray("")' so no chainId will be added.
        assertThat(CryptoUtils.HEX.encode(signedTransaction.toByteArray("")), equalTo(EXPECTED_BYTE_REPRESENTATION));

        byte[] transactionAsByteArrayWithDefaultChainId = signedTransaction.toByteArray();
        assertThat(CryptoUtils.HEX.encode(transactionAsByteArrayWithDefaultChainId), equalTo(EXPECTED_RESULT));
        assertThat(CryptoUtils.HEX.encode(Sha256Hash.of(transactionAsByteArrayWithDefaultChainId).getBytes()),
                equalTo(EXPECTED_HASH));
    }

    /**
     * Test if the required authorities are collected correctly.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testGetRequiredSignaturesSingleField() throws Exception {
        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);

        assertThat(signedTransaction.getRequiredSignatureKeys().size(), equalTo(1));
        assertThat(signedTransaction.getRequiredSignatureKeys().get(0), equalTo(SteemJConfig.getInstance()
                .getPrivateKeyStorage().getKeyForAccount(PrivateKeyType.POSTING, new AccountName("xeroc"))));
    }

    /**
     * Test if multiple required authorities are collected correctly.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testGetRequiredSignaturesMapField() throws Exception {
        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(customJsonOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);

        assertThat(signedTransaction.getRequiredSignatureKeys().size(), equalTo(1));
        assertThat(signedTransaction.getRequiredSignatureKeys().get(0), equalTo(SteemJConfig.getInstance()
                .getPrivateKeyStorage().getKeyForAccount(PrivateKeyType.POSTING, new AccountName("dez1337"))));

    }

    /**
     * Test the transaction serialization using the default chain id.
     * 
     * @throws UnsupportedEncodingException
     *             If something went wrong.
     * @throws SteemInvalidTransactionException
     *             If something went wrong.
     */
    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        final String EXPECTED_BYTE_REPRESENTATION = "0000000000000000000000000000000000000000000000000000000000000000f6858"
                + "5abf4dce7c804570100057865726f63057865726f6306706973746f6e102700";

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);

        assertThat(CryptoUtils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    /**
     * Test the transaction serialization using the default chain id.
     * 
     * @throws UnsupportedEncodingException
     *             If something went wrong.
     * @throws SteemInvalidTransactionException
     *             If something went wrong.
     */
    @Override
    @Test
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        final String EXPECTED_HASH = "2581eb832809ca62a75871c72c275e32b5d2c320a761412a1158f52798530490";

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(voteOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);

        assertThat(CryptoUtils.HEX.encode(Sha256Hash.of(signedTransaction.toByteArray()).getBytes()),
                equalTo(EXPECTED_HASH));
    }
}
