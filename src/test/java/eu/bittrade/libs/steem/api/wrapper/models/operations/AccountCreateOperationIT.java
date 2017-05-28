package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;
import eu.bittrade.libs.steem.api.wrapper.models.Block;
import eu.bittrade.libs.steem.api.wrapper.models.PublicKey;

/**
 * Verify the functionality of the "account create operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5726699;
    private static final int TRANSACTION_INDEX = 6;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_CREATOR = "steem";
    private static final PublicKey EXPECTED_OWNER_KEY = new PublicKey(
            "STM7LkKo3FU1w7m6ce4W5rfhdNUKd7CprHrZXnmNeU3SX2ggSikdw");
    private static final String EXPECTED_TRANSACTION_HEX = "f";

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

        AccountCreateOperation accountCreateOperation = new AccountCreateOperation();
        accountCreateOperation.setFee(new Asset(10000, AssetSymbolType.STEEM));
        accountCreateOperation.setCreator(new AccountName("dez1337"));
        accountCreateOperation.setJsonMetadata("");
        accountCreateOperation.setMemoKey(new PublicKey("STM6zLNtyFVToBsBZDsgMhgjpwysYVbsQD6YhP3kRkQhANUB4w7Qp"));
        accountCreateOperation.setNewAccountName(new AccountName("steemJ"));

        Authority posting = new Authority();
        posting.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("STM8CemMDjdUWSV5wKotEimhK6c4dY7p2PdzC2qM1HpAP8aLtZfE7"), 1);
        posting.setKeyAuths(postingKeyAuth);
        posting.setWeightThreshold(1);

        accountCreateOperation.setPosting(posting);

        Authority active = new Authority();
        active.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> activeKeyAuth = new HashMap<>();
        activeKeyAuth.put(new PublicKey("STM6pbVDAjRFiw6fkiKYCrkz7PFeL7XNAfefrsREwg8MKpJ9VYV9x"), 1);
        active.setKeyAuths(activeKeyAuth);
        active.setWeightThreshold(1);

        accountCreateOperation.setActive(active);

        Authority owner = new Authority();
        owner.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> ownerKeyAuth = new HashMap<>();
        ownerKeyAuth.put(new PublicKey("STM5jYVokmZHdEpwo5oCG3ES2Ca4VYzy6tM8pWWkGdgVnwo2mFLFq"), 1);
        owner.setKeyAuths(activeKeyAuth);
        owner.setWeightThreshold(1);

        accountCreateOperation.setOwner(owner);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(accountCreateOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingAccountCreateOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation accountCreateOperation = blockContainingAccountCreateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(accountCreateOperation, instanceOf(AccountCreateOperation.class));
        assertThat(((AccountCreateOperation) accountCreateOperation).getCreator().getAccountName(),
                equalTo(EXPECTED_CREATOR));
        assertThat(((AccountCreateOperation) accountCreateOperation).getOwner().getKeyAuths()
                .containsKey(EXPECTED_OWNER_KEY), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemApiWrapper.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemApiWrapper.getTransactionHex(transaction),
         equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
