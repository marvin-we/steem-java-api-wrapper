package eu.bittrade.libs.steemj.chain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "decline_voting_rights_request_object" object.
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class DeclineVotingRightsRequestObject {
    // Original type is "id_type".
    @JsonProperty("id")
    private long id;
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("effective_date")
    private TimePointSec effectiveDate;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private DeclineVotingRightsRequestObject() {
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
     * @return the effectiveDate
     */
    public TimePointSec getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
