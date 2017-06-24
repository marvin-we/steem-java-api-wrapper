package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is the java implementation of the Steem "signed_block_with_info"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SignedBlockWithInfo extends SignedBlock {
    // TODO: Original type is "block_id_type".
    @JsonProperty("block_id")
    private byte[] blockId;
    @JsonProperty("signing_key")
    private PublicKey signingKey;
    // TODO: Original type is "vector< transaction_id_type > ".
    @JsonProperty("transaction_ids")
    private List<byte[]> transactionIds;

    /**
     * @return the blockId
     */
    public byte[] getBlockId() {
        return blockId;
    }

    /**
     * @param blockId
     *            the blockId to set
     */
    public void setBlockId(byte[] blockId) {
        this.blockId = blockId;
    }

    /**
     * @return the signingKey
     */
    public PublicKey getSigningKey() {
        return signingKey;
    }

    /**
     * @param signingKey
     *            the signingKey to set
     */
    public void setSigningKey(PublicKey signingKey) {
        this.signingKey = signingKey;
    }

    /**
     * @return the transactionIds
     */
    public List<byte[]> getTransactionIds() {
        return transactionIds;
    }

    /**
     * @param transactionIds
     *            the transactionIds to set
     */
    public void setTransactionIds(List<byte[]> transactionIds) {
        this.transactionIds = transactionIds;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
