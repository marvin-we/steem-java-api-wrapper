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
package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

import java.util.List;

import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class implements the Steem "enum_virtual_ops_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EnumVirtualOps {
    @JsonProperty("ops")
    private List<AppliedOperation> operations;
    @JsonProperty("next_block_range_begin")
    private UInteger nextBlockRangeBegin;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private EnumVirtualOps() {
    }

    /**TODO
     * @return the operations
     */
    public List<AppliedOperation> getOperations() {
        return operations;
    }

    /**TODO
     * @return the nextBlockRangeBegin
     */
    public UInteger getNextBlockRangeBegin() {
        return nextBlockRangeBegin;
    }
}
