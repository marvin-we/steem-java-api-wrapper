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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "list_owner_histories_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListOwnerHistoriesArgs {
    // TODO: Original type is "fc::variant".
    @JsonProperty("start")
    private Object start;
    @JsonProperty("limit")
    private UInteger limit;

    /**
     * 
     * @param start
     * @param limit
     */
    @JsonCreator
    public ListOwnerHistoriesArgs(@JsonProperty("start") Object start, @JsonProperty("limit") UInteger limit) {
        this.setStart(start);
        this.setLimit(limit);
    }

    /**
     * @return the start
     */
    public Object getStart() {
        return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(Object start) {
        this.start = SteemJUtils.setIfNotNull(start, "Start must be provided.");
    }

    /**
     * @return the limit
     */
    public UInteger getLimit() {
        return limit;
    }

    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(UInteger limit) {
        this.limit = SteemJUtils.setIfNotNull(limit, "The limit must be provided.");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
