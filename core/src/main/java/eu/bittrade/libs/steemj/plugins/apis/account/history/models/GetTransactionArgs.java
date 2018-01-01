package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class implements the Steem "get_transaction_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetTransactionArgs {
    steem::protocol::transaction_id_type id;
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
