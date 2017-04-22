package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateOperation extends Operation {
    @JsonProperty("fee")
    private Asset fee;
    // Accurate type is fixed_string
    @JsonProperty("creator")
    private String creator;
    // Accurate type is fixed_string
    @JsonProperty("new_account_name")
    private String newAccountName;
    @JsonProperty("owner")
    private Authority owner;
    @JsonProperty("active")
    private Authority active;
    @JsonProperty("posting")
    private Authority posting;
    @JsonProperty("memo_key")
    // TODO: Type should be public_key_type which consists of ?
    private String memoKey;
    @JsonProperty("json_metadata")
    private String jsonMetadata;

    public AccountCreateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public Asset getFee() {
        return fee;
    }

    public String getCreator() {
        return creator;
    }

    public String getNewAccountName() {
        return newAccountName;
    }

    public Authority getOwner() {
        return owner;
    }

    public Authority getActive() {
        return active;
    }

    public Authority getPosting() {
        return posting;
    }

    public String getMemoKey() {
        return memoKey;
    }

    public String getJsonMetadata() {
        return jsonMetadata;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
