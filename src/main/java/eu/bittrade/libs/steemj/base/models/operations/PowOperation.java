package eu.bittrade.libs.steemj.base.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.Pow;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class represents the Steem "pow_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class PowOperation extends Operation {
    @JsonProperty("worker_account")
    private AccountName workerAccount;
    @JsonProperty("block_id")
    // Original type is "checksum_type" which wraps a ripemd160 hash. We use an
    // byte array to cover this type.
    private String blockId;
    // Original type is uint64_t.
    @JsonProperty("nonce")
    private long nonce;
    @JsonProperty("work")
    private Pow work;
    @JsonProperty("props")
    private ChainProperties properties;

    /**
     * Create a mew pow operation.
     */
    public PowOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
        // Set default values:
        this.setNonce(0);
    }

    /**
     * @return the workerAccount
     */
    public AccountName getWorkerAccount() {
        return workerAccount;
    }

    /**
     * @param workerAccount
     *            the workerAccount to set
     */
    public void setWorkerAccount(AccountName workerAccount) {
        this.workerAccount = workerAccount;
    }

    /**
     * @return the blockId
     */
    public String getBlockId() {
        return blockId;
    }

    /**
     * @param blockId
     *            the blockId to set
     */
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    /**
     * @return the nonce
     */
    public long getNonce() {
        return nonce;
    }

    /**
     * @param nonce
     *            the nonce to set
     */
    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    /**
     * @return the work
     */
    public Pow getWork() {
        return work;
    }

    /**
     * @param work
     *            the work to set
     */
    public void setWork(Pow work) {
        this.work = work;
    }

    /**
     * @return the properties
     */
    public ChainProperties getProperties() {
        return properties;
    }

    /**
     * @param properties
     *            the properties to set
     */
    public void setProperties(ChainProperties properties) {
        this.properties = properties;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
