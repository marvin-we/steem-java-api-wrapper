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
package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.protocol.Asset;

/**
 * This class represents a Steem "tag_api_obj" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Tag implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    /** The name of the tag. */
    @JsonProperty("name")
    private String name;
    @JsonProperty("total_payouts")
    /** The total payouts for posts and comments using this tag. */
    private Asset totalPayouts;
    @JsonProperty("net_votes")
    /** The number of votes made for posts and comments which used this tag. */
    private Integer netVotes;
    @JsonProperty("top_posts")
    /** The number of posts that made it to a "top post" and used this tag. */
    private UInteger topPosts;
    @JsonProperty("comments")
    private UInteger comments;
    // Original type is "fc::uint128" so we use BigInteger here.
    @JsonProperty("trending")
    private BigInteger trending;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Tag() {
    }

    /**
     * @return The tag name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The sum of the paid amounts for posts using this tag.
     */
    public Asset getTotalPayouts() {
        return totalPayouts;
    }

    /**
     * @return The number of votes.
     */
    public int getNetVotes() {
        return netVotes;
    }

    /**
     * @return The number of top posts.
     */
    public UInteger getTopPosts() {
        return topPosts;
    }

    /**
     * @return The number of comments.
     */
    public UInteger getComments() {
        return comments;
    }

    /**
     * @return Trending.
     */
    public BigInteger getTrending() {
        return trending;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
