package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.FutureExtensions;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "account_create_with_delegation_operation"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateWithDelegationOperation extends AbstractAccountCreateOperation {
    @JsonProperty("delegation")
    private Asset delegation;
    // Original type is "extension_type" which is an array of "future_extions".
    @JsonProperty("extensions")
    private List<FutureExtensions> extensions;

    /**
     * Create a new create account with delegation operation. Use this operation
     * to create a new account.
     * 
     * @param creator
     *            Set the account that will pay the <code>fee</code> to create
     *            the <code>newAccountName</code> (see
     *            {@link #setCreator(AccountName)}).
     * @param fee
     *            Set the fee the <code>creator</code> will pay (see
     *            {@link #setFee(Asset)}).
     * @param newAccountName
     *            Set the new account name (see
     *            {@link #setNewAccountName(AccountName)}).
     * @param delegation
     *            Set the amount of VESTS to delegate from the
     *            <code>creator</code> account to the
     *            <code>newAccountName</code> (see
     *            {@link #setDelegation(Asset)}).
     * @param owner
     *            The new owner authority or null if the owner authority should
     *            not be updated (see {@link #setOwner(Authority)}).
     * @param active
     *            The new active authority or null if the active authority
     *            should not be updated (see {@link #setActive(Authority)}).
     * @param posting
     *            The new posting authority or null if the posting authority
     *            should not be updated (see {@link #setPosting(Authority)}).
     * @param memoKey
     *            The new memo key or null if the memo key should not be updated
     *            (see {@link #setMemoKey(PublicKey)}).
     * @param jsonMetadata
     *            Set the additional information for the <code>account</code>
     *            (see {@link #setJsonMetadata(String)}).
     * @param extensions
     *            Add additional extensions to this operation (see
     *            {@link #setExtensions(List)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public AccountCreateWithDelegationOperation(@JsonProperty("creator") AccountName creator,
            @JsonProperty("fee") Asset fee, @JsonProperty("new_account_name") AccountName newAccountName,
            @JsonProperty("delegation") Asset delegation, @JsonProperty("owner") Authority owner,
            @JsonProperty("active") Authority active, @JsonProperty("posting") Authority posting,
            @JsonProperty("memo_key") PublicKey memoKey, @JsonProperty("json_metadata") String jsonMetadata,
            @JsonProperty("extensions") List<FutureExtensions> extensions) {
        super(false);

        this.setCreator(creator);
        this.setFee(fee);
        this.setNewAccountName(newAccountName);
        this.setDelegation(delegation);
        this.setOwner(owner);
        this.setActive(active);
        this.setPosting(posting);
        this.setMemoKey(memoKey);
        this.setJsonMetadata(jsonMetadata);
        this.setExtensions(extensions);
    }

    /**
     * Get the amount of VESTS the {@link #getCreator() creator} has delegated
     * to the {@link #getNewAccountName() newAccountName}.
     * 
     * @return The amount of VESTS delegated to the new account.
     */
    public Asset getDelegation() {
        return delegation;
    }

    /**
     * Set the amount of VESTS the {@link #getCreator() creator} will delegate
     * to the {@link #getNewAccountName() newAccountName}.
     * 
     * @param delegation
     *            The amount of VESTS delegated to the new account.
     * @throws InvalidParameterException
     *             If the <code>fee</code> is null, of symbol type VESTS or less
     *             than 0.
     */
    public void setDelegation(Asset delegation) {
        this.delegation = setIfNotNull(delegation, "The delegation can't be null.");
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
        if (extensions == null) {
            this.extensions = new ArrayList<>();
        } else {
            this.extensions = extensions;
        }
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountCreateWithDelegationOperation = new ByteArrayOutputStream()) {
            serializedAccountCreateWithDelegationOperation.write(SteemJUtils.transformIntToVarIntByteArray(
                    OperationType.ACCOUNT_CREATE_WITH_DELEGATION_OPERATION.getOrderId()));
            serializedAccountCreateWithDelegationOperation.write(this.getFee().toByteArray());
            serializedAccountCreateWithDelegationOperation.write(this.getDelegation().toByteArray());
            serializedAccountCreateWithDelegationOperation.write(this.getCreator().toByteArray());
            serializedAccountCreateWithDelegationOperation.write(this.getNewAccountName().toByteArray());
            serializedAccountCreateWithDelegationOperation.write(this.getOwner().toByteArray());
            serializedAccountCreateWithDelegationOperation.write(this.getActive().toByteArray());
            serializedAccountCreateWithDelegationOperation.write(this.getPosting().toByteArray());
            serializedAccountCreateWithDelegationOperation.write(this.getMemoKey().toByteArray());
            serializedAccountCreateWithDelegationOperation
                    .write(SteemJUtils.transformStringToVarIntByteArray(this.getJsonMetadata()));

            serializedAccountCreateWithDelegationOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(this.getExtensions().size()));
            for (FutureExtensions futureExtensions : this.getExtensions()) {
                serializedAccountCreateWithDelegationOperation.write(futureExtensions.toByteArray());
            }

            return serializedAccountCreateWithDelegationOperation.toByteArray();
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
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)) {
            super.validate(validationType);

            if (!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)) {
                if (!delegation.getSymbol().equals(SteemJConfig.getInstance().getVestsSymbol())) {
                    throw new InvalidParameterException("The delegation must have the symbol type VESTS.");
                } else if (delegation.getAmount() < 0) {
                    throw new InvalidParameterException("The delegation must be a postive amount.");
                }
            }
        }
    }
}
