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
package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

/**
 * This class represents a Steem "api_savings_withdraw_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SavingsWithdraw {
    // Original type is "savings_withdraw_id_type".
    @JsonProperty("id")
    private long id;
    @JsonProperty("from")
    private AccountName from;
    @JsonProperty("to")
    private AccountName to;
    @JsonProperty("memo")
    private String memo;
    @JsonProperty("request_id")
    private UInteger requestId;
    @JsonProperty("amount")
    private Asset amount;
    @JsonProperty("complete")
    private TimePointSec complete;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private SavingsWithdraw() {
        this.requestId = UInteger.valueOf(0);
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the from
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * @return the to
     */
    public AccountName getTo() {
        return to;
    }

    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @return the requestId
     */
    public UInteger getRequestId() {
        return requestId;
    }

    /**
     * @return the amount
     */
    public Asset getAmount() {
        return amount;
    }

    /**
     * @return the complete
     */
    public TimePointSec getComplete() {
        return complete;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
