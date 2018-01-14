package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "find_account_recovery_requests_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindAccountRecoveryRequestsArgs {
    @JsonProperty("accounts")
    private List<AccountName> accounts;

    /**
     * 
     * @param accounts
     */
    @JsonCreator()
    public FindAccountRecoveryRequestsArgs(@JsonProperty("accounts") List<AccountName> accounts) {
        this.setAccounts(accounts);
    }

    /**
     * 
     * @param account
     */
    public FindAccountRecoveryRequestsArgs(AccountName account) {
        this.setAccounts(Arrays.asList(account));
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
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
