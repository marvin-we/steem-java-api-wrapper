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

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.plugins.apis.database.models.Comment;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "comment_feed_entry" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentFeedEntry implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    private Comment comment;
    @JsonProperty("reblog_by")
    private List<AccountName> reblogBy;
    @JsonProperty("reblog_on")
    private TimePointSec reblogOn;
    // Original type is uint32_t.
    @JsonProperty("entry_id")
    private int entryId;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected CommentFeedEntry() {
    }

    /**
     * Get the whole content of the blog entry.
     * 
     * @return The whole content of the blog entry.
     */
    public Comment getComment() {
        return comment;
    }

    /**
     * @return The account names which reblogged this post.
     */
    public List<AccountName> getReblogBy() {
        return reblogBy;
    }

    /**
     * In case this blog entry is not written by the blog owner, but was
     * resteemed by the blog owner, this field contains the date when the blog
     * owner has resteemed this post. If the entry has not been resteemed, the
     * timestamp is set to 0 which results in <code>1970-01-01T00:00:00</code>
     * as the date.
     * 
     * @return The date when the blog entry has been restemmed by the blog
     *         owner.
     */
    public TimePointSec getReblogOn() {
        return reblogOn;
    }

    /**
     * Each blog entry has an id. The first posted or resteemed post of a blog
     * owner has the id 0. This id is incremented for each new post or resteem.
     * 
     * @return The id of the blog entry.
     */
    public int getEntryId() {
        return entryId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
