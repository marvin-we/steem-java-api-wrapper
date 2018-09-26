package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "verify_account_authority_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VerifyAccountAuthorityArgs {
    // TODO: account_name_type account;
    // TODO: flat_set< public_key_type > signers;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
