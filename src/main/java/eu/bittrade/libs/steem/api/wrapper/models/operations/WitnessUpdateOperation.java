package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.ChainProperties;
import eu.bittrade.libs.steem.api.wrapper.models.PublicKey;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "witness_update_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessUpdateOperation extends Operation {
    @JsonProperty("owner")
    private AccountName owner;
    @JsonProperty("url")
    private String url;
    @JsonProperty("block_signing_key")
    private PublicKey blockSigningKey;
    @JsonProperty("props")
    private ChainProperties properties;
    @JsonProperty("fee")
    private Asset fee;

    /**
     * Create a new witness update operation.
     * 
     * Users who wish to become a witness must pay a
     * {@link eu.bittrade.libs.steem.api.wrapper.models.operations.WitnessUpdateOperation#fee
     * fee} acceptable to the current witnesses to apply for the position and
     * allow voting to begin.
     *
     * If the
     * {@link eu.bittrade.libs.steem.api.wrapper.models.operations.WitnessUpdateOperation#owner
     * owner} isn't a witness they will become a witness. Witnesses are charged
     * a fee equal to 1 weeks worth of witness pay which in turn is derived from
     * the current share supply. The fee is only applied if the owner is not
     * already a witness.
     *
     * If the
     * {@link eu.bittrade.libs.steem.api.wrapper.models.operations.WitnessUpdateOperation#blockSigningKey
     * blockSigningKey} is null then the witness is removed from contention. The
     * network will pick the top 21 witnesses for producing blocks.
     */
    public WitnessUpdateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
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
     * should be executed.
     * 
     * @param owner
     *            The name of the account that this operation should be executed
     *            for.
     */
    public void setOwner(AccountName owner) {
        this.owner = owner;
    }

    /**
     * Get the URL that has been added to this witness update operation. In most
     * of the cases this URL is a link to a comment that describes the update.
     * 
     * @return The URL that has been added to this witness update operation.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL that should be added to this witness update operation. In
     * most of the cases this URL is a link to a comment that describes the
     * update.
     * 
     * @param url
     *            The URL that should be added to this witness update operation.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the public key of the key pair that will be used to sign blocks.
     * 
     * @return The public key of the key pair that will be used to sign blocks.
     */
    public PublicKey getBlockSigningKey() {
        return blockSigningKey;
    }

    /**
     * Set the public key of the key pair that will be used to sign blocks.
     * 
     * @param blockSigningKey
     *            The public key of the key pair that will be used to sign
     *            blocks.
     */
    public void setBlockSigningKey(PublicKey blockSigningKey) {
        this.blockSigningKey = blockSigningKey;
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
     */
    public void setProperties(ChainProperties properties) {
        this.properties = properties;
    }

    /**
     * Get the fee that has been paid for this witness update.
     * 
     * @return The fee that has been paid for this witness update.
     */
    public Asset getFee() {
        return fee;
    }

    /**
     * Set the fee that should be paid for this witness update.
     * 
     * @param fee
     *            The fee that should be paid for this witness update.
     */
    public void setFee(Asset fee) {
        this.fee = fee;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedWitnessUpdateOperation = new ByteArrayOutputStream()) {
            serializedWitnessUpdateOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.WITNESS_UPDATE_OPERATION.ordinal()));
            serializedWitnessUpdateOperation.write(this.getOwner().toByteArray());
            serializedWitnessUpdateOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getUrl()));
            serializedWitnessUpdateOperation.write(this.getBlockSigningKey().toByteArray());
            serializedWitnessUpdateOperation.write(this.getProperties().toByteArray());
            serializedWitnessUpdateOperation.write(this.getFee().toByteArray());

            return serializedWitnessUpdateOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
