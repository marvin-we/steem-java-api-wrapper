package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.ECKey.ECDSASignature;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemFatalErrorException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem Transaction and also provides methods to sign a
 * Transaction.
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class Transaction implements ByteTransformable, Serializable {
    private static final long serialVersionUID = 4821422578657270330L;
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
    private transient TimePointSec expirationDate;
    private transient List<Operation> operations;
    protected transient List<String> signatures;
    // Original type is "extension_type" which is an array of "future_extions".
    private transient List<FutureExtensions> extensions;

    /**
     * Create a new Transaction.
     */
    public Transaction() {
        // Set default values to avoid null pointer exceptions.
        this.signatures = new ArrayList<>();
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
    protected List<String> getSignatures() {
        return this.signatures;
    }

    /**
     * Verify that the signature is canonical.
     * 
     * Original implementation can be found <a href=
     * "https://github.com/kenCode-de/graphenej/blob/master/graphenej/src/main/java/de/bitsharesmunich/graphenej/Transaction.java"
     * >here.</a>
     * 
     * @param signature
     *            A single signature in its byte representation.
     * @return True if the signature is canonical or false if not.
     */
    private boolean isCanonical(byte[] signature) {
        return ((signature[0] & 0x80) != 0) || (signature[0] == 0) || ((signature[1] & 0x80) != 0)
                || ((signature[32] & 0x80) != 0) || (signature[32] == 0) || ((signature[33] & 0x80) != 0);
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
     * @param refBlockNum
     *            The ref block number as int.
     */
    public void setRefBlockNum(int refBlockNum) {
        this.refBlockNum = refBlockNum;
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
        this.refBlockPrefix = refBlockPrefix;
    }

    /**
     * Set the ref block prefix by providing its String representation. The
     * String representation can be received from the @link
     * {@link eu.bittrade.libs.steemj.Steemj#getDynamicGlobalProperties
     * getDynamicGlobalProperties} method.
     * 
     * @param refBlockPrefix
     *            The String representation of the ref block prefix.
     */
    public void setRefBlockPrefix(String refBlockPrefix) {
        this.refBlockPrefix = Utils.readUint32(Utils.HEX.decode(refBlockPrefix), 4);
    }

    /**
     * TODO:
     * 
     * @return the expirationDate
     */
    public TimePointSec getExpirationDate() {
        return expirationDate;
    }

    /**
     * TODO:
     * 
     * @param expirationDate
     *            the expirationDate to set
     */
    public void setExpirationDate(TimePointSec expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     *
     * Like {@link #sign(String) sign(String)}, but uses the default Steem chain
     * id.
     *
     * @throws SteemInvalidTransactionException
     *             If the transaction can not be signed.
     */
    public void sign() throws SteemInvalidTransactionException {
        sign(SteemJConfig.getInstance().getChainId());
    }

    /**
     * Use this method if you want to specify a different chainId than the
     * default one for STEEM. Otherwise use the {@link #sign() sign()} method.
     * 
     * @param chainId
     *            The chain id that should be used during signing.
     * @throws SteemInvalidTransactionException
     *             If the transaction can not be signed.
     */
    public void sign(String chainId) throws SteemInvalidTransactionException {
        // Before we start signing the transaction we check if all required
        // fields are present, which private keys are required and if those keys
        // are provided.
        if (this.getExpirationDate() == null || this.getExpirationDate().getDateTimeAsTimestamp() == 0) {
            // The expiration date is not set by the user so we do it on our own
            // by adding the maximal allowed offset to the current time.
            this.setExpirationDate(new TimePointSec(
                    System.currentTimeMillis() + SteemJConfig.getInstance().getMaximumExpirationDateOffset() - 60000L));
            LOGGER.debug("No expiration date has been provided so the latest possible time is used.");
        } else if (this.getExpirationDate()
                .getDateTimeAsTimestamp() > (new Timestamp(System.currentTimeMillis())).getTime()
                        + SteemJConfig.getInstance().getMaximumExpirationDateOffset()) {
            LOGGER.warn("The configured expiration date for this transaction is to far "
                    + "in the future and may not be accepted by the Steem node.");
        } else if (this.operations == null || this.operations.isEmpty()) {
            throw new SteemInvalidTransactionException("At least one operation is required to sign the transaction.");
        } else if (this.refBlockNum == 0) {
            throw new SteemInvalidTransactionException("The refBlockNum field needs to be set.");
        } else if (this.refBlockPrefix == 0) {
            throw new SteemInvalidTransactionException("The refBlockPrefix field needs to be set.");
        }

        // Create a local list containing all required key types per account.
        List<ECKey> requiredPrivateKeys = new ArrayList<>();

        // Check which keys are required for the attached operations to
        // avoid an "irrelevant signature included / Unnecessary signature(s)
        // detected" error.
        for (Operation operation : this.operations) {
            // Skip validation for Operations that do not need a specific
            // private key type.
            if (operation.getRequiredPrivateKeyTypes() == null || operation.getRequiredPrivateKeyTypes().isEmpty()) {
                continue;
            }

            // Check if the requested keys are present.
            for (ImmutablePair<AccountName, PrivateKeyType> privateKeyType : operation.getRequiredPrivateKeyTypes()) {
                try {
                    ECKey privateKey = SteemJConfig.getInstance().getPrivateKeyStorage()
                            .getKeyForAccount(privateKeyType.getRight(), privateKeyType.getLeft());
                    if (!requiredPrivateKeys.contains(privateKey)) {
                        requiredPrivateKeys.add(privateKey);
                    }
                } catch (InvalidParameterException ipe) {
                    throw new SteemInvalidTransactionException(
                            "The operation of type " + operation.getClass().getSimpleName()
                                    + " requires a private key of type " + privateKeyType.getRight() + " for the user "
                                    + privateKeyType.getLeft().getAccountName() + ".");
                }
            }
        }

        for (ECKey requiredPrivateKey : requiredPrivateKeys) {
            boolean isCanonical = false;
            byte[] signedTransaction = null;

            Sha256Hash messageAsHash;
            while (!isCanonical) {
                try {
                    messageAsHash = Sha256Hash.wrap(Sha256Hash.hash(this.toByteArray(chainId)));
                } catch (SteemInvalidTransactionException e) {
                    throw new SteemInvalidTransactionException(
                            "The required encoding is not supported by your platform.", e);
                }
                ECDSASignature signature = requiredPrivateKey.sign(messageAsHash);

                /*
                 * Identify the correct key type (posting, active, owner, memo)
                 * by iterating through the types and comparing the elliptic
                 * curves.
                 */
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
                    throw new SteemFatalErrorException(
                            "Could not construct a recoverable key. This should never happen.");
                }

                int headerByte = recId + 27 + (requiredPrivateKey.isCompressed() ? 4 : 0);
                signedTransaction = new byte[65];
                signedTransaction[0] = (byte) headerByte;
                System.arraycopy(Utils.bigIntegerToBytes(signature.r, 32), 0, signedTransaction, 1, 32);
                System.arraycopy(Utils.bigIntegerToBytes(signature.s, 32), 0, signedTransaction, 33, 32);

                if (isCanonical(signedTransaction)) {
                    this.getExpirationDate().setDateTime(this.getExpirationDate().getDateTimeAsTimestamp() + 1);
                } else {
                    isCanonical = true;
                }
            }

            this.signatures.add(Utils.HEX.encode(signedTransaction));
        }
    }

    /**
     * Like {@link #toByteArray(String) toByteArray(String)}, but uses the
     * default Steem chain id.
     * 
     * @throws SteemInvalidTransactionException
     *             If the transaction can not be signed.
     */
    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        return toByteArray(SteemJConfig.getInstance().getChainId());
    }

    /**
     * This method creates a byte array based on a transaction object under the
     * use of a guide written by <a href="https://Steemit.com/Steem/@xeroc/">
     * Xeroc</a>. This method should only be used internally.
     * 
     * If a chainId is provided it will be added in front of the byte array.
     * 
     * @return The serialized transaction object.
     * @param chainId
     *            The HEX representation of the chain Id you want to use for
     *            this transaction.
     * @throws SteemInvalidTransactionException
     *             If the transaction can not be signed.
     */
    protected byte[] toByteArray(String chainId) throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedTransaction = new ByteArrayOutputStream()) {
            if (chainId != null && !chainId.isEmpty()) {
                serializedTransaction.write(Utils.HEX.decode(chainId));
            }
            serializedTransaction.write(SteemJUtils.transformShortToByteArray(this.getRefBlockNum()));
            serializedTransaction.write(SteemJUtils.transformIntToByteArray((int) this.getRefBlockPrefix()));
            serializedTransaction.write(this.getExpirationDate().toByteArray());
            serializedTransaction.write(SteemJUtils.transformLongToVarIntByteArray(this.getOperations().size()));

            for (Operation operation : this.getOperations()) {
                serializedTransaction.write(operation.toByteArray());
            }

            for (FutureExtensions futureExtensions : this.getExtensions()) {
                serializedTransaction.write(futureExtensions.toByteArray());
            }

            return serializedTransaction.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the transaction into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
