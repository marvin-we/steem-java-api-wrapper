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
package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "get_replies_by_last_update_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetRepliesByLastUpdateArgs {
    @JsonProperty("start_parent_author")
    private AccountName startParentAuthor;
    @JsonProperty("start_permlink")
    private Permlink startPermlink;
    @JsonProperty("limit")
    private UInteger limit;

    /**
     * 
     * @param startParentAuthor
     * @param startPermlink
     * @param limit
     */
    @JsonCreator
    public GetRepliesByLastUpdateArgs(@JsonProperty("start_parent_author") AccountName startParentAuthor,
            @JsonProperty("start_permlink") Permlink startPermlink, @Nullable @JsonProperty("limit") UInteger limit) {
        this.setStartParentAuthor(startParentAuthor);
        this.setStartPermlink(startPermlink);
        this.setLimit(limit);
    }

    /**
     * @return the startParentAuthor
     */
    public AccountName getStartParentAuthor() {
        return startParentAuthor;
    }

    /**
     * @param startParentAuthor
     *            the startParentAuthor to set
     */
    public void setStartParentAuthor(AccountName startParentAuthor) {
        this.startParentAuthor = SteemJUtils.setIfNotNull(startParentAuthor,
                "The startParentAuthor needs to be provided.");
    }

    /**
     * @return the startPermlink
     */
    public Permlink getStartPermlink() {
        return startPermlink;
    }

    /**
     * @param startPermlink
     *            the startPermlink to set
     */
    public void setStartPermlink(Permlink startPermlink) {
        this.startPermlink = SteemJUtils.setIfNotNull(startPermlink, "The startPermlink needs to be provided.");
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
        this.limit = SteemJUtils.setIfNotNull(limit, UInteger.valueOf(100));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
