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

import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.plugins.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class implements the Steem "get_followers_args" object.
 * 
 * @author <a href="http://steemit.com/@paatrick">paatrick</a>
 */
public class GetFollowersArgs {

    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("start")
    private AccountName start;
    @JsonProperty("type")
    private FollowType type;
    @JsonProperty("limit")
    private UInteger limit;

    public GetFollowersArgs(@JsonProperty("account") AccountName account, @JsonProperty("start") AccountName start,
            @JsonProperty("type") FollowType type, @JsonProperty("limit") UInteger limit) {
        this.setAccount(account);
        this.setStart(start);
        this.setType(type);
        this.setLimit(limit);
    }

    public AccountName getAccount() {
        return account;
    }

    public void setAccount(AccountName account) {
        this.account = account;
    }

    public AccountName getStart() {
        return start;
    }

    public void setStart(AccountName start) {
        this.start = start;
    }

    public FollowType getType() {
        return type;
    }

    public void setType(FollowType type) {
        this.type = type;
    }

    public UInteger getLimit() {
        return limit;
    }

    public void setLimit(UInteger limit) {
        this.limit = SteemJUtils.setIfNotNull(limit, UInteger.valueOf(1000));
    }

}
