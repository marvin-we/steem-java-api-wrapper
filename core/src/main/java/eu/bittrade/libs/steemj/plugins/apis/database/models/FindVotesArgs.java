package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "find_votes_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindVotesArgs {
    // TODO: account_name_type author;
    // TODO: string permlink;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
