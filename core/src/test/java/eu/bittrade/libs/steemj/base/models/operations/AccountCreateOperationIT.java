package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseTransactionVerificationIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * Verify the functionality of the "account create operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateOperationIT extends BaseTransactionVerificationIT {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce9c804570109102700000000000003"
            + "535445454d00000764657a3133333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757"
            + "f8bb879d68e7b09d9ea6eedec21de6fa4c4010001000000000102fe8cc11cc8251de6977636b55c1ab8a9d1"
            + "2b0b26154ac78e56e7c4257d8bcf69010001000000000103b453f46013fdbccb90b09ba169c388c34d84454"
            + "a3b9fbec68d5a7819a734fca001000314aa202c9158990b3ec51a1aa49b2ab5d300c97b391df3beb34bb74f"
            + "3c62699e0000011b45bfc693eb47ee2f002d0c2a6f432ce023e0dda97c2e76692a063b241752df0b37bd558"
            + "982f2978e7923b44ba4c349845e3bd3dff50ce7785051918162dc64bc";
    private static final String EXPECTED_TRANSACTION_HEX_TESTNET = "f68585abf4dce9c804570109102700000000000003535445454"
            + "d00000764657a3133333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b09d9e"
            + "a6eedec21de6fa4c4010001000000000102fe8cc11cc8251de6977636b55c1ab8a9d12b0b26154ac78e56e7c4257d8bc"
            + "f69010001000000000103b453f46013fdbccb90b09ba169c388c34d84454a3b9fbec68d5a7819a734fca001000314aa2"
            + "02c9158990b3ec51a1aa49b2ab5d300c97b391df3beb34bb74f3c62699e0000011c4ecd2e6834850672b9c33d199ea0e"
            + "165b4da1a1cdedcfc11af34dfa49e90db5c257ac84e46aca2cf2c41a66d9a6dc13604f960b4260b380c9e37e985b7272"
            + "c44";

    /**
     * Prepare all required fields used by this test class.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironmentForTransactionVerificationTests(HTTP_MODE_IDENTIFIER,
                STEEMNET_ENDPOINT_IDENTIFIER);

        Asset fee = new Asset(10000, AssetSymbolType.STEEM);
        AccountName creator = new AccountName("dez1337");
        String jsonMetadata = "";
        PublicKey memoKey = new PublicKey(SteemJConfig.getInstance().getAddressPrefix().name().toUpperCase()
                + "6zLNtyFVToBsBZDsgMhgjpwysYVbsQD6YhP3kRkQhANUB4w7Qp");
        AccountName newAccountName = new AccountName("steemj");

        Authority posting = new Authority();
        posting.setAccountAuths(new HashMap<AccountName, Integer>());
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey(SteemJConfig.getInstance().getAddressPrefix().name().toUpperCase()
                + "8CemMDjdUWSV5wKotEimhK6c4dY7p2PdzC2qM1HpAP8aLtZfE7"), 1);
        posting.setKeyAuths(postingKeyAuth);
        posting.setWeightThreshold(1);

        Authority active = new Authority();
        active.setAccountAuths(new HashMap<AccountName, Integer>());
        Map<PublicKey, Integer> activeKeyAuth = new HashMap<>();
        activeKeyAuth.put(new PublicKey(SteemJConfig.getInstance().getAddressPrefix().name().toUpperCase()
                + "6pbVDAjRFiw6fkiKYCrkz7PFeL7XNAfefrsREwg8MKpJ9VYV9x"), 1);
        active.setKeyAuths(activeKeyAuth);
        active.setWeightThreshold(1);

        Authority owner = new Authority();
        owner.setAccountAuths(new HashMap<AccountName, Integer>());
        Map<PublicKey, Integer> ownerKeyAuth = new HashMap<>();
        ownerKeyAuth.put(new PublicKey(SteemJConfig.getInstance().getAddressPrefix().name().toUpperCase()
                + "5jYVokmZHdEpwo5oCG3ES2Ca4VYzy6tM8pWWkGdgVnwo2mFLFq"), 1);
        owner.setKeyAuths(ownerKeyAuth);
        owner.setWeightThreshold(1);

        AccountCreateOperation accountCreateOperation = new AccountCreateOperation(creator, fee, newAccountName, owner,
                active, posting, memoKey, jsonMetadata);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(accountCreateOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
        signedTransaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemJ.verifyAuthority(signedTransaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        if (TEST_ENDPOINT.equals(TESTNET_ENDPOINT_IDENTIFIER)) {
            assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX_TESTNET));
        } else {
            assertThat(steemJ.getTransactionHex(signedTransaction), equalTo(EXPECTED_TRANSACTION_HEX));
        }
    }
}
