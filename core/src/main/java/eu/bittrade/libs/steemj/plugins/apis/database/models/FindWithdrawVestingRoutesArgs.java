package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "find_withdraw_vesting_routes_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindWithdrawVestingRoutesArgs {
    // TODO: account_name_type account;
    // TODO: sort_order_type order;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
