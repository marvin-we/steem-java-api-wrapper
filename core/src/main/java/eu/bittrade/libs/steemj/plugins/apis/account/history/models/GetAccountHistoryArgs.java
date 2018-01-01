package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;
import org.joou.ULong;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.plugins.apis.account.history.AccountHistoryApi;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class implements the Steem "get_account_history_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetAccountHistoryArgs {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("start")
    private ULong start;
    @JsonProperty("limit")
    private UInteger limit;

    /**
     * Create a new {@link GetAccountHistoryArgs} instance to be passed to the
     * {@link AccountHistoryApi#getAccountHistory(eu.bittrade.libs.steemj.communication.CommunicationHandler, GetAccountHistoryArgs)}
     * method.
     * 
     * @param account
     *            The account name to request the history for.
     * @param start
     * @param limit
     */
    @JsonCreator()
    public GetAccountHistoryArgs(@JsonProperty("account") AccountName account, @JsonProperty("start") ULong start,
            @JsonProperty("limit") UInteger limit) {
        this.setAccount(account);
        this.setStart(start);
        this.setLimit(limit);
    }

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(AccountName account) {
        this.account = SteemJUtils.setIfNotNull(account, "The account is required.");
    }

    /**
     * @return the start
     */
    public ULong getStart() {
        return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(ULong start) {
        // If not provided set the start to its default value.
        this.start = SteemJUtils.setIfNotNull(start, ULong.valueOf(-1));
    }

    /**
     * @return the limit
     */
    public UInteger getLimit() {
        return limit;
    }

    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(@Nullable UInteger limit) {
        // If not provided set the limit to its default value.
        this.limit = SteemJUtils.setIfNotNull(limit, UInteger.valueOf(1000));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
