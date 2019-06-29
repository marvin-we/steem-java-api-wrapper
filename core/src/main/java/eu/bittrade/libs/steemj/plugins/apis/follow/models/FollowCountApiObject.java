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
package eu.bittrade.libs.steemj.plugins.apis.follow.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "follow_count_api_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FollowCountApiObject {
    private AccountName account;
    // Original type is uint32_t.
    @JsonProperty("follower_count")
    private int followerCount;
    // Original type is uint32_t.
    @JsonProperty("following_count")
    private int followingCount;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected FollowCountApiObject() {
    }

    /**
     * @return The account which the {@link #getFollowerCount()
     *         getFollowerCount()} and {@link #getFollowingCount()
     *         getFollowingCount()} results belong to.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * @return The number of accounts following the {@link #getAccount()
     *         getAccount()}.
     */
    public int getFollowerCount() {
        return followerCount;
    }

    /**
     * @return The number of accounts the {@link #getAccount() getAccount()}
     *         account is following.
     */
    public int getFollowingCount() {
        return followingCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
