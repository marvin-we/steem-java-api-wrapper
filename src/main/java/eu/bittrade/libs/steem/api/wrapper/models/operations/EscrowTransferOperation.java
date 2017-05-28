package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

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
