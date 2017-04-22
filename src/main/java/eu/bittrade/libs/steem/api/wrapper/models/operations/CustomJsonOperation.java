package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomJsonOperation extends Operation {
    @JsonProperty("required_auths")
    private String[] requiredAuths;
    @JsonProperty("required_posting_auths")
    private String[] requiredPostingAuths;
    @JsonProperty("id")
    private String id;
    @JsonProperty("json")
    private String json;

    public CustomJsonOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String[] getRequiredAuths() {
        return requiredAuths;
    }

    public String[] getRequiredPostingAuths() {
        return requiredPostingAuths;
    }

    public String getId() {
        return id;
    }

    public String getJson() {
        return json;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}