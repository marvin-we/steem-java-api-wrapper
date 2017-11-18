package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "challenge_authority_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ChallengeAuthorityOperation extends Operation {
    @JsonProperty("challenger")
    private AccountName challenger;
    @JsonProperty("challenged")
    private AccountName challenged;
    @JsonProperty("require_owner")
    private boolean requireOwner;

    /**
     * Create a new challenge authority operation.
     * 
     * @param challenger
     *            Set the account that challenges the <code>challenged</code>
     *            account (see {@link #setChallenger(AccountName)}).
     * @param challenged
     *            Define the account to challenge (see
     *            {@link #setChallenged(AccountName)}).
     * @param requireOwner
     *            Define if the owner key or the active key should be used to
     *            sign this operation ({@link #setRequireOwner(boolean)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public ChallengeAuthorityOperation(@JsonProperty("challenger") AccountName challenger,
            @JsonProperty("challenged") AccountName challenged, @JsonProperty("require_owner") boolean requireOwner) {
        super(false);

        this.setChallenger(challenger);
        this.setChallenged(challenged);
        this.setRequireOwner(requireOwner);
    }

    /**
     * Like
     * {@link #ChallengeAuthorityOperation(AccountName, AccountName, boolean)},
     * but sets the <code>requireOwner</code> to false.
     * 
     * @param challenger
     *            Set the account that challenges the <code>challenged</code>
     *            account (see {@link #setChallenger(AccountName)}).
     * @param challenged
     *            Define the account to challenge (see
     *            {@link #setChallenged(AccountName)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public ChallengeAuthorityOperation(AccountName challenger, AccountName challenged) {
        this(challenger, challenged, false);
    }

    /**
     * Get the account name that challenges the <code>challenged</code> account.
     * 
     * @return The challenger.
     */
    public AccountName getChallenger() {
        return challenger;
    }

    /**
     * Set the account name that challenges the <code>challenged</code> account.
     * <b>Notice:</b> The private active key of this account needs to be stored
     * in the key storage.
     * 
     * @param challenger
     *            The account name that challenges the <code>challenged</code>
     *            account.
     * @throws InvalidParameterException
     *             If the challenger account is null or equal to the
     *             <code>challenged</code> account.
     */
    public void setChallenger(AccountName challenger) {
        this.challenger = setIfNotNull(challenger, "The challenger can't be null");
    }

    /**
     * @return The account to challenge.
     */
    public AccountName getChallenged() {
        return challenged;
    }

    /**
     * Set the account to challenge.
     * 
     * @param challenged
     *            The account to challenge.
     * @throws InvalidParameterException
     *             If the challenged account is null or equal to the
     *             <code>challenger</code> account.
     */
    public void setChallenged(AccountName challenged) {
        this.challenged = setIfNotNull(challenged, "The challenged can't be null");
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
        try (ByteArrayOutputStream serializedChallengeAuthorityOperation = new ByteArrayOutputStream()) {
            serializedChallengeAuthorityOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.CHALLENGE_AUTHORITY_OPERATION.getOrderId()));
            serializedChallengeAuthorityOperation.write(this.getChallenger().toByteArray());
            serializedChallengeAuthorityOperation.write(this.getChallenged().toByteArray());
            serializedChallengeAuthorityOperation
                    .write(SteemJUtils.transformBooleanToByteArray(this.getRequireOwner()));

            return serializedChallengeAuthorityOperation.toByteArray();
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
            return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getChallenger(), PrivateKeyType.OWNER);
        } else {
            return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getChallenger(), PrivateKeyType.ACTIVE);
        }
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType) && (this.getChallenged() == this.getChallenger())) {
            throw new InvalidParameterException(
                    "The challenged account and the challenger account can't be the same account.");
        }
    }
}
