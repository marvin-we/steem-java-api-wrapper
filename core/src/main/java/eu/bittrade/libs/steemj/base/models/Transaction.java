package eu.bittrade.libs.steemj.base.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bitcoinj.core.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;

/**
 * This class represents a Steem "transaction" object.
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class Transaction implements Serializable {
    /** Generated serial version uid. */
    private static final long serialVersionUID = -3834759301983200246L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

    /**
     * The ref_block_num indicates a particular block in the past by referring
     * to the block number which has this number as the last two bytes.
     * 
     * The original type is Uint16, but we have to use int (32bit) as Java does
     * not support unsigned types. For sure we will only use 2 bytes of this
     * field when we serialize it.
     */
    @JsonProperty("ref_block_num")
    protected short refBlockNum;

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
    protected int refBlockPrefix;
    @JsonProperty("expiration")
    protected transient TimePointSec expirationDate;
    protected transient List<Operation> operations;
    // Original type is "extension_type" which is an array of "future_extions".
    protected transient List<FutureExtensions> extensions;

    /**
     * Create a new transaction object.
     */
    public Transaction() {
        // Apply default values:
        this.setRefBlockNum(0);
        this.setRefBlockPrefix(0);
    }

    /**
     * Get the list of configured extensions.
     * 
     * @return All extensions.
     */
    public List<FutureExtensions> getExtensions() {
        if (extensions == null || extensions.isEmpty()) {
            // Create a new ArrayList that contains an empty FutureExtension so
            // one byte gets added to the signature for sure.
            extensions = new ArrayList<>();
            extensions.add(new FutureExtensions());
        }
        return extensions;
    }

    /**
     * Get all Operations that have been added to this transaction.
     * 
     * @return All operations.
     */
    public List<Operation> getOperations() {
        return operations;
    }

    /**
     * Get the ref block number in its int representation.
     * 
     * The ref_block_num indicates a particular block in the past by referring
     * to the block number which has this number as the last two bytes.
     * 
     * @return The ref block number.
     */
    public int getRefBlockNum() {
        return Short.toUnsignedInt(refBlockNum);
    }

    /**
     * Get the ref block prefix in its long representation.
     * 
     * The ref_block_prefix on the other hand is obtain from the block id of
     * that particular reference block.
     * 
     * @return The ref block prefix.
     */
    public long getRefBlockPrefix() {
        return Integer.toUnsignedLong(refBlockPrefix);
    }

    /**
     * Extensions are currently not supported and will be ignored.
     * 
     * @param extensions
     *            Define a list of extensions.
     */
    public void setExtensions(List<FutureExtensions> extensions) {
        this.extensions = extensions;
    }

    /**
     * Define a list of operations that should be send with this transaction.
     * 
     * @param operations
     *            A list of operations.
     */
    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    /**
     * Set the ref block number by providing its int representation.
     * 
     * The ref_block_num indicates a particular block in the past by referring
     * to the block number which has this number as the last two bytes.
     * 
     * @param refBlockNum
     *            The ref block number as int.
     */
    public void setRefBlockNum(int refBlockNum) {
        this.refBlockNum = (short) refBlockNum;
    }

    /**
     * Set the ref block prefix by providing its long representation. If you
     * only have the String representation use {@link #setRefBlockPrefix(String)
     * setRefBlockPrefix(String)}.
     * 
     * @param refBlockPrefix
     *            The ref block prefix.
     */
    public void setRefBlockPrefix(long refBlockPrefix) {
        this.refBlockPrefix = (int) refBlockPrefix;
    }

    /**
     * Set the ref block prefix by providing its String representation. The
     * String representation can be received from the @link
     * {@link eu.bittrade.libs.steemj.SteemJ#getDynamicGlobalProperties
     * getDynamicGlobalProperties} method.
     * 
     * @param refBlockPrefix
     *            The String representation of the ref block prefix.
     */
    public void setRefBlockPrefix(String refBlockPrefix) {
        this.refBlockPrefix = (int) Utils.readUint32(Utils.HEX.decode(refBlockPrefix), 4);
    }

    /**
     * Get the currently configured expiration date. The expiration date defines
     * in which time the operation has to be processed. If not processed in the
     * given time, the transaction will not be accepted. <b>Notice</b> that this
     * method will return the latest possible expiration date if no other time
     * has been configured using the {@link #setExpirationDate(TimePointSec)
     * setExpirationDate(TimePointSec)} method.
     * 
     * @return The expiration date.
     */
    public TimePointSec getExpirationDate() {
        if (this.expirationDate == null || this.expirationDate.getDateTimeAsTimestamp() == 0) {
            // The expiration date is not set by the user so we do it on our own
            // by adding the maximal allowed offset to the current time.
            LOGGER.debug("No expiration date has been provided so the latest possible time is used.");
            return new TimePointSec(
                    System.currentTimeMillis() + SteemJConfig.getInstance().getMaximumExpirationDateOffset() - 60000L);
        }

        return this.expirationDate;
    }

    /**
     * Define how long this transaction is valid. If not processed in the given
     * time, the transaction will not be accepted.
     * 
     * @param expirationDate
     *            The expiration date to set.
     */
    public void setExpirationDate(TimePointSec expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
