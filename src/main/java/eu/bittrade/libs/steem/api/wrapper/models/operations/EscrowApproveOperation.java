package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;

public class EscrowApproveOperation extends Operation {
    private AccountName from;
    private AccountName to;
    private AccountName agent;
    private AccountName who;
    // Original type is unit32_t so we use long here.
    @JsonProperty("escrow_id")
    private long escrowId;
    private Boolean approve;

    public EscrowApproveOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
        // Apply default values:
        this.setEscrowId(30);
        this.setApprove(true);
    }

    /**
     * @return the from
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * @param from
     *            the from to set
     */
    public void setFrom(AccountName from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public AccountName getTo() {
        return to;
    }

    /**
     * @param to
     *            the to to set
     */
    public void setTo(AccountName to) {
        this.to = to;
    }

    /**
     * @return the agent
     */
    public AccountName getAgent() {
        return agent;
    }

    /**
     * @param agent
     *            the agent to set
     */
    public void setAgent(AccountName agent) {
        this.agent = agent;
    }

    /**
     * Either to or agent
     * 
     * @return the who
     */
    public AccountName getWho() {
        return who;
    }

    /**
     * @param who
     *            the who to set
     */
    public void setWho(AccountName who) {
        this.who = who;
    }

    /**
     * @return the escrowId
     */
    public long getEscrowId() {
        return escrowId;
    }

    /**
     * @param escrowId
     *            the escrowId to set
     */
    public void setEscrowId(long escrowId) {
        this.escrowId = escrowId;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
