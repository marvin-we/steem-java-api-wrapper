package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.bitcoinj.core.Utils;
import org.junit.Test;

import eu.bittrade.libs.steemj.base.models.AccountName;

/**
 * Test the AccountName object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class AccountNameTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "0764657a31333337";
    
    @Test
    public void testAccountNameToByteArray() throws Exception {
        AccountName myAccount = new AccountName("dez1337");

        assertThat("Expect that the accountName object has the given byte representation.",
                Utils.HEX.encode(myAccount.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }
}
