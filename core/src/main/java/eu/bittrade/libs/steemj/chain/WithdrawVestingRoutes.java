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
import org.joou.UShort;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "withdraw_vesting_route_object" object.
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class WithdrawVestingRoutes {
    // Original type is "id_type" so we use long here.
    @JsonProperty("id")
    private long id;
    @JsonProperty("from_account")
    private AccountName fromAccount;
    @JsonProperty("to_account")
    private AccountName toAccount;
    @JsonProperty("percent")
    private UShort percent;
    @JsonProperty("auto_vest")
    private boolean autoVest;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private WithdrawVestingRoutes() {
        this.percent = UShort.valueOf(0);
        this.autoVest = false;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the fromAccount
     */
    public AccountName getFromAccount() {
        return fromAccount;
    }

    /**
     * @return the toAccount
     */
    public AccountName getToAccount() {
        return toAccount;
    }

    /**
     * @return the percent
     */
    public UShort getPercent() {
        return percent;
    }

    /**
     * @return the autoVest
     */
    public boolean isAutoVest() {
        return autoVest;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
