package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bitcoinj.core.Utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
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

    /**
     * Create a new custom binary operation.
     */
    public CustomBinaryOperation() {
        // Define the required key type for this operation.
        super(false);
        // Set default values.
        this.requiredActiveAuths = new ArrayList<>();
        this.requiredAuths = new ArrayList<>();
        this.requiredOwnerAuths = new ArrayList<>();
        this.requiredPostingAuths = new ArrayList<>();
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
        this.requiredOwnerAuths = requiredOwnerAuths;

        // Update the List of required private key types.
        this.addRequiredPrivateKeyType(this.mergeRequiredAuth());
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
        this.requiredActiveAuths = requiredActiveAuths;

        // Update the List of required private key types.
        this.addRequiredPrivateKeyType(this.mergeRequiredAuth());
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
        this.requiredPostingAuths = requiredPostingAuths;

        // Update the List of required private key types.
        this.addRequiredPrivateKeyType(this.mergeRequiredAuth());
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
        this.requiredAuths = requiredAuths;

        // Update the List of required private key types.
        this.addRequiredPrivateKeyType(this.mergeRequiredAuth());
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * TODO The id must be less than 32 characters long.
     * 
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
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

    /**
     * Merge both required authorities list to determine all required keys.
     * 
     * @return A merged list of the required active and posting authorities.
     */
    private List<ImmutablePair<AccountName, PrivateKeyType>> mergeRequiredAuth() {
        List<ImmutablePair<AccountName, PrivateKeyType>> requiredPrivateKeys = new ArrayList<>();

        // TODO: Support this auth.
        // for (Authority authority : this.getRequiredAuths()) { }

        for (AccountName accountName : this.getRequiredPostingAuths()) {
            requiredPrivateKeys.add(new ImmutablePair<>(accountName, PrivateKeyType.POSTING));
        }

        for (AccountName accountName : this.getRequiredActiveAuths()) {
            requiredPrivateKeys.add(new ImmutablePair<>(accountName, PrivateKeyType.POSTING));
        }

        for (AccountName accountName : this.getRequiredOwnerAuths()) {
            requiredPrivateKeys.add(new ImmutablePair<>(accountName, PrivateKeyType.POSTING));
        }

        return requiredPrivateKeys;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCustomBinaryOperation = new ByteArrayOutputStream()) {
            serializedCustomBinaryOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.CUSTOM_BINARY_OPERATION.ordinal()));

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

            byte[] decodedData = Utils.HEX.decode(this.getData());
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
}
