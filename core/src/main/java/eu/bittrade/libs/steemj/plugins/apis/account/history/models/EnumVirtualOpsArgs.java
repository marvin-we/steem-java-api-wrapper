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

import org.apache.commons.lang3.Validate;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class implements the Steem "enum_virtual_ops_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EnumVirtualOpsArgs {
    @JsonProperty("block_range_begin")
    private UInteger blockRangeBegin;
    @JsonProperty("block_range_end")
    private UInteger blockRangeEnd;

    public static class Builder {
        private UInteger blockRangeBegin;
        private UInteger blockRangeEnd;

        public Builder() {
            this.blockRangeBegin = UInteger.valueOf(1);
            this.blockRangeEnd = UInteger.valueOf(2);
        }

        public Builder startAt(UInteger startBlock) {
            this.blockRangeBegin = Validate.notNull(startBlock);
            return this;
        }

        public Builder endAt(UInteger endBlock) {
            this.blockRangeEnd = Validate.notNull(endBlock);
            return this;
        }

        public EnumVirtualOpsArgs build() {
            EnumVirtualOpsArgs enumVirtualOpsArgs = new EnumVirtualOpsArgs();
            enumVirtualOpsArgs.blockRangeBegin = this.blockRangeBegin;
            enumVirtualOpsArgs.blockRangeEnd = this.blockRangeEnd;
            return enumVirtualOpsArgs;
        }
    }

    @JsonCreator()
    private EnumVirtualOpsArgs() {

    }

}
