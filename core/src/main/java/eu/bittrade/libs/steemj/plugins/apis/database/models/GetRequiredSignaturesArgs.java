package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "get_required_signatures_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetRequiredSignaturesArgs {
    // TODO: signed_transaction trx;
    // TODO: flat_set< public_key_type > available_keys;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
