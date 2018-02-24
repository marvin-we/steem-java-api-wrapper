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
package eu.bittrade.libs.steemj.plugins.apis.witness.models.operations;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.protocol.operations.CustomJsonOperationPayload;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "enable_content_editing_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EnableContentEditingOperation extends CustomJsonOperationPayload {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("relock_time")
    private TimePointSec relockTime;

    /**
     * Use this operation to enable the editing of posts who are already
     * disabled.
     * 
     * The {@link CustomJsonOperation} that will carry this operation needs to
     * be signed with the active key of the <code>account</code> to prevent
     * stolen posting keys from being used to deface old content.
     * 
     * @param account
     *            The account to enable the editing for.
     * @param relockTime
     *            The time when editing is locked again.
     */
    @JsonCreator
    public EnableContentEditingOperation(@JsonProperty("account") AccountName account,
            @JsonProperty("relock_time") TimePointSec relockTime) {
        this.setAccount(account);
        this.setRelockTime(relockTime);
    }

    /**
     * Get the account whose posts are editable again.
     * 
     * @return The account whose posts are editable again.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Set the <code>account</code> whose posts should be editable again.
     * 
     * @param account
     *            The account whose posts should be editable again.
     * @throws InvalidParameterException
     *             If the <code>account</code> is <code>null</code>.
     */
    public void setAccount(AccountName account) {
        this.account = SteemJUtils.setIfNotNull(account, "The account cannot be null");
    }

    /**
     * Get the point in time when posts are locked again.
     * 
     * @return The point in time when posts are locked again.
     */
    public TimePointSec getRelockTime() {
        return relockTime;
    }

    /**
     * Set the point in time when posts are locked again.
     * 
     * @param relockTime
     *            The point in time when posts are locked again.
     * @throws InvalidParameterException
     *             If the <code>relockTime</code> is <code>null</code>.
     */
    public void setRelockTime(TimePointSec relockTime) {
        this.relockTime = SteemJUtils.setIfNotNull(relockTime, "The relock time cannot be null");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public void validate(ValidationType validationType) {
        // No additional validation required.
    }
}
