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
package eu.bittrade.libs.steemj.plugins.apis.follow.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class implements the Steem "get_followers_return" object.
 * 
 * @author <a href="http://steemit.com/@paatrick">paatrick</a>
 */
public class GetFollowersReturn {
    @JsonProperty("followers")
    private List<FollowApiObject> followers;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetFollowersReturn() {
    }

    /**
     * Get the requested history for the requested account. The history is
     * represented by a list of all operations ever made by an account. The map
     * <code>key</code> represents the <code>id</code> of the operation and the
     * map <code>value</code> is the operation itself.
     * 
     * @return A map of operations and their id.
     */
    public List<FollowApiObject> getFollowers() {
        return followers;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
