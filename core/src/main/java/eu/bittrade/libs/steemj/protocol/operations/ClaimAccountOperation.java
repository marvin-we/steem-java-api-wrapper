package eu.bittrade.libs.steemj.protocol.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.FutureExtensions;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "claim_account_operation" object.
 *
 * @author <a href="https://github.com/radoslawpanczyk">radoslawpanczyk</a>
 */
public class ClaimAccountOperation extends Operation {

    @JsonProperty("fee")
    private Asset fee;

    @JsonProperty("creator")
    private AccountName creator;

    @JsonProperty("extensions")
    private List<FutureExtensions> extensions;

    /**
     * Create a new Operation object by providing the operation type.
     *
     * @param virtual
     *            Define if the operation instance is a virtual
     *            (<code>true</code>) or a market operation
     *            (<code>false</code>).
     */
    public ClaimAccountOperation(boolean virtual) {
        super(virtual);
    }

    @JsonCreator
    public ClaimAccountOperation(@JsonProperty("fee") Asset fee, @JsonProperty("creator") AccountName creator,
            @JsonProperty("extensions") List<FutureExtensions> extensions) {
        super(false);
        this.fee = fee;
        this.creator = creator;
        this.extensions = extensions;
    }

    /**
     * Get the fee the {@link #getCreator() creator} has paid to claim this
     * account.
     *
     * @return The fee.
     */
    public Asset getFee() {
        return fee;
    }

    /**
     * Set the fee the {@link #getCreator() creator} will pay to claim an
     * account.
     *
     * @param fee
     *            The fee.
     * @throws InvalidParameterException
     *             If the <code>fee</code> is null, of symbol type STEEM or less
     *             than 0.
     */
    public void setFee(Asset fee) {
        this.fee = fee;
    }

    /**
     * Get the account who claimed an account.
     *
     * @return The the user who claimed an account.
     */
    public AccountName getCreator() {
        return creator;
    }

    /**
     * Set the user who claimed an account. <b>Notice:</b> The private active
     * and owner keys of this account needs to be stored in the key storage.
     *
     * @param creator
     *            The the user who claims an account.
     * @throws InvalidParameterException
     *             If the <code>creator</code> is null.
     */
    public void setCreator(AccountName creator) {
        this.creator = creator;
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
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        Map<SignatureObject, PrivateKeyType> requiredAuthorities = requiredAuthoritiesBase;

        requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, this.getCreator(), PrivateKeyType.ACTIVE);
        requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, this.getCreator(), PrivateKeyType.OWNER);

        return requiredAuthorities;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedClaimAccountOperation = new ByteArrayOutputStream()) {
            serializedClaimAccountOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.CLAIM_ACCOUNT_OPERATION.getOrderId()));
            serializedClaimAccountOperation.write(this.getCreator().toByteArray());
            serializedClaimAccountOperation.write(this.getFee().toByteArray());

            serializedClaimAccountOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(this.getExtensions().size()));
            for (FutureExtensions futureExtensions : this.getExtensions()) {
                serializedClaimAccountOperation.write(futureExtensions.toByteArray());
            }

            return serializedClaimAccountOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)) {
            if (!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)) {
                if (!fee.getSymbol().equals(SteemJConfig.getInstance().getTokenSymbol())) {
                    throw new InvalidParameterException("The fee must be paid in STEEM.");
                } else if (fee.getAmount() < 0) {
                    throw new InvalidParameterException("The fee must be a postive amount.");
                }
            }
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
