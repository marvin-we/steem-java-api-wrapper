package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WithdrawVestingOperation extends Operation {
    @JsonProperty("account")
    private String account;
    @JsonProperty("vesting_shares")
    private String vestingShares;

    public String getAccount() {
        return account;
    }

    public String getVestingShares() {
        return vestingShares;
    }
}
