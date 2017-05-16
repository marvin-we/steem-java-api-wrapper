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
import eu.bittrade.libs.steem.api.wrapper.models.Price;
import eu.bittrade.libs.steem.api.wrapper.models.Transaction;

/**
 * Test a Steem "limit order create 2 operation" and verify the results against
 * the api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreate2OperationTest extends BaseTest {
    final String EXPECTED_BYTE_REPRESENTATION = "150764657a31333337bf85070001000000000000000353424400000000010000"
            + "000000000003534244000000000a0000000000000003535445454d000000e7c80457";
    final String EXPECTED_TRANSACTION_HASH = "e9bcd1316cd72a43190a8c10c7aab8fbc87a0287bb8ca0e9b449f50c6330a4de";
    final String EXPECTED_TRANSACTION_SERIALIZATION = "000000000000000000000000000000000000000000000000000000000"
            + "0000000f68585abf4dce8c8045701150764657a31333337bf850700010000000000000003534244000000000100000000"
            + "00000003534244000000000a0000000000000003535445454d000000e7c8045700";

    private static LimitOrderCreate2Operation limitOrderCreate2Operation;
    private static Transaction limitOrderCreate2OperationTransaction;

    @BeforeClass
    public static void setup() throws Exception {
        limitOrderCreate2Operation = new LimitOrderCreate2Operation();

        Asset base = new Asset();
        base.setAmount(1L);
        base.setSymbol(AssetSymbolType.SBD);

        Asset quote = new Asset();
        quote.setAmount(10L);
        quote.setSymbol(AssetSymbolType.STEEM);

        Price exchangeRate = new Price();
        exchangeRate.setBase(base);
        exchangeRate.setQuote(quote);

        limitOrderCreate2Operation.setExchangeRate(exchangeRate);

        Asset amountToSell = new Asset();
        amountToSell.setAmount(1L);
        amountToSell.setSymbol(AssetSymbolType.SBD);

        limitOrderCreate2Operation.setAmountToSell(amountToSell);
        limitOrderCreate2Operation.setExpirationDate(EXPIRATION_DATE);
        limitOrderCreate2Operation.setFillOrKill(false);
        limitOrderCreate2Operation.setOrderId(492991L);
        limitOrderCreate2Operation.setOwner(new AccountName("dez1337"));

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(limitOrderCreate2Operation);

        limitOrderCreate2OperationTransaction = new Transaction();
        limitOrderCreate2OperationTransaction.setExpirationDate(EXPIRATION_DATE);
        limitOrderCreate2OperationTransaction.setRefBlockNum(REF_BLOCK_NUM);
        limitOrderCreate2OperationTransaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
        limitOrderCreate2OperationTransaction.setOperations(operations);
    }

    @Test
    public void testLimitOrderCreate2OperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        assertThat("Expect that the operation has the given byte representation.",
                Utils.HEX.encode(limitOrderCreate2Operation.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testLimitOrderCreate2OperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        limitOrderCreate2OperationTransaction.sign();

        assertThat("The serialized transaction should look like expected.",
                Utils.HEX.encode(limitOrderCreate2OperationTransaction.toByteArray()),
                equalTo(EXPECTED_TRANSACTION_SERIALIZATION));
        assertThat(
                "Expect that the serialized transaction results in the given hex.", Utils.HEX.encode(Sha256Hash
                        .wrap(Sha256Hash.hash(limitOrderCreate2OperationTransaction.toByteArray())).getBytes()),
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
