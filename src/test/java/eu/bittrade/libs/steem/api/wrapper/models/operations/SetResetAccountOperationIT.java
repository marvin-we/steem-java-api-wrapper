package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;

/**
 * Verify the functionality of the "set reset account operation" under the use
 * of real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetResetAccountOperationIT extends BaseIntegrationTest {
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce9c80457012606737465656d6a0764"
            + "657a313333370764657a3133333700011c7102480199695aa22070a3a46c3c96bc9487ea23fa718374b7965"
            + "8cedbbdff3142e335b79633346d73576fe7c731728d26411ed81dabc4fc491606ada16d4b49";

    /**
     * <b>Attention:</b> This test class requires a valid owner key of the used
     * "account". If no owner key is provided or the owner key is not valid an
     * Exception will be thrown. The owner key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        SetResetAccountOperation setResetAccountOperation = new SetResetAccountOperation();

        setResetAccountOperation.setAccount(new AccountName("steemj"));
        setResetAccountOperation.setCurrentResetAccount(new AccountName("dez1337"));
        setResetAccountOperation.setResetAccount(new AccountName("dez1337"));

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(setResetAccountOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        // TODO: Implement
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
