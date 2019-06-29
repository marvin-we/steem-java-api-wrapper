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
package eu.bittrade.libs.steemj.plugins.apis.follow.models.operations;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.protocol.operations.CustomJsonOperationPayload;

/**
 * This class represents the Steem "reblog_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ReblogOperation extends CustomJsonOperationPayload {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private Permlink permlink;

    /**
     * Create a new reblog operation to reblog a comment or a post.
     * <b>Attention</b>
     * <ul>
     * <li>This operation can't be broadcasted directly. It needs to be added as
     * a payload to a {@link CustomJsonOperation}.</li>
     * <li>Once broadcasted, this operation cannot be reverted.</li>
     * </ul>
     * 
     * @param account
     *            The account that wants to reblog the post or comment specified
     *            by the <code>permlink</code> and <code>author</code> (see
     *            {@link #setAccount(AccountName)}).
     * @param author
     *            The author that has written the comment or the post to resteem
     *            (see {@link #setAuthor(AccountName)}).
     * @param permlink
     *            The permlink of the comment or post to resteem (see
     *            {@link #setPermlink(Permlink)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public ReblogOperation(@JsonProperty("account") AccountName account, @JsonProperty("author") AccountName author,
            @JsonProperty("permlink") Permlink permlink) {
        this.setAccount(account);
        this.setAuthor(author);
        this.setPermlink(permlink);
    }

    /**
     * Create an empty reblog operation to reblog a comment or a post.
     * <b>Attention</b>
     * <ul>
     * <li>This operation can't be broadcasted directly. It needs to be added as
     * a payload to a {@link CustomJsonOperation}.</li>
     * <li>Once broadcasted, this operation cannot be reverted.</li>
     * </ul>
     */
    public ReblogOperation() {
    }

    /**
     * @return The account that resteemed the comment or the post.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Define the account whose want to reblog the post. <b>Notice:</b> The
     * private posting key of this account needs to be stored in the key
     * storage.
     * 
     * @param account
     *            The account who wants to reblog the post.
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    /**
     * @return The author of the post or comment to resteem.
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * @param author
     *            The author of the post or comment to resteem.
     */
    public void setAuthor(AccountName author) {
        this.author = author;
    }

    /**
     * Get the permanent link of the post or comment that has been resteemed.
     *
     * @return The permanent link of the post or comment that has been
     *         resteemed.
     */
    public Permlink getPermlink() {
        return permlink;
    }

    /**
     * Set the permanent link of the post or comment that should be resteemed.
     * 
     * @param permlink
     *            The permanent link of the post or comment that should be
     *            resteened.
     */
    public void setPermlink(Permlink permlink) {
        this.permlink = permlink;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)) {
            if (this.getAccount() == null) {
                throw new InvalidParameterException("The account cannot be null.");
            } else if (this.getAuthor() == null) {
                throw new InvalidParameterException("The author account cannot be null.");
            } else if (this.getPermlink() == null) {
                throw new InvalidParameterException("The permlink account cannot be null.");
            } else if (this.getAccount().equals(this.getAuthor())) {
                throw new InvalidParameterException("You cannot reblog your own content.");
            }
        }
    }
}
