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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class is used to wrap the C++ "pair" type properly.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class PostsPerAuthorPair {
    private AccountName account;
    private int numberOfPosts;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected PostsPerAuthorPair() {
    }

    /**
     * Private constructor used by Jackson to serialize a PostsPerAuthorPair
     * object.
     * 
     * @param rawPostsPerAuthorPairObject
     *            An array of objects while the first field contains an account
     *            name and the second field contains the number of posts on the
     *            requested blog.
     */
    @JsonCreator
    private PostsPerAuthorPair(@JsonProperty Object[] rawPostsPerAuthorPairObject) {
        this.account = new AccountName(rawPostsPerAuthorPairObject[0].toString());
        this.numberOfPosts = Integer.valueOf(rawPostsPerAuthorPairObject[1].toString());
    }

    /**
     * Get the author which also has posts on the requested blog.
     * 
     * @return The author which also has posts on the requested blog.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Get the number of posts written by the {@link #getAccount() account} on
     * the requested blog.
     * 
     * @return the numberOfPosts
     */
    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
