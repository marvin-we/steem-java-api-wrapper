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
 *     along with SteemJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.follow.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "account_reputation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountReputation {
    private AccountName account;
    // Original type is "share_type" which is a "safe<int64_t>".
    private long reputation;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected AccountReputation() {
    }

    /**
     * Get the name of the account.
     * 
     * @return The name of the account.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Get the reputation of the requested {@link #getAccount()}.
     * 
     * @return The reputation of the requested {@link #getAccount()}.
     */
    public long getReputation() {
        return reputation;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
