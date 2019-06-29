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

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.bittrade.libs.steemj.plugins.apis.account.history.models.deserializer.AppliedOperationHashMapDeserializer;

/**
 * This class implements the Steem "get_account_history_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetAccountHistoryReturn {
    @JsonProperty("history")
    @JsonDeserialize(using = AppliedOperationHashMapDeserializer.class)
    private Map<UInteger, AppliedOperation> history;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetAccountHistoryReturn() {
    }

    /**
     * Get the requested history for the requested account. The history is
     * represented by a list of all operations ever made by an account. The map
     * <code>key</code> represents the <code>id</code> of the operation and the
     * map <code>value</code> is the operation itself.
     * 
     * @return A map of operations and their id.
     */
    public Map<UInteger, AppliedOperation> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
