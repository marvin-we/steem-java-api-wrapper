package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "find_decline_voting_rights_requests_args"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindDeclineVotingRightsRequestsArgs {
    // TODO: vector< account_name_type > accounts;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
