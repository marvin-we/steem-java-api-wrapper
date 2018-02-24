package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "verify_signatures_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VerifySignaturesArgs {
/* TODO:
       fc::sha256                    hash;
       vector< signature_type >      signatures;
       vector< account_name_type >   required_owner;
       vector< account_name_type >   required_active;
       vector< account_name_type >   required_posting;
       vector< authority >           required_other;

       void get_required_owner_authorities( flat_set< account_name_type >& a )const
       {
          a.insert( required_owner.begin(), required_owner.end() );
       }

       void get_required_active_authorities( flat_set< account_name_type >& a )const
       {
          a.insert( required_active.begin(), required_active.end() );
       }

       void get_required_posting_authorities( flat_set< account_name_type >& a )const
       {
          a.insert( required_posting.begin(), required_posting.end() );
       }

       void get_required_authorities( vector< authority >& a )const
       {
          a.insert( a.end(), required_other.begin(), required_other.end() );
       }*/
       
       @Override
       public String toString() {
           return ToStringBuilder.reflectionToString(this);
       }
}
