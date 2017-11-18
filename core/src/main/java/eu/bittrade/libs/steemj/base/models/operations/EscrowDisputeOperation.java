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
 * This class represents the Steem "escrow_dispute_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowDisputeOperation extends AbstractEscrowOperation {
    @JsonProperty("who")
    private AccountName who;

    /**
     * Create a new escrow dispute operation.
     * 
     * If either the sender or receiver of an escrow payment has an issue, they
     * can raise it for dispute. Once a payment is in dispute, the agent has
     * authority over who gets what.
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
     *            The account who disputes the escrow operation (see
     *            {@link #setWho(AccountName)}).
     * @throws InvalidParameterException
     *             If one of the arguemnts does not fulfill the requirements.
     */
    @JsonCreator
    public EscrowDisputeOperation(@JsonProperty("from") AccountName from, @JsonProperty("to") AccountName to,
            @JsonProperty("agent") AccountName agent, @JsonProperty("escrow_id") long escrowId,
            @JsonProperty("who") AccountName who) {
        super(false);

        this.setFrom(from);
        this.setTo(to);
        this.setAgent(agent);
        this.setEscrowId(escrowId);
        this.setWho(who);
    }

    /**
     * Like
     * {@link #EscrowDisputeOperation(AccountName, AccountName, AccountName, long, AccountName)},
     * but sets the <code>escrowId</code> to its default value (30).
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
     * @param who
     *            The account who disputes the escrow operation (see
     *            {@link #setWho(AccountName)}).
     * @throws InvalidParameterException
     *             If one of the arguemnts does not fulfill the requirements.
     */
    public EscrowDisputeOperation(AccountName from, AccountName to, AccountName agent, AccountName who) {
        this(from, to, agent, 30, who);
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
     * Set the account who disputes this operation. This can either be the
     * {@link #getTo() to} account or the {@link #getAgent() agent} account.
     * 
     * @param who
     *            The account which disputes this operation.
     * @throws InvalidParameterException
     *             If the <code>who</code> is null.
     */
    public void setWho(AccountName who) {
        this.who = setIfNotNull(who, "The who account can't be null.");
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedEscrowDisputeOperation = new ByteArrayOutputStream()) {
            serializedEscrowDisputeOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.ESCROW_DISPUTE_OPERATION.getOrderId()));
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

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getWho(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType) && (!who.equals(from) || !who.equals(to))) {
            throw new InvalidParameterException("The who account must be either the from account or the to account.");
        }
    }
}
