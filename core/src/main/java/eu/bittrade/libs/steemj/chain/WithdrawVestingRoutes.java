package eu.bittrade.libs.steemj.chain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UShort;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "withdraw_vesting_route_object" object.
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class WithdrawVestingRoutes {
    // Original type is "id_type" so we use long here.
    @JsonProperty("id")
    private long id;
    @JsonProperty("from_account")
    private AccountName fromAccount;
    @JsonProperty("to_account")
    private AccountName toAccount;
    @JsonProperty("percent")
    private UShort percent;
    @JsonProperty("auto_vest")
    private boolean autoVest;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private WithdrawVestingRoutes() {
        this.percent = UShort.valueOf(0);
        this.autoVest = false;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the fromAccount
     */
    public AccountName getFromAccount() {
        return fromAccount;
    }

    /**
     * @return the toAccount
     */
    public AccountName getToAccount() {
        return toAccount;
    }

    /**
     * @return the percent
     */
    public UShort getPercent() {
        return percent;
    }

    /**
     * @return the autoVest
     */
    public boolean isAutoVest() {
        return autoVest;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
