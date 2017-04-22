package eu.bittrade.libs.steem.api.wrapper.models;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.ECKey.ECDSASignature;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;
import eu.bittrade.libs.steem.api.wrapper.interfaces.IByteArray;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * This class represents a Steem Transaction and also provides methods to sign a
 * Transaction.
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class Transaction implements IByteArray, Serializable {
    private static final long serialVersionUID = 4821422578657270330L;

    /**
     * For STEEM the the chain id is a 256bit long 0 sequence. It could also be
     * pulled using the "get_config" api call, but we are going to safe some
     * resources by using a static reference.
     */
    private static final String CHAIN_ID = "0000000000000000000000000000000000000000000000000000000000000000";

    /**
     * The ref_block_num indicates a particular block in the past by referring
     * to the block number which has this number as the last two bytes.
     * 
     * The original type is Uint16, but we have to use int (32bit) as Java does
     * not support unsigned types. For sure we will only use 2 bytes of this
     * field when we serialize it.
     */
    @JsonProperty("ref_block_num")
    private int refBlockNum;

    /**
     * The ref_block_prefix on the other hand is obtain from the block id of
     * that particular reference block.
     * 
     * The original type is Uint32, but we have to use long (64 bit) as Java
     * does not support unsigned types. For sure we will only use 4 bytes of
     * this field when we serialize it.
     * 
     */
    @JsonProperty("ref_block_prefix")
    private long refBlockPrefix;
    @JsonProperty("expiration")
    private long expirationDate;
    private Operation[] operations;
    protected String[] signatures;
    // TODO: Find out what type this is and what the use of this field is.
    private List<Object> extensions;

    public Transaction() {
        // Set the expiration date to the current date and time.
        this.expirationDate = (new Timestamp(System.currentTimeMillis())).getTime();
    }

    /**
     * This method returns the expiration date as its String representation. For
     * this a specific date format ("yyyy-MM-dd'T'HH:mm:ss") is used as it is
     * required by the Steem api.
     * 
     * @return The expiration date as String.
     */
    public String getExpirationDate() {
        SimpleDateFormat simpleDateFormatForJSON = new SimpleDateFormat(
                SteemApiWrapperConfig.getInstance().getDateTimePattern());
        simpleDateFormatForJSON.setTimeZone(TimeZone.getTimeZone(SteemApiWrapperConfig.getInstance().getTimeZoneId()));
        return simpleDateFormatForJSON.format(getExpirationDateAsDate());
    }

    /**
     * Get the configured expiration date as a Java.util.Date object.
     * 
     * @return The expiration date.
     */
    @JsonIgnore
    public Date getExpirationDateAsDate() {
        return new Date(expirationDate);
    }

    /**
     * This method returns the expiration data as a Date object.
     * 
     * @return The expiration date.
     */
    @JsonIgnore
    public int getExpirationDateAsInt() {
        return (int) (expirationDate / 1000);
    }

    /**
     * Get the list of configured extensions.
     * 
     * @return All extensions.
     */
    public List<Object> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<>();
        }
        return extensions;
    }

    /**
     * Get all Operations that have been added to this transaction.
     * 
     * @return All operations.
     */
    public Operation[] getOperations() {
        return operations;
    }

    /**
     * Get the ref block number in its int representation.
     * 
     * @return The ref block number.
     */
    public int getRefBlockNum() {
        return refBlockNum;
    }

    /**
     * Get the ref block prefix in its long representation.
     * 
     * @return The ref block prefix.
     */
    public long getRefBlockPrefix() {
        return refBlockPrefix;
    }

    /**
     * Get the signatures for this transaction. This method is only used for
     * JSON deserialization and for testing purposes.
     * 
     * @return An array of currently appended signatures.
     */
    @JsonProperty("signatures")
    protected String[] getSignatures() {
        return this.signatures;
    }

    /**
     * The date has to be specified as String and needs a special format:
     * yyyy-MM-dd'T'HH:mm:ss
     * 
     * <p>
     * Example: "2016-08-08T12:24:17"
     * </p>
     * 
     * @param expirationDate
     *            The expiration date as its String representation.
     * @throws ParseException
     *             If the given String does not patch the pattern.
     */
    public void setExpirationDate(String expirationDate) throws ParseException {
        // TODO: Verifiy with
        // SteemApiWrapperConfig.getInstance().getMaximumExpirationDateOffset
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                SteemApiWrapperConfig.getInstance().getDateTimePattern());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(SteemApiWrapperConfig.getInstance().getTimeZoneId()));
        calendar.setTime(simpleDateFormat.parse(expirationDate + SteemApiWrapperConfig.getInstance().getTimeZoneId()));
        this.expirationDate = calendar.getTimeInMillis();
    }

    /**
     * Extensions are currently not supported and will be ignored.
     * 
     * @param extensions
     */
    public void setExtensions(List<Object> extensions) {
        this.extensions = extensions;
    }

    /**
     * Define a list of operations that should be send with this transaction.
     * 
     * @param operations
     *            A list of operations.
     */
    public void setOperations(Operation[] operations) {
        this.operations = operations;
    }

    public void setRefBlockNum(int refBlockNum) {
        this.refBlockNum = refBlockNum;
    }

    /**
     * Set the ref block prefix by providing its long representation. If you
     * only have the String representation use {@link #setRefBlockPrefix(String)
     * setRefBlockPrefix(String)}.
     * 
     * @param refBlockPrefix
     */
    public void setRefBlockPrefix(long refBlockPrefix) {
        this.refBlockPrefix = refBlockPrefix;
    }

    /**
     * Set the ref block prefix by providing its String representation. The
     * String representation can be received from the @link
     * {@link eu.bittrade.libs.Steem.api.wrapper.SteemApiWrapper#getDynamicGlobalProperties
     * getDynamicGlobalProperties} method.
     * 
     * @param refBlockPrefix
     *            The String representation of the ref block prefix.
     */
    public void setRefBlockPrefix(String refBlockPrefix) {
        this.refBlockPrefix = Utils.readUint32(Utils.HEX.decode(refBlockPrefix), 4);
    }

    /**
     *
     * Like {@link #sign(List) #sign(List)}, but uses the default Steem chain
     * id.
     *
     * @throws UnsupportedEncodingException
     *             If the used encoding is not supported on your platform.
     */
    public void sign() throws UnsupportedEncodingException {
        sign(CHAIN_ID);
    }

    /**
     * Use this method if you want to specify a different chainId than the
     * default one for STEEM. Otherwise use the {@link #sign(List) sign(List)}
     * method.
     * 
     * @param chainId
     *            The chain id that should be used during signing.
     * @throws UnsupportedEncodingException
     *             If the used encoding is not supported on your platform.
     */
    public void sign(String chainId) throws UnsupportedEncodingException {
        // TODO Verify that all required fields are provided and throw an
        // Exception if not. (Create InvalidSteemTransactionException)
        // TODO Check which keys are required for the attached operations to
        // avoid an "irrelevant signature included\nUnnecessary signature(s)
        // detected" error.
        for (ECKey privateKey : SteemApiWrapperConfig.getInstance().getPrivateKeys().values()) {
            // Skip not provided keys.
            if (privateKey == null) {
                continue;
            }

            boolean isCanonical = false;
            byte[] signedTransaction = null;

            while (!isCanonical) {
                Sha256Hash messageAsHash = Sha256Hash.wrap(Sha256Hash.hash(this.toByteArray(chainId)));
                ECDSASignature signature = privateKey.sign(messageAsHash);

                /*
                 * Identify the correct key type (posting, active, owner, memo)
                 * by iterating through the types and comparing the elliptic
                 * curves.
                 */
                Integer recId = null;
                for (int i = 0; i < 4; i++) {
                    ECKey publicKey = ECKey.recoverFromSignature(i, signature, messageAsHash,
                            privateKey.isCompressed());
                    if (publicKey != null && publicKey.getPubKeyPoint().equals(privateKey.getPubKeyPoint())) {
                        recId = i;
                        break;
                    }
                }

                if (recId == null) {
                    throw new RuntimeException("Could not construct a recoverable key. This should never happen.");
                }

                int headerByte = recId + 27 + (privateKey.isCompressed() ? 4 : 0);
                signedTransaction = new byte[65];
                signedTransaction[0] = (byte) headerByte;
                System.arraycopy(Utils.bigIntegerToBytes(signature.r, 32), 0, signedTransaction, 1, 32);
                System.arraycopy(Utils.bigIntegerToBytes(signature.s, 32), 0, signedTransaction, 33, 32);

                if (isCanonical(signedTransaction)) {
                    this.expirationDate += 1;
                } else {
                    isCanonical = true;
                }
            }

            this.signatures = ArrayUtils.add(this.signatures, Utils.HEX.encode(signedTransaction));
        }
    }

    /**
     * Verify that the signature is canonical.
     * 
     * Original implementation can be found <a href=
     * "https://github.com/kenCode-de/graphenej/blob/master/graphenej/src/main/java/de/bitsharesmunich/graphenej/Transaction.java"
     * >here.</a>
     */
    private boolean isCanonical(byte[] signature) {
        return ((signature[0] & 0x80) != 0) || (signature[0] == 0) || ((signature[1] & 0x80) != 0)
                || ((signature[32] & 0x80) != 0) || (signature[32] == 0) || ((signature[33] & 0x80) != 0);
    }

    /**
     * Like {@link #toByteArray(String) toByteArray(String)}, but uses the
     * default Steem chain id.
     */
    @Override
    public byte[] toByteArray() throws UnsupportedEncodingException {
        return toByteArray(CHAIN_ID);
    }

    /**
     * This method creates a byte array based on a transaction object under the
     * use of a guide written by <a
     * href="https://Steemit.com/Steem/@xeroc/steem-transaction-signing-in-a-nutshell>Xeroc</a>.
     * This method should only be used internally.
     * 
     * If a chainId is provided it will be added in front of the byte array.
     * 
     * @return The serialized transaction object.
     * @throws UnsupportedEncodingException
     *             If your platform does not know US-ASCII.
     */
    protected byte[] toByteArray(String chainId) throws UnsupportedEncodingException {
        // TODO: Use
        // https://bitcoinj.github.io/javadoc/0.12/org/bitcoinj/core/Utils.html
        // methods
        byte[] serializedTransaction = {};

        if (chainId != null && !chainId.isEmpty()) {
            serializedTransaction = ArrayUtils.addAll(serializedTransaction, Utils.HEX.decode(chainId));
        }

        // 2 Btyes for ref block number. Ignore the first 2 bytes of the long
        // field which has be used because Java does not support unsigned types.
        serializedTransaction = ArrayUtils.addAll(serializedTransaction,
                ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short) this.getRefBlockNum()).array());

        // 4 Bytes for ref block prefix. Ignore the first 4 bytes of the long
        // field which has be used because Java does not support unsigned types.
        serializedTransaction = ArrayUtils.addAll(serializedTransaction,
                ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt((int) this.getRefBlockPrefix()).array());

        // 4 Bytes for expiration date
        serializedTransaction = ArrayUtils.addAll(serializedTransaction,
                ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(this.getExpirationDateAsInt()).array());

        VarInt numberOfTransactionsAsVarInt = new VarInt(this.getOperations().length);
        serializedTransaction = ArrayUtils.addAll(serializedTransaction, numberOfTransactionsAsVarInt.encode());

        for (Operation operation : this.getOperations()) {
            serializedTransaction = ArrayUtils.addAll(serializedTransaction, operation.toByteArray());
        }

        // TODO: Understand what this field is used for. For now we just append
        // an empty byte.
        byte[] extension = { 0x00 };

        serializedTransaction = ArrayUtils.addAll(serializedTransaction, extension);

        return serializedTransaction;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
