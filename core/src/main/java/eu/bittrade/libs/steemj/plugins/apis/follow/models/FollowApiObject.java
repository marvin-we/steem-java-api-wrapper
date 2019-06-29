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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.plugins.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "follow_api_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FollowApiObject implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    private AccountName follower;
    private AccountName following;
    private List<FollowType> what;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected FollowApiObject() {
    }

    /**
     * @return The account which is following {@link #getFollowing()}.
     */
    public AccountName getFollower() {
        return follower;
    }

    /**
     * @return The account which is followed by {@link #getFollower()}.
     */
    public AccountName getFollowing() {
        return following;
    }

    /**
     * @return The variant of the follow (see
     *         {@link eu.bittrade.libs.steemj.plugins.apis.follow.enums.FollowType
     *         FollowType}.
     */
    public List<FollowType> getWhat() {
        return what;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
