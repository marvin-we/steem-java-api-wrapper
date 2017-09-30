package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.FutureExtensions;
import eu.bittrade.libs.steemj.enums.OperationType;
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
     */
    public AccountCreateWithDelegationOperation() {
        super(false);
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
     */
    public void setDelegation(Asset delegation) {
        this.delegation = delegation;
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
            serializedAccountCreateWithDelegationOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.ACCOUNT_CREATE_WITH_DELEGATION_OPERATION.ordinal()));
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
}
