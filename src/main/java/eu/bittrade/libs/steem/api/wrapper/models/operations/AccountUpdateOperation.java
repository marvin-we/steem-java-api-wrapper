package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountUpdateOperation extends Operation {
    @JsonProperty("account")
    private String account;
    @JsonProperty("memo_key")
    private String memoKey;
    @JsonProperty("json_metadata")
    private String jsonMetadata;
    @JsonProperty("posting")
    private Authority posting;
    @JsonProperty("owner")
    private Authority owner;
    @JsonProperty("active")
    private Authority active;

    public AccountUpdateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String getAccount() {
        return account;
    }

    public String getMemoKey() {
        return memoKey;
    }

    public String getJsonMetadata() {
        return jsonMetadata;
    }

    public Authority getPosting() {
        return posting;
    }

    public Authority getOwner() {
        return owner;
    }

    public Authority getActive() {
        return active;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
