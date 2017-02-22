package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateOperation extends Operation {
    @JsonProperty("fee")
    private String fee;
    @JsonProperty("creator")
    private String creator;
    @JsonProperty("new_account_name")
    private String newAccountName;
    @JsonProperty("owner")
    private Key owner;
    @JsonProperty("active")
    private Key active;
    @JsonProperty("posting")
    private Key posting;
    @JsonProperty("memo_key")
    private String memoKey;
    @JsonProperty("json_metadata")
    private String jsonMetadata;

    public String getFee() {
        return fee;
    }

    public String getCreator() {
        return creator;
    }

    public String getNewAccountName() {
        return newAccountName;
    }

    public Key getOwner() {
        return owner;
    }

    public Key getActive() {
        return active;
    }

    public Key getPosting() {
        return posting;
    }

    public String getMemoKey() {
        return memoKey;
    }

    public String getJsonMetadata() {
        return jsonMetadata;
    }
}
