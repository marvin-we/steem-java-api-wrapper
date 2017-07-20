package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents the Steem data type "vote_state".
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class VoteState {
    private AccountName voter;
    // Original type is uint64_t.
    private long weight;
    // Original type is uint64_t.
    private long rshares;
    // Original type is int16_t.
    private short percent;
    // TODO Original type is share_type.
    private String reputation;
    private TimePointSec time;

    public AccountName getVoter() {
        return voter;
    }

    public long getWeight() {
        return weight;
    }

    public long getRshares() {
        return rshares;
    }

    public short getPercent() {
        return percent;
    }

    public String getReputation() {
        return reputation;
    }

    public TimePointSec getTime() {
        return time;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
