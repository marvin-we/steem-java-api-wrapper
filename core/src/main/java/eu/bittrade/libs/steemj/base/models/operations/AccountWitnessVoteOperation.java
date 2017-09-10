package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.annotations.SignatureRequired;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "account_witness_vote_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountWitnessVoteOperation extends Operation {
    @SignatureRequired(type = PrivateKeyType.ACTIVE)
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("witness")
    private AccountName witness;
    @JsonProperty("approve")
    private Boolean approve;

    /**
     * Create a new account witness vote operation.
     * 
     * All accounts with a VFS can vote for or against any witness.
     *
     * If a proxy is specified then all existing votes are removed.
     */
    public AccountWitnessVoteOperation() {
        super(false);
        // Set default values:
        this.setApprove(true);
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
     */
    public void setAccount(AccountName account) {
        this.account = account;
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
     */
    public void setWitness(AccountName witness) {
        this.witness = witness;
    }

    /**
     * Get the information if this vote has been approved or not.
     * 
     * @return The information if this vote has been approved or not.
     */
    public Boolean getApprove() {
        return approve;
    }

    /**
     * Define if this vote is approved or not.
     * 
     * @param approve
     *            Define if this vote is approved or not.
     */
    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountWitnessVoteOperation = new ByteArrayOutputStream()) {
            serializedAccountWitnessVoteOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.ACCOUNT_WITNESS_VOTE_OPERATION.ordinal()));
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
}
