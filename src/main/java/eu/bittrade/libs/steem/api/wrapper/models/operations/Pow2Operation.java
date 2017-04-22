package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.models.ChainProperties;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Pow2Operation extends Operation {
    // TODO: Fix type of work
    @JsonProperty("work")
    private Object[] work;
    @JsonProperty("props")
    private ChainProperties properties;
    @JsonProperty("new_owner_key")
    private String newOwnerKey;

    public Pow2Operation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public Object[] getWork() {
        return work;
    }

    public ChainProperties getProperties() {
        return properties;
    }

    public String getNewOwnerKey() {
        return newOwnerKey;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
