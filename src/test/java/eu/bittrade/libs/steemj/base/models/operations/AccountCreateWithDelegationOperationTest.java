package eu.bittrade.libs.steemj.base.models.operations;

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

import eu.bittrade.libs.steemj.BaseUnitTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.base.models.operations.AccountCreateWithDelegationOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transformation of a Steem "account create with delegation
 * operation".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateWithDelegationOperationTest extends BaseUnitTest {
    final String EXPECTED_BYTE_REPRESENTATION = "29f40100000000000003535445454d00001f69003124000000065645535453"
            + "00000764657a3133333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b09d9ea"
            + "6eedec21de6fa4c4010001000000000102fe8cc11cc8251de6977636b55c1ab8a9d12b0b26154ac78e56e7c4257d8bcf"
            + "69010001000000000103b453f46013fdbccb90b09ba169c388c34d84454a3b9fbec68d5a7819a734fca001000314aa20"
            + "2c9158990b3ec51a1aa49b2ab5d300c97b391df3beb34bb74f3c62699e0000";
    final String EXPECTED_TRANSACTION_HASH = "b60482bbf6b2f77f23456aed12b45361518c936d873a9c6210ae67b108e6d113";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "0000000000000000000000000000000000000000000000000000000"
            + "000000000f68585abf4dce9c804570129f40100000000000003535445454d00001f6900312400000006564553545300"
            + "000764657a3133333706737465656d6a010000000001026f6231b8ed1c5e964b42967759757f8bb879d68e7b09d9ea6"
            + "eedec21de6fa4c4010001000000000102fe8cc11cc8251de6977636b55c1ab8a9d12b0b26154ac78e56e7c4257d8bcf"
            + "69010001000000000103b453f46013fdbccb90b09ba169c388c34d84454a3b9fbec68d5a7819a734fca001000314aa2"
            + "02c9158990b3ec51a1aa49b2ab5d300c97b391df3beb34bb74f3c62699e000000";

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
        posting.setAccountAuths(new HashMap<AccountName,Integer>());
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("STM8CemMDjdUWSV5wKotEimhK6c4dY7p2PdzC2qM1HpAP8aLtZfE7"), 1);
        posting.setKeyAuths(postingKeyAuth);
        posting.setWeightThreshold(1);

        accountCreateWithDelegationOperation.setPosting(posting);

        Authority active = new Authority();
        active.setAccountAuths(new HashMap<AccountName,Integer>());
        Map<PublicKey, Integer> activeKeyAuth = new HashMap<>();
        activeKeyAuth.put(new PublicKey("STM6pbVDAjRFiw6fkiKYCrkz7PFeL7XNAfefrsREwg8MKpJ9VYV9x"), 1);
        active.setKeyAuths(activeKeyAuth);
        active.setWeightThreshold(1);

        accountCreateWithDelegationOperation.setActive(active);

        Authority owner = new Authority();
        owner.setAccountAuths(new HashMap<AccountName,Integer>());
        Map<PublicKey, Integer> ownerKeyAuth = new HashMap<>();
        ownerKeyAuth.put(new PublicKey("STM5jYVokmZHdEpwo5oCG3ES2Ca4VYzy6tM8pWWkGdgVnwo2mFLFq"), 1);
        owner.setKeyAuths(ownerKeyAuth);
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
