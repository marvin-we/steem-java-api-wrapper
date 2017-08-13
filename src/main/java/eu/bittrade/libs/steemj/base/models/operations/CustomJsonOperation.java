package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;

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
     * Create a new custom json operation. This operation serves the same
     * purpose as
     * {@link eu.bittrade.libs.steemj.base.models.operations.CustomOperation
     * CustomOperation} but also supports required posting authorities. Unlike
     * {@link eu.bittrade.libs.steemj.base.models.operations.CustomOperation
     * CustomOperation}, this operation is designed to be human
     * readable/developer friendly.
     */
    public CustomJsonOperation() {
        super(false);
        // Apply default values:
        this.requiredAuths = new ArrayList<>();
        this.requiredPostingAuths = new ArrayList<>();
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
     */
    public void setRequiredAuths(List<AccountName> requiredAuths) {
        this.requiredAuths = requiredAuths;

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
     * Plugin ID (e.g. follow)
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *            TODO: Must be less than 32 characters long.
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

    /**
     * Merge both required authorities list to determine all required keys.
     * 
     * @return A merged list of the required active and posting authorities.
     */
    private List<ImmutablePair<AccountName, PrivateKeyType>> mergeRequiredAuth() {
        List<ImmutablePair<AccountName, PrivateKeyType>> requiredPrivateKeys = new ArrayList<>();

        for (AccountName accountName : this.getRequiredAuths()) {
            requiredPrivateKeys.add(new ImmutablePair<>(accountName, PrivateKeyType.ACTIVE));
        }

        for (AccountName accountName : this.getRequiredPostingAuths()) {
            requiredPrivateKeys.add(new ImmutablePair<>(accountName, PrivateKeyType.POSTING));
        }

        return requiredPrivateKeys;
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
