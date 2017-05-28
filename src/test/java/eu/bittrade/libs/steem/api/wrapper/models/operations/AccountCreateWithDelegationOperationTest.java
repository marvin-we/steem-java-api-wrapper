package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steem.api.wrapper.BaseUnitTest;
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;
import eu.bittrade.libs.steem.api.wrapper.models.PublicKey;

/**
 * Test the transformation of a Steem "account create with delegation
 * operation".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateWithDelegationOperationTest extends BaseUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "29f40100000000000003535445454d00001f69003124000000065645535453"
            + "00000764657a3133333706737465656d6a01000000000102fe8cc11cc8251de6977636b55c1ab8a9d12b0b26154ac78e"
            + "56e7c4257d8bcf69010001000000000102fe8cc11cc8251de6977636b55c1ab8a9d12b0b26154ac78e56e7c4257d8bcf"
            + "69010001000000000103b453f46013fdbccb90b09ba169c388c34d84454a3b9fbec68d5a7819a734fca001000314aa20"
            + "2c9158990b3ec51a1aa49b2ab5d300c97b391df3beb34bb74f3c62699e0000";
    final String EXPECTED_TRANSACTION_HASH = "acaad84b2e4445aaea2252327a93a580b24d7e6e63454acd7e69f71b0f6611be";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "00000000000000000000000000000000000000000000000000000000"
            + "00000000f68585abf4dce9c804570129f40100000000000003535445454d00001f690031240000000656455354530000"
            + "0764657a3133333706737465656d6a01000000000102fe8cc11cc8251de6977636b55c1ab8a9d12b0b26154ac78e56e7"
            + "c4257d8bcf69010001000000000102fe8cc11cc8251de6977636b55c1ab8a9d12b0b26154ac78e56e7c4257d8bcf6901"
            + "0001000000000103b453f46013fdbccb90b09ba169c388c34d84454a3b9fbec68d5a7819a734fca001000314aa202c91"
            + "58990b3ec51a1aa49b2ab5d300c97b391df3beb34bb74f3c62699e000000";

    private static AccountCreateWithDelegationOperation accountCreateWithDelegationOperation;

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironment();

        accountCreateWithDelegationOperation = new AccountCreateWithDelegationOperation();
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
        owner.setKeyAuths(activeKeyAuth);
        owner.setWeightThreshold(1);

        accountCreateWithDelegationOperation.setOwner(owner);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(accountCreateWithDelegationOperation);

        transaction.setOperations(operations);
    }

    @Test
    public void testAccountCreateWithDelegationOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(accountCreateWithDelegationOperation.toByteArray()),
                equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testAccountCreateWithDelegationOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        transaction.sign();

        assertThat("The serialized transaction should look like expected.", Utils.HEX.encode(transaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat("Expect that the serialized transaction results in the given hex.",
                Utils.HEX.encode(Sha256Hash.wrap(Sha256Hash.hash(transaction.toByteArray())).getBytes()),
                equalTo(EXPECTED_TRANSACTION_HASH));
    }
}
