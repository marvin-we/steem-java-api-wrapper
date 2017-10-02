package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalUnitTest;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transformation of a Steem "prove authority operation".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ChallangeAuthorityOperationTest extends BaseTransactionalUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION_WITH_OWNER = "1606737465656d6a0764657a3133333701";
    final String EXPECTED_BYTE_REPRESENTATION_WITH_ACTIVE = "1606737465656d6a0764657a3133333700";
    final String EXPECTED_TRANSACTION_HASH = "a572c07078f6f89c93c26df29f5dad2d010f75812ffa20c32a630391b05de545";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dce7c80457021606737465656d6a0764657a31333337011606737465656d6a0764657a313333"
            + "370000";

    private static ChallengeAuthorityOperation challengeAuthorityOperationWithOwnerKey;
    private static ChallengeAuthorityOperation challengeAuthorityOperationWithActiveKey;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

        AccountName challengedAccount = new AccountName("dez1337");
        AccountName challengerAccount = new AccountName("steemj");

        challengeAuthorityOperationWithOwnerKey = new ChallengeAuthorityOperation(challengerAccount, challengedAccount,
                true);
        challengeAuthorityOperationWithActiveKey = new ChallengeAuthorityOperation(challengerAccount,
                challengedAccount);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(challengeAuthorityOperationWithOwnerKey);
        operations.add(challengeAuthorityOperationWithActiveKey);

        signedTransaction.setOperations(operations);
    }

    @Override
    @Test
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(challengeAuthorityOperationWithOwnerKey.toByteArray()),
                equalTo(EXPECTED_BYTE_REPRESENTATION_WITH_OWNER));
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(challengeAuthorityOperationWithActiveKey.toByteArray()),
                equalTo(EXPECTED_BYTE_REPRESENTATION_WITH_ACTIVE));
    }

    @Override
    @Test
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        // Sign the transaction.
        sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(signedTransaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
