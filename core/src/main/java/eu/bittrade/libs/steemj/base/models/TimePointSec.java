package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "time_point_sec" Object. It basically wraps a
 * date in its long representation and offers a bunch of utility methods to
 * transform the timestamp into other representations.
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class TimePointSec implements ByteTransformable , HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    @JsonIgnore
    private long dateTime;

    /**
     * Default constructor used to deserialize a json String into a date.
     * 
     * The date has to be specified as String and needs a special format:
     * yyyy-MM-dd'T'HH:mm:ss
     * 
     * <p>
     * Example: "2016-08-08T12:24:17"
     * </p>
     * 
     * @param dateTime
     *            The date in its String representation.
     */
    public TimePointSec(@JsonProperty String dateTime) {
        try {
            this.setDateTime(dateTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Not able to transform '" + dateTime + "' into a date object.");
        }
    }

    /**
     * Create a new TimePointSec object by providing the date as a timestamp.
     * 
     * @param dateTime
     *            The date as a timestamp.
     */
    public TimePointSec(long dateTime) {
        this.setDateTime(dateTime);
    }

    /**
     * This method returns the date as its String representation. For this, a
     * specific date format ("yyyy-MM-dd'T'HH:mm:ss") is used as it is required
     * by the Steem api.
     * 
     * @return The date as String.
     */
    @JsonValue
    public String getDateTime() {
        return SteemJUtils.transformDateToString(this.getDateTimeAsDate());
    }

    /**
     * Get the configured date as a {@link java.util.Date Date} object.
     * 
     * @return The date.
     */
    @JsonIgnore
    public Date getDateTimeAsDate() {
        return new Date(this.dateTime);
    }

    /**
     * This method returns the data as its int representation.
     * 
     * @return The date.
     */
    @JsonIgnore
    public int getDateTimeAsInt() {
        return (int) (this.dateTime / 1000);
    }

    /**
     * This method returns the data as its timestamp representation.
     * 
     * @return The date.
     */
    @JsonIgnore
    public long getDateTimeAsTimestamp() {
        return this.dateTime;
    }

    /**
     * Set the date. The date has to be specified as String and needs a special
     * format: yyyy-MM-dd'T'HH:mm:ss
     * 
     * <p>
     * Example: "2016-08-08T12:24:17"
     * </p>
     * 
     * @param dateTime
     *            The date as its String representation.
     * @throws ParseException
     *             If the given String does not match the pattern.
     */
    public void setDateTime(String dateTime) throws ParseException {
        this.setDateTime(SteemJUtils.transformStringToTimestamp(dateTime));
    }

    /**
     * Set the date by providing a timestamp.
     * 
     * @param dateTime
     *            The date as a timestamp.
     */
    @JsonIgnore
    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedDateTime = new ByteArrayOutputStream()) {
            serializedDateTime.write(SteemJUtils.transformIntToByteArray(this.getDateTimeAsInt()));

            return serializedDateTime.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the model into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object otherTimePointSec) {
        if (this == otherTimePointSec)
            return true;
        if (otherTimePointSec == null || !(otherTimePointSec instanceof TimePointSec))
            return false;
        TimePointSec other = (TimePointSec) otherTimePointSec;
        return (this.getDateTime().equals(other.getDateTime()));
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getDateTime() == null ? 0 : this.getDateTime().hashCode());
        return hashCode;
    }
}
