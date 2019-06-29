/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
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
