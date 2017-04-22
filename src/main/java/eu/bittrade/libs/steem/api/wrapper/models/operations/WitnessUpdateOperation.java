package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.models.ChainProperties;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessUpdateOperation extends Operation {
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("url")
    private String url;
    @JsonProperty("block_signing_key")
    private String blockSigningKey;
    @JsonProperty("props")
    private ChainProperties properties;
    @JsonProperty("fee")
    private String fee;

    public WitnessUpdateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String getOwner() {
        return owner;
    }

    public String getUrl() {
        return url;
    }

    public String getBlockSigningKey() {
        return blockSigningKey;
    }

    public ChainProperties getProperties() {
        return properties;
    }

    public String getFee() {
        return fee;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
