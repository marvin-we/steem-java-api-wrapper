package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "escrow_dispute_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowDisputeOperation extends Operation {
    private AccountName from;
    private AccountName to;
    private AccountName agent;
    private AccountName who;
    // Original type is unit32_t so we use long here.
    @JsonProperty("escrow_id")
    private long escrowId;

    /**
     * Create a new escrow dispute operation.
     * 
     * If either the sender or receiver of an escrow payment has an issue, they
     * can raise it for dispute. Once a payment is in dispute, the agent has
     * authority over who gets what.
     */
    public EscrowDisputeOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
        // Apply default values:
        this.setEscrowId(30);
    }

    /**
     * Get the account who wants to transfer the fund to the {@link #to to}
     * account.
     * 
     * @return The account who wants to transfer the fund.
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * Set the account who wants to transfer the fund to the {@link #to to}
     * account.
     * 
     * @param from
     *            The account who wants to transfer the fund.
     */
    public void setFrom(AccountName from) {
        this.from = from;
    }

    /**
     * Get the account who should receive the funds.
     * 
     * @return The account who should receive the funds.
     */
    public AccountName getTo() {
        return to;
    }

    /**
     * Set the account who should receive the funds.
     * 
     * @param to
     *            The account who should receive the funds.
     */
    public void setTo(AccountName to) {
        this.to = to;
    }

    /**
     * Get the agent account.
     * 
     * @return The agent account.
     */
    public AccountName getAgent() {
        return agent;
    }

    /**
     * Set the agent account.
     * 
     * @param agent
     *            The agent account.
     */
    public void setAgent(AccountName agent) {
        this.agent = agent;
    }

    /**
     * Get the account which approved this operation.
     * 
     * @return The account which approved this operation.
     */
    public AccountName getWho() {
        return who;
    }

    /**
     * Set the account who approves this operation. This can either be the
     * {@link #to to} account or the {@link #agent agent} account.
     * 
     * @param who
     *            The account which approved this operation.
     */
    public void setWho(AccountName who) {
        this.who = who;
    }

    /**
     * Get the unique id of this escrow operation.
     * 
     * @return The unique id of this escrow operation.
     */
    public long getEscrowId() {
        return escrowId;
    }

    /**
     * Set the unique id of this escrow operation.
     * 
     * @param escrowId
     *            The unique id of this escrow operation.
     */
    public void setEscrowId(long escrowId) {
        this.escrowId = escrowId;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedEscrowDisputeOperation = new ByteArrayOutputStream()) {
            serializedEscrowDisputeOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.ESCROW_APPROVE_OPERATION.ordinal()));
            serializedEscrowDisputeOperation.write(this.getFrom().toByteArray());
            serializedEscrowDisputeOperation.write(this.getTo().toByteArray());
            serializedEscrowDisputeOperation.write(this.getAgent().toByteArray());
            serializedEscrowDisputeOperation.write(this.getWho().toByteArray());
            serializedEscrowDisputeOperation.write(SteemJUtils.transformLongToByteArray(this.getEscrowId()));

            return serializedEscrowDisputeOperation.toByteArray();
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
