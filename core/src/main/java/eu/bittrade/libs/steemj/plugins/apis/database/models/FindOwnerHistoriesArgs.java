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
package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "find_owner_histories_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindOwnerHistoriesArgs {
    @JsonProperty("owner")
    private AccountName owner;

    /**
     * 
     * @param owner
     */
    @JsonCreator
    public FindOwnerHistoriesArgs(@JsonProperty("owner") AccountName owner) {
        this.setOwner(owner);
    }

    /**
     * @return the owner
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(AccountName owner) {
        this.owner = SteemJUtils.setIfNotNull(owner, "The owner needs to be provided.");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
