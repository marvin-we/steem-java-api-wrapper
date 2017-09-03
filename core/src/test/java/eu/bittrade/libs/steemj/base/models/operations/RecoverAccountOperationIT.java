package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIntegrationTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.base.models.operations.RecoverAccountOperation;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "recover account operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RecoverAccountOperationIT extends BaseIntegrationTest {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c8045701250764657a313"
            + "3333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b09d9e"
            + "a6eedec21de6fa4c4010000011c79f3dfadf424b2f5b7eb5e49b1ea559b1f5f3efec747df108abf7"
            + "5e34313dd15046d7c1645755b215395cc7d8a04b72aabdffbfdcb77bbbe4bd2501aeb453fb2";

    /**
     * <b>Attention:</b> This test class requires a valid owner key of the used
     * "account to recover". If no owner key is provided or the owner key is not
     * valid an Exception will be thrown. The owner key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        RecoverAccountOperation recoverAccountOperation = new RecoverAccountOperation();
        recoverAccountOperation.setAccountToRecover(new AccountName("dez1337"));

        Authority newOwnerAuthority = new Authority();
        newOwnerAuthority.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> ownerKeyAuth = new HashMap<>();
        ownerKeyAuth.put(new PublicKey("STM5jYVokmZHdEpwo5oCG3ES2Ca4VYzy6tM8pWWkGdgVnwo2mFLFq"), 1);
        newOwnerAuthority.setKeyAuths(ownerKeyAuth);
        newOwnerAuthority.setWeightThreshold(1);

        recoverAccountOperation.setNewOwnerAuthority(newOwnerAuthority);

        Authority recentOwnerAuthority = new Authority();
        recentOwnerAuthority.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> ownerKeyAuth2 = new HashMap<>();
        ownerKeyAuth.put(new PublicKey("STM688NyXXSjXmXCy4FSaPH5L2FitugsKU9PbLn5ZiUQr3GaztmCL"), 1);
        recentOwnerAuthority.setKeyAuths(ownerKeyAuth2);
        recentOwnerAuthority.setWeightThreshold(1);

        recoverAccountOperation.setRecentOwnerAuthority(recentOwnerAuthority);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(recoverAccountOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        // TODO: Implement
    }

    @Category({ IntegrationTest.class })
    @Test
    @Ignore
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    @Ignore
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
