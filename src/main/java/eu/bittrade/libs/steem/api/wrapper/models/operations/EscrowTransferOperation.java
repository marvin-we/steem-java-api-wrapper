package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;

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
    // TODO: Handle Timestamp.
    @JsonProperty("ratification_deadline")
    private long ratificationDeadline;
    // TODO: Handle Timestamp.
    @JsonProperty("escrow_expiration")
    private long escrowExpiration;
    @JsonProperty("json_meta")
    private String jsonMeta;

    public EscrowTransferOperation() {
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

    /**
     * @return the fee
     */
    public Asset getFee() {
        return fee;
    }

    /**
     * @param fee
     *            the fee to set
     */
    public void setFee(Asset fee) {
        this.fee = fee;
    }

    /**
     * @return the ratificationDeadline
     */
    public long getRatificationDeadline() {
        return ratificationDeadline;
    }

    /**
     * @param ratificationDeadline
     *            the ratificationDeadline to set
     */
    public void setRatificationDeadline(long ratificationDeadline) {
        this.ratificationDeadline = ratificationDeadline;
    }

    /**
     * @return the escrowExpiration
     */
    public long getEscrowExpiration() {
        return escrowExpiration;
    }

    /**
     * @param escrowExpiration
     *            the escrowExpiration to set
     */
    public void setEscrowExpiration(long escrowExpiration) {
        this.escrowExpiration = escrowExpiration;
    }

    /**
     * @return the jsonMeta
     */
    public String getJsonMeta() {
        return jsonMeta;
    }

    /**
     * @param jsonMeta
     *            the jsonMeta to set
     */
    public void setJsonMeta(String jsonMeta) {
        this.jsonMeta = jsonMeta;
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
