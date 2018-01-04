package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.protocol.HardforkVersion;

/**
 * This class represents a Steem "api_hardfork_property_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class HardforkProperty {
    // Original type is hardfork_property_id_type
    @JsonProperty("id")
    private long id;
    @JsonProperty("processed_hardforks")
    private List<TimePointSec> processedHardforks;
    @JsonProperty("last_hardfork")
    private UInteger lastHardfork;
    @JsonProperty("current_hardfork_version")
    private HardforkVersion currentHardforkVersion;
    @JsonProperty("next_hardfork")
    private HardforkVersion nextHardfork;
    @JsonProperty("next_hardfork_time")
    private TimePointSec nextHardforkTime;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected HardforkProperty() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the processedHardforks
     */
    public List<TimePointSec> getProcessedHardforks() {
        return processedHardforks;
    }

    /**
     * @return the lastHardfork
     */
    public UInteger getLastHardfork() {
        return lastHardfork;
    }

    /**
     * @return the currentHardforkVersion
     */
    public HardforkVersion getCurrentHardforkVersion() {
        return currentHardforkVersion;
    }

    /**
     * @return the nextHardfork
     */
    public HardforkVersion getNextHardfork() {
        return nextHardfork;
    }

    /**
     * @return the nextHardforkTime
     */
    public TimePointSec getNextHardforkTime() {
        return nextHardforkTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
