package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Transaction {
    @JsonProperty("ref_block_num")
    private long refBlockNum;
    @JsonProperty("ref_block_prefix")
    private long refBlockPrefix;
    private String expiration;
    private List<Operation> operations;
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

    public List<Operation> getOperations() {
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
