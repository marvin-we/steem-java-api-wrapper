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
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "get_discussions_by_author_before_date_args"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetDiscussionsByAuthorBeforeDateArgs {
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("start_permlink")
    private Permlink startPermlink;
    @JsonProperty("before_date")
    private TimePointSec beforeDate;
    @JsonProperty("limit")
    private UInteger limit;

    /**
     * 
     * @param author
     * @param startPermlink
     * @param beforeDate
     * @param limit
     */
    @JsonCreator
    public GetDiscussionsByAuthorBeforeDateArgs(@JsonProperty("author") AccountName author,
            @JsonProperty("start_permlink") Permlink startPermlink,
            @JsonProperty("before_date") TimePointSec beforeDate, @Nullable @JsonProperty("limit") UInteger limit) {
        this.setAuthor(author);
        this.setStartPermlink(startPermlink);
        this.setBeforeDate(beforeDate);
        this.setLimit(limit);
    }

    /**
     * @return the author
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setAuthor(AccountName author) {
        this.author = SteemJUtils.setIfNotNull(author, "The author needs to be provided.");
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
     * @return the beforeDate
     */
    public TimePointSec getBeforeDate() {
        return beforeDate;
    }

    /**
     * @param beforeDate
     *            the beforeDate to set
     */
    public void setBeforeDate(TimePointSec beforeDate) {
        this.beforeDate = SteemJUtils.setIfNotNull(beforeDate, "The beforeDate needs to be provided.");
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
    public void setLimit(@Nullable UInteger limit) {
        this.limit = SteemJUtils.setIfNotNull(limit, UInteger.valueOf(100));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
