package eu.bittrade.libs.steem.api.wrapper.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steem.api.wrapper.BaseIntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.IntegrationTest;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.SignedBlockWithInfo;

/**
 * Verify the functionality of the "witness update operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessUpdateOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5717839;
    private static final int TRANSACTION_INDEX = 3;
    private static final int OPERATION_INDEX = 0;
    private static final String WITNESS_NAME = "glitterpig";
    private static final double FEE_AMOUNT = 0.0;
    private static final double ACCOUNT_CREATION_FEE = 3.0;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dceec80457010c0764657a313333370a"
            + "676f6f642d6b61726d610000011b74932d668952cbdf53423956b32800cfde661abbb87893a5642055848b7"
            + "74805583ceb968a5d267f79b3bd59325a8f7c7e7e4322dfd383383515187957bda8ec";

    /**
     * <b>Attention:</b> This test class requires a valid active key of the used
     * "account". If no active key is provided or the active key is not valid an
     * Exception will be thrown. The active key is passed as a -D parameter
     * during test execution.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
        
        AccountWitnessVoteOperation accountWitnessVoteOperation = new AccountWitnessVoteOperation();
        accountWitnessVoteOperation.setAccount(new AccountName("dez1337"));
        accountWitnessVoteOperation.setWitness(new AccountName("good-karma"));
        accountWitnessVoteOperation.setApprove(false);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(accountWitnessVoteOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        SignedBlockWithInfo blockContainingWitnessUpdateOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation witnessUpdateOperation = blockContainingWitnessUpdateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(witnessUpdateOperation, instanceOf(WitnessUpdateOperation.class));
        assertThat(((WitnessUpdateOperation) witnessUpdateOperation).getOwner().getAccountName(),
                equalTo(WITNESS_NAME));
        assertThat(((WitnessUpdateOperation) witnessUpdateOperation).getFee().getAmount(), equalTo(FEE_AMOUNT));
        assertThat(
                ((WitnessUpdateOperation) witnessUpdateOperation).getProperties().getAccountCreationFee().getAmount(),
                equalTo(ACCOUNT_CREATION_FEE));
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
