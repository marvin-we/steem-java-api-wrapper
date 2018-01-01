package eu.bittrade.libs.steemj.plugins.apis.block.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

import eu.bittrade.libs.steemj.protocol.BlockHeader;

/**
 * This class is the java implementation of the Steem "get_block_header_return"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetBlockHeaderReturn {
    @JsonProperty("header")
    private BlockHeader header;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetBlockHeaderReturn() {
    }

    /**
     * @return the header
     */
    public Optional<BlockHeader> getHeader() {
        return Optional.fromNullable(header);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
