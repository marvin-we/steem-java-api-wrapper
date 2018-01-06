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

/**
 * This class represents a Steem "change_recovery_account_request_object"
 * object.
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class ChangeRecoveryAccountRequest {
    // Original type is "id_type" so we use long here.
    @JsonProperty("id")
    private long id;
    @JsonProperty("account_to_recover")
    private AccountName accountToRecover;
    @JsonProperty("recovery_account")
    private AccountName recoveryAccount;
    @JsonProperty("effective_on")
    private TimePointSec effectiveOn;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ChangeRecoveryAccountRequest() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the accountToRecover
     */
    public AccountName getAccountToRecover() {
        return accountToRecover;
    }

    /**
     * @return the recoveryAccount
     */
    public AccountName getRecoveryAccount() {
        return recoveryAccount;
    }

    /**
     * @return the effectiveOn
     */
    public TimePointSec getEffectiveOn() {
        return effectiveOn;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
