package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "custom_json_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomJsonOperation extends Operation {
    @JsonProperty("required_auths")
    private List<AccountName> requiredAuths;
    @JsonProperty("required_posting_auths")
    private List<AccountName> requiredPostingAuths;
    @JsonProperty("id")
    private String id;
    @JsonProperty("json")
    private String json;

    /**
     * serves the same purpose as custom_operation but also supports required
     * posting authorities. Unlike custom_operation, this operation is designed
     * to be human readable/developer friendly.
     */
    public CustomJsonOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public List<AccountName> getRequiredAuths() {
        return requiredAuths;
    }

    /**
     * 
     * @param requiredAuths
     */
    public void setRequiredAuths(List<AccountName> requiredAuths) {
        this.requiredAuths = requiredAuths;
    }

    /**
     * 
     * @return
     */
    public List<AccountName> getRequiredPostingAuths() {
        return requiredPostingAuths;
    }

    /**
     * 
     * @param requiredPostingAuths
     */
    public void setRequiredPostingAuths(List<AccountName> requiredPostingAuths) {
        this.requiredPostingAuths = requiredPostingAuths;
    }

    /**
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *            Must be less than 32 characters long.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     */
    public String getJson() {
        return json;
    }

    /**
     * 
     * @param json
     *            Must be proper utf8 / JSON string.
     */
    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCustomOperation = new ByteArrayOutputStream()) {
            serializedCustomOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.CUSTOM_JSON_OPERATION.ordinal()));

            serializedCustomOperation.write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredAuths().size()));

            for (AccountName accountName : this.getRequiredAuths()) {
                serializedCustomOperation.write(accountName.toByteArray());
            }

            serializedCustomOperation
                    .write(SteemJUtils.transformLongToVarIntByteArray(this.getRequiredPostingAuths().size()));

            for (AccountName accountName : this.getRequiredPostingAuths()) {
                serializedCustomOperation.write(accountName.toByteArray());
            }

            serializedCustomOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getId()));
            serializedCustomOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getJson()));

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
