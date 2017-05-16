package eu.bittrade.libs.steem.api.wrapper.models;

import java.text.ParseException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.util.SteemUtils;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlockHeader {
    // Original type is "block_id_type" which wraps a ripemd160 hash. We use an
    // byte array to cover this type.
    private byte[] previous;
    private long timestamp;
    private String witness;
    // Original type is "checksum_type" which wraps a ripemd160 hash. We use an
    // byte array to cover this type.
    @JsonProperty("transaction_merkle_root")
    private String transactionMerkleRoot;
    // TODO: Original type is block_header_extensions_type which is an array of
    // block_header_extensions.
    private Object[] extensions;

    /**
     * @return the previous
     */
    public byte[] getPrevious() {
        return previous;
    }

    /**
     * @param previous
     *            the previous to set
     */
    public void setPrevious(byte[] previous) {
        this.previous = previous;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     *            the timestamp to set
     */
    @JsonIgnore
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Additional setter to also handle dates provided as String. The date has
     * to be specified as String and needs a special format:
     * yyyy-MM-dd'T'HH:mm:ss
     * 
     * <p>
     * Example: "2016-08-08T12:24:17"
     * </p>
     * 
     * @param timestamp
     *            The creation date of this block in its String representation.
     * @throws ParseException
     *             If the given String does not patch the pattern.
     */
    public void setTimestamp(String timestamp) throws ParseException {
        this.timestamp = SteemUtils.transformStringToTimestamp(timestamp);
    }

    /**
     * @return the witness
     */
    public String getWitness() {
        return witness;
    }

    /**
     * @param witness
     *            the witness to set
     */
    public void setWitness(String witness) {
        this.witness = witness;
    }

    /**
     * @return the transactionMerkleRoot
     */
    public String getTransactionMerkleRoot() {
        return transactionMerkleRoot;
    }

    /**
     * @param transactionMerkleRoot
     *            the transactionMerkleRoot to set
     */
    public void setTransactionMerkleRoot(String transactionMerkleRoot) {
        this.transactionMerkleRoot = transactionMerkleRoot;
    }

    /**
     * @return the extensions
     */
    public Object[] getExtensions() {
        return extensions;
    }

    /**
     * @param extensions
     *            the extensions to set
     */
    public void setExtensions(Object[] extensions) {
        this.extensions = extensions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
