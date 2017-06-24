package eu.bittrade.libs.steemj.base.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class represents a Steem "hardfork_operation" object.
 * 
 * This operation type occurs if a new hardfork occurred.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class HardforkOperation extends Operation {
    // Original type is uint32_t here so we have to use long.
    @JsonProperty("hardfork_id")
    private long hardforkId;

    /**
     * Get the hardfork id.
     * 
     * @return The hardfork Id.
     */
    public long getHardforkId() {
        return hardforkId;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // The byte representation is not needed for virtual operations as we
        // can't broadcast them.
        return new byte[0];
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
