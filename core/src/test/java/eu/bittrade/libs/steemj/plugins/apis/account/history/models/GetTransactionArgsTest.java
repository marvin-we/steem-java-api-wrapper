package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

import java.security.InvalidParameterException;

import org.junit.Test;

import eu.bittrade.libs.steemj.protocol.TransactionId;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.plugins.apis.account.history.models.GetTransactionArgs
 * GetTransactionArgs} object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetTransactionArgsTest {
    /**
     * Test if the {@link GetTransactionArgs} fields are validated correctly.
     */
    @Test
    public void testFieldValidation() {
        TransactionId transactionId = new TransactionId("bd8069e6544f658da560b72e93b605dfe2cb0aaf");

        GetTransactionArgs getTransactionArgs = new GetTransactionArgs(transactionId);

        assertThat(getTransactionArgs.getId(), equalTo(transactionId));

        // Verify that an exception is thrown if required fields are not
        // provided:
        try {
            getTransactionArgs.setId(null);
            fail();
        } catch (InvalidParameterException e) {
            // Expected.
        }
    }
}
