package eu.bittrade.libs.steem.api.wrapper.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.VarInt;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;
import eu.bittrade.libs.steem.api.wrapper.enums.DiscussionSortType;
import eu.bittrade.libs.steem.api.wrapper.enums.RequestMethods;

/**
 * This class contains some utility methods used by the steem api wrapper.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemUtils {
    private static final Logger LOGGER = LogManager.getLogger(SteemUtils.class);

    /** Add a private constructor to hide the implicit public one. */
    private SteemUtils() {
    }

    /**
     * TODO: Is there a nicer way to solve this?
     * 
     * @param discussionSortType
     * @return
     */
    public static RequestMethods getEquivalentRequestMethod(DiscussionSortType discussionSortType) {
        switch (discussionSortType) {
        case SORT_BY_ACTIVE:
            return RequestMethods.GET_DISCUSSIONS_BY_ACTIVE;
        case SORT_BY_BLOG:
            return RequestMethods.GET_DISCUSSIONS_BY_BLOG;
        case SORT_BY_CASHOUT:
            return RequestMethods.GET_DISCUSSIONS_BY_CASHOUT;
        case SORT_BY_CHILDREN:
            return RequestMethods.GET_DISCUSSIONS_BY_CHILDREN;
        case SORT_BY_COMMENTS:
            return RequestMethods.GET_DISCUSSIONS_BY_COMMENTS;
        case SORT_BY_CREATED:
            return RequestMethods.GET_DISCUSSIONS_BY_CREATED;
        case SORT_BY_FEED:
            return RequestMethods.GET_DISCUSSIONS_BY_FEED;
        case SORT_BY_HOT:
            return RequestMethods.GET_DISCUSSIONS_BY_HOT;
        case SORT_BY_PAYOUT:
            return RequestMethods.GET_DISCUSSIONS_BY_PAYOUT;
        case SORT_BY_PROMOTED:
            return RequestMethods.GET_DISCUSSIONS_BY_PROMOTED;
        case SORT_BY_TRENDING:
            return RequestMethods.GET_DISCUSSIONS_BY_TRENDING;
        case SORT_BY_TRENDING_30_DAYS:
            return RequestMethods.GET_DISCUSSIONS_BY_TRENDING30;
        case SORT_BY_VOTES:
            return RequestMethods.GET_DISCUSSIONS_BY_VOTES;
        default:
            LOGGER.warn(
                    "Unkown sort type. The resulting discussions are now sorted by the values of the 'active' field (SORT_BY_ACTIVE).");
            return RequestMethods.GET_DISCUSSIONS_BY_ACTIVE;
        }
    }

    /**
     * Transform a short variable into a byte array.
     * 
     * @param shortValue
     *            The short value to transform.
     * @return The byte representation of the short value.
     */
    public static byte[] transformShortToByteArray(int shortValue) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) shortValue).array();
    }

    /**
     * Transform a long variable into a byte array.
     * 
     * @param longValue
     *            The long value to transform.
     * @return The byte representation of the long value.
     */
    public static byte[] transformLongToByteArray(long longValue) {
        return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(longValue).array();
    }

    /**
     * Change the order of a byte to little endian.
     * 
     * @param byteValue
     *            The byte to transform.
     * @return The byte in its little endian representation.
     */
    public static byte transformByteToLittleEndian(byte byteValue) {
        return ByteBuffer.allocate(1).order(ByteOrder.LITTLE_ENDIAN).put(byteValue).get(0);
    }

    /**
     * Get the VarInt-byte representation of a String.
     * 
     * Serializing a String has to be done in two steps:
     * 
     * <ul>
     * <li>1. Length as VarInt</li>
     * <li>2. The account name.</li>
     * </ul>
     *
     * @param string
     *            The string to transform.
     * @return The VarInt-byte representation of the given String.
     */
    public static byte[] transformStringToVarIntByteArray(String string) {
        Charset encodingCharset = SteemApiWrapperConfig.getInstance().getEncodingCharset();
        byte[] resultingByteRepresentation = {};

        VarInt stringLength = new VarInt(string.length());
        resultingByteRepresentation = ArrayUtils.addAll(resultingByteRepresentation, stringLength.encode());

        resultingByteRepresentation = ArrayUtils.addAll(resultingByteRepresentation,
                ByteBuffer.allocate(string.length()).put(string.getBytes(encodingCharset)).array());

        return resultingByteRepresentation;
    }

    /**
     * Transform an int value into its byte representation.
     * 
     * @param intValue
     *            The int value to transform.
     * @return The byte representation of the given value.
     */
    public static byte[] transformIntToVarIntByteArray(int intValue) {
        return (new VarInt(intValue)).encode();
    }

    /**
     * Transform an short value into its byte representation.
     * 
     * @param shortValue
     *            The short value to transform.
     * @return The byte representation of the given value.
     */
    public static byte[] transformShortToByteArray(short shortValue) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(shortValue).array();
    }

    /**
     * Transform an int value into its byte representation.
     * 
     * @param intValue
     *            The int value to transform.
     * @return The byte representation of the given value.
     */
    public static byte[] transformIntToByteArray(int intValue) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(intValue).array();
    }

    /**
     * Transform a boolean value into its byte representation.
     * 
     * @param boolValue
     *            The bool value to transform.
     * @return The byte representation of the given value.
     */
    public static byte[] transformBooleanToByteArray(boolean boolValue) {
        return new byte[] { (byte) (boolValue ? 1 : 0) };
    }

    /**
     * Transform a long value into its byte representation.
     * 
     * @param longValue
     *            value The long value to transform.
     * @return The byte representation of the given value.
     */
    public static byte[] transformLongToVarIntByteArray(long longValue) {
        return (new VarInt(longValue)).encode();
    }

    /**
     * This method transform a date and returns this date in its String
     * representation. The method is using the timezone and the date time
     * pattern defined in the
     * {@link eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig
     * SteemApiWrapperConfig}.
     * 
     * @param date
     *            The date in its String representation.
     * @return
     */
    public static String transformDateToString(Date date) {
        SimpleDateFormat simpleDateFormatForJSON = new SimpleDateFormat(
                SteemApiWrapperConfig.getInstance().getDateTimePattern());
        simpleDateFormatForJSON.setTimeZone(TimeZone.getTimeZone(SteemApiWrapperConfig.getInstance().getTimeZoneId()));
        return simpleDateFormatForJSON.format(date);
    }

    /**
     * This method transforms a String into a timestamp. The method is using the
     * timezone and the date time pattern defined in the
     * {@link eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig
     * SteemApiWrapperConfig}.
     * 
     * @param dateTime
     *            The date to transform.
     * @return The timestamp representation of the given String.
     * @throws ParseException
     *             If the String could not be transformed.
     */
    public static long transformStringToTimestamp(String dateTime) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                SteemApiWrapperConfig.getInstance().getDateTimePattern());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(SteemApiWrapperConfig.getInstance().getTimeZoneId()));
        calendar.setTime(simpleDateFormat.parse(dateTime + SteemApiWrapperConfig.getInstance().getTimeZoneId()));
        return calendar.getTimeInMillis();
    }
}
