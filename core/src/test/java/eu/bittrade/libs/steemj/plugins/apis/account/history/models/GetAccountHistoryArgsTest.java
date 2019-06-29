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

import org.joou.UInteger;
import org.joou.ULong;
import org.junit.Test;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.plugins.apis.account.history.models.GetAccountHistoryArgs
 * GetAccountHistoryArgs} object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetAccountHistoryArgsTest {
    /**
     * Test if the {@link GetAccountHistoryArgs} fields are validated correctly.
     */
    @Test
    public void testFieldValidation() {
        AccountName accountName = new AccountName("dez1337");
        ULong start = ULong.valueOf(20);
        UInteger limit = UInteger.valueOf(10);

        GetAccountHistoryArgs getAccountHistoryArgs = new GetAccountHistoryArgs(accountName, start, limit);

        assertThat(getAccountHistoryArgs.getAccount(), equalTo(accountName));
        assertThat(getAccountHistoryArgs.getStart(), equalTo(start));
        assertThat(getAccountHistoryArgs.getLimit(), equalTo(limit));

        // Check if default values are applied correctly if <code>null</code>
        // values are provided:
        getAccountHistoryArgs.setLimit(null);
        getAccountHistoryArgs.setStart(null);

        assertThat(getAccountHistoryArgs.getStart(), equalTo(ULong.valueOf(-1)));
        assertThat(getAccountHistoryArgs.getLimit(), equalTo(UInteger.valueOf(1000)));

        // Verify that an exception is thrown if required fields are not
        // provided:
        try {
            getAccountHistoryArgs.setAccount(null);
            fail();
        } catch (InvalidParameterException e) {
            // Expected.
        }
    }
}
