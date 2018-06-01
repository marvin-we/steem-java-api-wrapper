package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

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
