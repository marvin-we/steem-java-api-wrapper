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
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.BaseTransactionalIntegrationTest;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "account create operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateOperationIT extends BaseTransactionalIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5726699;
    private static final int TRANSACTION_INDEX = 6;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_CREATOR = "steem";
    private static final PublicKey EXPECTED_OWNER_KEY = new PublicKey(
            "STM7LkKo3FU1w7m6ce4W5rfhdNUKd7CprHrZXnmNeU3SX2ggSikdw");
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce9c804570109102700000000000003"
            + "535445454d00000764657a3133333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757"
            + "f8bb879d68e7b09d9ea6eedec21de6fa4c4010001000000000102fe8cc11cc8251de6977636b55c1ab8a9d1"
            + "2b0b26154ac78e56e7c4257d8bcf69010001000000000103b453f46013fdbccb90b09ba169c388c34d84454"
            + "a3b9fbec68d5a7819a734fca001000314aa202c9158990b3ec51a1aa49b2ab5d300c97b391df3beb34bb74f"
            + "3c62699e0000011b45bfc693eb47ee2f002d0c2a6f432ce023e0dda97c2e76692a063b241752df0b37bd558"
            + "982f2978e7923b44ba4c349845e3bd3dff50ce7785051918162dc64bc";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "creator". If no active key is provided or the active key is not valid an
     * Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionalTests();

        Asset fee = new Asset(10000, AssetSymbolType.STEEM);
        AccountName creator = new AccountName("dez1337");
        String jsonMetadata = "";
        PublicKey memoKey = new PublicKey("STM6zLNtyFVToBsBZDsgMhgjpwysYVbsQD6YhP3kRkQhANUB4w7Qp");
        AccountName newAccountName = new AccountName("steemj");

        Authority posting = new Authority();
        posting.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("STM8CemMDjdUWSV5wKotEimhK6c4dY7p2PdzC2qM1HpAP8aLtZfE7"), 1);
        posting.setKeyAuths(postingKeyAuth);
        posting.setWeightThreshold(1);

        Authority active = new Authority();
        active.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> activeKeyAuth = new HashMap<>();
        activeKeyAuth.put(new PublicKey("STM6pbVDAjRFiw6fkiKYCrkz7PFeL7XNAfefrsREwg8MKpJ9VYV9x"), 1);
        active.setKeyAuths(activeKeyAuth);
        active.setWeightThreshold(1);

        Authority owner = new Authority();
        owner.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> ownerKeyAuth = new HashMap<>();
        ownerKeyAuth.put(new PublicKey("STM5jYVokmZHdEpwo5oCG3ES2Ca4VYzy6tM8pWWkGdgVnwo2mFLFq"), 1);
        owner.setKeyAuths(ownerKeyAuth);
        owner.setWeightThreshold(1);

        AccountCreateOperation accountCreateOperation = new AccountCreateOperation(creator, fee, newAccountName, owner,
                active, posting, memoKey, jsonMetadata);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(accountCreateOperation);

        signedTransaction.setOperations(operations);

        sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingAccountCreateOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation accountCreateOperation = blockContainingAccountCreateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(accountCreateOperation, instanceOf(AccountCreateOperation.class));
        assertThat(((AccountCreateOperation) accountCreateOperation).getCreator().getName(), equalTo(EXPECTED_CREATOR));
        assertThat(((AccountCreateOperation) accountCreateOperation).getOwner().getKeyAuths()
                .containsKey(EXPECTED_OWNER_KEY), equalTo(true));
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
