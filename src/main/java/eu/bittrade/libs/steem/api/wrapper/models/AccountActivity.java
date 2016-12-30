package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author http://steemit.com/@dez1337
 */
public class AccountActivity {
    private String trxId;
    private long block;
    private int trxInBlock;
    private int opInTrx;
    private int virtualOp;
    private Date timestamp;
    // TODO Look up how this object looks like.
    private Object[] op;

    @JsonProperty("trx_id")
    public String getTrxId() {
        return trxId;
    }

    public long getBlock() {
        return block;
    }

    @JsonProperty("trx_in_block")
    public int getTrxInBlock() {
        return trxInBlock;
    }

    @JsonProperty("op_in_trx")
    public int getOpInTrx() {
        return opInTrx;
    }

    @JsonProperty("virtual_op")
    public int getVirtualOp() {
        return virtualOp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Object[] getOp() {
        return op;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
