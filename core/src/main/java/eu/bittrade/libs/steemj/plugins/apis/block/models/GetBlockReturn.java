package eu.bittrade.libs.steemj.plugins.apis.block.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

/**
 * This class is the java implementation of the Steem "get_block_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetBlockReturn {
    @JsonProperty("block")
    private ExtendedSignedBlock block;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetBlockReturn() {
    }

    /**
     * @return the header
     */
    public Optional<ExtendedSignedBlock> getBlock() {
        return Optional.fromNullable(block);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
