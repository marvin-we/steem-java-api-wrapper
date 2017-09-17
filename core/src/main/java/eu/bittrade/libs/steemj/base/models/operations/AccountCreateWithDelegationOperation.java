package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

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
public class AccountCreateWithDelegationOperation extends AccountCreateOperation {
    private Asset delegation;
    // Original type is "extension_type" which is an array of "future_extions".
    private List<FutureExtensions> extensions;

    /**
     * Create a new create account with delegation operation. Use this operation
     * to create a new account.
     */
    public AccountCreateWithDelegationOperation() {
    }

    /**
     * Get the amount of VESTS the {@link #creator creator} has delegated to the
     * {{@link #newAccountName newAccountName}.
     * 
     * @return The amount of VESTS delegated to the new account.
     */
    public Asset getDelegation() {
        return delegation;
    }

    /**
     * Set the amount of VESTS the {@link #creator creator} has delegated to the
     * {{@link #newAccountName newAccountName}.
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
        if (extensions == null || extensions.isEmpty()) {
            // Create a new ArrayList that contains an empty FutureExtension so
            // one byte gets added to the signature for sure.
            extensions = new ArrayList<>();
            extensions.add(new FutureExtensions());
        }
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
