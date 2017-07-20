package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Graphene Chain "liquidity_balance" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LiquidityBalance {
    private AccountName account;
    // Original type is uint128_t.
    private BigInteger weight;

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
