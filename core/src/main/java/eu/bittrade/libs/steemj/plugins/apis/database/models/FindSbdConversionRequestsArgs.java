package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "find_sbd_conversion_requests_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindSbdConversionRequestsArgs {
    // TODO: account_name_type account;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
