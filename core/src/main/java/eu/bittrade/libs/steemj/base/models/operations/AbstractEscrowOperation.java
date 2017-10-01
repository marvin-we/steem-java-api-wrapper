package eu.bittrade.libs.steemj.base.models.operations;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;

/**
 * This abstract class contains fields that exist in all Steem
 * "escrow_operation" objects.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
abstract class AbstractEscrowOperation extends Operation {
    @JsonProperty("from")
    protected AccountName from;
    @JsonProperty("to")
    protected AccountName to;
    @JsonProperty("agent")
    protected AccountName agent;
    // Original type is unit32_t so we use long here.
    @JsonProperty("escrow_id")
    protected long escrowId;

    /**
     * Create a new Operation object by providing the operation type.
     */
    protected AbstractEscrowOperation(boolean virtual) {
        super(virtual);
    }

    /**
     * Get the account who wants to transfer the fund to the {@link #getTo() to}
     * account.
     * 
     * @return The account who wants to transfer the fund.
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * Set the account who wants to transfer the fund to the {@link #getTo() to}
     * account. <b>Notice:</b> The private active key of this account needs to
     * be stored in the key storage.
     * 
     * @param from
     *            The account who wants to transfer the fund.
     * @throws InvalidParameterException
     *             If the <code>from</code> is null.
     */
    public void setFrom(AccountName from) {
        if (from == null) {
            throw new InvalidParameterException("The from account can't be null.");
        }

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
     * @throws InvalidParameterException
     *             If the <code>to</code> is null.
     */
    public void setTo(AccountName to) {
        if (to == null) {
            throw new InvalidParameterException("The to account can't be null.");
        }

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
     * @throws InvalidParameterException
     *             If the <code>agent</code> is null.
     */
    public void setAgent(AccountName agent) {
        if (agent == null) {
            throw new InvalidParameterException("The agent can't be null.");
        }

        this.agent = agent;
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
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getFrom(), PrivateKeyType.ACTIVE);
    }
}
