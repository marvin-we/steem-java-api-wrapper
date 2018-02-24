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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Authority;
import eu.bittrade.libs.steemj.protocol.PublicKey;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AuthorityTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "060000000001032c5f5ef5d71e5196b0831c6683db997b6c95ce5e13d52"
            + "ce5bf78d5dd3aa731550100";

    /**
     * Test the {@link eu.bittrade.libs.steemj.protocol.Authority#toByteArray()
     * toByteArray()} method.
     * 
     * @throws Exception
     *             In case of a problem.
     */
    @Test
    public void testSteemChainPropertiesToByteArray() throws Exception {
        Authority exampleAuthority = new Authority();
        exampleAuthority.setAccountAuths(new HashMap<AccountName, Integer>());
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("STM7Amy3akYfmSY92YrxYxfEGfc1pe3ctJtWjRi1wfo66K2e9veCN"), 1);
        exampleAuthority.setKeyAuths(postingKeyAuth);
        exampleAuthority.setWeightThreshold(6);

        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(exampleAuthority.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));

    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.protocol.Authority#equals(Object)
     * equals(Object)} method.
     */
    @Test
    public void testAuthorityEqualsMethod() {
        Authority authorityOne = new Authority();
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("STM7Amy3akYfmSY92YrxYxfEGfc1pe3ctJtWjRi1wfo66K2e9veCN"), 1);
        authorityOne.setKeyAuths(postingKeyAuth);
        Map<AccountName, Integer> accountAuths = new HashMap<>();
        accountAuths.put(new AccountName("dez1337"), 2);
        authorityOne.setAccountAuths(accountAuths);
        authorityOne.setWeightThreshold(1);

        Authority authorityTwo = new Authority();
        authorityTwo.setKeyAuths(postingKeyAuth);
        authorityTwo.setAccountAuths(accountAuths);
        authorityTwo.setWeightThreshold(1);

        assertTrue(authorityOne.equals(authorityTwo));

        authorityTwo.setWeightThreshold(2);

        assertFalse(authorityOne.equals(authorityTwo));

    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.protocol.Authority#isEmpty()}
     * method.
     */
    @Test
    public void testAuthorityIsEmptyMethod() {
        Authority exampleAuthority = new Authority();
        assertTrue(exampleAuthority.isEmpty());

        exampleAuthority = new Authority();
        exampleAuthority.setWeightThreshold(1);
        assertTrue(exampleAuthority.isEmpty());

        exampleAuthority = new Authority();
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("STM7Amy3akYfmSY92YrxYxfEGfc1pe3ctJtWjRi1wfo66K2e9veCN"), 1);
        exampleAuthority.setKeyAuths(postingKeyAuth);
        assertFalse(exampleAuthority.isEmpty());

        exampleAuthority = new Authority();
        Map<AccountName, Integer> accountAuths = new HashMap<>();
        accountAuths.put(new AccountName("dez1337"), 2);
        exampleAuthority.setAccountAuths(accountAuths);
        assertFalse(exampleAuthority.isEmpty());

        exampleAuthority = new Authority();
        exampleAuthority.setKeyAuths(postingKeyAuth);
        exampleAuthority.setAccountAuths(accountAuths);
        assertFalse(exampleAuthority.isEmpty());
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.protocol.Authority#hashCode()}
     * method.
     */
    @Test
    public void testAuthorityHashCodeMethod() {
        Authority exampleAuthority = new Authority();
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("STM7Amy3akYfmSY92YrxYxfEGfc1pe3ctJtWjRi1wfo66K2e9veCN"), 1);
        exampleAuthority.setKeyAuths(postingKeyAuth);
        Map<AccountName, Integer> accountAuths = new HashMap<>();
        accountAuths.put(new AccountName("dez1337"), 2);
        exampleAuthority.setAccountAuths(accountAuths);
        exampleAuthority.setWeightThreshold(1);

        assertThat(exampleAuthority.hashCode(), equalTo(390728456));
    }
}
