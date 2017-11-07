package eu.bittrade.libs.steemj.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.ECKey.ECDSASignature;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemFatalErrorException;

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
        return currentPrivateKey.getPrivateKeyEncoded(NetworkParameters.fromID(NetworkParameters.ID_MAINNET))
                .toBase58();
    }

    /**
     * Use this method to identify the correct key type (posting, active, owner,
     * memo) by iterating through the types and comparing the elliptic curves.
     * 
     * @param signature
     *            The created signature of the message.
     * @param messageAsHash
     *            The hash value of the message.
     * @param requiredPrivateKey
     *            The private key which has been used to sign the message.
     * @return The key type indicator (0 = posting, 1 = active, 2 = owner, 3 =
     *         memo).
     * @throws SteemFatalErrorException
     *             If no key type could have been identified.
     */
    public static int getKeyType(ECDSASignature signature, Sha256Hash messageAsHash, ECKey requiredPrivateKey) {
        Integer recId = null;
        for (int i = 0; i < 4; i++) {
            ECKey publicKey = ECKey.recoverFromSignature(i, signature, messageAsHash,
                    requiredPrivateKey.isCompressed());
            if (publicKey != null && publicKey.getPubKeyPoint().equals(requiredPrivateKey.getPubKeyPoint())) {
                recId = i;
                break;
            }
        }

        if (recId == null) {
            throw new SteemFatalErrorException("Could not construct a recoverable key. This should never happen.");
        }

        return recId;
    }

    /**
     * Create a signed transaction based on the given <code>keyType</code>,
     * <code>signature</code> and <code>requiredPrivateKey</code>.
     * 
     * @param keyType
     *            The key type to set.
     * @param signature
     *            The signature used to sign a message.
     * @param requiredPrivateKey
     *            The signature of the message.
     * @return The signed Transaction.
     */
    public static byte[] createSignedTransaction(int keyType, ECDSASignature signature, ECKey requiredPrivateKey) {
        int headerByte = keyType + 27 + (requiredPrivateKey.isCompressed() ? 4 : 0);

        byte[] signedTransaction = new byte[65];

        signedTransaction[0] = (byte) headerByte;
        System.arraycopy(Utils.bigIntegerToBytes(signature.r, 32), 0, signedTransaction, 1, 32);
        System.arraycopy(Utils.bigIntegerToBytes(signature.s, 32), 0, signedTransaction, 33, 32);

        return signedTransaction;
    }

    /**
     * Create a permlink string from the given <code>title</code>:
     * <ol>
     *     <li>The title is trimmed and converted to lowercase</li>
     *     <li>Spaces are converted to hyphens</li>
     *     <li>Disallowed characters are removed</li>
     *     <li>Contiguous hyphens are replaced with a single hyphen</li>
     * </ol>
     *
     * @param title
     *          The string to convert
     * @return The generated permlink
     */
    public static String createPermlinkString(String title) {
        return title
                .trim().toLowerCase()
                .replaceAll(" ", "-")
                .replaceAll("[^a-z0-9-]+", "")
                .replaceAll("-+", "-");
    }
}