package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;
import java.net.URL;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.enums.WitnessScheduleType;

/**
 * This class represents a Steem "witness_api_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Witness {
    private int id;
    private AccountName owner;
    private TimePointSec created;
    private URL url;
    // The original type is "uint32_t".
    @JsonProperty("total_missed")
    private long totalMissed;
    // The original type is "uint64_t".
    @JsonProperty("last_aslot")
    private BigInteger lastAslot;
    // The original type is "uint64_t".
    @JsonProperty("last_confirmed_block_num")
    private BigInteger lastConfirmedBlockNum;
    /**
     * Some witnesses have the job because they did a proof of work, this field
     * indicates where they were in the POW order. After each round, the witness
     * with the lowest pow_worker value greater than 0 is removed.
     */
    // The original type is "uint64_t".
    @JsonProperty("pow_worker")
    private BigInteger powWorker;
    @JsonProperty("signing_key")
    private PublicKey signingKey;
    private ChainProperties props;
    @JsonProperty("sbd_exchange_rate")
    private Price sbdExchangeRate;
    @JsonProperty("last_sbd_exchange_update")
    private TimePointSec lastSbdExchangeUpdate;
    /**
     * The total votes for this witness. This determines how the witness is
     * ranked for scheduling. The top N witnesses by votes are scheduled every
     * round, every one else takes turns being scheduled proportional to their
     * votes.
     */
    // The original type is "share_type" which is an "int64".
    private long votes;
    /** How the witness was scheduled the last time it was scheduled. */
    private WitnessScheduleType schedule;
    /**
     * These fields are used for the witness scheduling algorithm which uses
     * virtual time to ensure that all witnesses are given proportional time for
     * producing blocks.
     *
     * {@link #votes} is used to determine speed. The
     * {@link #virtualScheduledTime} is the expected time at which this witness
     * should complete a virtual lap which is defined as the position equal to
     * 1000 times MAXVOTES.
     *
     * virtual_scheduled_time = virtual_last_update + (1000*MAXVOTES -
     * virtual_position) / votes
     *
     * Every time the number of votes changes the virtual_position and
     * virtual_scheduled_time must update. There is a global current
     * virtual_scheduled_time which gets updated every time a witness is
     * scheduled. To update the virtual_position the following math is
     * performed.
     *
     * virtual_position = virtual_position + votes * (virtual_current_time -
     * virtual_last_update) virtual_last_update = virtual_current_time votes +=
     * delta_vote virtual_scheduled_time = virtual_last_update + (1000*MAXVOTES
     * - virtual_position) / votes
     *
     */
    // Original type is "fc::uint128".
    @JsonProperty("virtual_last_update")
    private String virtualLastUpdate;
    // Original type is "fc::uint128".
    @JsonProperty("virtual_position")
    private String virtualPosition;
    // Original type is "fc::uint128".
    @JsonProperty("virtual_scheduled_time")
    private BigInteger virtualScheduledTime;

    // Original type is "digest_type" which is a "fc:sha256" object.
    @JsonProperty("last_work")
    private Sha256Hash lastWork;
    @JsonProperty("running_version")
    private Version runningVersion;
    @JsonProperty("hardfork_version_vote")
    private HardforkVersion hardforkVersionVote;
    @JsonProperty("hardfork_time_vote")
    private TimePointSec hardforkTimeVote;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Witness() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the owner
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * @return the created
     */
    public TimePointSec getCreated() {
        return created;
    }

    /**
     * @return the url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * @return the totalMissed
     */
    public long getTotalMissed() {
        return totalMissed;
    }

    /**
     * @return the lastAslot
     */
    public BigInteger getLastAslot() {
        return lastAslot;
    }

    /**
     * @return the lastConfirmedBlockNum
     */
    public BigInteger getLastConfirmedBlockNum() {
        return lastConfirmedBlockNum;
    }

    /**
     * @return the powWorker
     */
    public BigInteger getPowWorker() {
        return powWorker;
    }

    /**
     * @return the signingKey
     */
    public PublicKey getSigningKey() {
        return signingKey;
    }

    /**
     * @return the props
     */
    public ChainProperties getProps() {
        return props;
    }

    /**
     * @return the sbdExchangeRate
     */
    public Price getSbdExchangeRate() {
        return sbdExchangeRate;
    }

    /**
     * @return the lastSbdExchangeUpdate
     */
    public TimePointSec getLastSbdExchangeUpdate() {
        return lastSbdExchangeUpdate;
    }

    /**
     * @return the votes
     */
    public long getVotes() {
        return votes;
    }

    /**
     * @return the schedule
     */
    public WitnessScheduleType getSchedule() {
        return schedule;
    }

    /**
     * @return the virtualLastUpdate
     */
    public String getVirtualLastUpdate() {
        return virtualLastUpdate;
    }

    /**
     * @return the virtualPosition
     */
    public String getVirtualPosition() {
        return virtualPosition;
    }

    /**
     * @return the virtualScheduledTime
     */
    public BigInteger getVirtualScheduledTime() {
        return virtualScheduledTime;
    }

    /**
     * @return the lastWork
     */
    public Sha256Hash getLastWork() {
        return lastWork;
    }

    /**
     * @return the runningVersion
     */
    public Version getRunningVersion() {
        return runningVersion;
    }

    /**
     * @return the hardforkVersionVote
     */
    public HardforkVersion getHardforkVersionVote() {
        return hardforkVersionVote;
    }

    /**
     * @return the hardforkTimeVote
     */
    public TimePointSec getHardforkTimeVote() {
        return hardforkTimeVote;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
