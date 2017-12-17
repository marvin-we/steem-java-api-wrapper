package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UShort;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "custom_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomOperation extends Operation {
    // Original type is flat_set< account_name_type >.
    @JsonProperty("required_auths")
    private List<AccountName> requiredAuths;
    // Original type is uint16_t.
    @JsonProperty("id")
    private UShort id;
    // Original type is vector< char >.
    @JsonProperty("data")
    private String data;

    /**
     * Create a new custom operation.
     * 
     * @param requiredAuths
     *            A list of account names whose private active keys are required
     *            to sign the transaction (see {@link #setRequiredAuths(List)}).
     * @param id
     *            The id of this operation (see {@link #setId(int)}).
     * @param data
     *            The data (see {@link #setData(String)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public CustomOperation(@JsonProperty("required_auths") List<AccountName> requiredAuths,
            @JsonProperty("id") short id, @JsonProperty("data") String data) {
        super(false);

        this.setRequiredAuths(requiredAuths);
        this.setId(id);
        this.setData(data);
    }

    /**
     * Like {@link #CustomOperation(List, short, String)}, but sets the
     * <code>id</code> to its default value (0).
     * 
     * @param requiredAuths
     *            A list of account names whose private active keys are required
     *            to sign the transaction (see {@link #setRequiredAuths(List)}).
     * @param data
     *            The data (see {@link #setData(String)}).
     */
    public CustomOperation(List<AccountName> requiredAuths, String data) {
        this(requiredAuths, (short) 0, data);
    }

    /**
     * Get the list of account names whose private active keys were required to
     * sign this transaction.
     * 
     * @return The list of account names whose private active keys were required
     */
    public List<AccountName> getRequiredAuths() {
        return requiredAuths;
    }

    /**
     * Set the list of account names whose private active keys are required to
     * sign this transaction.
     * 
     * @param requiredAuths
     *            The account names whose private active keys are required.
     * @throws InvalidParameterException
     *             If less than 1 account name has been provided.
     */
    public void setRequiredAuths(List<AccountName> requiredAuths) {
        this.requiredAuths = setIfNotNull(requiredAuths, "At least on account must be specified.");
    }

    /**
     * Get the id of this operation.
     * 
     * @return The id of this operation.
     */
    public UShort getId() {
        return this.id;
    }

    /**
     * Set the id of this operation.
     * 
     * @param id
     *            The id to set.
     */
    public void setId(int id) {
        this.id = UShort.valueOf(id);
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
        try (ByteArrayOutputStream serializedCustomOperation = new ByteArrayOutputStream()) {
            serializedCustomOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.CUSTOM_OPERATION.getOrderId()));

            serializedCustomOperation.write(SteemJUtils.transformIntToVarIntByteArray(this.getRequiredAuths().size()));

            for (AccountName accountName : this.getRequiredAuths()) {
                serializedCustomOperation.write(accountName.toByteArray());
            }

            serializedCustomOperation.write(SteemJUtils.transformShortToByteArray(this.getId().shortValue()));

            byte[] decodedData = CryptoUtils.HEX.decode(this.getData());
            serializedCustomOperation.write(SteemJUtils.transformIntToVarIntByteArray(decodedData.length));
            serializedCustomOperation.write(decodedData);

            return serializedCustomOperation.toByteArray();
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
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getRequiredAuths(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType) && requiredAuths.isEmpty()) {
            throw new InvalidParameterException("At least on account must be specified.");
        }
    }
}
