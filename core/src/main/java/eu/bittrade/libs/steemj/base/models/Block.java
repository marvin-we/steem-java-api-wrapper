package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is the java implementation of the Steem "block_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Block extends SignedBlockWithInfo {
    // Original type is comment_id_type.
    private long id;
    // Original type is uint32_t so we use long here.
    @JsonProperty("ref_prefix")
    private long refPrefix;
    // Original type is uint32_t so we use long here.
    @JsonProperty("block_num")
    private long blockNum;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Block() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the refPrefix
     */
    public long getRefPrefix() {
        return refPrefix;
    }

    /**
     * @return the blockNum
     */
    public long getBlockNum() {
        return blockNum;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
