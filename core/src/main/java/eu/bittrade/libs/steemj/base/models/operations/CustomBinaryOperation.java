package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
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
    @JsonProperty("id")
    private String id;
    @JsonProperty("data")
    private String data;

    /**
     * Create a new custom binary operation.
     * 
     * @param requiredOwnerAuths
     *            The owner authorities that need to sign this operation (see
     *            {@link #setRequiredOwnerAuths(List)}).
     * @param requiredActiveAuths
     *            The active authorities that need to sign this operation (see
     *            {@link #setRequiredActiveAuths(List)}).
     * @param requiredPostingAuths
     *            The posting authorities that need to sign this operation (see
     *            {@link #setRequiredPostingAuths(List)}).
     * @param requiredAuths
     *            The required authorities that need to sign this operation (see
     *            {@link #setRequiredAuths(List)}).
     * @param id
     *            The id of the plugin which can process this operation (see
     *            {@link #setId(String)}).
     * @param data
     *            The data to set (see {@link #setData(String)}).
     * @throws InvalidParameterException
     *             If a parameter does not fulfill the requirements.
     */
    public CustomBinaryOperation(@JsonProperty("required_owner_auths") List<AccountName> requiredOwnerAuths,
            @JsonProperty("required_active_auths") List<AccountName> requiredActiveAuths,
            @JsonProperty("required_posting_auths") List<AccountName> requiredPostingAuths,
            @JsonProperty("required_auths") List<Authority> requiredAuths, @JsonProperty("id") String id,
            @JsonProperty("data") String data) {
        // Define the required key type for this operation.
        super(false);

        this.setRequiredOwnerAuths(requiredOwnerAuths);
        this.setRequiredActiveAuths(requiredActiveAuths);
        this.setRequiredPostingAuths(requiredPostingAuths);
        this.setRequiredAuths(requiredAuths);
        this.setId(id);
        this.setData(data);
    }

    /**
     * Get the list of account names whose private owner keys were required to
     * sign this transaction.
     * 
     * @return The list of account names whose private owner keys were required.
     */
    public List<AccountName> getRequiredOwnerAuths() {
        return requiredOwnerAuths;
    }

    /**
     * Set the list of account names whose private owner keys are required to
     * sign this transaction.
     * 
     * @param requiredOwnerAuths
     *            The account names whose private owner keys are required.
     */
    public void setRequiredOwnerAuths(List<AccountName> requiredOwnerAuths) {
        if (requiredOwnerAuths == null) {
            this.requiredOwnerAuths = new ArrayList<>();
        } else {
            this.requiredOwnerAuths = requiredOwnerAuths;
        }
    }

    /**
     * Get the list of account names whose private active keys were required to
     * sign this transaction.
     * 
     * @return The list of account names whose private active keys were
     *         required.
     */
    public List<AccountName> getRequiredActiveAuths() {
        return requiredActiveAuths;
    }

    /**
     * Set the list of account names whose private active keys are required to
     * sign this transaction.
     * 
     * @param requiredActiveAuths
     *            The account names whose private active keys are required.
     */
    public void setRequiredActiveAuths(List<AccountName> requiredActiveAuths) {
        if (requiredActiveAuths == null) {
            this.requiredActiveAuths = new ArrayList<>();
        } else {
            this.requiredActiveAuths = requiredActiveAuths;
        }
    }

    /**
     * Get the list of account names whose private posting keys were required to
     * sign this transaction.
     * 
     * @return The list of account names whose private posting keys were
     *         required.
     */
    public List<AccountName> getRequiredPostingAuths() {
        return requiredPostingAuths;
    }

    /**
     * Set the list of account names whose private posting keys are required to
     * sign this transaction.
     * 
     * @param requiredPostingAuths
     *            The account names whose private posting keys are required.
     */
    public void setRequiredPostingAuths(List<AccountName> requiredPostingAuths) {
        if (requiredPostingAuths == null) {
            this.requiredPostingAuths = new ArrayList<>();
        } else {
            this.requiredPostingAuths = requiredPostingAuths;
        }
    }

    /**
     * Get the list of account names whose private keys were required to sign
     * this transaction.
     * 
     * @return The list of account names whose private keys were required.
     */
    public List<Authority> getRequiredAuths() {
        return requiredAuths;
    }

    /**
     * Set the list of account names whose private keys are required to sign
     * this transaction.
     * 
     * @param requiredAuths
     *            The account names whose private keys are required.
     */
    public void setRequiredAuths(List<Authority> requiredAuths) {
        if (requiredAuths == null) {
            this.requiredAuths = new ArrayList<>();
        } else {
            this.requiredAuths = requiredAuths;
        }
    }

    /**
     * @return Get the plugin id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id of the plugin which can process this operation.
     * 
     * @param id
     *            The plugin id to set.
     * @throws InvalidParameterException
     *             If the <code>id</code> is null.
     */
    public void setId(String id) {
        this.id = setIfNotNull(id, "The id can't be null.");
    }

    /**
     * Get the data that this operation contains. <b>Notice</b> that the
     * original type of this field is "vector&lt; char &gt;" and that its
     * returned as a String.
     * 
     * @return the data The data transfered with this operation.
     */
    public String getData() {
        return data;
    }

    /**
     * Set the data to send with this operation in its HEX representation.
     * 
     * @param data
     *            The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCustomBinaryOperation = new ByteArrayOutputStream()) {
            serializedCustomBinaryOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.CUSTOM_BINARY_OPERATION.getOrderId()));

            serializedCustomBinaryOperation
                    .write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredOwnerAuths().size()));

            for (AccountName accountName : this.getRequiredOwnerAuths()) {
                serializedCustomBinaryOperation.write(accountName.toByteArray());
            }

            serializedCustomBinaryOperation
                    .write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredActiveAuths().size()));

            for (AccountName accountName : this.getRequiredActiveAuths()) {
                serializedCustomBinaryOperation.write(accountName.toByteArray());
            }

            serializedCustomBinaryOperation
                    .write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredPostingAuths().size()));

            for (AccountName accountName : this.getRequiredPostingAuths()) {
                serializedCustomBinaryOperation.write(accountName.toByteArray());
            }

            serializedCustomBinaryOperation
                    .write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredAuths().size()));

            for (Authority authority : this.getRequiredAuths()) {
                serializedCustomBinaryOperation.write(authority.toByteArray());
            }

            serializedCustomBinaryOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getId()));

            byte[] decodedData = CryptoUtils.HEX.decode(this.getData());
            serializedCustomBinaryOperation.write(SteemJUtils.transformIntToVarIntByteArray(decodedData.length));
            serializedCustomBinaryOperation.write(decodedData);

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

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        Map<SignatureObject, PrivateKeyType> requiredAuthorities = requiredAuthoritiesBase;

        requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, this.getRequiredActiveAuths(),
                PrivateKeyType.OWNER);
        requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, this.getRequiredActiveAuths(),
                PrivateKeyType.ACTIVE);
        requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, this.getRequiredPostingAuths(),
                PrivateKeyType.POSTING);
        requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, this.getRequiredAuths(),
                PrivateKeyType.OTHER);

        return requiredAuthorities;
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)) {
            if (id.length() > 32) {
                throw new InvalidParameterException("The id must be less than 32 characters long.");
            } else if (requiredOwnerAuths.size() + requiredActiveAuths.size() + requiredPostingAuths.size() <= 0) {
                throw new InvalidParameterException("At least on account must be specified.");
            }
        }
    }
}
