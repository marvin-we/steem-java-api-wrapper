package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "prove_authority_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ProveAuthorityOperation extends Operation {
    @JsonProperty("challenged")
    private AccountName challenged;
    @JsonProperty("require_owner")
    private boolean requireOwner;

    /**
     * Create a new prove authority operation.
     * 
     * @param challenged
     *            The account name of the account which has been challenged
     *            ({@link #setChallenged(AccountName)}).
     * @param requireOwner
     *            Define if the owner key or the active key should be used to
     *            sign this operation ({@link #setRequireOwner(boolean)}).
     * @throws InvalidParameterException
     *             If a parameter does not fulfill the requirements.
     */
    @JsonCreator
    public ProveAuthorityOperation(@JsonProperty("challenged") AccountName challenged,
            @JsonProperty("require_owner") boolean requireOwner) {
        super(false);

        this.setChallenged(challenged);
        this.setRequireOwner(requireOwner);
    }

    /**
     * Create a new prove authority operation. Like
     * {@link ProveAuthorityOperation}, but sets the {@link #getRequireOwner()}
     * to <code>false</code> by default.
     * 
     * @param challenged
     *            The account name of the account which has been challenged
     *            ({@link #setChallenged(AccountName)}).
     * @throws InvalidParameterException
     *             If a parameter does not fulfill the requirements.
     */
    public ProveAuthorityOperation(AccountName challenged) {
        super(false);

        this.setChallenged(challenged);
        // Set default values:
        this.setRequireOwner(false);
    }

    /**
     * @return The account name of the challenged account.
     */
    public AccountName getChallenged() {
        return challenged;
    }

    /**
     * <b>Notice:</b> The private owner key of this account needs to be stored
     * in the key storage.
     * 
     * @param challenged
     *            The challenged account.
     * @throws InvalidParameterException
     *             If no account name has been provided.
     */
    public void setChallenged(AccountName challenged) {
        this.challenged = setIfNotNull(challenged, "An account name needs to be provided.");
    }

    /**
     * @return <code>true</code> if the owner key should be used to sign this
     *         operation, or false, if the active key is sufficient.
     */
    public boolean getRequireOwner() {
        return requireOwner;
    }

    /**
     * Define if the owner key should be used to sign this operation
     * (<code>true</code>) or if if the active key is sufficient
     * (<code>false</code>).
     * 
     * @param requireOwner
     *            Define if the owner or active key should be used.
     */
    public void setRequireOwner(boolean requireOwner) {
        this.requireOwner = requireOwner;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedProveAuthorityOperation = new ByteArrayOutputStream()) {
            serializedProveAuthorityOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.PROVE_AUTHORITY_OPERATION.getOrderId()));
            serializedProveAuthorityOperation.write(this.getChallenged().toByteArray());
            serializedProveAuthorityOperation.write(SteemJUtils.transformBooleanToByteArray(this.getRequireOwner()));

            return serializedProveAuthorityOperation.toByteArray();
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
        if (this.getRequireOwner()) {
            return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getChallenged(), PrivateKeyType.OWNER);
        } else {
            return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getChallenged(), PrivateKeyType.ACTIVE);
        }
    }

    @Override
    public void validate(ValidationType validationType) {
        return;
    }
}
