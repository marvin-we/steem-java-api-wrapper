package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents the Steem "account_vote" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountVote {
    private String authorperm;
    // Original type is uint64_t
    private long weight;
    // Original type is int64_t
    private long rshares;
    // Original type is int16_t
    private short percent;
    private TimePointSec time;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private AccountVote() {
    }

    /**
     * @return the authorperm
     */
    public String getAuthorperm() {
        return authorperm;
    }

    /**
     * @return the weight
     */
    public long getWeight() {
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
