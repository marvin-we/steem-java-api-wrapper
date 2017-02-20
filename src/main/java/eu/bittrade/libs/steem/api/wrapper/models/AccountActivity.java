package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountActivity {
    @JsonIgnore
    private int activityId;
    @JsonProperty("trx_id")
    private String trxId;
    private long block;
    @JsonProperty("trx_in_block")
    private int trxInBlock;
    @JsonProperty("op_in_trx")
    private int opInTrx;
    @JsonProperty("virtual_op")
    private int virtualOp;
    private String timestamp;
    @JsonProperty("op")
    private List<Operation> operations;

    int getActivityId() {
        return activityId;
    }

    public String getTrxId() {
        return trxId;
    }

    public long getBlock() {
        return block;
    }

    public int getTrxInBlock() {
        return trxInBlock;
    }

    public int getOpInTrx() {
        return opInTrx;
    }

    public int getVirtualOp() {
        return virtualOp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    void setBlock(long block) {
        this.block = block;
    }

    void setTrxInBlock(int trxInBlock) {
        this.trxInBlock = trxInBlock;
    }

    void setOpInTrx(int opInTrx) {
        this.opInTrx = opInTrx;
    }

    void setVirtualOp(int virtualOp) {
        this.virtualOp = virtualOp;
    }

    void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
