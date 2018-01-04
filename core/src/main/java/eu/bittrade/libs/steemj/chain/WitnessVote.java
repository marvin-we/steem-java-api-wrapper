package eu.bittrade.libs.steemj.chain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents the Steem data type "witness_vote_object".
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class WitnessVote {
    // Original type is "id_type".
    @JsonProperty("id")
    private long id;
    @JsonProperty("witness")
    private AccountName witness;
    @JsonProperty("account")
    private AccountName account;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private WitnessVote() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the witness
     */
    public AccountName getWitness() {
        return witness;
    }

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
