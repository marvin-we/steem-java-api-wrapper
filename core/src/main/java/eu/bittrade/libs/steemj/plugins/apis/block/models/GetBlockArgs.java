package eu.bittrade.libs.steemj.plugins.apis.block.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.plugins.apis.block.BlockApi;

/**
 * This class is the java implementation of the Steem "get_block_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetBlockArgs {
    @JsonProperty("block_num")
    private UInteger blockNumber;

    // TODO: Original: Height of the block to be returned.
    /**
     * Create a new {@link GetBlockHeaderArgs} instance to be passed to the
     * {@link BlockApi#getBlock(CommunicationHandler, UInteger)} method.
     * 
     * @param blockNumber
     *            The block number to search for.
     */
    @JsonCreator()
    public GetBlockArgs(@JsonProperty("block_num") UInteger blockNumber) {
        this.setBlockNumber(blockNumber);
    }

    /**
     * @return The currently configured block number to search for.
     */
    public UInteger getBlockNumber() {
        return blockNumber;
    }

    /**
     * @param blockNumber
     *            The block number to search for.
     */
    public void setBlockNumber(UInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
