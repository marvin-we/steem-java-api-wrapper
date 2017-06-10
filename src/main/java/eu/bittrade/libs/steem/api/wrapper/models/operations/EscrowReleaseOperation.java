package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "escrow_release_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowReleaseOperation extends Operation {
    private AccountName from;
    private AccountName to;
    private AccountName agent;
    private AccountName who;
    private AccountName receiver;
    // Original type is unit32_t so we use long here.
    @JsonProperty("escrow_id")
    private long escrowId;
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
     */
    public EscrowReleaseOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
        // Apply default values:
        this.setEscrowId(30);

        Asset sbdAmount = new Asset();
        sbdAmount.setAmount(0);
        sbdAmount.setSymbol(AssetSymbolType.SBD);
        this.setSbdAmount(sbdAmount);

        Asset steemAmount = new Asset();
        steemAmount.setAmount(0);
        steemAmount.setSymbol(AssetSymbolType.STEEM);
        this.setSbdAmount(steemAmount);
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
     */
    public void setWho(AccountName who) {
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
     */
    public void setReceiver(AccountName receiver) {
        this.receiver = receiver;
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
     */
    public void setSbdAmount(Asset sbdAmount) {
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
     */
    public void setSteemAmount(Asset steemAmount) {
        this.steemAmount = steemAmount;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedEscrowReleaseOperation = new ByteArrayOutputStream()) {
            serializedEscrowReleaseOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.ESCROW_APPROVE_OPERATION.ordinal()));
            serializedEscrowReleaseOperation.write(this.getFrom().toByteArray());
            serializedEscrowReleaseOperation.write(this.getTo().toByteArray());
            serializedEscrowReleaseOperation.write(this.getAgent().toByteArray());
            serializedEscrowReleaseOperation.write(this.getWho().toByteArray());
            serializedEscrowReleaseOperation.write(this.getReceiver().toByteArray());
            serializedEscrowReleaseOperation.write(SteemJUtils.transformLongToByteArray(this.getEscrowId()));
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
