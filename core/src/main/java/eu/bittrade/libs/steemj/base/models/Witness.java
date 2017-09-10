package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "witness_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Witness {
    private int id;
    private String owner;
    private TimePointSec created;
    private String url;
    private BigInteger votes;
    @JsonProperty("virtual_last_update")
    private String virtualLastUpdate;
    @JsonProperty("virtual_position")
    private String virtualPosition;
    @JsonProperty("virtual_scheduled_time")
    private String virtualScheduledTime;
    @JsonProperty("total_missed")
    private int totalMissed;
    @JsonProperty("last_aslot")
    private int lastAslot;
    @JsonProperty("last_confirmed_block_num")
    private BigInteger lastConfirmedBlockNum;
    @JsonProperty("pow_worker")
    private int powWorker;
    @JsonProperty("signing_key")
    private String signingKey;
    private ChainProperties props;
    @JsonProperty("sbd_exchange_rate")
    private Price sbdExchangeRate;
    @JsonProperty("last_sbd_exchange_update")
    private TimePointSec lastSbdExchangeUpdate;
    @JsonProperty("last_work")
    private String lastWork;
    @JsonProperty("running_version")
    private String runningVersion;
    @JsonProperty("hardfork_version_vote")
    private String hardforkVersionVote;
    @JsonProperty("hardfork_time_vote")
    private TimePointSec hardforkTimeVote;

    public int getId() {
        return id;
    }

    public String getVirtualPosition() {
        return virtualPosition;
    }

    public String getOwner() {
        return owner;
    }

    public TimePointSec getCreated() {
        return created;
    }

    public String getUrl() {
        return url;
    }

    public BigInteger getVotes() {
        return votes;
    }

    public String getVirtualLastUpdate() {
        return virtualLastUpdate;
    }

    public String getVirtualScheduledTime() {
        return virtualScheduledTime;
    }

    public int getTotalMissed() {
        return totalMissed;
    }

    public int getLastAslot() {
        return lastAslot;
    }

    public BigInteger getLastConfirmedBlockNum() {
        return lastConfirmedBlockNum;
    }

    public int getPowWorker() {
        return powWorker;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public ChainProperties getProps() {
        return props;
    }

    public Price getSbdExchangeRate() {
        return sbdExchangeRate;
    }

    public TimePointSec getLastSbdExchangeUpdate() {
        return lastSbdExchangeUpdate;
    }

    public String getLastWork() {
        return lastWork;
    }

    public String getRunningVersion() {
        return runningVersion;
    }

    public String getHardforkVersionVote() {
        return hardforkVersionVote;
    }

    public TimePointSec getHardforkTimeVote() {
        return hardforkTimeVote;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
