package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.chain.ChangeRecoveryAccountRequest;

/**
 * This class represents a Steem "list_change_recovery_account_requests_return"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListChangeRecoveryAccountRequestsReturn {
    @JsonProperty("requests")
    private ChangeRecoveryAccountRequest requests;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * 
     * Visibility set to <code>protected</code> as this is the parent of the
     * {@link FindChangeRecoveryAccountRequestsReturn} class.
     */
    protected ListChangeRecoveryAccountRequestsReturn() {
    }

    /**
     * @return the requests
     */
    public ChangeRecoveryAccountRequest getRequests() {
        return requests;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
