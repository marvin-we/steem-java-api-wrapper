package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

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
    private long ratificationDeadlineDate;
    @JsonProperty("escrow_expiration")
    private long escrowExpirationDate;
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
     * This method returns the ratification deadline as its String
     * representation. For this a specific date format ("yyyy-MM-dd'T'HH:mm:ss")
     * is used as it is required by the Steem api.
     * 
     * @return The the ratification deadline as String.
     */
    public String getRatificationDeadlineDate() {
        return SteemJUtils.transformDateToString(getRatificationDeadlineDateAsDate());
    }

    /**
     * Get the configured the ratification deadline as a Java.util.Date object.
     * 
     * @return The ratification deadline date.
     */
    @JsonIgnore
    public Date getRatificationDeadlineDateAsDate() {
        return new Date(ratificationDeadlineDate);
    }

    /**
     * This method returns the ratification deadline as its int representation.
     * 
     * @return The ratification deadline date.
     */
    @JsonIgnore
    public int getRatificationDeadlineDateAsInt() {
        return (int) (ratificationDeadlineDate / 1000);
    }

    /**
     * Define the ratification deadline. The date has to be specified as String
     * and needs a special format: yyyy-MM-dd'T'HH:mm:ss
     * 
     * <p>
     * Example: "2016-08-08T12:24:17"
     * </p>
     * 
     * @param ratificationDeadlineDate
     *            The ratification deadline as its String representation.
     * @throws ParseException
     *             If the given String does not match the pattern.
     */
    public void setRatificationDeadlineDate(String ratificationDeadlineDate) throws ParseException {
        this.setEscrowExpirationDate(SteemJUtils.transformStringToTimestamp(ratificationDeadlineDate));
    }

    /**
     * Set the ratification deadline by providing a timestamp.
     * 
     * @param ratificationDeadlineDate
     *            The ratification deadline as a timestamp.
     */
    @JsonIgnore
    public void setRatificationDeadlineDate(long ratificationDeadlineDate) {
        this.ratificationDeadlineDate = ratificationDeadlineDate;
    }

    /**
     * This method returns the escrow expiration date as its String
     * representation. For this a specific date format ("yyyy-MM-dd'T'HH:mm:ss")
     * is used as it is required by the Steem api.
     * 
     * @return The escrow expiration date as String.
     */
    public String getEscrowExpirationDate() {
        return SteemJUtils.transformDateToString(getEscrowExpirationDateAsDate());
    }

    /**
     * Get the configured escrow expiration date as a Java.util.Date object.
     * 
     * @return The escrow expiration date.
     */
    @JsonIgnore
    public Date getEscrowExpirationDateAsDate() {
        return new Date(escrowExpirationDate);
    }

    /**
     * This method returns the escrow expiration data as its int representation.
     * 
     * @return The escrow expiration date.
     */
    @JsonIgnore
    public int getEscrowExpirationDateAsInt() {
        return (int) (escrowExpirationDate / 1000);
    }

    /**
     * Define how long this escrow transfer is valid. The date has to be
     * specified as String and needs a special format: yyyy-MM-dd'T'HH:mm:ss
     * 
     * <p>
     * Example: "2016-08-08T12:24:17"
     * </p>
     * 
     * @param escrowExpirationDate
     *            The escrow expiration date as its String representation.
     * @throws ParseException
     *             If the given String does not match the pattern.
     */
    public void setEscrowExpirationDate(String escrowExpirationDate) throws ParseException {
        this.setEscrowExpirationDate(SteemJUtils.transformStringToTimestamp(escrowExpirationDate));
    }

    /**
     * Set the escrow expiration date by providing a timestamp.
     * 
     * @param escrowExpirationDate
     *            The escrow expiration date as a timestamp.
     */
    @JsonIgnore
    public void setEscrowExpirationDate(long escrowExpirationDate) {
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
            serializedEscrowTransferOperation.write(SteemJUtils.transformLongToByteArray(this.getEscrowId()));
            serializedEscrowTransferOperation.write(this.getSbdAmount().toByteArray());
            serializedEscrowTransferOperation.write(this.getSteemAmount().toByteArray());
            serializedEscrowTransferOperation.write(this.getFee().toByteArray());
            serializedEscrowTransferOperation
                    .write(SteemJUtils.transformIntToByteArray(this.getRatificationDeadlineDateAsInt()));
            serializedEscrowTransferOperation
                    .write(SteemJUtils.transformIntToByteArray(this.getEscrowExpirationDateAsInt()));
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
