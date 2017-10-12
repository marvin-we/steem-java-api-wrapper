package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

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
        super(false);
        // Apply default values:
        this.setEscrowId(30);
        this.setApprove(true);
    }

    /**
     * Create a new escrow approve operation.
     * 
     * The agent and to accounts must approve an escrow transaction for it to be
     * valid on the blockchain. Once a part approves the escrow, the cannot
     * revoke their approval. Subsequent escrow approve operations, regardless
     * of the approval, will be rejected.
     * 
     * @param from
     *            The source account of the escrow operation (see
     *            {@link #setFrom(AccountName)}).
     * @param to
     *            The target account of the escrow operation (see
     *            {@link #setTo(AccountName)}).
     * @param agent
     *            The agent account of the escrow operation (see
     *            {@link #setAgent(AccountName)}).
     * @param escrowId
     *            The <b>unique</b> id of the escrow operation (see
     *            {@link #setEscrowId(long)}).
     * @param who
     *            The account who approves the escrow operation (see
     *            {@link #setWho(AccountName)}).
     * @param approve
     *            Define if the {@link #getWho()} account approves the operation
     *            (see {@link #setApprove(Boolean)}).
     * @throws InvalidParameterException
     *             If one of the arguemnts does not fulfill the requirements.
     */
    @JsonCreator
    public EscrowApproveOperation(@JsonProperty("from") AccountName from, @JsonProperty("to") AccountName to,
            @JsonProperty("agent") AccountName agent, @JsonProperty("escrow_id") long escrowId,
            @JsonProperty("who") AccountName who, @JsonProperty("approve") Boolean approve) {
        super(false);

        this.setFrom(from);
        this.setTo(to);
        this.setAgent(agent);
        this.setEscrowId(escrowId);
        this.setWho(who);
        this.setApprove(approve);
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
            serializedEscrowApproveOperation.write(SteemJUtils.transformIntToByteArray(this.getEscrowId()));
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
