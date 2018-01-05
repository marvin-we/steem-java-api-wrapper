package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.ULong;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents the Steem data type "vote_state".
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class VoteState {
    private AccountName voter;
    // Original type is uint64_t.
    private ULong weight;
    // Original type is int64_t.
    private long rshares;
    // Original type is int16_t.
    private short percent;
    // Original type is share_type while a share_type is a int64_t so we use
    // long here.
    private long reputation;
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
    public ULong getWeight() {
        return weight;
    }

    /**
     * @return the rshares
     */
    public long getRshares() {
        return rshares;
    }

    /**
     * @return the percent
     */
    public short getPercent() {
        return percent;
    }

    /**
     * @return the reputation
     */
    public long getReputation() {
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
