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
import eu.bittrade.libs.steem.api.wrapper.util.SteemUtils;

/**
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
        super(PrivateKeyType.POSTING);
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
     * TODO: Is this the URL to the node?
     * 
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the blockSigningKey
     */
    public PublicKey getBlockSigningKey() {
        return blockSigningKey;
    }

    /**
     * @param blockSigningKey
     *            the blockSigningKey to set
     */
    public void setBlockSigningKey(PublicKey blockSigningKey) {
        this.blockSigningKey = blockSigningKey;
    }

    /**
     * @return the properties
     */
    public ChainProperties getProperties() {
        return properties;
    }

    /**
     * @param properties
     *            the properties to set
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
                    .write(SteemUtils.transformIntToVarIntByteArray(OperationType.WITNESS_UPDATE_OPERATION.ordinal()));
            serializedWitnessUpdateOperation.write(this.getOwner().toByteArray());
            serializedWitnessUpdateOperation.write(SteemUtils.transformStringToVarIntByteArray(this.getUrl()));
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
