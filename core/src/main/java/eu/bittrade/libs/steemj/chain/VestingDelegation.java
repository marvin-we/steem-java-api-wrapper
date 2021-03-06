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
package eu.bittrade.libs.steemj.chain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.LegacyAsset;

/**
 * This class represents a Steem "vesting_delegation_object" object.
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class VestingDelegation {
    // Original type is "id_type" so we use long here.
    @JsonProperty("id")
    private long id;
    @JsonProperty("delegator")
    private AccountName delegator;
    @JsonProperty("delegatee")
    private AccountName delegatee;
    @JsonProperty("vesting_shares")
    private LegacyAsset vestingShares;
    @JsonProperty("min_delegation_time")
    private TimePointSec minDelegationTime;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private VestingDelegation() {
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
     * @return the delegatee
     */
    public AccountName getDelegatee() {
        return delegatee;
    }

    /**
     * @param delegatee
     *            the delegatee to set
     */
    public void setDelegatee(AccountName delegatee) {
        this.delegatee = delegatee;
    }

    /**
     * @return the vestingShares
     */
    public LegacyAsset getVestingShares() {
        return vestingShares;
    }

    /**
     * @param vestingShares
     *            the vestingShares to set
     */
    public void setVestingShares(LegacyAsset vestingShares) {
        this.vestingShares = vestingShares;
    }

    /**
     * @return the minDelegationTime
     */
    public TimePointSec getMinDelegationTime() {
        return minDelegationTime;
    }

    /**
     * @param minDelegationTime
     *            the minDelegationTime to set
     */
    public void setMinDelegationTime(TimePointSec minDelegationTime) {
        this.minDelegationTime = minDelegationTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
