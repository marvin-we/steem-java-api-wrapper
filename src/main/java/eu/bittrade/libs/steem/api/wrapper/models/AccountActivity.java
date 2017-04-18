package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountActivity {
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
    private Operation operations;

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

    public Operation getOperations() {
        return operations;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
