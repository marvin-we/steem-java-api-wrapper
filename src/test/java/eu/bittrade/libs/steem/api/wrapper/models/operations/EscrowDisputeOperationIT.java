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
import eu.bittrade.libs.steem.api.wrapper.models.Block;

/**
 * Verify the functionality of the "escrow dispute operation" under the use of
 * real api calls.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowDisputeOperationIT extends BaseIntegrationTest {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 9543384;
    private static final int TRANSACTION_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_FROM = "anonymtest";
    private static final int EXPECTED_ESCROW_ID = 72526562;
    private static final String EXPECTED_TRANSACTION_HEX = "f68585abf4dce8c80457011c0764657a31333337076"
            + "4657a3133333706737465656d6a0764657a313333372200000000011c2deb8ab73f8e68ad02b700291a237e8"
            + "dcd918d54b0259ba51d887de8726c653f306f27c86f76891c070a84f1979935afdba31ed2a08a896b73badfc" + "4b1050b16";

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();

        EscrowDisputeOperation escrowDisputeOperation = new EscrowDisputeOperation();

        escrowDisputeOperation.setAgent(new AccountName("steemj"));
        escrowDisputeOperation.setFrom(new AccountName("dez1337"));
        escrowDisputeOperation.setTo(new AccountName("dez1337"));
        escrowDisputeOperation.setWho(new AccountName("dez1337"));
        escrowDisputeOperation.setEscrowId(34);

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(escrowDisputeOperation);

        transaction.setOperations(operations);
        transaction.sign();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testOperationParsing() throws SteemCommunicationException {
        Block blockContainingEscrowDisputeOperation = steemApiWrapper.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION);

        Operation escrowDisputeOperation = blockContainingEscrowDisputeOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(escrowDisputeOperation, instanceOf(EscrowDisputeOperation.class));
        assertThat(((EscrowDisputeOperation) escrowDisputeOperation).getFrom().getAccountName(),
                equalTo(EXPECTED_FROM));
        assertThat(((EscrowDisputeOperation) escrowDisputeOperation).getEscrowId(), equalTo(EXPECTED_ESCROW_ID));
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
