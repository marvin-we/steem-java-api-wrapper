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
 * This class represents the Steem "account_witness_vote_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountWitnessVoteOperation extends Operation {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("witness")
    private AccountName witness;
    @JsonProperty("approve")
    private boolean approve;

    /**
     * Create a new account witness vote operation.
     * 
     * All accounts with a VFS can vote for or against any witness.
     *
     * If a proxy is specified then all existing votes are removed.
     * 
     * @param account
     *            Set the <code>account</code> that votes for a
     *            <code>witness</code> (see {@link #getAccount()}).
     * @param witness
     *            Set the <code>witness</code> to vote for (see
     *            {@link #setWitness(AccountName)}).
     * @param approve
     *            Define if the vote is be approved or not (see
     *            {@link #setApprove(boolean)}).
     * @throws InvalidParameterException
     *             If one of the parameters does not fulfill the requirements.
     */
    @JsonCreator
    public AccountWitnessVoteOperation(@JsonProperty("account") AccountName account,
            @JsonProperty("witness") AccountName witness, @JsonProperty("approve") boolean approve) {
        super(false);

        this.setAccount(account);
        this.setWitness(witness);
        this.setApprove(approve);
    }

    /**
     * Like
     * {@link #AccountWitnessVoteOperation(AccountName, AccountName, boolean)},
     * but the <code>approve</code> parameter is automatically set to true.
     * 
     * @param account
     *            Set the <code>account</code> that votes for a
     *            <code>witness</code> (see {@link #getAccount()}).
     * @param witness
     *            Set the <code>witness</code> to vote for (see
     *            {@link #setWitness(AccountName)}).
     * @throws InvalidParameterException
     *             If one of the parameters does not fulfill the requirements.
     */
    public AccountWitnessVoteOperation(AccountName account, AccountName witness) {
        // Set default values:
        this(account, witness, true);
    }

    /**
     * Get the account name that has performed the vote.
     * 
     * @return The account name that has performed the vote.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Set the account name that should perform the vote. <b>Notice:</b> The
     * private active key of this account needs to be stored in the key storage.
     * 
     * @param account
     *            The account name that should perform the vote.
     * @throws InvalidParameterException
     *             If the <code>account</code> account is null
     */
    public void setAccount(AccountName account) {
        this.account = setIfNotNull(account, "The witness acccount can't be null.");
    }

    /**
     * Get the witness that has been voted for.
     * 
     * @return The witness that has been voted for.
     */
    public AccountName getWitness() {
        return witness;
    }

    /**
     * Set the witness that should be voted for.
     * 
     * @param witness
     *            The witness that should be voted for.
     * @throws InvalidParameterException
     *             If the <code>witness</code> account is null
     */
    public void setWitness(AccountName witness) {
        this.witness = setIfNotNull(witness, "The witness acccount can't be null.");
    }

    /**
     * Get the information if this vote has been approved or not.
     * 
     * @return The information if this vote has been approved or not.
     */
    public boolean getApprove() {
        return approve;
    }

    /**
     * Define if this vote is approved or not.
     * 
     * @param approve
     *            Define if this vote is approved or not.
     */
    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountWitnessVoteOperation = new ByteArrayOutputStream()) {
            serializedAccountWitnessVoteOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.ACCOUNT_WITNESS_VOTE_OPERATION.getOrderId()));
            serializedAccountWitnessVoteOperation.write(this.getAccount().toByteArray());
            serializedAccountWitnessVoteOperation.write(this.getWitness().toByteArray());
            serializedAccountWitnessVoteOperation.write(SteemJUtils.transformBooleanToByteArray(this.getApprove()));

            return serializedAccountWitnessVoteOperation.toByteArray();
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
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getAccount(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        return;
    }
}
