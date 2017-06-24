package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "custom_binary_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomBinaryOperation extends Operation {
    // Original type is flat_set< account_name_type >.
    @JsonProperty("required_owner_auths")
    private List<AccountName> requiredOwnerAuths;
    // Original type is flat_set< account_name_type >.
    @JsonProperty("required_active_auths")
    private List<AccountName> requiredActiveAuths;
    // Original type is flat_set< account_name_type >.
    @JsonProperty("required_posting_auths")
    private List<AccountName> requiredPostingAuths;
    // Original type is vector< authority >.
    @JsonProperty("required_auths")
    private List<Authority> requiredAuths;
    private String id;
    private String data;

    public CustomBinaryOperation() {
        // Define the required key type for this operation.
        super(null);
    }

    /**
     * @return the requiredOwnerAuths
     */
    public List<AccountName> getRequiredOwnerAuths() {
        return requiredOwnerAuths;
    }

    /**
     * @param requiredOwnerAuths
     *            the requiredOwnerAuths to set
     */
    public void setRequiredOwnerAuths(List<AccountName> requiredOwnerAuths) {
        this.requiredOwnerAuths = requiredOwnerAuths;
    }

    /**
     * @return the requiredActiveAuths
     */
    public List<AccountName> getRequiredActiveAuths() {
        return requiredActiveAuths;
    }

    /**
     * @param requiredActiveAuths
     *            the requiredActiveAuths to set
     */
    public void setRequiredActiveAuths(List<AccountName> requiredActiveAuths) {
        this.requiredActiveAuths = requiredActiveAuths;
    }

    /**
     * @return the requiredPostingAuths
     */
    public List<AccountName> getRequiredPostingAuths() {
        return requiredPostingAuths;
    }

    /**
     * @param requiredPostingAuths
     *            the requiredPostingAuths to set
     */
    public void setRequiredPostingAuths(List<AccountName> requiredPostingAuths) {
        this.requiredPostingAuths = requiredPostingAuths;
    }

    /**
     * @return the requiredAuths
     */
    public List<Authority> getRequiredAuths() {
        return requiredAuths;
    }

    /**
     * @param requiredAuths
     *            the requiredAuths to set
     */
    public void setRequiredAuths(List<Authority> requiredAuths) {
        this.requiredAuths = requiredAuths;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * The id must be less than 32 characters long.
     * 
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCustomBinaryOperation = new ByteArrayOutputStream()) {
            serializedCustomBinaryOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.CUSTOM_BINARY_OPERATION.ordinal()));
            
            serializedCustomBinaryOperation.write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredOwnerAuths().size()));

            for (AccountName accountName : this.getRequiredOwnerAuths()) {
                serializedCustomBinaryOperation.write(accountName.toByteArray());
            }
            
            serializedCustomBinaryOperation.write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredActiveAuths().size()));

            for (AccountName accountName : this.getRequiredActiveAuths()) {
                serializedCustomBinaryOperation.write(accountName.toByteArray());
            }
            
            serializedCustomBinaryOperation.write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredPostingAuths().size()));

            for (AccountName accountName : this.getRequiredPostingAuths()) {
                serializedCustomBinaryOperation.write(accountName.toByteArray());
            }
            
            serializedCustomBinaryOperation.write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredAuths().size()));

            for (Authority authority : this.getRequiredAuths()) {
                serializedCustomBinaryOperation.write(authority.toByteArray());
            }
            
            serializedCustomBinaryOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getId()));
            serializedCustomBinaryOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getData()));

            return serializedCustomBinaryOperation.toByteArray();
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
