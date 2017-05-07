package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.util.SteemUtils;

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
    private Boolean approve;

    /**
     * Create a new account witness vote operation.
     */
    public AccountWitnessVoteOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    /**
     * @return the witness
     */
    public AccountName getWitness() {
        return witness;
    }

    /**
     * @param witness
     *            the witness to set
     */
    public void setWitness(AccountName witness) {
        this.witness = witness;
    }

    /**
     * @return the approve
     */
    public Boolean getApprove() {
        return approve;
    }

    /**
     * @param approve
     *            the approve to set
     */
    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountWitnessVoteOperation = new ByteArrayOutputStream()) {
            serializedAccountWitnessVoteOperation.write(
                    SteemUtils.transformIntToVarIntByteArray(OperationType.ACCOUNT_WITNESS_VOTE_OPERATION.ordinal()));
            serializedAccountWitnessVoteOperation.write(this.getAccount().toByteArray());
            serializedAccountWitnessVoteOperation.write(this.getWitness().toByteArray());
            serializedAccountWitnessVoteOperation.write(SteemUtils.transformBooleanToByteArray(this.getApprove()));

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
