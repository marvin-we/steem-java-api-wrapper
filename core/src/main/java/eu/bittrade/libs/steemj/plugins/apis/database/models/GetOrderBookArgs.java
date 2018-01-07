package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "get_order_book_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetOrderBookArgs {
    // TODO: uint32_t limit;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
