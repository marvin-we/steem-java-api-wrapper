package eu.bittrade.libs.steemj.base.models.operations;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.Checksum;
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
    private Checksum blockId;
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
        super(false);
        // Set default values:
        this.setNonce(BigInteger.valueOf(0));
    }

    /**
     * @return the workerAccount
     */
    public AccountName getWorkerAccount() {
        return workerAccount;
    }

    /**
     * <b>Notice:</b> The private active key of this account needs to be stored
     * in the key storage.
     * 
     * @param workerAccount
     *            the workerAccount to set
     */
    public void setWorkerAccount(AccountName workerAccount) {
        this.workerAccount = workerAccount;

        // Update the List of required private key types.
        addRequiredPrivateKeyType(workerAccount, PrivateKeyType.ACTIVE);
    }

    /**
     * @return the blockId
     */
    public Checksum getBlockId() {
        return blockId;
    }

    /**
     * @param blockId
     *            the blockId to set
     */
    public void setBlockId(Checksum blockId) {
        this.blockId = blockId;
    }

    /**
     * @return the nonce
     */
    public BigInteger getNonce() {
        return new BigInteger(Long.toUnsignedString(this.nonce));
    }

    /**
     * @param nonce
     *            the nonce to set
     */
    public void setNonce(BigInteger nonce) {
        this.nonce = Long.parseUnsignedLong(nonce.toString());
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
