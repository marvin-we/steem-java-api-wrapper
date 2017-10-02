package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "escrow_release_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowReleaseOperation extends AbstractEscrowOperation {
    @JsonProperty("who")
    private AccountName who;
    @JsonProperty("receiver")
    private AccountName receiver;
    @JsonProperty("sbd_amount")
    private Asset sbdAmount;
    @JsonProperty("steem_amount")
    private Asset steemAmount;

    /**
     * Create a new escrow release operation. This operation can be used by
     * anyone associated with the escrow transfer to release funds if they have
     * permission.
     *
     * <p>
     * The permission scheme is as follows:
     * <ul>
     * <li>If there is no dispute and escrow has not expired, either party can
     * release funds to the other.</li>
     * <li>If escrow expires and there is no dispute, either party can release
     * funds to either party.</li>
     * <li>If there is a dispute regardless of expiration, the agent can release
     * funds to either party following whichever agreement was in place between
     * the parties.</li>
     * </ul>
     * <p>
     * 
     * @param from
     * @param to
     * @param agent
     * @param escrowId
     * @param who
     * @param receiver
     * @param sbdAmount
     * @param steemAmount
     * @throws InvalidParameterException
     *             If one of the arguemnts does not fulfill the requirements.
     */
    @JsonCreator
    public EscrowReleaseOperation(@JsonProperty("from") AccountName from, @JsonProperty("to") AccountName to,
            @JsonProperty("agent") AccountName agent, @JsonProperty("escrow_id") long escrowId,
            @JsonProperty("who") AccountName who, @JsonProperty("receiver") AccountName receiver,
            @JsonProperty("sbd_amount") Asset sbdAmount, @JsonProperty("steem_amount") Asset steemAmount) {
        super(false);

        if (from == null || to == null || agent == null || who == null
                || (!who.equals(agent) && !who.equals(to) && !who.equals(from))) {
            throw new InvalidParameterException(
                    "The who account must be the from account or the to account or the agent account.");
        }

        if (sbdAmount == null || steemAmount == null || (sbdAmount.getAmount() + steemAmount.getAmount() <= 0)) {
            throw new InvalidParameterException("An escrow must transfer a non-zero amount.");
        }

        this.setFrom(from);
        this.setTo(to);
        this.setAgent(agent);
        this.setEscrowId(escrowId);
        this.setWho(who);
        this.setSbdAmount(sbdAmount);
        this.setSteemAmount(steemAmount);
    }

    /**
     * 
     * @param from
     * @param to
     * @param agent
     * @param escrowId
     * @param who
     * @param receiver
     * @throws InvalidParameterException
     *             If one of the arguemnts does not fulfill the requirements.
     */
    public EscrowReleaseOperation(AccountName from, AccountName to, AccountName agent, long escrowId, AccountName who,
            AccountName receiver) {
        this(from, to, agent, escrowId, who, receiver, new Asset(0, AssetSymbolType.SBD),
                new Asset(0, AssetSymbolType.STEEM));
    }

    /**
     * 
     * @param from
     * @param to
     * @param agent
     * @param who
     * @param receiver
     * @throws InvalidParameterException
     *             If one of the arguemnts does not fulfill the requirements.
     */
    public EscrowReleaseOperation(AccountName from, AccountName to, AccountName agent, AccountName who,
            AccountName receiver) {
        this(from, to, agent, 30, who, receiver);
    }

    /**
     * Set the account who wants to transfer the fund to the {@link #getTo() to}
     * account. <b>Notice:</b> The private active key of this account needs to
     * be stored in the key storage.
     * 
     * @param from
     *            The account who wants to transfer the fund.
     * @throws InvalidParameterException
     *             If the <code>from</code> is null or is equal to the
     *             <code>agent</code> or <code>to</code> account.
     */
    @Override
    public void setFrom(AccountName from) {
        if (from == null) {
            throw new InvalidParameterException("The from account can't be null.");
        } else if (!this.getWho().equals(this.getAgent()) && !this.getWho().equals(this.getTo())
                && !this.getWho().equals(from)) {
            throw new InvalidParameterException(
                    "The who account must be the from account or the to account or the agent account.");
        } else if (!this.getReceiver().equals(from) && !this.getReceiver().equals(this.getTo())) {
            throw new InvalidParameterException("The receiver account must be from or to.");
        }

        this.from = from;
    }

    /**
     * Set the account who should receive the funds.
     * 
     * @param to
     *            The account who should receive the funds.
     * @throws InvalidParameterException
     *             If the <code>to</code> is null or is not equal to the
     *             <code>agent</code> or <code>from</code> account.
     */
    @Override
    public void setTo(AccountName to) {
        if (to == null) {
            throw new InvalidParameterException("The to account can't be null.");
        } else if (!this.getWho().equals(this.getAgent()) && !this.getWho().equals(to)
                && !this.getWho().equals(this.getFrom())) {
            throw new InvalidParameterException(
                    "The who account must be the from account or the to account or the agent account.");
        } else if (!this.getReceiver().equals(this.getFrom()) && !this.getReceiver().equals(to)) {
            throw new InvalidParameterException("The receiver account must be from or to.");
        }

        this.to = to;
    }

    /**
     * Set the agent account.
     * 
     * @param agent
     *            The agent account.
     * @throws InvalidParameterException
     *             If the <code>agent</code> is null or not equal to the
     *             <code>agent</code> or <code>to</code> or <code>from</code>
     *             account.
     */
    @Override
    public void setAgent(AccountName agent) {
        if (agent == null) {
            throw new InvalidParameterException("The agent can't be null.");
        } else if (!this.getWho().equals(agent) && !this.getWho().equals(this.getTo())
                && !this.getWho().equals(this.getFrom())) {
            throw new InvalidParameterException(
                    "The who account must be the from account or the to account or the agent account.");
        }

        this.agent = agent;
    }

    /**
     * Get the account that is attempting to release the funds.
     * 
     * @return The account that is attempting to release the funds.
     */
    public AccountName getWho() {
        return who;
    }

    /**
     * Set the account that is attempting to release the funds, determines valid
     * 'receiver'.
     * 
     * @param who
     *            The account that is attempting to release the funds.
     * @throws InvalidParameterException
     *             If the <code>who</code> is null or not one of
     *             {@link #getAgent()} and {@link #getFrom()} account.
     */
    public void setWho(AccountName who) {
        if (who == null) {
            throw new InvalidParameterException("The who account can't be null.");
        } else if (!who.equals(this.getFrom()) && !who.equals(this.getAgent())) {
            throw new InvalidParameterException("The who account must be the from or the agent account.");
        }

        this.who = who;
    }

    /**
     * Get the account that has received funds (might be from, might be to).
     * 
     * @return The account that has received funds.
     */
    public AccountName getReceiver() {
        return receiver;
    }

    /**
     * Set the account that should receive funds (might be from, might be to).
     * 
     * @param receiver
     *            The account that should receive funds.
     * @throws InvalidParameterException
     *             If the <code>receiver</code> is null or is not equal to the
     *             {@link #getFrom()} account or the {@link #getTo()} account.
     */
    public void setReceiver(AccountName receiver) {
        if (receiver == null) {
            throw new InvalidParameterException("The receiver account can't be null.");
        } else if (!receiver.equals(this.getFrom()) && !receiver.equals(this.getTo())) {
            throw new InvalidParameterException("The receiver account must be the from or the to account.");
        }

        this.receiver = receiver;
    }

    /**
     * Get the amount of SBD to release.
     * 
     * @return The amount of SBD to release.
     */
    public Asset getSbdAmount() {
        return sbdAmount;
    }

    /**
     * Set the amount of SBD to release.
     * 
     * @param sbdAmount
     *            The amount of SBD to release.
     * @throws InvalidParameterException
     *             If the <code>sbdAmount</code> is null, has a negative amount,
     *             has a different symbol type than SBD or if the
     *             <code>sbdAmount</code> <b>and</b> {@link #getSteemAmount()}
     *             both have an amount of 0.
     */
    public void setSbdAmount(Asset sbdAmount) {
        if (sbdAmount == null) {
            throw new InvalidParameterException("The sbd amount can't be null.");
        } else if (sbdAmount.getAmount() < 0) {
            throw new InvalidParameterException("The sbd amount cannot be negative.");
        } else if (!sbdAmount.getSymbol().equals(AssetSymbolType.SBD)) {
            throw new InvalidParameterException("The sbd amount must contain SBD.");
        } else if (sbdAmount.getAmount() + this.getSteemAmount().getAmount() < 0) {
            throw new InvalidParameterException("An escrow must release a non-zero amount.");
        }

        this.sbdAmount = sbdAmount;
    }

    /**
     * Get the amount of STEEM to release.
     * 
     * @return The amount of STEEM to release.
     */
    public Asset getSteemAmount() {
        return steemAmount;
    }

    /**
     * Set the amount of STEEM to release.
     * 
     * @param steemAmount
     *            The amount of STEEM to release.
     * @throws InvalidParameterException
     *             If the <code>steemAmount</code> is null, has a negative
     *             amount, has a different symbol type than STEEM or if the
     *             <code>steemAmount</code> <b>and</b> {@link #getSbdAmount()}
     *             both have an amount of 0.
     */
    public void setSteemAmount(Asset steemAmount) {
        if (steemAmount == null) {
            throw new InvalidParameterException("The steem amount can't be null.");
        } else if (steemAmount.getAmount() < 0) {
            throw new InvalidParameterException("The steem amount cannot be negative.");
        } else if (!steemAmount.getSymbol().equals(AssetSymbolType.SBD)) {
            throw new InvalidParameterException("The steem amount must contain STEEM.");
        } else if (steemAmount.getAmount() + this.getSbdAmount().getAmount() < 0) {
            throw new InvalidParameterException("An escrow must release a non-zero amount.");
        }

        this.steemAmount = steemAmount;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedEscrowReleaseOperation = new ByteArrayOutputStream()) {
            serializedEscrowReleaseOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.ESCROW_RELEASE_OPERATION.ordinal()));
            serializedEscrowReleaseOperation.write(this.getFrom().toByteArray());
            serializedEscrowReleaseOperation.write(this.getTo().toByteArray());
            serializedEscrowReleaseOperation.write(this.getAgent().toByteArray());
            serializedEscrowReleaseOperation.write(this.getWho().toByteArray());
            serializedEscrowReleaseOperation.write(this.getReceiver().toByteArray());
            serializedEscrowReleaseOperation.write(SteemJUtils.transformIntToByteArray(this.getEscrowId()));
            serializedEscrowReleaseOperation.write(this.getSbdAmount().toByteArray());
            serializedEscrowReleaseOperation.write(this.getSteemAmount().toByteArray());

            return serializedEscrowReleaseOperation.toByteArray();
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
