package eu.bittrade.libs.steemj.plugins.follow.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.base.models.AccountName;

/**
 * This class represents a Steem "account_reputation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountReputation {
    private AccountName account;
    // Original type is safe<int64_t>.
    private long reputation;

    /**
     * Get the name of the account.
     * 
     * @return The name of the account.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Get the reputation of the requested {@link #account}.
     * 
     * @return The reputation of the requested {@link #account}.
     */
    public long getReputation() {
        return reputation;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
