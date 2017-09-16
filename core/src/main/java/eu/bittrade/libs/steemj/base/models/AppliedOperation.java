package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.operations.Operation;

/**
 * This class is the java implementation of the Steem "applied_operation"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AppliedOperation {
    @JsonProperty("trx_id")
    private TransactionId trxId;
    // Original type is uint32_t.
    private int block;
    // Original type is uint32_t.
    @JsonProperty("trx_in_block")
    private int trxInBlock;
    // Original type is uint16_t.
    @JsonProperty("op_in_trx")
    private int opInTrx;
    // Original type is uint64_t.
    @JsonProperty("virtual_op")
    private long virtualOp;
    private TimePointSec timestamp;
    private Operation op;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private AppliedOperation() {
    }

    /**
     * Get the id of this transaction.
     * 
     * @return The transaction id.
     */
    public TransactionId getTrxId() {
        return trxId;
    }

    /**
     * Get the block number.
     * 
     * @return The block number.
     */
    public int getBlock() {
        return block;
    }

    /**
     * Get the index of the transaction inside the block.
     * 
     * @return The transaction index in the block.
     */
    public int getTrxInBlock() {
        return trxInBlock;
    }

    /**
     * Get the index of the operation inside the transaction.
     * 
     * @return The operation index in the transaction.
     */
    public int getOpInTrx() {
        return opInTrx;
    }

    /**
     * Get the index of the virtual operation inside the transaction.
     * 
     * @return The virtual operation index in the transaction.
     */
    public long getVirtualOp() {
        return virtualOp;
    }

    /**
     * Get the time point at which this transaction has been submitted.
     * 
     * @return The submition date and time.
     */
    public TimePointSec getTimestamp() {
        return timestamp;
    }

    /**
     * Get the whole operation object.
     * 
     * @return The operation object.
     */
    public Operation getOp() {
        return op;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
