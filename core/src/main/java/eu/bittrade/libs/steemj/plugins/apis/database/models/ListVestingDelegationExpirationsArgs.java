package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "list_vesting_delegation_expirations_args"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListVestingDelegationExpirationsArgs {
    // TODO: fc::variant start;
    // TODO: uint32_t limit;
    // TODO: sort_order_type order;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
