package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "escrow_transfer_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EscrowTransferOperation extends Operation {
    private AccountName from;
    private AccountName to;
    private AccountName agent;
    // Original type is unit32_t so we use long here.
    @JsonProperty("escrow_id")
    private long escrowId;
    @JsonProperty("sbd_amount")
    private Asset sbdAmount;
    @JsonProperty("steem_amount")
    private Asset steemAmount;
    private Asset fee;
    @JsonProperty("ratification_deadline")
    private TimePointSec ratificationDeadlineDate;
    @JsonProperty("escrow_expiration")
    private TimePointSec escrowExpirationDate;
    @JsonProperty("json_meta")
    private String jsonMeta;

    /**
     * Create a new escrow transfer operation.
     * 
     * The purpose of this operation is to enable someone to send money
     * contingently to another individual. The funds leave the {@link #from
     * from} account and go into a temporary balance where they are held until
     * {@link #from from} releases it to {@link #to to} or {@link #to to}
     * refunds it to {@link #from from}.
     *
     * In the event of a dispute the {@link #agent agent} can divide the funds
     * between the to/from account. Disputes can be raised any time before or on
     * the dispute deadline time, after the escrow has been approved by all
     * parties.
     *
     * This operation only creates a proposed escrow transfer. Both the
     * {@link #agent agent} and {@link #to to} must agree to the terms of the
     * arrangement by approving the escrow.
     *
     * The escrow agent is paid the fee on approval of all parties. It is up to
     * the escrow agent to determine the fee.
     *
     * Escrow transactions are uniquely identified by 'from' and 'escrow_id',
     * the 'escrow_id' is defined by the sender.
     */
    public EscrowTransferOperation() {
        super(false);
        // Apply default values:
        this.setEscrowId(30);

        Asset initialSbdAmount = new Asset();
        initialSbdAmount.setAmount(0);
        initialSbdAmount.setSymbol(AssetSymbolType.SBD);
        this.setSbdAmount(initialSbdAmount);

        Asset initialSteemAmount = new Asset();
        initialSteemAmount.setAmount(0);
        initialSteemAmount.setSymbol(AssetSymbolType.STEEM);
        this.setSbdAmount(initialSteemAmount);
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
     * Get the SDB amount that has been transfered.
     * 
     * @return The SDB amount that has been transfered.
     */
    public Asset getSbdAmount() {
        return sbdAmount;
    }

    /**
     * Set the SDB amount that should be transfered.
     * 
     * @param sbdAmount
     *            The SDB amount that has been transfered.
     */
    public void setSbdAmount(Asset sbdAmount) {
        this.sbdAmount = sbdAmount;
    }

    /**
     * Get the STEEM amount that has been transfered.
     * 
     * @return The STEEM amount that has been transfered.
     */
    public Asset getSteemAmount() {
        return steemAmount;
    }

    /**
     * Set the STEEM amount that has been transfered.
     * 
     * @param steemAmount
     *            The STEEM amount that has been transfered.
     */
    public void setSteemAmount(Asset steemAmount) {
        this.steemAmount = steemAmount;
    }

    /**
     * Get the fee that has been paid to the agent.
     * 
     * @return The fee that will be paid to the agent.
     */
    public Asset getFee() {
        return fee;
    }

    /**
     * Set the fee that will be paid to the agent.
     * 
     * @param fee
     *            The fee that will be paid to the agent.
     */
    public void setFee(Asset fee) {
        this.fee = fee;
    }

    /**
     * TODO:
     * 
     * @return the ratificationDeadlineDate
     */
    public TimePointSec getRatificationDeadlineDate() {
        return ratificationDeadlineDate;
    }

    /**
     * TODO:
     * 
     * @param ratificationDeadlineDate
     *            the ratificationDeadlineDate to set
     */
    public void setRatificationDeadlineDate(TimePointSec ratificationDeadlineDate) {
        this.ratificationDeadlineDate = ratificationDeadlineDate;
    }

    /**
     * TODO:
     * 
     * @return the escrowExpirationDate
     */
    public TimePointSec getEscrowExpirationDate() {
        return escrowExpirationDate;
    }

    /**
     * TODO:
     * 
     * @param escrowExpirationDate
     *            the escrowExpirationDate to set
     */
    public void setEscrowExpirationDate(TimePointSec escrowExpirationDate) {
        this.escrowExpirationDate = escrowExpirationDate;
    }

    /**
     * Get the json metadata that has been added to this operation.
     * 
     * @return The json metadata that has been added to this operation.
     */
    public String getJsonMeta() {
        return jsonMeta;
    }

    /**
     * Set the json metadata that has been added to this operation.
     * 
     * @param jsonMeta
     *            The json metadata that has been added to this operation.
     */
    public void setJsonMeta(String jsonMeta) {
        this.jsonMeta = jsonMeta;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedEscrowTransferOperation = new ByteArrayOutputStream()) {
            serializedEscrowTransferOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.ESCROW_TRANSFER_OPERATION.ordinal()));
            serializedEscrowTransferOperation.write(this.getFrom().toByteArray());
            serializedEscrowTransferOperation.write(this.getTo().toByteArray());
            serializedEscrowTransferOperation.write(this.getAgent().toByteArray());
            serializedEscrowTransferOperation.write(SteemJUtils.transformIntToByteArray(this.getEscrowId()));
            serializedEscrowTransferOperation.write(this.getSbdAmount().toByteArray());
            serializedEscrowTransferOperation.write(this.getSteemAmount().toByteArray());
            serializedEscrowTransferOperation.write(this.getFee().toByteArray());
            serializedEscrowTransferOperation.write(this.getRatificationDeadlineDate().toByteArray());
            serializedEscrowTransferOperation.write(this.getEscrowExpirationDate().toByteArray());
            serializedEscrowTransferOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getJsonMeta()));

            return serializedEscrowTransferOperation.toByteArray();
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
