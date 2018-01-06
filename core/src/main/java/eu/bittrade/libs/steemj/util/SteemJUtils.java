/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.bittrade.crypto.core.ECKey;
import eu.bittrade.crypto.core.VarInt;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;

/**
 * This class contains some utility methods used by SteemJ.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemJUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SteemJUtils.class);

    /** Add a private constructor to hide the implicit public one. */
    private SteemJUtils() {
    }

    /**
     * This method can be used to verify, if the given String is a valid JSON
     * string.
     * 
     * @param customJsonString
     *            The string to be checked.
     * @return <code>true</code> If the given String is valid JSON,
     *         <code>false</code> if not.
     */
    public static boolean verifyJsonString(String customJsonString) {
        boolean valid = true;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(customJsonString);
        } catch (IOException e) {
            LOGGER.debug("The given json is invalid.", e);
            valid = false;
        }
        return valid;
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
        if (string == null) {
            return new byte[0];
        }

        Charset encodingCharset = SteemJConfig.getInstance().getEncodingCharset();
        try (ByteArrayOutputStream resultingByteRepresentation = new ByteArrayOutputStream()) {
            byte[] stringAsByteArray = string.getBytes(encodingCharset);

            resultingByteRepresentation.write(transformLongToVarIntByteArray(stringAsByteArray.length));
            resultingByteRepresentation.write(stringAsByteArray);

            return resultingByteRepresentation.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("A problem occured while transforming the string into a byte array.", e);
        }
    }

    /**
     * Transform an int value into its byte representation.
     * 
     * @param intValue
     *            The int value to transform.
     * @return The byte representation of the given value.
     */
    public static byte[] transformIntToVarIntByteArray(int intValue) {
        try {
            int value = intValue;

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutput out = new DataOutputStream(byteArrayOutputStream);

            while ((value & 0xFFFFFF80) != 0L) {
                out.writeByte((value & 0x7F) | 0x80);
                value >>>= 7;
            }

            out.writeByte(intValue & 0x7F);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Could not transform the given int value into its VarInt representation - "
                    + "Using BitcoinJ as Fallback. This could cause problems for values > 127.", e);
            return (new VarInt(intValue)).encode();
        }
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
        try {
            long value = longValue;

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutput out = new DataOutputStream(byteArrayOutputStream);

            while ((value & 0xFFFFFFFFFFFFFF80L) != 0L) {
                out.writeByte(((int) value & 0x7F) | 0x80);
                value >>>= 7;
            }

            out.writeByte((int) value & 0x7F);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Could not transform the given long value into its VarInt representation - "
                    + "Using BitcoinJ as Fallback. This could cause problems for values > 127.", e);
            return (new VarInt(longValue)).encode();
        }
    }

    /**
     * This method transform a date and returns this date in its String
     * representation. The method is using the timezone and the date time
     * pattern defined in the
     * {@link eu.bittrade.libs.steemj.configuration.SteemJConfig SteemJConfig}.
     * 
     * @param date
     *            The date to transform.
     * @return The date in its String representation.
     */
    public static String transformDateToString(Date date) {
        SimpleDateFormat simpleDateFormatForJSON = new SimpleDateFormat(
                SteemJConfig.getInstance().getDateTimePattern());
        simpleDateFormatForJSON.setTimeZone(TimeZone.getTimeZone(SteemJConfig.getInstance().getTimeZoneId()));
        return simpleDateFormatForJSON.format(date);
    }

    /**
     * This method transforms a String into a timestamp. The method is using the
     * timezone and the date time pattern defined in the
     * {@link eu.bittrade.libs.steemj.configuration.SteemJConfig SteemJConfig}.
     * 
     * @param dateTime
     *            The date to transform.
     * @return The timestamp representation of the given String.
     * @throws ParseException
     *             If the String could not be transformed.
     */
    public static long transformStringToTimestamp(String dateTime) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SteemJConfig.getInstance().getDateTimePattern());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(SteemJConfig.getInstance().getTimeZoneId()));
        calendar.setTime(simpleDateFormat.parse(dateTime + SteemJConfig.getInstance().getTimeZoneId()));
        return calendar.getTimeInMillis();
    }

    /**
     * Get the WIF representation of a private key.
     * 
     * @param privateKey
     *            The private key to get WIF representation for.
     * @return The WIF representation of the given private key.
     * @throws IllegalStateException
     *             If no private key is present in the given ECKey instance.
     */
    public static String privateKeyToWIF(ECKey privateKey) {
        ECKey currentPrivateKey = privateKey;
        if (currentPrivateKey.isCompressed()) {
            currentPrivateKey = currentPrivateKey.decompress();
        }
        return currentPrivateKey.getPrivateKeyEncoded(128).toBase58();
    }

    /**
     * Like {@link #setIfNotNull(Object, String, Object)}, but does not require
     * a default value.
     * 
     * This method will check if given <code>objectToSet</code> is
     * <code>null</code> and throw an {@link InvalidParameterException} if this
     * is the case.
     * 
     * @param <T>
     *            The type of the <code>objectToSet</code>.
     * @param objectToSet
     *            The object to check.
     * @param message
     *            The message of the generated exception.
     * @return The given <code>objectToSet</code> if its not <code>null</code>.
     * @throws InvalidParameterException
     *             If the <code>objectToSet</code> is <code>null</code>.
     */
    public static <T> T setIfNotNull(T objectToSet, String message) {
        return setIfNotNull(objectToSet, message, null);
    }

    /**
     * This method will check if given <code>objectToSet</code> is
     * <code>null</code>.
     * 
     * In case <code>objectToSet</code> is not <code>null</code>, the
     * <code>objectToSet</code> will be returned. Otherwise the method will
     * check the <code>defaultValue</code>. The method will return the
     * <code>defaultValue</code> if one has been provided.
     * 
     * If all values are not set the method will throw an
     * {@link InvalidParameterException}.
     * 
     * @param <T>
     *            The type of the <code>objectToSet</code>.
     * @param objectToSet
     *            The object to check.
     * @param message
     *            The message of the generated exception.
     * @param defaultValue
     *            The default value to apply in case the
     *            <code>objectToSet</code> is <code>null</code>.
     * @return The given <code>objectToSet</code> if its not <code>null</code>.
     *         Otherwise the <code>defaultValue</code> will be returned.
     * @throws InvalidParameterException
     *             If the <code>objectToSet</code> is <code>null</code> and, in
     *             addition, no <code>defaultValue</code> has been provided.
     */
    public static <T> T setIfNotNull(T objectToSet, String message, @Nullable T defaultValue) {
        if (objectToSet == null) {
            if (defaultValue != null) {
                return defaultValue;
            }

            throw new InvalidParameterException(message);
        }

        return objectToSet;
    }

    /**
     * Like {@link #setIfNotNull(Object, String, Object)}, but the generated
     * exception will contain a static text.
     * 
     * @param <T>
     *            The type of the <code>objectToSet</code>.
     * @param objectToSet
     *            The object to check.
     * @param defaultValue
     *            The default value to apply in case the
     *            <code>objectToSet</code> is <code>null</code>.
     * @return The given <code>objectToSet</code> if its not <code>null</code>.
     *         Otherwise the <code>defaultValue</code> will be returned.
     * @throws InvalidParameterException
     *             If the <code>objectToSet</code> is <code>null</code> and, in
     *             addition, no <code>defaultValue</code> has been provided.
     */
    public static <T> T setIfNotNull(T objectToSet, @Nullable T defaultValue) {
        return setIfNotNull(objectToSet, "Both, the objectToSet and the default value are null.", defaultValue);
    }

    /**
     * 
     * @param collectionToSet
     * @param message
     * @return
     */
    public static <T> List<T> setIfNotNullAndNotEmpty(List<T> collectionToSet, String message) {
        if (collectionToSet == null || collectionToSet.isEmpty()) {
            throw new InvalidParameterException(message);
        }

        return collectionToSet;
    }
}
