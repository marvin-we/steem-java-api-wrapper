package eu.bittrade.libs.steemj.apis.database.models.state;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * This class represents a Steem "candle_stick" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CandleStick {
    @JsonProperty("open_time")
    private TimePointSec openTime;
    @JsonProperty("period")
    // The original type is "uint32_t".
    private UInteger period;
    @JsonProperty("high")
    private double high;
    @JsonProperty("low")
    private double low;
    @JsonProperty("open")
    private double opne;
    @JsonProperty("close")
    private double close;
    @JsonProperty("steem_volume")
    private double steemVolume;
    @JsonProperty("dollar_volume")
    private double dollarVolume;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected CandleStick() {
    }

    /**
     * @return the openTime
     */
    public TimePointSec getOpenTime() {
        return openTime;
    }

    /**
     * @return the period
     */
    public UInteger getPeriod() {
        return period;
    }

    /**
     * @return the high
     */
    public double getHigh() {
        return high;
    }

    /**
     * @return the low
     */
    public double getLow() {
        return low;
    }

    /**
     * @return the opne
     */
    public double getOpne() {
        return opne;
    }

    /**
     * @return the close
     */
    public double getClose() {
        return close;
    }

    /**
     * @return the steemVolume
     */
    public double getSteemVolume() {
        return steemVolume;
    }

    /**
     * @return the dollarVolume
     */
    public double getDollarVolume() {
        return dollarVolume;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
