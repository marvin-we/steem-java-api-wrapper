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

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class implements the Steem "get_ops_in_block_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetOpsInBlockReturn {
    @JsonProperty("ops")
    private List<AppliedOperation> operations;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetOpsInBlockReturn() {
    }

    /**
     * Get the list of {@link AppliedOperation AppliedOperations} returned from
     * the Steem Node.
     * 
     * @return A list of {@link AppliedOperation AppliedOperations}.
     */
    public List<AppliedOperation> getOperations() {
        return operations;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
