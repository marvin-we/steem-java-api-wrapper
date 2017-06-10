package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;

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
     * the original 'to'
     * 
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
     * @return the who
     */
    public AccountName getWho() {
        return who;
    }

    /**
     * the account that is attempting to release the funds, determines valid
     * 'receiver'
     * 
     * @param who
     *            the who to set
     */
    public void setWho(AccountName who) {
        this.who = who;
    }

    /**
     * the account that should receive funds (might be from, might be to)
     * 
     * @return the receiver
     */
    public AccountName getReceiver() {
        return receiver;
    }

    /**
     * @param receiver
     *            the receiver to set
     */
    public void setReceiver(AccountName receiver) {
        this.receiver = receiver;
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
     * the amount of sbd to release
     * 
     * @return the sbdAmount
     */
    public Asset getSbdAmount() {
        return sbdAmount;
    }

    /**
     * @param sbdAmount
     *            the sbdAmount to set
     */
    public void setSbdAmount(Asset sbdAmount) {
        this.sbdAmount = sbdAmount;
    }

    /**
     * the amount of steem to release
     * 
     * @return the steemAmount
     */
    public Asset getSteemAmount() {
        return steemAmount;
    }

    /**
     * @param steemAmount
     *            the steemAmount to set
     */
    public void setSteemAmount(Asset steemAmount) {
        this.steemAmount = steemAmount;
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
