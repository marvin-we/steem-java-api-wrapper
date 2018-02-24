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
package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.security.InvalidParameterException;

import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * Test the AccountName object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class AccountNameTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "0764657a31333337";

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.protocol.AccountName#toByteArray()
     * toByteArray()} method.
     * 
     * @throws Exception
     *             In case of a problem.
     */
    @Test
    public void testAccountNameToByteArray() throws Exception {
        AccountName myAccount = new AccountName("dez1337");

        assertThat("Expect that the accountName object has the given byte representation.",
                CryptoUtils.HEX.encode(myAccount.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.AccountName#setName(String name)
     * setName(String name)} method by providing an account name which is too
     * long.
     */
    @Test(expected = InvalidParameterException.class)
    public void testAccountNameValidationTooLong() {
        new AccountName("thisaccountnameistoolong");
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.AccountName#setName(String name)
     * setName(String name)} method by providing an account name which is too
     * short.
     */
    @Test(expected = InvalidParameterException.class)
    public void testAccountNameValidationTooShort() {
        new AccountName("sh");
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.AccountName#setName(String name)
     * setName(String name)} method by providing an account name with an invalid
     * start character.
     */
    @Test(expected = InvalidParameterException.class)
    public void testAccountNameValidationWrongStartChar() {
        new AccountName("-dez1337");
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.AccountName#setName(String name)
     * setName(String name)} method by providing an account name with an invalid
     * end character.
     */
    @Test(expected = InvalidParameterException.class)
    public void testAccountNameValidationWrongEndChar() {
        new AccountName("dez1337-");
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.protocol.AccountName#setName(String name)
     * setName(String name)} method by providing valid account names.
     */
    @Test
    public void testAccountNameValidation() {
        new AccountName("dez-1337");
        new AccountName("dez");
        new AccountName("dez1337-steemj");
    }
}
