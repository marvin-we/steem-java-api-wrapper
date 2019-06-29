package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "get_transaction_hex_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetTransactionHexArgs {
    // TODO: signed_transaction trx;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
