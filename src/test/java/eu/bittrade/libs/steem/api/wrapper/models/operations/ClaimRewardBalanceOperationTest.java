package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Transaction;

/**
 * Test a Steem "claim rewards balance operation" and verify the results against
 * the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ClaimRewardBalanceOperationTest extends BaseTest {
    final String EXPECTED_BYTE_REPRESENTATION = "2706737465656d4a020000000000000003535445454d000001000000000000"
            + "00035342440000000003000000000000000656455354530000";
    final String EXPECTED_TRANSACTION_HASH = "b7310d6d135eaf871e211c8a472e21c02aa913d71d6e467776e5d4fd3228c239";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "00000000000000000000000000000000000000000000000000000000"
            + "00000000f68585abf4dce8c80457012706737465656d4a020000000000000003535445454d0000010000000000000003"
            + "534244000000000300000000000000065645535453000000";

    private static ClaimRewardBalanceOperation claimRewardBalanceOperation;
    private static Transaction claimRewardBalanceOperationTransaction;

    @BeforeClass
    public static void setup() throws Exception {
        claimRewardBalanceOperation = new ClaimRewardBalanceOperation();
        claimRewardBalanceOperation.setAccount(new AccountName("steemJ"));

        Asset sbdReward = new Asset();
        sbdReward.setAmount(1L);
        sbdReward.setSymbol(AssetSymbolType.SBD);

        claimRewardBalanceOperation.setRewardSbd(sbdReward);

        Asset steemReward = new Asset();
        steemReward.setAmount(2L);
        steemReward.setSymbol(AssetSymbolType.STEEM);

        claimRewardBalanceOperation.setRewardSteem(steemReward);

        Asset vestsReward = new Asset();
        vestsReward.setAmount(3L);
        vestsReward.setSymbol(AssetSymbolType.VESTS);

        claimRewardBalanceOperation.setRewardVests(vestsReward);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(claimRewardBalanceOperation);

        claimRewardBalanceOperationTransaction = new Transaction();
        claimRewardBalanceOperationTransaction.setExpirationDate(EXPIRATION_DATE);
        claimRewardBalanceOperationTransaction.setRefBlockNum(REF_BLOCK_NUM);
        claimRewardBalanceOperationTransaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        claimRewardBalanceOperationTransaction.setOperations(operations);
    }

    @Test
    public void testClaimRewardBalanceOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(claimRewardBalanceOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testClaimRewardBalanceOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        claimRewardBalanceOperationTransaction.sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(claimRewardBalanceOperationTransaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat(
                "Expect that the serialized transaction results in the given hex.", Utils.HEX.encode(Sha256Hash
                        .wrap(Sha256Hash.hash(claimRewardBalanceOperationTransaction.toByteArray())).getBytes()),
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
