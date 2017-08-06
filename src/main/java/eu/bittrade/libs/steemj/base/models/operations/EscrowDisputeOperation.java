package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

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
        super(false);
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
     * account. <b>Notice:</b> The private active key of this account needs to
     * be stored in the key storage.
     * 
     * @param from
     *            The account who wants to transfer the fund.
     */
    public void setFrom(AccountName from) {
        this.from = from;

        // Update the List of required private key types.
        addRequiredPrivateKeyType(from, PrivateKeyType.ACTIVE);
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
    public int getEscrowId() {
        return (int) escrowId;
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
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.ESCROW_DISPUTE_OPERATION.ordinal()));
            serializedEscrowDisputeOperation.write(this.getFrom().toByteArray());
            serializedEscrowDisputeOperation.write(this.getTo().toByteArray());
            serializedEscrowDisputeOperation.write(this.getAgent().toByteArray());
            serializedEscrowDisputeOperation.write(this.getWho().toByteArray());
            serializedEscrowDisputeOperation.write(SteemJUtils.transformIntToByteArray(this.getEscrowId()));

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
