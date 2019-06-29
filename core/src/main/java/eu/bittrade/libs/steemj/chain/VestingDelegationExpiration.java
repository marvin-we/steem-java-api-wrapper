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
package eu.bittrade.libs.steemj.chain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

/**
 * This class represents a Steem "vesting_delegation_expiration_object" object.
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class VestingDelegationExpiration {
    // Original type is "id_type" so we use long here.
    @JsonProperty("id")
    private long id;
    @JsonProperty("delegator")
    private AccountName delegator;
    @JsonProperty("vesting_shares")
    private Asset vestingShares;
    @JsonProperty("expiration")
    private TimePointSec expiration;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private VestingDelegationExpiration() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the delegator
     */
    public AccountName getDelegator() {
        return delegator;
    }

    /**
     * @param delegator
     *            the delegator to set
     */
    public void setDelegator(AccountName delegator) {
        this.delegator = delegator;
    }

    /**
     * @return the vestingShares
     */
    public Asset getVestingShares() {
        return vestingShares;
    }

    /**
     * @param vestingShares
     *            the vestingShares to set
     */
    public void setVestingShares(Asset vestingShares) {
        this.vestingShares = vestingShares;
    }

    /**
     * @return the expiration
     */
    public TimePointSec getExpiration() {
        return expiration;
    }

    /**
     * @param expiration
     *            the expiration to set
     */
    public void setExpiration(TimePointSec expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
