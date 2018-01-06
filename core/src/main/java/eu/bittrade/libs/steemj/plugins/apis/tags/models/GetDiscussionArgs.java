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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "get_discussion_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetDiscussionArgs {
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private Permlink permlink;

    /**
     * 
     * @param author
     * @param permlink
     */
    @JsonCreator
    public GetDiscussionArgs(@JsonProperty("author") AccountName author, @JsonProperty("permlink") Permlink permlink) {
        this.setAuthor(author);
        this.setPermlink(permlink);
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
        this.author = SteemJUtils.setIfNotNull(author, "The account needs to be provided.");
    }

    /**
     * @return the permlink
     */
    public Permlink getPermlink() {
        return permlink;
    }

    /**
     * @param permlink
     *            the permlink to set
     */
    public void setPermlink(Permlink permlink) {
        this.permlink = SteemJUtils.setIfNotNull(permlink, "The permlink needs to be provided.");
        ;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
