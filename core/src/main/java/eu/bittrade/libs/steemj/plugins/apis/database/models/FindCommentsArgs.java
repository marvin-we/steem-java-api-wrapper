package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "find_comments_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindCommentsArgs {
    // TODO: vector< std::pair< account_name_type, string > > comments;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
