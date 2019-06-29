package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Steem "list_sbd_conversion_requests_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListSbdConversionRequestsReturn {
    // TODO: vector< api_convert_request_object > requests;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * 
     * Visibility set to <code>protected</code> as this is the parent of the
     * {@link FindSbdConversionRequestsReturn} class.
     */
    protected ListSbdConversionRequestsReturn() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
