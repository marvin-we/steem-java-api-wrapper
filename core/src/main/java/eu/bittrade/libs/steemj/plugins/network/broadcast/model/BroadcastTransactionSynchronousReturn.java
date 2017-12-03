package eu.bittrade.libs.steemj.plugins.network.broadcast.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.TransactionId;

/**
 * This class represents a Steem "broadcast_transaction_synchronous_return"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BroadcastTransactionSynchronousReturn {
    @JsonProperty("id")
    private TransactionId id;
    @JsonProperty("block_num")
    private int blockNum = 0;
    @JsonProperty("trx_num")
    private int trxNum = 0;
    @JsonProperty("expired")
    private boolean expired = false;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected BroadcastTransactionSynchronousReturn() {
    }

    /**
     * Get the Id of the applied transaction.
     * 
     * @return The transaction Id.
     */
    public TransactionId getId() {
        return id;
    }

    /**
     * Get the block number the applied transaction has been processed with.
     * 
     * @return The block number.
     */
    public int getBlockNum() {
        return blockNum;
    }

    /**
     * Get the transaction number inside the block.
     * 
     * @return The transaction number.
     */
    public int getTrxNum() {
        return trxNum;
    }

    /**
     * Check if the applied transaction is already expired.
     * 
     * @return <code>True</code> if the transaction is already expired or
     *         <code>false</code> if not.
     */
    public boolean isExpired() {
        return expired;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
