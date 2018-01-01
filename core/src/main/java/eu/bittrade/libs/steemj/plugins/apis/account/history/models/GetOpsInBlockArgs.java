package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.plugins.apis.witness.WitnessApi;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.GetAccountBandwidthArgs;

/**
 * This class implements the Steem "get_ops_in_block_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetOpsInBlockArgs {
    @JsonProperty("block_num")
    private UInteger blockNum;
    @JsonProperty("onlyVirtual")
    private Boolean onlyVirtual;

    /**
     * Create a new {@link GetAccountBandwidthArgs} instance to be passed to the
     * {@link WitnessApi#getAccountBandwidth(CommunicationHandler, GetAccountBandwidthArgs)}
     * method.
     * @param blockNum 
     * @param onlyVirtual 
     */
    @JsonCreator()
    public GetOpsInBlockArgs(@JsonProperty("block_num") UInteger blockNum,
            @JsonProperty("onlyVirtual") Boolean onlyVirtual) {
        this.setBlockNum(blockNum);
        this.setOnlyVirtual(onlyVirtual);
    }

    /**
     * @return the blockNum
     */
    public UInteger getBlockNum() {
        return blockNum;
    }

    /**
     * @param blockNum
     *            the blockNum to set
     */
    public void setBlockNum(UInteger blockNum) {
        this.blockNum = blockNum;
    }

    /**
     * @return the onlyVirtual
     */
    public Boolean getOnlyVirtual() {
        return onlyVirtual;
    }

    /**
     * @param onlyVirtual
     *            the onlyVirtual to set
     */
    public void setOnlyVirtual(Boolean onlyVirtual) {
        this.onlyVirtual = onlyVirtual;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
