package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
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
     *             If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public EscrowReleaseOperation(@JsonProperty("from") AccountName from, @JsonProperty("to") AccountName to,
            @JsonProperty("agent") AccountName agent, @JsonProperty("escrow_id") long escrowId,
            @JsonProperty("who") AccountName who, @JsonProperty("receiver") AccountName receiver,
            @JsonProperty("sbd_amount") Asset sbdAmount, @JsonProperty("steem_amount") Asset steemAmount) {
        super(false);

        this.setFrom(from);
        this.setTo(to);
        this.setAgent(agent);
        this.setEscrowId(escrowId);
        this.setWho(who);
        this.setReceiver(receiver);
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
     *             If one of the arguments does not fulfill the requirements.
     */
    public EscrowReleaseOperation(AccountName from, AccountName to, AccountName agent, long escrowId, AccountName who,
            AccountName receiver) {
        this(from, to, agent, escrowId, who, receiver, new Asset(0, SteemJConfig.getInstance().getDollarSymbol()),
                new Asset(0, SteemJConfig.getInstance().getTokenSymbol()));
    }

    /**
     * 
     * @param from
     * @param to
     * @param agent
     * @param who
     * @param receiver
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
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
        this.from = setIfNotNull(from, "The from account can't be null.");
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
        this.to = setIfNotNull(to, "The to account can't be null.");
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
        this.agent = setIfNotNull(agent, "The agent can't be null.");
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
        this.who = setIfNotNull(who, "The who account can't be null.");
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
        this.receiver = setIfNotNull(receiver, "The receiver account can't be null.");
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
        this.sbdAmount = setIfNotNull(sbdAmount, "The sbd amount can't be null.");
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
        this.steemAmount = setIfNotNull(steemAmount, "The steem amount can't be null.");
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedEscrowReleaseOperation = new ByteArrayOutputStream()) {
            serializedEscrowReleaseOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.ESCROW_RELEASE_OPERATION.getOrderId()));
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

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getWho(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)) {
            if (!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)) {
                if (steemAmount.getAmount() < 0) {
                    throw new InvalidParameterException("The steem amount cannot be negative.");
                } else if (!steemAmount.getSymbol().equals(SteemJConfig.getInstance().getTokenSymbol())) {
                    throw new InvalidParameterException("The steem amount must contain STEEM.");
                } else if (sbdAmount.getAmount() < 0) {
                    throw new InvalidParameterException("The sbd amount cannot be negative.");
                } else if (!sbdAmount.getSymbol().equals(SteemJConfig.getInstance().getDollarSymbol())) {
                    throw new InvalidParameterException("The sbd amount must contain SBD.");
                } else if (sbdAmount.getAmount() + steemAmount.getAmount() < 0) {
                    throw new InvalidParameterException("An escrow must release a non-zero amount.");
                }
            }

            if (!who.equals(from) && !who.equals(to) && !who.equals(agent)) {
                throw new InvalidParameterException(
                        "The who account must be either the from account, the to account or the agent account.");
            } else if (!receiver.equals(from) && receiver.equals(to)) {
                throw new InvalidParameterException(
                        "The receiver account must be either the from account or the to account.");
            }
        }
    }
}
