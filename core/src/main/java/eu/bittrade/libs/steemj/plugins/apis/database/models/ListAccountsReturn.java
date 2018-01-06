package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "list_accounts_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListAccountsReturn {
    @JsonProperty("accounts")
    private List<AccountName> accounts;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    public ListAccountsReturn() {
    }

    /**
     * @return the accounts
     */
    public List<AccountName> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
