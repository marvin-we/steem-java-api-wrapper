package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountWitnessProxyOperation extends Operation {
    @JsonProperty("account")
    private String account;
    @JsonProperty("proxy")
    private String proxy;

    public String getAccount() {
        return account;
    }

    public String getProxy() {
        return proxy;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
