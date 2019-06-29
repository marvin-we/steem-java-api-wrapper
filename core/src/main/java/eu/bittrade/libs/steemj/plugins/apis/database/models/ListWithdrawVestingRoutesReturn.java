package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "list_withdraw_vesting_routes_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListWithdrawVestingRoutesReturn {
    // TODO: vector< api_withdraw_vesting_route_object > routes;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * 
     * Visibility set to <code>protected</code> as this is the parent of the
     * {@link FindWithdrawVestingRoutesReturn} class.
     */
    protected ListWithdrawVestingRoutesReturn() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
