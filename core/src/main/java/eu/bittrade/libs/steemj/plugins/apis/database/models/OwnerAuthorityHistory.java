package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Authority;

/**
 * This class represents a Steem "api_owner_authority_history_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class OwnerAuthorityHistory {
    // Original type is "owner_authority_history_id_type".
    @JsonProperty("")
    private long id;
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("previous_owner_authority")
    private Authority previousOwnerAuthority;
    @JsonProperty("last_valid_time")
    private TimePointSec lastValidTime;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private OwnerAuthorityHistory() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * @return the previousOwnerAuthority
     */
    public Authority getPreviousOwnerAuthority() {
        return previousOwnerAuthority;
    }

    /**
     * @return the lastValidTime
     */
    public TimePointSec getLastValidTime() {
        return lastValidTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
