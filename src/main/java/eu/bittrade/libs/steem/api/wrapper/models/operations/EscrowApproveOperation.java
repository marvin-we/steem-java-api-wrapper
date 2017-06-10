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
 * This class represents the Steem "escrow_approve_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowApproveOperation extends Operation {
    private AccountName from;
    private AccountName to;
    private AccountName agent;
    private AccountName who;
    // Original type is unit32_t so we use long here.
    @JsonProperty("escrow_id")
    private long escrowId;
    private Boolean approve;

    /**
     * Create a new escrow approve operation.
     * 
     * The agent and to accounts must approve an escrow transaction for it to be
     * valid on the blockchain. Once a part approves the escrow, the cannot
     * revoke their approval. Subsequent escrow approve operations, regardless
     * of the approval, will be rejected.
     */
    public EscrowApproveOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
        // Apply default values:
        this.setEscrowId(30);
        this.setApprove(true);
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

    /**
     * Get the information if the {@link #who who} account has approved the
     * operation or not.
     * 
     * @return True if the operation has been approved or false if not.
     */
    public Boolean getApprove() {
        return approve;
    }

    /**
     * Define if the {@link #who who} account approves the operation or not.
     * 
     * @param approve
     *            True if the operation has been approved or false if not.
     */
    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedEscrowApproveOperation = new ByteArrayOutputStream()) {
            serializedEscrowApproveOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.ESCROW_APPROVE_OPERATION.ordinal()));
            serializedEscrowApproveOperation.write(this.getFrom().toByteArray());
            serializedEscrowApproveOperation.write(this.getTo().toByteArray());
            serializedEscrowApproveOperation.write(this.getAgent().toByteArray());
            serializedEscrowApproveOperation.write(this.getWho().toByteArray());
            serializedEscrowApproveOperation.write(SteemJUtils.transformLongToByteArray(this.getEscrowId()));
            serializedEscrowApproveOperation.write(SteemJUtils.transformBooleanToByteArray(this.getApprove()));

            return serializedEscrowApproveOperation.toByteArray();
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
