package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountUpdateOperation extends Operation {
    @JsonProperty("account")
    private String account;
    @JsonProperty("memo_key")
    private String memoKey;
    @JsonProperty("json_metadata")
    private String jsonMetadata;

    public String getAccount() {
        return account;
    }

    public String getMemoKey() {
        return memoKey;
    }

    public String getJsonMetadata() {
        return jsonMetadata;
    }
}
