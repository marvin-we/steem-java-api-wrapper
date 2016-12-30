package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author http://steemit.com/@dez1337
 */
public class BlockHeader {
    private String previous;
    private String timestamp;
    private String witness;
    @JsonProperty("transaction_merkle_root")
    private String transactionMerkleRoot;
    // TODO: Look up how this object looks like.
    private Object[] extensions;

    public String getPrevious() {
        return previous;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getWitness() {
        return witness;
    }

    public String getTransactionMerkleRoot() {
        return transactionMerkleRoot;
    }

    public Object[] getExtensions() {
        return extensions;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
