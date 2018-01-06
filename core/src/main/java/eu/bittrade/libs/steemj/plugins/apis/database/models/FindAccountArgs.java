package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "find_accounts_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindAccountArgs {
    @JsonProperty("accounts")
    private List<AccountName> accounts;

    /**
     * 
     * @param accounts
     */
    @JsonCreator
    public FindAccountArgs(@JsonProperty("accounts") List<AccountName> accounts) {
    }

    /**
     * @return the accounts
     */
    public List<AccountName> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts
     *            the accounts to set
     */
    public void setAccounts(List<AccountName> accounts) {
        this.accounts = SteemJUtils.setIfNotNullAndNotEmpty(accounts, "You need to provide atleast one account.");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
