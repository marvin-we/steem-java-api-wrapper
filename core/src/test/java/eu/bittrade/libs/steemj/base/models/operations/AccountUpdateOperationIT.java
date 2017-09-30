package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalIntegrationTest;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "account update operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountUpdateOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5681154;
    private static final int TRANSACTION_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_ACCOUNT = "virekolosa";
    private static final PublicKey EXPECTED_PUBLIC_KEY = new PublicKey(
            "STM5PcXipEAThkBhkXawSqL1mqTVU9iRNLasAw9sbYnkRApAxbTWR");
    private static final int EXPECTED_WEIGHT_THRESHOLD = 0;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce7c80457010a0764657a31333337010100000000010245afe8ba"
            + "78a2023ef69af6d3ecbbf26e37f5f5b9c1a8b37f80f4a1b761a75e590100010100000000010309b5f14e6ca97187ae2a2cdf4c6e7b15b"
            + "5a434bf53ffcea6642f28e1aa5336c101000101000000000102a2a9e8a0ec260cfef0e7708d88a99f90809db2a61ea2a87dddb062d5f5"
            + "9a011c01000314aa202c9158990b3ec51a1aa49b2ab5d300c97b391df3beb34bb74f3c62699e0000011c792fac8bfdee0dac9086a4908"
            + "f3d3798a7e551292f13c2dbfaa8c2b4b71cb6767136f8d7c11a6a24993980480ea45eef40485995e04062d5329ab1e98e80c6a5";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "creator". If no owner key is provided or the owner key is not valid an
     * Exception will be thrown. The owner key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionalTests();

        AccountName account = new AccountName("dez1337");
        PublicKey memoKey = new PublicKey("STM6zLNtyFVToBsBZDsgMhgjpwysYVbsQD6YhP3kRkQhANUB4w7Qp");

        Authority posting = new Authority();
        posting.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("STM688NyXXSjXmXCy4FSaPH5L2FitugsKU9PbLn5ZiUQr3GaztmCL"), 1);
        posting.setKeyAuths(postingKeyAuth);
        posting.setWeightThreshold(1);

        Authority active = new Authority();
        active.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> activeKeyAuth = new HashMap<>();
        activeKeyAuth.put(new PublicKey("STM6uWaRvGTtvKTdciKU3rtBbeq3ZfBopvjewQdngeAG31EGSXA2f"), 1);
        active.setKeyAuths(activeKeyAuth);
        active.setWeightThreshold(1);

        Authority owner = new Authority();
        owner.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> ownerKeyAuth = new HashMap<>();
        ownerKeyAuth.put(new PublicKey("STM5RBRDAfpq4RrWGtLAyMf2qQaiS9abkU2nmDegQiH3P1vMbP2Lq"), 1);
        owner.setKeyAuths(ownerKeyAuth);
        owner.setWeightThreshold(1);

        String jsonMetadata = "";

        AccountUpdateOperation accountUpdateOperation = new AccountUpdateOperation(account, owner, active, posting,
                memoKey, jsonMetadata);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(accountUpdateOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingAccountUpdateOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation accountUpdateOperation = blockContainingAccountUpdateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(accountUpdateOperation, instanceOf(AccountUpdateOperation.class));
        assertThat(((AccountUpdateOperation) accountUpdateOperation).getAccount().getName().toString(),
                equalTo(EXPECTED_ACCOUNT));
        System.out.println(((PublicKey) (((AccountUpdateOperation) accountUpdateOperation).getActive().getKeyAuths()
                .keySet().toArray())[0]).getAddressFromPublicKey());
        assertThat(((AccountUpdateOperation) accountUpdateOperation).getActive().getKeyAuths().get(EXPECTED_PUBLIC_KEY),
                equalTo(EXPECTED_WEIGHT_THRESHOLD));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(signedTransaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
