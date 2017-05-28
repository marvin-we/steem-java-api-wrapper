package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Price;

/**
 * Verify the functionality of the "limit order create 2 operation" under the
 * use of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreate2OperationIT extends BaseIntegrationTest {
    // private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5681456;
    // private static final int TRANSACTION_INDEX = 0;
    // private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c8045701150764657a31333337"
            + "bf85070001000000000000000353424400000000010000000000000003534244000000000a00000000000"
            + "00003535445454d000000e7c8045700011b682f18ada39a8194c5408bbd3a7b6185cd85e75f94e1d533f6"
            + "29c76a9ac5a1723257562c699d267f4428b17c682106f846fd973539abb55f7e2c7423cb48af5a";

    /**
     * <b>Attention:</b> This test class requires a valid posting key of the
     * used "voter". If no posting key is provided or the posting key is not
     * valid an Exception will be thrown. The private key is passed as a -D
     * parameter during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        LimitOrderCreate2Operation limitOrderCreate2Operation = new LimitOrderCreate2Operation();

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

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        // TODO
    }

    @Category({ IntegrationTest.class })
    @Test
    public void verifyTransaction() throws Exception {
        assertThat(steemApiWrapper.verifyAuthority(transaction), equalTo(true));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void getTransactionHex() throws Exception {
        assertThat(steemApiWrapper.getTransactionHex(transaction), equalTo(EXPECTED_TRANSACTION_HEX));
    }
}
