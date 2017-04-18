package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LiquidityQueueEntry {
    private String account;
    private int weight;

    public String getAccount() {
        return account;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
