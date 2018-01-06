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
package eu.bittrade.libs.steemj.plugins.apis.condenser.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.plugins.apis.tags.models.TagName;

/**
 * This class represents a Steem "tag_index" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TagIndex {
    @JsonProperty("trending")
    List<TagName> trending;
    /// --> pending payouts??

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected TagIndex() {
    }

    /**
     * @return the trending
     */
    public List<TagName> getTrending() {
        return trending;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
