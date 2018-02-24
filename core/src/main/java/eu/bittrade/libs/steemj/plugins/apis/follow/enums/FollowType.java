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
package eu.bittrade.libs.steemj.plugins.apis.follow.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.plugins.apis.follow.models.deserializer.FollowTypeDeserializer;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.serializer.FollowTypeSerializer;

/**
 * An enumeration for all existing follow types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonDeserialize(using = FollowTypeDeserializer.class)
@JsonSerialize(using = FollowTypeSerializer.class)
public enum FollowType {
    /** Used to unfollow someone. */
    UNDEFINED,
    /** Used to follow someone. */
    BLOG,
    /** Used to mute someone. */
    IGNORE
}
