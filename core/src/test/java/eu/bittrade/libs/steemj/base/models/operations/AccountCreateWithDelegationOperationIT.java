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

import eu.bittrade.libs.steemj.BaseIntegrationTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * Verify the functionality of the "account create with delegation operation"
 * under the use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateWithDelegationOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 12326238;
    private static final int TRANSACTION_INDEX = 3;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_CREATOR = "dez1337";
    private static final PublicKey EXPECTED_OWNER_KEY = new PublicKey(
            "STM6uWaRvGTtvKTdciKU3rtBbeq3ZfBopvjewQdngeAG31EGSXA2f");
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce9c804570129f4010000"
            + "0000000003535445454d00001f6900312400000006564553545300000764657a3133333706737"
            + "465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b09d9ea6eedec"
            + "21de6fa4c4010001000000000102fe8cc11cc8251de6977636b55c1ab8a9d12b0b26154ac78e5"
            + "6e7c4257d8bcf69010001000000000103b453f46013fdbccb90b09ba169c388c34d84454a3b9f"
            + "bec68d5a7819a734fca001000314aa202c9158990b3ec51a1aa49b2ab5d300c97b391df3beb34"
            + "bb74f3c62699e000000011c6dbbad1d44a8cef307da8a7c542f2b442394edce566fe5680ce369"
            + "ebf04276452e74669c28cfb6d7906d120adb2e52a38419ec09d51d2f88574f59e1f07cc4c9";

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
        setupIntegrationTestEnvironment();

        AccountCreateWithDelegationOperation accountCreateWithDelegationOperation = new AccountCreateWithDelegationOperation();
        accountCreateWithDelegationOperation.setFee(new Asset(500, AssetSymbolType.STEEM));
        accountCreateWithDelegationOperation.setDelegation(new Asset(155440933151L, AssetSymbolType.VESTS));
        accountCreateWithDelegationOperation.setCreator(new AccountName("dez1337"));
        accountCreateWithDelegationOperation.setJsonMetadata("");
        accountCreateWithDelegationOperation
                .setMemoKey(new PublicKey("STM6zLNtyFVToBsBZDsgMhgjpwysYVbsQD6YhP3kRkQhANUB4w7Qp"));
        accountCreateWithDelegationOperation.setNewAccountName(new AccountName("steemj"));

        Authority posting = new Authority();
        posting.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("STM8CemMDjdUWSV5wKotEimhK6c4dY7p2PdzC2qM1HpAP8aLtZfE7"), 1);
        posting.setKeyAuths(postingKeyAuth);
        posting.setWeightThreshold(1);

        accountCreateWithDelegationOperation.setPosting(posting);

        Authority active = new Authority();
        active.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> activeKeyAuth = new HashMap<>();
        activeKeyAuth.put(new PublicKey("STM6pbVDAjRFiw6fkiKYCrkz7PFeL7XNAfefrsREwg8MKpJ9VYV9x"), 1);
        active.setKeyAuths(activeKeyAuth);
        active.setWeightThreshold(1);

        accountCreateWithDelegationOperation.setActive(active);

        Authority owner = new Authority();
        owner.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> ownerKeyAuth = new HashMap<>();
        ownerKeyAuth.put(new PublicKey("STM5jYVokmZHdEpwo5oCG3ES2Ca4VYzy6tM8pWWkGdgVnwo2mFLFq"), 1);
        owner.setKeyAuths(ownerKeyAuth);
        owner.setWeightThreshold(1);

        accountCreateWithDelegationOperation.setOwner(owner);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(accountCreateWithDelegationOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingAccountCreateWithDelegationOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation accountCreateWithDelegationOperation = blockContainingAccountCreateWithDelegationOperation
                .getTransactions().get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(accountCreateWithDelegationOperation, instanceOf(AccountCreateWithDelegationOperation.class));
        assertThat(((AccountCreateWithDelegationOperation) accountCreateWithDelegationOperation).getCreator().getName(),
                equalTo(EXPECTED_CREATOR));
        assertThat(((AccountCreateWithDelegationOperation) accountCreateWithDelegationOperation).getOwner()
                .getKeyAuths().containsKey(EXPECTED_OWNER_KEY), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemJ.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
