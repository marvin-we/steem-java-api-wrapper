package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "list_savings_withdrawals_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListSavingsWithdrawalsReturn {
    @JsonProperty("withdrawals")
    private List<SavingsWithdraw> withdrawals;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * 
     * Visibility set to <code>protected</code> as this is the parent of the
     * {@link ListLimitOrdersReturn} class.
     */
    protected ListSavingsWithdrawalsReturn() {
    }

    /**
     * @return the withdrawals
     */
    public List<SavingsWithdraw> getWithdrawals() {
        return withdrawals;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
