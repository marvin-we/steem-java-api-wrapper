package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Transaction {
    @JsonProperty("ref_block_num")
    private long refBlockNum;
    @JsonProperty("ref_block_prefix")
    private long refBlockPrefix;
    private String expiration;
    // TODO Same question as in AccountActivity.java: Look up how this object
    // looks like.
    private Object[] operations;
    private Object[] extensions;
    private Object[] signatures;

    public long getRefBlockNum() {
        return refBlockNum;
    }

    public long getRefBlockPrefix() {
        return refBlockPrefix;
    }

    public String getExpiration() {
        return expiration;
    }

    public Object[] getOperations() {
        return operations;
    }

    public Object[] getExtensions() {
        return extensions;
    }

    public Object[] getSignatures() {
        return signatures;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
