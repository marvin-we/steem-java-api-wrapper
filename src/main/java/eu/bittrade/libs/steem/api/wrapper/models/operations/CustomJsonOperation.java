package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomJsonOperation extends Operation {
    @JsonProperty("required_auths")
    private List<AccountName> requiredAuths;
    @JsonProperty("required_posting_auths")
    private List<AccountName> requiredPostingAuths;
    @JsonProperty("id")
    private String id;
    @JsonProperty("json")
    private String json;

    /**
     * serves the same purpose as custom_operation but also supports required
     * posting authorities. Unlike custom_operation, this operation is designed
     * to be human readable/developer friendly.
     */
    public CustomJsonOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public List<AccountName> getRequiredAuths() {
        return requiredAuths;
    }

    public void setRequiredAuths(List<AccountName> requiredAuths) {
        this.requiredAuths = requiredAuths;
    }

    public List<AccountName> getRequiredPostingAuths() {
        return requiredPostingAuths;
    }

    public void setRequiredPostingAuths(List<AccountName> requiredPostingAuths) {
        this.requiredPostingAuths = requiredPostingAuths;
    }

    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *            Must be less than 32 characters long.
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    /**
     * 
     * @param json
     *            Must be proper utf8 / JSON string.
     */
    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
