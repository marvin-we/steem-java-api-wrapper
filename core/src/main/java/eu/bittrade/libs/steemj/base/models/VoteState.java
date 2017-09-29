package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents the Steem data type "vote_state".
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class VoteState {
    private AccountName voter;
    // Original type is uint64_t.
    private BigInteger weight;
    // Original type is uint64_t.
    private BigInteger rshares;
    // Original type is int16_t.
    private int percent;
    // Original type is share_type while a share_type is a int64_t so we use
    // long here.
    private BigInteger reputation;
    private TimePointSec time;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private VoteState() {
    }

    /**
     * @return the voter
     */
    public AccountName getVoter() {
        return voter;
    }

    /**
     * @return the weight
     */
    public BigInteger getWeight() {
        return weight;
    }

    /**
     * @return the rshares
     */
    public BigInteger getRshares() {
        return rshares;
    }

    /**
     * @return the percent
     */
    public int getPercent() {
        return percent;
    }

    /**
     * @return the reputation
     */
    public BigInteger getReputation() {
        return reputation;
    }

    /**
     * @return the time
     */
    public TimePointSec getTime() {
        return time;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
