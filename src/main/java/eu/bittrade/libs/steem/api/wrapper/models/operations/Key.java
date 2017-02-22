package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Key {
    @JsonProperty("weight_threshold")
    private short weightThreshold;
    // TODO: Find out what this field is used for.
    @JsonProperty("account_auths")
    private Object[] accountAuths;
    // TODO: Find out what this field is used for.
    @JsonProperty("key_auths")
    private Object[] keyAuths;

    public short getWeightThreshold() {
        return weightThreshold;
    }

    public Object[] getAccountAuths() {
        return accountAuths;
    }

    public Object[] getKeyAuths() {
        return keyAuths;
    }
}
