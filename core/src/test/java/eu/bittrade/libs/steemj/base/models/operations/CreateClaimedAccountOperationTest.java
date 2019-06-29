package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.BaseTransactionalUT;
import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Authority;
import eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.protocol.operations.AccountCreateOperation;

/**
 * Test the transformation of the {@link AccountCreateOperation}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CreateClaimedAccountOperationTest extends BaseTransactionalUT {
    final String EXPECTED_BYTE_REPRESENTATION = "2b102700000000000003535445454d00000764657a3133333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b09d9ea6eedec21de6fa4c4010001000000000102fe8cc11cc8251de6977636b55c1ab8a9d12b0b26154ac78e56e7c4257d8bcf69010001000000000103b453f46013fdbccb90b09ba169c388c34d84454a3b9fbec68d5a7819a734fca001000314aa202c9158990b3ec51a1aa49b2ab5d300c97b391df3beb34bb74f3c62699e00";
    final String EXPECTED_TRANSACTION_HASH = "e8c62269473b1fc43193ca59e4cbe677ed3cb12ee23b4e590897b9b5d98d4575";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000000000000f68585abf4dcebc80457012b102700000000000003535445454d00000764657a3133333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b09d9ea6eedec21de6fa4c4010001000000000102fe8cc11cc8251de6977636b55c1ab8a9d12b0b26154ac78e56e7c4257d8bcf69010001000000000103b453f46013fdbccb90b09ba169c388c34d84454a3b9fbec68d5a7819a734fca001000314aa202c9158990b3ec51a1aa49b2ab5d300c97b391df3beb34bb74f3c62699e0000";

    private static CreateClaimedAccountOperation createClaimedAccountOperation;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironmentForTransactionalTests();

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

        createClaimedAccountOperation = new CreateClaimedAccountOperation(creator, fee, newAccountName, owner, active, posting,
                memoKey, jsonMetadata);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(createClaimedAccountOperation);

        signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX, new TimePointSec(EXPIRATION_DATE),
                operations, null);
        signedTransaction.sign();
    }

    @Override
    public void testOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                CryptoUtils.HEX.encode(createClaimedAccountOperation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Override
    public void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("The serialized transaction should look like expected.",
                CryptoUtils.HEX.encode(signedTransaction.toByteArray()), equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                CryptoUtils.HEX.encode(Sha256Hash.of(signedTransaction.toByteArray()).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
