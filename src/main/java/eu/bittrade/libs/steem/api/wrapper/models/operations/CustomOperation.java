package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "custom_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomOperation extends Operation {
    // Original type is flat_set< account_name_type >.
    @JsonProperty("required_auths")
    private List<AccountName> requiredAuths;
    // Original type is uint16_t so we use int here.
    @JsonProperty("id")
    private int id;
    // Original type is vector< char >.
    @JsonProperty("data")
    private String data;

    /**
     * Create a new custom operation.
     */
    public CustomOperation() {
        // Define the required key type for this operation.
        super(null);
        // Set default values:
        this.setId(0);
    }

    /**
     * Get the list of accounts name whose private active keys were required to
     * sign this transaction.
     * 
     * @return The list of accounts name whose private active keys were required
     */
    public List<AccountName> getRequiredAuths() {
        return requiredAuths;
    }

    /**
     * @param requiredAuths
     *            the requiredAuths to set
     */
    public void setRequiredAuths(List<AccountName> requiredAuths) {
        this.requiredAuths = requiredAuths;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
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
     * @param data
     *            the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCustomOperation = new ByteArrayOutputStream()) {
            serializedCustomOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.CUSTOM_OPERATION.ordinal()));

            serializedCustomOperation.write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredAuths().size()));

            for (AccountName accountName : this.getRequiredAuths()) {
                serializedCustomOperation.write(accountName.toByteArray());
            }

            serializedCustomOperation.write(SteemJUtils.transformShortToByteArray(this.getId()));
            serializedCustomOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getData()));

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
}
