package eu.bittrade.libs.steem.api.wrapper.interfaces;

import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This interface is used for operations that have an "expiration" field. All
 * those operations should offer the same methods regarding the expiration field
 * which can be achieved with this interface.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public interface Expirable {
    /**
     * This method returns the expiration date as its String representation. For
     * this a specific date format ("yyyy-MM-dd'T'HH:mm:ss") is used as it is
     * required by the Steem api.
     * 
     * @return The expiration date as String.
     */
    public String getExpirationDate();

    /**
     * Get the configured expiration date as a Java.util.Date object.
     * 
     * @return The expiration date.
     */
    @JsonIgnore
    public Date getExpirationDateAsDate();

    /**
     * This method returns the expiration data as its int representation.
     * 
     * @return The expiration date.
     */
    @JsonIgnore
    public int getExpirationDateAsInt();

    /**
     * Define how long this action is valid. The date has to be specified as
     * String and needs a special format: yyyy-MM-dd'T'HH:mm:ss
     * 
     * <p>
     * Example: "2016-08-08T12:24:17"
     * </p>
     * 
     * If not set the current time plus the maximal allowed offset is used by
     * default.
     * 
     * @param expirationDate
     *            The expiration date as its String representation.
     * @throws ParseException
     *             If the given String does not patch the pattern.
     */
    public void setExpirationDate(String expirationDate) throws ParseException;

    /**
     * Set the expiration date by providing a timestamp.
     * 
     * @param expirationDate
     *            The expiration date as a Timestamp.
     */
    public void setExpirationDate(long expirationDate);
}
