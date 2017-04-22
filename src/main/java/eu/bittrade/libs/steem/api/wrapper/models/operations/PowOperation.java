package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.models.ChainProperties;
import eu.bittrade.libs.steem.api.wrapper.models.Work;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class PowOperation extends Operation {
    @JsonProperty("worker_account")
    private String workerAccount;
    @JsonProperty("block_id")
    private String blockId;
    @JsonProperty("nonce")
    private BigInteger nonce;
    @JsonProperty("work")
    private Work work;
    @JsonProperty("props")
    private ChainProperties properties;

    public PowOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String getWorkerAccount() {
        return workerAccount;
    }

    public String getBlockId() {
        return blockId;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public Work getWork() {
        return work;
    }

    public ChainProperties getProperties() {
        return properties;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
