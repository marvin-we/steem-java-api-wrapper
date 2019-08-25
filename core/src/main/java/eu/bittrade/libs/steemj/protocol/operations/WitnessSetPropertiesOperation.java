package eu.bittrade.libs.steemj.protocol.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.FutureExtensions;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.LegacyAsset;
import eu.bittrade.libs.steemj.protocol.PublicKey;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "witness_update_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessSetPropertiesOperation extends Operation {
    @JsonProperty("owner")
    private AccountName owner;
    @JsonProperty("url")
    private URL url;
    @JsonProperty("new_signing_key")
    private PublicKey newSigningKey;
    @JsonProperty("props")
    private ChainProperties properties;
    @JsonProperty("fee")
    private LegacyAsset fee;
    @JsonProperty("extensions")
    private List<FutureExtensions> extensions;

    /**
     * Create a new witness update operation.
     *
     * Users who wish to become a witness must pay a {@link #getFee() fee}
     * acceptable to the current witnesses to apply for the position and allow
     * voting to begin.
     *
     * If the {@link #getOwner() owner} isn't a witness they will become a
     * witness. Witnesses are charged a fee equal to 1 weeks worth of witness
     * pay which in turn is derived from the current share supply. The fee is
     * only applied if the owner is not already a witness.
     *
     * If the {@link #getNewSigningKey() newSigningKey} is null then the witness
     * is removed from contention. The network will pick the top 21 witnesses
     * for producing blocks.
     *
     * @param owner
     *            The Witness account name to set (see
     *            {@link #setOwner(AccountName)}).
     * @param url
     *            The URL to a statement post (see {@link #setUrl(URL)}).
     * @param newSigningKey
     *            The public part of the key used to sign a block (see
     *            {@link #setNewSigningKey(PublicKey)}).
     * @param properties
     *            The chain properties the witness is voting for (see
     *            {@link #setProperties(ChainProperties)}).
     * @param fee
     *            The fee to pay for this update (see
     *            {@link #setFee(LegacyAsset)}).
     */
    @JsonCreator
    public WitnessSetPropertiesOperation(@JsonProperty("owner") AccountName owner, @JsonProperty("url") URL url,
            @JsonProperty("block_signing_key") PublicKey newSigningKey,
            @JsonProperty("props") ChainProperties properties, @JsonProperty("fee") LegacyAsset fee,
            @JsonProperty("extensions") List<FutureExtensions> extensions) {
        super(false);

        this.setOwner(owner);
        this.setUrl(url);
        this.setNewSigningKey(newSigningKey);
        this.setProperties(properties);
        this.setFee(fee);
        this.setExtensions(extensions);
    }

    /**
     * Like
     * {@link #WitnessSetPropertiesOperation(AccountName, URL, PublicKey, ChainProperties, LegacyAsset, List)},
     * but creates a new ChainProperties object with default values (Account
     * creation fee = 0.001 STEEM, maximum block size = minimum block size * 2,
     * SBD interest rate = 10%).
     *
     * @param owner
     *            The Witness account name to set (see
     *            {@link #setOwner(AccountName)}).
     * @param url
     *            The URL to a statement post (see {@link #setUrl(URL)}).
     * @param blockSigningKey
     *            The public part of the key used to sign a block (see
     *            {@link #setNewSigningKey(PublicKey)}).
     * @param fee
     *            The fee to pay for this update (see
     *            {@link #setFee(LegacyAsset)}).
     */
    public WitnessSetPropertiesOperation(AccountName owner, URL url, PublicKey blockSigningKey, LegacyAsset fee,
            List<FutureExtensions> extensions) {
        super(false);

        this.setOwner(owner);
        this.setUrl(url);
        this.setNewSigningKey(blockSigningKey);
        this.setFee(fee);

        LegacyAsset accountCreationFee = new LegacyAsset(1, SteemJConfig.getInstance().getTokenSymbol());
        long maximumBlockSize = 131072;
        int sdbInterestRate = 1000;
        ChainProperties chainProperties = new ChainProperties(accountCreationFee, maximumBlockSize, sdbInterestRate);

        this.setProperties(chainProperties);
    }

    /**
     * Get the account name of the account for that the witness update operation
     * has been executed.
     * 
     * @return The name of the account that this operation has been executed
     *         for.
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * Set the account name of the account for that the witness update operation
     * should be executed. <b>Notice:</b> The private active key of this account
     * needs to be stored in the key storage.
     * 
     * @param owner
     *            The name of the account that this operation should be executed
     *            for.
     * @throws InvalidParameterException
     *             If the owner is null.
     */
    public void setOwner(AccountName owner) {
        this.owner = SteemJUtils.setIfNotNull(owner, "The owner can't be null.");
    }

    /**
     * Get the URL that has been added to this witness update operation. In most
     * of the cases this URL is a link to a comment that describes the update.
     * 
     * @return The URL that has been added to this witness update operation.
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Set the URL that should be added to this witness update operation. In
     * most of the cases this URL is a link to a comment that describes the
     * update.
     * 
     * @param url
     *            The URL that should be added to this witness update operation.
     * @throws InvalidParameterException
     *             If the url is null or empty.
     */
    public void setUrl(URL url) {
        this.url = SteemJUtils.setIfNotNull(url, "You need to provide a URL.");
    }

    /**
     * Get the public key of the key pair that will be used to sign blocks.
     * 
     * @return The public key of the key pair that will be used to sign blocks.
     */
    public PublicKey getNewSigningKey() {
        return newSigningKey;
    }

    /**
     * Set the public key of the key pair that will be used to sign blocks.
     * 
     * @param blockSigningKey
     *            The public key of the key pair that will be used to sign
     *            blocks.
     * @throws InvalidParameterException
     *             If the blockSigningKey is null.
     */
    public void setNewSigningKey(PublicKey blockSigningKey) {
        this.newSigningKey = SteemJUtils.setIfNotNull(blockSigningKey, "You need to provide a new signing key.");
    }

    /**
     * Get the chain properties that this witness is using.
     * 
     * @return The chain properties used by this witness.
     */
    public ChainProperties getProperties() {
        return properties;
    }

    /**
     * Set the chain properties that should be used.
     * 
     * @param properties
     *            The chain properties used by this witness.
     * @throws InvalidParameterException
     *             If the properties are null.
     */
    public void setProperties(ChainProperties properties) {
        this.properties = SteemJUtils.setIfNotNull(properties, "You need to provide the blockchain properties.");
    }

    /**
     * Get the fee that has been paid for this witness update.
     * 
     * @return The fee that has been paid for this witness update.
     */
    public LegacyAsset getFee() {
        return fee;
    }

    /**
     * Set the fee that should be paid for this witness update. The
     * <code>fee</code> paid to register a new witness, should be 10x current
     * block production pay.
     * 
     * @param fee
     *            The fee that should be paid for this witness update.
     * @throws InvalidParameterException
     *             If the provided asset object is null.
     */
    public void setFee(LegacyAsset fee) {
        this.fee = SteemJUtils.setIfNotNull(fee, "The fee can't be null.");
    }

    /**
     * Get the list of configured extensions.
     *
     * @return All extensions.
     */
    public List<FutureExtensions> getExtensions() {
        return extensions;
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

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedWitnessSetPropertesOperation = new ByteArrayOutputStream()) {
            serializedWitnessSetPropertesOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.WITNESS_SET_PROPERTIES_OPERATION.getOrderId()));
            serializedWitnessSetPropertesOperation.write(this.getOwner().toByteArray());
            serializedWitnessSetPropertesOperation
                    .write(SteemJUtils.transformStringToVarIntByteArray(this.getUrl().toString()));
            serializedWitnessSetPropertesOperation.write(this.getNewSigningKey().toByteArray());
            serializedWitnessSetPropertesOperation.write(this.getProperties().toByteArray());
            serializedWitnessSetPropertesOperation.write(this.getFee().toByteArray());

            serializedWitnessSetPropertesOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(this.getExtensions().size()));
            for (FutureExtensions futureExtensions : this.getExtensions()) {
                serializedWitnessSetPropertesOperation.write(futureExtensions.toByteArray());
            }

            return serializedWitnessSetPropertesOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        Map<SignatureObject, PrivateKeyType> requiredAuthorities = requiredAuthoritiesBase;

        requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, this.getOwner(), PrivateKeyType.ACTIVE);
        requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, this.getOwner(), PrivateKeyType.OWNER);

        return requiredAuthorities;
    }

    @Override
    public void validate(List<ValidationType> validationsToSkip) {
        if ((!validationsToSkip.contains(ValidationType.SKIP_ASSET_VALIDATION)
                && !validationsToSkip.contains(ValidationType.SKIP_VALIDATION))
                && (this.getFee().getAmount() < 0
                        || !SteemJConfig.getInstance().getTokenSymbol().equals(this.getFee().getSymbol()))) {
            throw new InvalidParameterException("The fee needs to be a positive amount of STEEM.");
        }
    }
}
