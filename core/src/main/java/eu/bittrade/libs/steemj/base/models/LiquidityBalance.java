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
package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Graphene Chain "liquidity_balance" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LiquidityBalance implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    private AccountName account;
    // Original type is uint128_t.
    private BigInteger weight;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private LiquidityBalance() {
    }

    public AccountName getAccount() {
        return account;
    }

    public BigInteger getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
