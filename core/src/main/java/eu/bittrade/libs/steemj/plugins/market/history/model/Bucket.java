package eu.bittrade.libs.steemj.plugins.market.history.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "bucket_object" object of the
 * "market_history_plugin".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Bucket {
    /*
     * struct bucket_object:public object<bucket_object_type,bucket_object> {
     * template< typename Constructor, typename Allocator > bucket_object(
     * Constructor&& c, allocator< Allocator > a ) { c( *this ); }
     * 
     * id_type id;
     * 
     * fc::time_point_sec open; uint32_t seconds = 0; share_type high_steem;
     * share_type high_sbd; share_type low_steem; share_type low_sbd; share_type
     * open_steem; share_type open_sbd; share_type close_steem; share_type
     * close_sbd; share_type steem_volume; share_type sbd_volume;
     * 
     * price high()const { return asset( high_sbd, SBD_SYMBOL ) / asset(
     * high_steem, STEEM_SYMBOL ); }
     * 
     * price low()const { return asset( low_sbd, SBD_SYMBOL ) / asset(
     * low_steem, STEEM_SYMBOL ); } };
     */

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
